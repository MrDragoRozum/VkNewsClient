package ru.rozum.vknewsclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.vk.api.sdk.VK
import com.vk.api.sdk.VK.getVKAuthActivityResultContract
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import ru.rozum.vknewsclient.ui.theme.ActivityResultTest
import ru.rozum.vknewsclient.ui.theme.MainScreen
import ru.rozum.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                val launcher = rememberLauncherForActivityResult(getVKAuthActivityResultContract()) {
                    when(it) {
                        is VKAuthenticationResult.Success -> {
                            Log.d("MainActivity", "Ok")
                        }
                        is VKAuthenticationResult.Failed -> {
                            Log.d("MainActivity", "Bad")
                        }
                    }
                }

                launcher.launch(listOf(VKScope.WALL))

                ActivityResultTest()
            }
        }
    }
}












