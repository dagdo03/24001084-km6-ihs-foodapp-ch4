package com.example.foodapp.presentation.login

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

class LoginViewModelTest {
    private val email = "ihsan@gmail.com"
    private val password = "tes123"

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var userRepo: UserRepository

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(LoginViewModel(userRepo))
    }

    @Test
    fun doLogin() {
        every { userRepo.doLogin(any(), any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        val result = viewModel.doLogin(email, password).getOrAwaitValue()
        val payload = (result as ResultWrapper.Success).payload
        assertEquals(true, payload)
    }
}
