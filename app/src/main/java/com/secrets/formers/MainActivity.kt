package com.secrets.formers

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.secrets.formers.DetailsActivity.Companion.AMOUNT
import com.secrets.formers.DetailsActivity.Companion.SELLER_NAME
import com.secrets.formers.DetailsActivity.Companion.WEIGHT
import com.secrets.formers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initViewModel()
        initSpinner()
        observers()
        listeners()
    }

    private lateinit var viewModel: MandiViewModel
    private fun initViewModel() {
        viewModel = ViewModelProvider(this, MandiViewModelFactory(MandiRepo())).get(MandiViewModel::class.java)
    }

    private fun observers() {
        viewModel.selectedSeller.observe(this) {
            populateViews(it)
        }
        viewModel.selectedVillage.observe(this) {
            calculateAmount()
        }
    }

    private fun populateViews(sellerModel: SellerModel?) {
        sellerModel?.let {
            binding.sellerNameEt.setText(it.sellerName)
            binding.loyaltyEt.setText(it.loyaltyId)
        }
        calculateAmount()
    }

    private var amount = 0f
    private var weight = 0f
    private var loyaltyPoints = 0f
    private var villagePrice = 0f
    private fun calculateAmount() {
        val villageModel = viewModel.selectedVillage.value
        val sellerModel = viewModel.selectedSeller.value

        loyaltyPoints = sellerModel?.points ?: 0.98f
        villagePrice = villageModel?.price ?: 0.0f
        weight = if (!binding.weightEt.text.isNullOrBlank()) binding.weightEt.text.toString().toFloat() else 0f

        // villagePrice is of per kg,
        if (binding.unitSp.selectedItem.toString() == "Tonnes") weight *= 1000
        if (binding.unitSp.selectedItem.toString() == "Kilos") weight /= 1000


        amount =  loyaltyPoints * villagePrice * weight
        if (amount > 0) {
            setAmountTv(String.format("%.2f", amount))
            setRoyaltyTv(loyaltyPoints.toString())
        }
    }

    private fun isValid(): Boolean {

        if (villagePrice == 0.0f || viewModel.selectedVillage.value == null) {
            Toast.makeText(this, "Enter valid village", Toast.LENGTH_SHORT).show()
            return false
        }
        if (binding.sellerNameEt.text.toString().isNullOrBlank()) {
//            Toast.makeText(this, "Enter valid seller", Toast.LENGTH_SHORT).show()
            binding.sellerNameEt.error = "Mandatory"
            return false
        }
        if (weight == 0f) {
//            Toast.makeText(this, "Enter valid weight", Toast.LENGTH_SHORT).show()
            binding.weightEt.error = "Mandatory"
            return false
        }
        return true
    }

    private fun listeners() {
        binding.sellBtn.setOnClickListener {
            if (!isValid()) return@setOnClickListener
            val bundle = Bundle().apply {
                putString(SELLER_NAME, binding.sellerNameEt.text.toString().trim())
                putString(WEIGHT, binding.weightEt.text.toString().trim() + " " + binding.unitSp.selectedItem.toString() )
                putString(AMOUNT, "$amount")
            }
            DetailsActivity.start(this, bundle)
        }

        binding.sellerNameEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.fetchInfoByName(binding.sellerNameEt.text.toString().trim())
                true
            } else false
        }

        binding.loyaltyEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.fetchInfoById(binding.loyaltyEt.text.toString().trim())
                true
            } else false
        }

        binding.weightEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (!v.text.isNullOrBlank()) {
                    weight = v.text.toString().toFloat()
                    calculateAmount()
                }
                true
            } else false
        }

        binding.villageSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.fetchInfoVillageByName(binding.villageSp.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.unitSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                calculateAmount()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun initViews() {
        setRoyaltyTv("--")
        setAmountTv("--")
    }

    private fun setRoyaltyTv(index: String) {
        binding.loyaltyTv.text = resources.getString(R.string.applied_loyalty_index).replace("{index}", index)
    }

    private fun setAmountTv(amount1: String?) {
        binding.totalAmountTv.text = if (amount1.isNullOrBlank()) "--" else "$amount1 INR"
    }

    private fun initSpinner() {
        val list = viewModel.selectedVillage
    }
}