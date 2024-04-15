package com.example.foodapp.presentation.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.repository.CartRepository
import com.example.foodapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(private val cartRepository: CartRepository) : ViewModel() {
    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)
    private val _checkoutResult = MutableLiveData<ResultWrapper<Boolean>>()
    val checkoutResult: LiveData<ResultWrapper<Boolean>>
        get() = _checkoutResult

    fun checkout() {
        viewModelScope.launch(Dispatchers.IO) {
            val carts = checkoutData.value?.payload?.first ?: return@launch
            // tidak akan membuat order ketika list of cart nya null
            cartRepository.checkout(carts).collect {
                _checkoutResult.postValue(it)
            }
        }
    }

    fun removeAllCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAll()
        }
    }

}