package com.cse344group10.disastermanagement.ui.screens.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cse344group10.disastermanagement.R
import com.cse344group10.disastermanagement.database.datastore.SessionTokenDataStore
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.ui.viewmodels.authentication.LoginScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    signInAction: () -> Unit = {},
    createAccount: () -> Unit = {}
) {
    val repo = DbRepo(LocalContext.current)
    val sessionTokenStore = SessionTokenDataStore(LocalContext.current)

    val viewModel: LoginScreenViewModel = viewModel()
    val userMail = viewModel.userMail.observeAsState("")
    val userPassword = viewModel.userPassword.observeAsState("")
    val passwordVisibility = viewModel.passwordVisibility.observeAsState(false)
    val wrongLogin = viewModel.wrongLogin.observeAsState(false)

    LazyColumn (
        modifier = modifier
            .fillMaxSize(),
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
                text = "Sign In",
                style = MaterialTheme.typography.headlineLarge
            )
        }

        item {
            Spacer(modifier = Modifier.padding(28.dp))
        }

        item {
            OutlinedTextField(
                value = userMail.value,
                onValueChange = { viewModel.updateUserMail(it) },
                label = { Text(text = "Email Address") },
                placeholder = { Text(text = "Email Address") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                isError = wrongLogin.value,
                modifier = Modifier.fillMaxWidth().padding(all = 10.dp)
            )
        }

        item {
            OutlinedTextField(
                value = userPassword.value,
                onValueChange = { viewModel.updateUserPassword(it) },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Password") },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { viewModel.changePasswordVisibility() }
                    ) {
                        Icon(
                            imageVector =
                                if (passwordVisibility.value) ImageVector.vectorResource(id = R.drawable.visibility)
                                else ImageVector.vectorResource(id = R.drawable.visibilityoff),
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {viewModel.signIn(repo, signInAction, sessionTokenStore)}
                ),
                isError = wrongLogin.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp)
            )
        }

        item {
            Button(
                onClick = { viewModel.signIn(repo, signInAction, sessionTokenStore) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(all = 10.dp)
            ) {
                Text(
                    text = "Sign In",
                    fontSize = 22.sp
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(28.dp))
        }

        item {
            Text(
                text = "Create An Account",
                modifier = Modifier.clickable(onClick = createAccount)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}