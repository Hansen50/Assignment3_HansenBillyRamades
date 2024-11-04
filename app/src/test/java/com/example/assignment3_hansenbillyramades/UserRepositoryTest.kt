package com.example.assignment3_hansenbillyramades.domain.repository

import com.example.assignment3_hansenbillyramades.data.model.LoginDto
import com.example.assignment3_hansenbillyramades.data.source.local.LocalDataSource
import com.example.assignment3_hansenbillyramades.data.source.local.PreferenceDataStore
import com.example.assignment3_hansenbillyramades.data.source.network.UserRemoteDataSource
import com.example.assignment3_hansenbillyramades.domain.model.LoginRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class UserRepositoryImplTest {
    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var remoteDataSource: UserRemoteDataSource
    private lateinit var localDataSource: LocalDataSource
    private lateinit var preferenceDataStore: PreferenceDataStore

    @Before
    fun setUp() {
        remoteDataSource = mock(UserRemoteDataSource::class.java)
        localDataSource = mock(LocalDataSource::class.java)
        preferenceDataStore = mock(PreferenceDataStore::class.java)
        userRepository = UserRepositoryImpl(remoteDataSource, localDataSource, preferenceDataStore)
    }

    @Test
    fun loginShouldReturnLoginDtoFromRemoteDataSource() = runBlocking {
        val request = LoginRequest("phincon@academy.com", "password")
        val loginDto = LoginDto(
            success = true,
            message = "Login successful",
            data = LoginDto.Data(
                firstName = "First",
                lastName = "Last",
                email = "test@example.com",
                phone = "1234567890",
                avatar = "avatar_url",
                token = "sample_token"

            )
        )

        `when`(remoteDataSource.login(request)).thenReturn(loginDto)

        val result = userRepository.login(request)

        assertEquals(loginDto, result)
    }

    @Test
    fun getTokenMustGetTokenFromLocalDataSource() = runBlocking {
        val expectedToken = "sampleToken"
        `when`(localDataSource.getToken()).thenReturn(expectedToken)

        val result = userRepository.getToken()

        assertEquals(expectedToken, result)
    }
}
