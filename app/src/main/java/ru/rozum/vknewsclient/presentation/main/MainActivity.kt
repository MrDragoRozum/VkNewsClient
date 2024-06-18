package ru.rozum.vknewsclient.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.api.sdk.VK.getVKAuthActivityResultContract
import com.vk.api.sdk.auth.VKScope
import ru.rozum.vknewsclient.domain.entity.AuthState
import ru.rozum.vknewsclient.presentation.NewsFeedApplication
import ru.rozum.vknewsclient.presentation.ViewModelFactory
import ru.rozum.vknewsclient.ui.theme.VkNewsClientTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as NewsFeedApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
                val authState = viewModel.authState.collectAsState(AuthState.Initial)

                val launcher =
                    rememberLauncherForActivityResult(getVKAuthActivityResultContract()) {
                        viewModel.performAuthResult()
                    }

                when (authState.value) {
                    AuthState.Authorized -> {
                        MainScreen(viewModelFactory)
                    }

                    AuthState.NotAuthorized -> {
                        LoginScreen {
                            launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                        }
                    }

                    AuthState.Initial -> {}
                }
            }
        }
    }
}












