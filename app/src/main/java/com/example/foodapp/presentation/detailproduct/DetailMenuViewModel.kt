package com.example.foodapp.presentation.detailproduct

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.foodapp.R
import com.example.foodapp.data.model.Menu
import com.example.foodapp.data.repository.CartRepository
import com.example.foodapp.utils.AssetWrapper
import com.example.foodapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class DetailMenuViewModel(
    private val extras: Bundle?,
    private val cartRepository: CartRepository,
    private val assetWrapper: AssetWrapper,
) : ViewModel() {
    val menu = extras?.getParcelable<Menu>(DetailMenuActivity.EXTRA_MENU)

    val menuCountLiveData =
        MutableLiveData(0).apply {
            postValue(0)
        }

    val priceLiveData =
        MutableLiveData<Int>().apply {
            postValue(0)
        }

    fun add() {
        val count = (menuCountLiveData.value ?: 0) + 1
        menuCountLiveData.postValue(count)
        priceLiveData.postValue(menu?.price?.times(count) ?: 0)
    }

    fun minus() {
        if ((menuCountLiveData.value ?: 0) > 0) {
            val count = (menuCountLiveData.value ?: 0) - 1
            menuCountLiveData.postValue(count)
            priceLiveData.postValue(menu?.price?.times(count) ?: 0)
        }
    }

    fun addToCart(): LiveData<ResultWrapper<Boolean>> {
        return menu?.let {
            val quantity = menuCountLiveData.value ?: 0
            cartRepository.createCart(it, quantity).asLiveData(Dispatchers.IO)
        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException(assetWrapper.getString(R.string.text_product_not_found)))) }
    }
}
