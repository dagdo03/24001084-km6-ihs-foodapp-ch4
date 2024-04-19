package com.example.foodapp.presentation.checkout

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.foodapp.R
import com.example.foodapp.data.datasource.cart.CartDataSource
import com.example.foodapp.data.datasource.cart.CartDatabaseDataSource
import com.example.foodapp.data.datasource.menu.MenuApiDataSource
import com.example.foodapp.data.datasource.menu.MenuDataSource
import com.example.foodapp.data.repository.CartRepository
import com.example.foodapp.data.repository.CartRepositoryImpl
import com.example.foodapp.data.repository.MenuRepository
import com.example.foodapp.data.repository.MenuRepositoryImpl
import com.example.foodapp.data.source.local.database.AppDatabase
import com.example.foodapp.data.source.network.services.FoodAppApiService
import com.example.foodapp.databinding.ActivityCheckoutBinding
import com.example.foodapp.presentation.checkout.adapter.PriceListAdapter
import com.example.foodapp.presentation.common.adapter.CartListAdapter
import com.example.foodapp.utils.GenericViewModelFactory
import com.example.foodapp.utils.proceedWhen
import com.example.foodapp.utils.toIndonesianFormat

class CheckoutActivity : AppCompatActivity() {
    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }
    private val viewModel: CheckoutViewModel by viewModels {
        val service = FoodAppApiService.invoke()
        val db = AppDatabase.getInstance(this)
        val menuDataSource: MenuDataSource = MenuApiDataSource(service)
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(CheckoutViewModel(rp, menuRepository))
    }
    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }
    private val priceItemAdapter: PriceListAdapter by lazy {
        PriceListAdapter {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setClickListeners()

    }

    private fun setClickListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnCheckout.setOnClickListener {
            viewModel.checkoutCart()
            observeCheckoutResult()
        }
    }


    private fun setupList() {
        binding.layoutContent.rvCart.adapter = adapter
        binding.layoutContent.rvShoppingSummary.adapter = priceItemAdapter
    }

    private fun observeData() {
        viewModel.checkoutData.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = true
                binding.layoutContent.rvCart.isVisible = true
                binding.cvSectionOrder.isVisible = true
                result.payload?.let { (carts, priceItems, totalPrice) ->
                    adapter.submitData(carts)
                    binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    priceItemAdapter.submitData(priceItems)
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
            }, doOnEmpty = { data ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                data.payload?.let { (_, _, totalPrice) ->
                    binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                }
                binding.layoutContent.root.isVisible = false
                binding.layoutContent.rvCart.isVisible = false
                binding.cvSectionOrder.isVisible = false
            })
        }
    }

    private fun observeCheckoutResult() {
        viewModel.checkoutCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    dialogCheckoutSuccess(this)
                },
                doOnError = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    Toast.makeText(
                        this,
                        getString(R.string.text_checkout_error),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                }
            )
        }
    }

    private fun dialogCheckoutSuccess(context: Context) {
        val dialogView: View = LayoutInflater.from(context).inflate(R.layout.dialog_checkout, null)
        val finishBtn = dialogView.findViewById<Button>(R.id.btn_back_home)
        val alertDialogBuilder = AlertDialog.Builder(context)
        val dialog = alertDialogBuilder.create()
        alertDialogBuilder.setView(dialogView)
        finishBtn.setOnClickListener {
            viewModel.removeAllCart()
            (context as? Activity)?.finish() // Make sure context is an Activity
            dialog.dismiss()
        }
        alertDialogBuilder.show()
    }

}