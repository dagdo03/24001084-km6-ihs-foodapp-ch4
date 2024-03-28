package com.example.kokomputer.data.repository

import com.example.kokomputer.data.model.Cart
import com.example.kokomputer.data.model.Menu
import com.example.kokomputer.data.model.PriceItem
import com.example.kokomputer.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Double>>>
    fun getCheckoutData(): Flow<ResultWrapper<Triple<List<Cart>,List<PriceItem>, Double>>>
    fun createCart(product: Menu, quantity: Int, notes: String? = null) : Flow<ResultWrapper<Boolean>>
    fun decreaseCart(item : Cart) : Flow<ResultWrapper<Boolean>>
    fun increaseCart(item : Cart) : Flow<ResultWrapper<Boolean>>
    fun setCartNotes(item : Cart) : Flow<ResultWrapper<Boolean>>
    fun deleteCart(item : Cart) : Flow<ResultWrapper<Boolean>>
}