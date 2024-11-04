import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.assignment3_hansenbillyramades.data.source.local.PreferenceDataStore
import com.example.assignment3_hansenbillyramades.domain.model.Login
import com.example.assignment3_hansenbillyramades.domain.model.LoginRequest
import com.example.assignment3_hansenbillyramades.domain.model.LoginState
import com.example.assignment3_hansenbillyramades.domain.model.User
import com.example.assignment3_hansenbillyramades.domain.usecase.LoginUseCase
import com.example.assignment3_hansenbillyramades.presentation.viewModel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var loginUseCase: LoginUseCase

    @Mock
    private lateinit var preferenceDataStore: PreferenceDataStore

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loginViewModel = LoginViewModel(loginUseCase, preferenceDataStore)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun loginUserShouldReturnSuccess() = testDispatcher.runBlockingTest {
        val loginRequest = LoginRequest("phincon@academy.com", "password")
        val user = User("doe", "John", "dummy@gmail.com", "dummy_token", "dummy_role", "dummy_photo")
        val loginResponse = Login(token = "dummy_token", user = user)

        `when`(loginUseCase(loginRequest)).thenReturn(loginResponse)


        loginViewModel.loginUsers(loginRequest)
        assertEquals(LoginState.Success(loginResponse), loginViewModel.loginState.value)
    }

    @Test
    fun loginUserShouldReturnError() = testDispatcher.runBlockingTest {
        val loginRequest = LoginRequest("phincon@academy.com", "wrofghgh")
        val errorMessage = "Login failed"

        `when`(loginUseCase(loginRequest)).thenThrow(RuntimeException(errorMessage))

        loginViewModel.loginUsers(loginRequest)

        assertEquals(LoginState.Error(errorMessage), loginViewModel.loginState.value)
    }

    @Test
    fun loginUserShouldHandleEmptyCredentials() = testDispatcher.runBlockingTest {
        val loginRequest = LoginRequest("", "")
        val errorMessage = "Credentials cannot be empty"

        loginViewModel.loginUsers(loginRequest)

        assertEquals(LoginState.Error(errorMessage), loginViewModel.loginState.value)
    }
}
