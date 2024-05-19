package com.example.foodapp.data.repository

import app.cash.turbine.test
import com.example.foodapp.data.datasource.cart.CartDataSource
import com.example.foodapp.data.model.Cart
import com.example.foodapp.data.model.Menu
import com.example.foodapp.data.model.PriceItem
import com.example.foodapp.data.source.local.database.entity.CartEntity
import com.example.foodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartRepositoryImplTest {
    private val mockCartEntity =
        listOf(
            CartEntity(
                id = 1,
                productId = "1",
                productName = "Produk 1",
                productImgUrl = "google.com",
                productPrice = 100000,
                itemQuantity = 3,
                itemNotes = "Halo",
            ),
            CartEntity(
                id = 2,
                productId = "2",
                productName = "Produk 2",
                productImgUrl = "google.com",
                productPrice = 100000,
                itemQuantity = 3,
                itemNotes = "Halo",
            ),
        )

    private val mockCart =
        Cart(
            id = 1,
            productId = "1",
            productName = "produk1",
            productImgUrl = "google.com",
            productPrice = 100000,
            itemQuantity = 1,
            itemNotes = "halo",
        )

    private val mockPriceItem =
        listOf(
            PriceItem(
                name = "Produk 1",
                total = 300000,
            ),
            PriceItem(
                name = "Produk 2",
                total = 300000,
            ),
        )

    @MockK
    lateinit var dataSource: CartDataSource

    private lateinit var repo: CartRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = CartRepositoryImpl(dataSource)
    }

    @Test
    fun `get user cart data success`() {
        val mockList = mockCartEntity
        val mockFlow = flow { emit(mockList) }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockList.size, actualData.payload?.first?.size)
                assertEquals(600000, actualData.payload?.second)
            }
        }
    }

    @Test
    fun `get user cart data loading`() {
        val mockList = mockCartEntity
        val mockFlow = flow { emit(mockList) }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user cart data error`() {
        every { dataSource.getAllCarts() } returns
            flow {
                throw IllegalStateException("Mock Error")
            }
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `get user cart data on empty`() {
        val mockList = listOf<CartEntity>()
        val mockFlow = flow { emit(mockList) }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
                assertEquals(0, actualData.payload?.first?.size)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutData() {
        every { dataSource.getAllCarts() }
    }

    @Test
    fun deleteAll() {
    }

    @Test
    fun `create cart success`() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "1"
        coEvery { dataSource.insertCart(any()) } returns 1
        runTest {
            repo.createCart(mockMenu, 3, "halo").map {
                delay(100)
                it
            }.test {
                delay(1201)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Success)
                assertEquals(true, result.payload)
                coVerify { dataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `create cart loading`() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "1"
        coEvery { dataSource.insertCart(any()) } returns 1
        runTest {
            repo.createCart(mockMenu, 3, "halo").map {
                delay(100)
                it
            }.test {
                delay(1111)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Loading)
                coVerify { dataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `create cart error`() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "1"
        coEvery { dataSource.insertCart(any()) } throws IllegalStateException("Mock Error")
        runTest {
            repo.createCart(mockMenu, 3, "halo").map {
                delay(100)
                it
            }.test {
                delay(1201)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                assertEquals(null, result.payload)
                coVerify { dataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `create cart on error id`() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns null
        coEvery { dataSource.insertCart(any()) } throws IllegalStateException("Mock Error")
        runTest {
            repo.createCart(mockMenu, 0, "halo").map {
                delay(100)
                it
            }.test {
                delay(1211)
                val result = expectMostRecentItem()
                assertTrue(result is ResultWrapper.Error)
                coVerify { dataSource.insertCart(any()) }
            }
        }
    }

    @Test
    fun `decrease cart when cart more than 0`() {
        val mockCart =
            Cart(
                id = 1,
                productId = "1",
                productName = "produk1",
                productImgUrl = "google.com",
                productPrice = 100000,
                itemQuantity = 3,
                itemNotes = "halo",
            )
        coEvery { dataSource.deleteCart(any()) } returns 1
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repo.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.updateCart(any()) }
                coVerify(atLeast = 0) { dataSource.deleteCart(any()) }
            }
        }
    }

    @Test
    fun `decrease cart when cart less than 1`() {
        coEvery { dataSource.deleteCart(any()) } returns 1
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repo.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { dataSource.deleteCart(any()) }
                coVerify(atLeast = 0) { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `increase cart success more than 0`() {
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repo.increaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(1210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `set cart notes success`() {
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repo.setCartNotes(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `set cart notes loading`() {
        coEvery { dataSource.updateCart(any()) } returns 1
        runTest {
            repo.setCartNotes(mockCart).map {
                delay(100)
                it
            }.test {
                delay(110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.updateCart(any()) }
            }
        }
    }

    @Test
    fun `delete cart success`() {
        coEvery { dataSource.deleteCart(any()) } returns 1
        runTest {
            repo.deleteCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { dataSource.deleteCart(any()) }
            }
        }
    }

    @Test
    fun `delete cart loading`() {
        coEvery { dataSource.deleteCart(any()) } returns 1
        runTest {
            repo.deleteCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.deleteCart(any()) }
            }
        }
    }

    @Test
    fun `delete cart error`() {
        coEvery { dataSource.deleteCart(any()) } throws IllegalStateException("Mock Error")
        runTest {
            repo.deleteCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.deleteCart(any()) }
            }
        }
    }

    @Test
    fun `checkout data success`() {
        val mockFlow =
            flow {
                emit(mockCartEntity)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockCartEntity.size, actualData.payload?.first?.size)
                assertEquals(mockPriceItem.size, actualData.payload?.second?.size)
                assertEquals(600000, actualData.payload?.third)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `checkout data loading`() {
        val mockFlow =
            flow {
                emit(mockCartEntity)
            }
        every { dataSource.getAllCarts() } returns mockFlow
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                verify { dataSource.getAllCarts() }
            }
        }
    }

    @Test
    fun `checkout data error`() {
        every { dataSource.getAllCarts() } returns
            flow {
                throw IllegalStateException("Mock Error")
            }
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { dataSource.getAllCarts() }
            }
        }
    }
}
