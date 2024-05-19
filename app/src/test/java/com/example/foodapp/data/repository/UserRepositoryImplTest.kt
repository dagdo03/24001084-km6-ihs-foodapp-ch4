package com.example.foodapp.data.repository

import app.cash.turbine.test
import com.example.foodapp.data.datasource.auth.AuthDataSource
import com.example.foodapp.data.model.User
import com.example.foodapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    private val fullName = "Ihsan Widagdo"
    private val email = "ihsan@gmail.com"
    private val password = "tes123"

    private val mockUser =
        User(
            id = "1",
            fullName = "Ihsan Widagdo",
            email = "ihsan@gmail.com",
        )

    @MockK
    lateinit var dataSource: AuthDataSource

    private lateinit var repo: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = UserRepositoryImpl(dataSource)
    }

    @Test
    fun `do login success`() {
        coEvery { dataSource.doLogin(any(), any()) } returns true
        runTest {
            repo.doLogin(email, password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `do login loading`() {
        coEvery { dataSource.doLogin(any(), any()) } returns true
        runTest {
            repo.doLogin(email, password).map {
                delay(100)
                it
            }.test {
                delay(110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `do login error`() {
        coEvery { dataSource.doLogin(any(), any()) } throws IllegalStateException("Mock Error")
        runTest {
            repo.doLogin(email, password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.doLogin(any(), any()) }
            }
        }
    }

    @Test
    fun `do register success`() {
        coEvery { dataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            repo.doRegister(email, fullName, password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `do register loading`() {
        coEvery { dataSource.doRegister(any(), any(), any()) } returns true
        runTest {
            repo.doRegister(email, fullName, password).map {
                delay(100)
                it
            }.test {
                delay(110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `do register error`() {
        coEvery { dataSource.doRegister(any(), any(), any()) } throws IllegalStateException("Mock Error")
        runTest {
            repo.doRegister(email, fullName, password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.doRegister(any(), any(), any()) }
            }
        }
    }

    @Test
    fun `update profile success`() {
        coEvery { dataSource.updateProfile() } returns true
        runTest {
            repo.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { dataSource.updateProfile() }
            }
        }
    }

    @Test
    fun `update profile loading`() {
        coEvery { dataSource.updateProfile() } returns true
        runTest {
            repo.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.updateProfile() }
            }
        }
    }

    @Test
    fun `update profile error`() {
        coEvery { dataSource.updateProfile() } throws IllegalStateException("Mock Error")
        runTest {
            repo.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.updateProfile() }
            }
        }
    }

    @Test
    fun `update password success`() {
        coEvery { dataSource.updatePassword(any()) } returns true
        runTest {
            repo.updatePassword(password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { dataSource.updatePassword(any()) }
            }
        }
    }

    @Test
    fun `update password loading`() {
        coEvery { dataSource.updatePassword(any()) } returns true
        runTest {
            repo.updatePassword(password).map {
                delay(100)
                it
            }.test {
                delay(110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.updatePassword(any()) }
            }
        }
    }

    @Test
    fun `update password error`() {
        coEvery { dataSource.updatePassword(any()) } throws IllegalStateException("Mock Error")
        runTest {
            repo.updatePassword(password).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.updatePassword(any()) }
            }
        }
    }

    @Test
    fun `update email success`() {
        coEvery { dataSource.updateEmail(any()) } returns true
        runTest {
            repo.updateEmail(email).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { dataSource.updateEmail(any()) }
            }
        }
    }

    @Test
    fun `update email loading`() {
        coEvery { dataSource.updateEmail(any()) } returns true
        runTest {
            repo.updateEmail(email).map {
                delay(100)
                it
            }.test {
                delay(110)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { dataSource.updateEmail(any()) }
            }
        }
    }

    @Test
    fun `update email error`() {
        coEvery { dataSource.updateEmail(any()) } throws IllegalStateException("Mock Error")
        runTest {
            repo.updateEmail(email).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { dataSource.updateEmail(any()) }
            }
        }
    }

    @Test
    fun `request change password by email success`() {
        every { dataSource.requestChangePasswordByEmail() } returns true
        runTest {
            val result = repo.requestChangePasswordByEmail()
            assertEquals(true, result)
            verify { dataSource.requestChangePasswordByEmail() }
        }
    }

    @Test
    fun doLogout() {
        every { dataSource.doLogout() } returns true
        runTest {
            val result = repo.doLogout()
            assertEquals(true, result)
            verify { dataSource.doLogout() }
        }
    }

    @Test
    fun isLoggedIn() {
        every { dataSource.isLoggedIn() } returns true
        runTest {
            val result = repo.isLoggedIn()
            assertEquals(true, result)
            verify { dataSource.isLoggedIn() }
        }
    }

    @Test
    fun getCurrentUser() {
        val user = mockUser
        every { dataSource.getCurrentUser() } returns user
        runTest {
            val result = repo.getCurrentUser()
            assertEquals(user, result)
            verify { dataSource.getCurrentUser() }
        }
    }
}
