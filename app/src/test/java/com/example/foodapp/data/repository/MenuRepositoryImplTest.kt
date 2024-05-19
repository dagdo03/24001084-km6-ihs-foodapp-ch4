package com.example.foodapp.data.repository

import app.cash.turbine.test
import com.example.foodapp.data.datasource.menu.MenuDataSource
import com.example.foodapp.data.model.Cart
import com.example.foodapp.data.source.network.model.checkout.CheckoutResponse
import com.example.foodapp.data.source.network.model.menu.MenuItemResponse
import com.example.foodapp.data.source.network.model.menu.MenusResponse
import com.example.foodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MenuRepositoryImplTest {
    private val mockMenu =
        listOf(
            MenuItemResponse(
                alamatResto = "Jkt",
                detail = "Detail 1",
                harga = 100000,
                hargaFormat = "format 1",
                imageUrl = "google.com",
                nama = "Produk 1",
            ),
            MenuItemResponse(
                alamatResto = "Jkt",
                detail = "Detail 2",
                harga = 100000,
                hargaFormat = "format 2",
                imageUrl = "google.com",
                nama = "Produk 2",
            ),
        )

    private val mockCart =
        listOf(
            Cart(
                id = 1,
                productId = "1",
                productName = "produk1",
                productImgUrl = "google.com",
                productPrice = 100000,
                itemQuantity = 1,
                itemNotes = "halo",
            ),
            Cart(
                id = 2,
                productId = "2",
                productName = "produk2",
                productImgUrl = "google.com",
                productPrice = 100000,
                itemQuantity = 1,
                itemNotes = "halo",
            ),
        )

    @MockK
    lateinit var dataSource: MenuDataSource

    private lateinit var repo: MenuRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = MenuRepositoryImpl(dataSource)
    }

    @Test
    fun `get menus success without slug`() {
        val mockResponse = mockk<MenusResponse>()
        every { mockResponse.data } returns mockMenu
        coEvery { dataSource.getMenuData() } returns mockResponse
        runTest {
            repo.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { dataSource.getMenuData() }
            }
        }
    }

    @Test
    fun `get menus success with slug`() {
        val mockResponse = mockk<MenusResponse>()
        every { mockResponse.data } returns mockMenu
        coEvery { dataSource.getMenuData(any()) } returns mockResponse
        runTest {
            repo.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { dataSource.getMenuData(any()) }
            }
        }
    }

    @Test
    fun `get menus loading`() {
        val mockResponse = mockk<MenusResponse>()
        every { mockResponse.data } returns mockMenu
        coEvery { dataSource.getMenuData() } returns mockResponse
        runTest {
            repo.getMenus().map {
                delay(100)
                it
            }.test {
                delay(110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.getMenuData() }
            }
        }
    }

    @Test
    fun `get menus error`() {
        coEvery { dataSource.getMenuData() } throws IllegalStateException("Mock Error")
        runTest {
            repo.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.getMenuData() }
            }
        }
    }

    @Test
    fun `get menus data on empty`() {
        val mockList = listOf<MenuItemResponse>()
        val mockResponse = mockk<MenusResponse>()
        every { mockResponse.data } returns mockList
        coEvery { dataSource.getMenuData() } returns mockResponse
        runTest {
            repo.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
                coVerify { dataSource.getMenuData() }
            }
        }
    }

    @Test
    fun `create order success`() {
        val mockResponse = mockk<CheckoutResponse>(relaxed = true)
        coEvery { dataSource.createOrder(any()) } returns mockResponse
        runTest {
            repo.createOrder(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { dataSource.createOrder(any()) }
            }
        }
    }

    @Test
    fun `create order loading`() {
        val mockResponse = mockk<CheckoutResponse>(relaxed = true)
        coEvery { dataSource.createOrder(any()) } returns mockResponse
        runTest {
            repo.createOrder(mockCart).map {
                delay(100)
                it
            }.test {
                delay(110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.createOrder(any()) }
            }
        }
    }

    @Test
    fun `create order error`() {
        coEvery { dataSource.createOrder(any()) } throws IllegalStateException("Mock Error")
        runTest {
            repo.createOrder(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.createOrder(any()) }
            }
        }
    }
}
