package com.example.foodapp.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.foodapp.data.datasource.tools.MainCoroutineRule
import com.example.foodapp.data.datasource.tools.getOrAwaitValue
import com.example.foodapp.data.model.User
import com.example.foodapp.data.repository.UserRepository
import com.example.foodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
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

class ProfileViewModelTest {
    private val user =
        User(
            "1",
            "Ihsan",
            "ihsan@gmail.com",
        )

    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                ProfileViewModel(repo),
            )
    }

    @Test
    fun isEditMode() {
        val enableEdit = true
        viewModel.changeEditMode()
        val enabledEditValue = viewModel.isEditMode.value

        val disableEdit = false
        viewModel.changeEditMode()
        val disabledEditValue = viewModel.isEditMode.value
        assertEquals(disableEdit, disabledEditValue)
    }

    @Test
    fun doLogout() {
        every { repo.doLogout() } returns true
        val result = viewModel.doLogout()
        assertEquals(true, result)
    }

//    @Test
//    fun updateProfileName() {
//        every { repo.updateProfile() } returns
//            flow {
//                emit(ResultWrapper.Success(true))
//            }
//        viewModel.updateProfileName(fullName = "Ihsan").getOrAwaitValue()
//        verify { repo.updateProfile() }
//    }

    @Test
    fun updateProfileEmail() {
        every { repo.updateEmail(newEmail = "ihsan@gmail.com") } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.updateProfileEmail(email = "ihsan@gmail.com").getOrAwaitValue()
        verify { repo.updateEmail(newEmail = "ihsan@gmail.com") }
    }

    @Test
    fun getCurrentUser() {
        every { repo.getCurrentUser() } returns user
        val result = viewModel.getCurrentUser()
        assertEquals(user, result)
    }

//    @Test
//    fun changeEditMode() {
//
//    }
}
