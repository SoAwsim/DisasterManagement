package com.cse344group10.disastermanagement.ui.screens.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cse344group10.disastermanagement.ui.viewmodels.authentication.RegisterEmailPasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterEmailPasswordScreen(
    modifier: Modifier = Modifier,
    nextButtonAction: (mail: String, password: String) -> Unit = { _, _ ->},
    backButtonAction: () -> Unit = {}
) {
    val viewModel: RegisterEmailPasswordViewModel = viewModel()
    val userMail = viewModel.userMail.observeAsState("")
    val userPassword = viewModel.userPassword.observeAsState("")
    val userConfirmPassword = viewModel.userConfirmPassword.observeAsState("")
    val invalidMail = viewModel.invalidEmail.observeAsState(false)
    val invalidPassword = viewModel.invalidPassword.observeAsState(false)

    LazyColumn (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
        }

        item {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineLarge
            )
        }

        item {
            Spacer(modifier = Modifier.padding(28.dp))
        }

        item {
            OutlinedTextField(
                value = userMail.value,
                onValueChange = { viewModel.updateMail(it) },
                label = { Text(text = "Email Address") },
                placeholder = { Text(text = "Email Address") },
                isError = invalidMail.value,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

        item {
            OutlinedTextField(
                value = userPassword.value,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Password") },
                isError = invalidPassword.value,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

        item {
            OutlinedTextField(
                value = userConfirmPassword.value,
                onValueChange = { viewModel.updateConfirmPassword(it) },
                label = { Text(text = "Confirm Password") },
                placeholder = { Text(text = "Confirm Password") },
                isError = invalidPassword.value,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.padding(10.dp))
        }

        item {
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Button(
                    onClick = {
                        viewModel.checkInfo()
                        if (!invalidMail.value && !invalidPassword.value) {
                            nextButtonAction(userMail.value, userPassword.value)
                        }
                    },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                    )
                }

                Button(
                    onClick = backButtonAction,
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterEmailPasswordScreen()
}