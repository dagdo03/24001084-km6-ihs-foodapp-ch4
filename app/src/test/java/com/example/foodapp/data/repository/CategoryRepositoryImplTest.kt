package com.example.foodapp.data.repository

import app.cash.turbine.test
import com.example.foodapp.data.datasource.category.CategoryDataSource
import com.example.foodapp.data.source.network.model.category.CategoriesResponse
import com.example.foodapp.data.source.network.model.category.CategoryItemResponse
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

class CategoryRepositoryImplTest {
    @MockK
    lateinit var dataSource: CategoryDataSource

    private lateinit var repo: CategoryRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = CategoryRepositoryImpl(dataSource)
    }

    @Test
    fun `get categories loading`() {
        val c1 =
            CategoryItemResponse(
                imageUrl = "google.com",
                nama = "Kategori 1",
            )

        val c2 =
            CategoryItemResponse(
                imageUrl = "google.com",
                nama = "Kategori 2",
            )

        val mockListCategory = listOf(c1, c2)
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategoryData() } returns mockResponse
            repo.getCategories().map {
                delay(100)
                it
            }.test {
                delay(105) // 101 - 200 loading
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.getCategoryData() }
            }
        }
    }

    @Test
    fun `get categories success`() {
        val c1 =
            CategoryItemResponse(
                imageUrl = "google.com",
                nama = "Kategori 1",
            )

        val c2 =
            CategoryItemResponse(
                imageUrl = "google.com",
                nama = "Kategori 2",
            )

        val mockListCategory = listOf(c1, c2)
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategoryData() } returns mockResponse
            repo.getCategories().map {
                delay(100)
                it
            }.test {
                delay(205) // 201 - 300 success
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getCategoryData() }
            }
        }
    }

    @Test
    fun `get categories error`() {
        runTest {
            coEvery { dataSource.getCategoryData() } throws IllegalStateException("Mock Error")
            repo.getCategories().map {
                delay(100)
                it
            }.test {
                delay(205) // 201 - 300 success
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getCategoryData() }
            }
        }
    }

    @Test
    fun `get categories empty`() {
        val mockListCategory = listOf<CategoryItemResponse>()
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategoryData() } returns mockResponse
            repo.getCategories().map {
                delay(100)
                it
            }.test {
                delay(205) // 201 - 300 success
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getCategoryData() }
            }
        }
    }
}
