package com.example.foodapp.presentation.detailproduct

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.foodapp.R
import com.example.foodapp.data.datasource.cart.CartDataSource
import com.example.foodapp.data.datasource.cart.CartDatabaseDataSource
import com.example.foodapp.data.model.Menu
import com.example.foodapp.data.repository.CartRepository
import com.example.foodapp.data.repository.CartRepositoryImpl
import com.example.foodapp.data.source.local.database.AppDatabase
import com.example.foodapp.databinding.ActivityDetailMenuBinding
import com.example.foodapp.utils.GenericViewModelFactory
import com.example.foodapp.utils.proceedWhen
import com.example.foodapp.utils.toIndonesianFormat

class DetailMenuActivity : AppCompatActivity() {
    private var location: String? = ""
    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }
    private val viewModel: DetailMenuViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(
            DetailMenuViewModel(intent?.extras, rp)
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindMenu(viewModel.menu)
        setClickListener()
        observeData()
    }

    private fun setClickListener() {
        binding.layoutHeader.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.layoutAddToCart.ivDecrementButton.setOnClickListener {
            viewModel.minus()
        }
        binding.layoutAddToCart.ivIncrementButton.setOnClickListener {
            viewModel.add()
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
        viewModel.priceLiveData.observe(this) {
            binding.layoutAddToCart.bAddToCartButton.isEnabled = it != 0.0
            binding.layoutAddToCart.tvTotalPrice.text = it.toIndonesianFormat()
        }
        viewModel.menuCountLiveData.observe(this) {
            binding.layoutAddToCart.tvCounterText.text = it.toString()
        }
    }

    private fun addToCart() {
        viewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_successfully_add_product_to_cart),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_failed_add_product_to_cart), Toast.LENGTH_SHORT
                    ).show()
                }
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
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRA_MENU, menu)
            context.startActivity(intent)
        }
    }
}