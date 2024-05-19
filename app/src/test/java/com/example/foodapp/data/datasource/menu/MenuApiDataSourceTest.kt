package com.example.foodapp.data.datasource.menu

import com.example.foodapp.data.source.network.model.checkout.CheckoutRequestPayload
import com.example.foodapp.data.source.network.model.checkout.CheckoutResponse
import com.example.foodapp.data.source.network.model.menu.MenusResponse
import com.example.foodapp.data.source.network.services.FoodAppApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MenuApiDataSourceTest {
    @MockK
    lateinit var service: FoodAppApiService

    private lateinit var dataSource: MenuApiDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = MenuApiDataSource((service))
    }

    @Test
    fun getMenuData() {
        runTest {
            val mockResponse = mockk<MenusResponse>(relaxed = true)
            coEvery { service.getMenus(any()) } returns mockResponse
            val actualResult = dataSource.getMenuData()
            coVerify { service.getMenus(any()) }
            assertEquals(mockResponse, actualResult)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockRequest = mockk<CheckoutRequestPayload>(relaxed = true)
            val mockResponse = mockk<CheckoutResponse>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val actualResult = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(actualResult, mockResponse)
        }
    }
}
