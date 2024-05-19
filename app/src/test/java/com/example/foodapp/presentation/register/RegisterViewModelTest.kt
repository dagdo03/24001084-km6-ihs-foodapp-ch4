package com.example.foodapp.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.foodapp.data.datasource.tools.MainCoroutineRule
import com.example.foodapp.data.datasource.tools.getOrAwaitValue
import com.example.foodapp.data.repository.UserRepository
import com.example.foodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RegisterViewModelTest {
    private val name = "Ihsan"
    private val email = "ihsan@gmail.com"
    private val password = "tes123"

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var userRepo: UserRepository

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(RegisterViewModel(userRepo))
    }

    @Test
    fun doRegister() {
        every { userRepo.doRegister(name, email, password) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        val result = viewModel.doRegister(name, email, password).getOrAwaitValue()
        val payload = (result as ResultWrapper.Success).payload
        assertEquals(true, payload)
    }
}
