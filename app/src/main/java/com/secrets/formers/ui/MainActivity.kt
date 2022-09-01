package com.secrets.formers.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.lifecycle.ViewModelProvider
import com.secrets.formers.R
import com.secrets.formers.data.MandiRepo
import com.secrets.formers.data.models.SellerModel
import com.secrets.formers.databinding.ActivityMainBinding
import com.secrets.formers.ui.DetailsActivity.Companion.AMOUNT
import com.secrets.formers.ui.DetailsActivity.Companion.SELLER_NAME
import com.secrets.formers.ui.DetailsActivity.Companion.WEIGHT
import com.secrets.formers.utils.MandiViewModelFactory
import com.secrets.formers.utils.onDoneClick
import com.secrets.formers.utils.onDropDownSelected

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
        if (binding.unitSp.selectedItem.toString() == "Tonnes") villagePrice *= 1000
//        if (binding.unitSp.selectedItem.toString() == "Kilos") weight /= 1000


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
            binding.sellerNameEt.error = "Mandatory"
            return false
        }
        if (weight == 0f) {
            binding.weightEt.error = "Mandatory"
            return false
        }
        return true
    }

    private fun startDetailsActivity() {
        val bundle = Bundle().apply {
            putString(SELLER_NAME, binding.sellerNameEt.text.toString().trim())
            putString(WEIGHT, binding.weightEt.text.toString().trim() + " " + binding.unitSp.selectedItem.toString() )
            putString(AMOUNT, "$amount")
        }
        DetailsActivity.start(this, bundle)
    }

    private fun listeners() {
        binding.sellBtn.setOnClickListener {
            if (!isValid()) return@setOnClickListener
            startDetailsActivity()
        }

        binding.clearTv.setOnClickListener {
            resetViews()
        }

        binding.sellerNameEt.onDoneClick {
            viewModel.fetchInfoByName(binding.sellerNameEt.text.toString().trim())
        }

        binding.loyaltyEt.onDoneClick {
            viewModel.fetchInfoById(binding.loyaltyEt.text.toString().trim())
        }

        binding.sellerNameEt.setOnItemClickListener { parent, view, position, id ->
            viewModel.fetchInfoByName(binding.sellerNameEt.text.toString().trim())
        }
        binding.loyaltyEt.setOnItemClickListener { parent, view, position, id ->
            viewModel.fetchInfoById(binding.loyaltyEt.text.toString().trim())
        }

        binding.weightEt.onDoneClick { v ->
            if (!v.text.isNullOrBlank()) {
                weight = v.text.toString().toFloat()
                calculateAmount()
            }
        }

        binding.villageSp.onDropDownSelected {
            viewModel.fetchInfoVillageByName(binding.villageSp.selectedItem.toString())
        }

        binding.unitSp.onDropDownSelected {
            calculateAmount()
        }
    }

    private fun resetViews() {
        binding.sellerNameEt.text.clear()
        binding.loyaltyEt.text.clear()
        binding.weightEt.text.clear()
        binding.villageSp.setSelection(0)
        binding.unitSp.setSelection(0)

        amount = 0f
        weight = 0f
        loyaltyPoints = 0f
        villagePrice = 0f
        viewModel.resetValues()
        initViews()
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
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, viewModel.getVillagesName())
        binding.villageSp.adapter = arrayAdapter

        setupDropDown(binding.sellerNameEt, viewModel.getSellersNames())
        setupDropDown(binding.loyaltyEt, viewModel.getSellersLoyaltyIds())
    }

    private fun setupDropDown(
        loyaltyEt: AppCompatAutoCompleteTextView,
        sellersLoyaltyIds: MutableList<String>
    ) {
        val sellerLoyaltyIdAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sellersLoyaltyIds)
        loyaltyEt.setAdapter(sellerLoyaltyIdAdapter)
        loyaltyEt.threshold = 1
    }
}