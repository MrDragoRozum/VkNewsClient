package ru.rozum.vknewsclient

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import com.vk.api.sdk.VK
import com.vk.api.sdk.VK.getVKAuthActivityResultContract
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import ru.rozum.vknewsclient.ui.theme.ActivityResultTest
import ru.rozum.vknewsclient.ui.theme.MainScreen
import ru.rozum.vknewsclient.ui.theme.MyNumber
import ru.rozum.vknewsclient.ui.theme.SideEffectTest
import ru.rozum.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                Log.d("MainActivity", "Я вызвался: SideEffect")
                val launcher =
                    rememberLauncherForActivityResult(getVKAuthActivityResultContract()) {
                        Log.d("MainActivity", "Я вызвался: 1")
                        when (it) {
                            is VKAuthenticationResult.Success -> {
                                Log.d("MainActivity", "Ok")
                            }

                            is VKAuthenticationResult.Failed -> {
                                Log.d("MainActivity", "Bad")
                            }
                        }
                    }

                LaunchedEffect(key1 = Unit) {
                    Log.d("MainActivity", "Я вызвался: LaunchedEffect")
                }

                SideEffect {
                    Log.d("MainActivity", "Я вызвался: SideEffect-composable")
                }

                ActivityResultTest()
            }
        }
    }
}












