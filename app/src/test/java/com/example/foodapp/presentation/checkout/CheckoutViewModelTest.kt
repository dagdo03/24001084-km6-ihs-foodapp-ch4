package com.example.foodapp.presentation.checkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.foodapp.data.datasource.tools.MainCoroutineRule
import com.example.foodapp.data.repository.CartRepository
import com.example.foodapp.data.repository.MenuRepository
import com.example.foodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CheckoutViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule() // khusus layer viewmodel test

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepo: CartRepository

    @MockK
    lateinit var menuRepo: MenuRepository

    private lateinit var viewModel: CheckoutViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { cartRepo.getCheckoutData() } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        Triple(
                            mockk(relaxed = true), mockk(relaxed = true), 1000,
                        ),
                    ),
                )
            }
        viewModel =
            spyk(
                CheckoutViewModel(
                    cartRepo,
                    menuRepo,
                ),
            )
    }

//    @Test
//    fun checkoutCart() {
//        every { menuRepo.createOrder(any()) } returns
//            flow {
//                emit(ResultWrapper.Success(true))
//            }
//        val result = viewModel.checkoutCart().getOrAwaitValue()
//        val payload = (result as ResultWrapper.Success).payload
//        assertEquals(true, payload)
//    }

    @Test
    fun removeAllCart() {
        coEvery { cartRepo.deleteAll() } returns Unit
        viewModel.removeAllCart()
        coVerify { cartRepo.deleteAll() }
    }
}
