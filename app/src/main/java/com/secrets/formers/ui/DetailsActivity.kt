package com.secrets.formers.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.secrets.formers.R
import com.secrets.formers.databinding.ActivityDetailsBinding
import com.secrets.formers.utils.getFromExtra

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val SELLER_NAME = "sellerName"
        const val WEIGHT = "weight"
        const val AMOUNT = "amount"
        fun start(context: Context, bundle: Bundle) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindData()
        listeners()
    }

    private fun bindData() {
        binding.apply {
            val seller = intent.getFromExtra(SELLER_NAME)
            thankuTv.text = resources.getString(R.string.thank_u_seller).replace("{seller}", seller)

            val amount = intent.getFromExtra(AMOUNT)
            val weight = intent.getFromExtra(WEIGHT)
            subtitleTv.text = resources.getString(R.string.please_ensuure).replace("{amount}", amount)
                .replace("{weight}", weight)
        }
    }

    private fun listeners() {
        binding.sellMoreBtn.setOnClickListener {
            onBackPressed()
        }
    }
}