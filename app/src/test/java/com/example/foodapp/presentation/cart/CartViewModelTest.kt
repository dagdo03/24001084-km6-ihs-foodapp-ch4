package com.example.foodapp.presentation.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.foodapp.data.datasource.tools.MainCoroutineRule
import com.example.foodapp.data.datasource.tools.getOrAwaitValue
import com.example.foodapp.data.repository.CartRepository
import com.example.foodapp.data.repository.UserRepository
import com.example.foodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CartViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule() // khusus layer viewmodel test

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartrepo: CartRepository

    @MockK
    lateinit var userRepo: UserRepository

    private lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(CartViewModel(cartrepo, userRepo))
    }

    @Test
    fun isLoggedIn() {
        every { userRepo.isLoggedIn() } returns true
        val result = viewModel.isLoggedIn()
        assertEquals(true, result)
    }

    @Test
    fun getAllCarts() {
        every { cartrepo.getUserCartData() } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        Pair(
                            listOf(
                                mockk(relaxed = true),
                                mockk(relaxed = true),
                            ),
                            100000,
                        ),
                    ),
                )
            }
        val result = viewModel.getAllCarts().getOrAwaitValue()
        assertEquals(2, result.payload?.first?.size)
        assertEquals(100000, result.payload?.second)
    }

    @Test
    fun decreaseCart() {
        every { cartrepo.decreaseCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.decreaseCart(mockk())
        verify { cartrepo.decreaseCart(any()) }
    }

    @Test
    fun increaseCart() {
        every { cartrepo.increaseCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.increaseCart(mockk())
        verify { cartrepo.increaseCart(any()) }
    }

    @Test
    fun removeCart() {
        every { cartrepo.deleteCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.removeCart(mockk())
        verify { cartrepo.deleteCart(any()) }
    }

    @Test
    fun setCartNotes() {
        every { cartrepo.setCartNotes(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.setCartNotes(mockk())
        verify { cartrepo.setCartNotes(any()) }
    }
}
