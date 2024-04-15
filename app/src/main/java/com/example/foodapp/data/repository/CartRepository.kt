package com.example.foodapp.data.repository

import com.example.foodapp.data.model.Cart
import com.example.foodapp.data.model.Menu
import com.example.foodapp.data.model.PriceItem
import com.example.foodapp.utils.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Int>>>
    fun getCheckoutData(): Flow<ResultWrapper<Triple<List<Cart>,List<PriceItem>, Int>>>
    fun createCart(product: Menu, quantity: Int, notes: String? = null) : Flow<ResultWrapper<Boolean>>
    fun decreaseCart(item : Cart) : Flow<ResultWrapper<Boolean>>
    fun increaseCart(item : Cart) : Flow<ResultWrapper<Boolean>>
    fun setCartNotes(item : Cart) : Flow<ResultWrapper<Boolean>>
    fun deleteCart(item : Cart) : Flow<ResultWrapper<Boolean>>
    suspend fun checkout(items: List<Cart>): Flow<ResultWrapper<Boolean>>
    suspend fun deleteAll()

}