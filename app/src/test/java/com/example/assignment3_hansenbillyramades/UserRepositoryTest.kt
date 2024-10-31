
import com.example.assignment3_hansenbillyramades.data.model.LoginDto
import com.example.assignment3_hansenbillyramades.data.repository.UserRepositoryImpl
import com.example.assignment3_hansenbillyramades.data.source.local.LocalDataSource
import com.example.assignment3_hansenbillyramades.data.source.local.PreferenceDataStore
import com.example.assignment3_hansenbillyramades.data.source.network.UserRemoteDataSource
import com.example.assignment3_hansenbillyramades.domain.model.LoginRequest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {
    private lateinit var userRepository: UserRepositoryImpl
    private lateinit var mockLocalDataSource: LocalDataSource
    private lateinit var mockRemoteDataSource: UserRemoteDataSource
    private lateinit var mockPreferenceDataStore: PreferenceDataStore


    @Before
    fun setUp() {
        userRepository = UserRepositoryImpl(mockRemoteDataSource, mockLocalDataSource, mockPreferenceDataStore)

    }

    @Test
    fun loginshouldreturnLoginDtowhencredentialsarevalid(){
        val loginRequest = LoginRequest("valid_user@example.com", "correct_password")
        val expectedLoginDto = LoginDto(
            success = true,
            message = "Login successful",
            data = LoginDto.Data(
                firstName = "John",
                lastName = "Doe",
                email = "valid_user@example.com",
                phone = "1234567890",
                avatar = "avatar_url",
                token = "token123"
            )
        )

        assertEquals(expectedLoginDto, loginRequest)
    }

    @Test
    fun loginshouldreturnfailedmessagewhencredentialsareinvalid(){
        val loginRequest = LoginRequest("invalid_user@example.com", "wrong_password")
        val expectedLoginDto = LoginDto(
            success = false,
            message = "Login failed",
            data = LoginDto.Data(
                firstName = "",
                lastName = "",
                email = "",
                phone = "",
                avatar = "",
                token = ""
            )
        )

        assertEquals(expectedLoginDto, loginRequest)
    }
}
