package ru.rozum.vknewsclient.ui.theme

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class MyNumber(var a: Int)

@Composable
fun SideEffectTest(number: MyNumber) {
    Column {
        LazyColumn {
            Log.d("MainActivity", "Я вызвался: 1")
            items(count = 5) {
                Text(text = "Number: ${number.a}")
            }
        }

        number.a = 5
        Log.d("MainActivity", "Я вызвался: 2")

        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn {
            Log.d("MainActivity", "Я вызвался: 3")
            items(count = 5) {
                Text(text = "Number: ${number.a}")
            }
        }
    }

}