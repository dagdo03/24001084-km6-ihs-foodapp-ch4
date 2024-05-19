package com.example.foodapp.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.foodapp.data.datasource.tools.MainCoroutineRule
import com.example.foodapp.data.datasource.tools.getOrAwaitValue
import com.example.foodapp.data.model.User
import com.example.foodapp.data.repository.CategoryRepository
import com.example.foodapp.data.repository.MenuRepository
import com.example.foodapp.data.repository.UserPreferenceRepository
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
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {
    private val user =
        User(
            "1",
            "Ihsan",
            "ihsan@gmail.com",
        )

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule() // khusus layer viewmodel test

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var categoryRepo: CategoryRepository

    @MockK
    lateinit var menuRepo: MenuRepository

    @MockK
    lateinit var userRepo: UserRepository

    @MockK
    lateinit var prefRepo: UserPreferenceRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { prefRepo.isUsingGridMode() } returns true
        every { prefRepo.setUsingGridMode(any()) } returns Unit
        viewModel =
            spyk(
                HomeViewModel(
                    categoryRepo,
                    menuRepo,
                    prefRepo,
                    userRepo,
                ),
            )
    }

    @Test
    fun isUsingGridMode() {
        val result = viewModel.getListMode()
        assertEquals(1, result)
    }

    @Test
    fun getListMode() {
        val result = viewModel.getListMode()
        assertEquals(1, result)
        verify { prefRepo.isUsingGridMode() }
    }

    @Test
    fun changeListMode() {
        val currentValue = viewModel.isUsingGridMode.value ?: false
        val newValue = !currentValue

        viewModel.changeListMode()
        assertEquals(newValue, viewModel.isUsingGridMode.value)
        verify { prefRepo.isUsingGridMode() }
        verify { prefRepo.setUsingGridMode(any()) }
    }

    @Test
    fun getMenu() {
        every { menuRepo.getMenus() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getMenu().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { menuRepo.getMenus() }
    }

    @Test
    fun getCategory() {
        every { categoryRepo.getCategories() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getCategory().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { categoryRepo.getCategories() }
    }

    @Test
    fun getCurrentUser() {
        val user = user
        every { userRepo.getCurrentUser() } returns user
        val result = viewModel.getCurrentUser()
        assertEquals(user, result)
        verify { userRepo.getCurrentUser() }
    }

    @Test
    fun isLoggedIn() {
        every { userRepo.isLoggedIn() } returns true
        val result = viewModel.isLoggedIn()
        assertEquals(true, result)
    }
}
