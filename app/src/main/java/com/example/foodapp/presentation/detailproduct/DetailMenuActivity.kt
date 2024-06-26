package com.example.foodapp.presentation.detailproduct

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.foodapp.R
import com.example.foodapp.data.model.Menu
import com.example.foodapp.databinding.ActivityDetailMenuBinding
import com.example.foodapp.utils.proceedWhen
import com.example.foodapp.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailMenuActivity : AppCompatActivity() {
    private var location: String? = ""
    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }
    private val detailMenuViewModel: DetailMenuViewModel by viewModel {
        parametersOf(intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindMenu(detailMenuViewModel.menu)
        setClickListener()
        observeData()
    }

    private fun setClickListener() {
        binding.layoutHeader.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.layoutAddToCart.ivDecrementButton.setOnClickListener {
            detailMenuViewModel.minus()
        }
        binding.layoutAddToCart.ivIncrementButton.setOnClickListener {
            detailMenuViewModel.add()
        }
        binding.layoutLocation.let {
            it.tvLocationDesc.setOnClickListener {
                openMaps()
            }
        }
        binding.layoutAddToCart.bAddToCartButton.setOnClickListener {
            addToCart()
        }
    }

    private fun bindMenu(menu: Menu?) {
        menu?.let { item ->
            binding.layoutHeader.ivBackgroundPictureActivityDetail.load(item.imgURL) {
                crossfade(true)
            }
            binding.layoutDesc.tvMenuTitle.text = item.name
            binding.layoutDesc.tvMenuDesc.text = item.description
            binding.layoutLocation.tvLocationDesc.text = item.location
            binding.layoutDesc.tvMenuPrice.text = item.price.toIndonesianFormat()
            location = item.locationURL
        }
    }

    private fun observeData() {
        detailMenuViewModel.priceLiveData.observe(this) {
            binding.layoutAddToCart.bAddToCartButton.isEnabled = it != 0
            binding.layoutAddToCart.tvTotalPrice.text = it.toIndonesianFormat()
        }
        detailMenuViewModel.menuCountLiveData.observe(this) {
            binding.layoutAddToCart.tvCounterText.text = it.toString()
        }
    }

    private fun addToCart() {
        detailMenuViewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_successfully_add_product_to_cart),
                        Toast.LENGTH_SHORT,
                    ).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_failed_add_product_to_cart),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
            )
        }
    }

    private fun openMaps() {
        val mapsURL = Uri.parse(location)
        val mapIntent = Intent(Intent.ACTION_VIEW, mapsURL)
        startActivity(mapIntent)
    }

    companion object {
        const val EXTRA_MENU = "EXTRA_MENU"

        fun startActivity(
            context: Context,
            menu: Menu,
        ) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRA_MENU, menu)
            context.startActivity(intent)
        }
    }
}
