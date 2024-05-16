package ru.rozum.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Test()
        }
    }
}

@Preview
@Composable
fun Test() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Example3()
    }
}

@Composable
fun Example1() {
    OutlinedButton(onClick = { /*TODO*/ }) {
        Text(text = "Hello World!")
    }
}

@Composable
fun Example2() {
    TextField(value = "Value", onValueChange = { }, label = { Text(text = "Label") })
}

@Composable
fun Example3() {
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = { Text(text = "Yes", modifier = Modifier.padding(8.dp)) },
        title = { Text(text = "Are you sure?") },
        text = { Text(text = "Do you want to delete this file?") },
        dismissButton = { Text(text = "No", modifier = Modifier.padding(8.dp)) }
    )
}










