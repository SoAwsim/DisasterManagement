package com.cse344group10.disastermanagement.ui.screens.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WelcomeScreen(
    nextPage: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = ("Welcome to our application!"),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge
        )
        Button(onClick = nextPage) {
            Text(text = "Next")
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    WelcomeScreen {}
}