package com.example.foodapp.data.repository

import com.example.foodapp.data.datasource.cart.CartDataSource
import com.example.foodapp.data.mapper.toCartEntity
import com.example.foodapp.data.mapper.toCartList
import com.example.foodapp.data.model.Cart
import com.example.foodapp.data.model.Menu
import com.example.foodapp.data.model.PriceItem
import com.example.foodapp.data.source.local.database.entity.CartEntity
import com.example.foodapp.utils.ResultWrapper
import com.example.foodapp.utils.proceed
import com.example.foodapp.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class CartRepositoryImpl(private val cartDataSource: CartDataSource) : CartRepository {
    override fun getUserCartData(): Flow<ResultWrapper<Pair<List<Cart>, Int>>> {
        return cartDataSource.getAllCarts()
            .map {
                // mapping into cartlist and sum into total price
                proceed {
                    val result = it.toCartList()
                    val totalPrice =
                        result.sumOf {
                            it.productPrice * it.itemQuantity
                        }
                    Pair(result, totalPrice)
                }
            }.map {
                // map to check when list is empty
                if (it.payload?.first?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override fun getCheckoutData(): Flow<ResultWrapper<Triple<List<Cart>, List<PriceItem>, Int>>> {
        return cartDataSource.getAllCarts()
            .map {
                // mapping into cart list and sum the total price
                proceed {
                    val result = it.toCartList()
                    val priceItemList =
                        result.map {
                            PriceItem(it.productName, it.productPrice * it.itemQuantity)
                        }
                    val totalPrice = priceItemList.sumOf { it.total }
                    Triple(result, priceItemList, totalPrice)
                }
            }.map {
                // map to check when list is empty
                if (it.payload?.first?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override suspend fun deleteAll() {
        cartDataSource.deleteAll()
    }

    override fun createCart(
        menu: Menu,
        quantity: Int,
        notes: String?,
    ): Flow<ResultWrapper<Boolean>> {
        return menu.id?.let { menuId ->
            // when id is not null
            proceedFlow {
                val affectedRow =
                    cartDataSource.insertCart(
                        CartEntity(
                            productId = menuId,
                            itemQuantity = quantity,
                            productName = menu.name,
                            productPrice = menu.price,
                            productImgUrl = menu.imgURL,
                            itemNotes = notes,
                        ),
                    )
                delay(1000)
                affectedRow > 0
            }
        } ?: flow {
            // when id is not exist
            emit(ResultWrapper.Error(IllegalStateException("Product Id not found")))
        }
    }

    override fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart =
            item.copy().apply {
                itemQuantity -= 1
            }
        return if (modifiedCart.itemQuantity <= 0) {
            proceedFlow { cartDataSource.deleteCart(item.toCartEntity()) > 0 }
        } else {
            proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
        }
    }

    override fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart =
            item.copy().apply {
                itemQuantity += 1
            }
        return proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
    }

    override fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.updateCart(item.toCartEntity()) > 0 }
    }

    override fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.deleteCart(item.toCartEntity()) > 0 }
    }

    override suspend fun checkout(items: List<Cart>): Flow<ResultWrapper<Boolean>> {
        return flow {
            delay(1000)
            emit(ResultWrapper.Success(true))
        }
    }
}
