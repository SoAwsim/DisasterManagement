package com.cse344group10.disastermanagement.ui.screens.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cse344group10.disastermanagement.models.user.User
import com.cse344group10.disastermanagement.ui.viewmodels.authentication.RegisterQuestionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterQuestionScreen(
    nextButtonAction: (
        mail: String,
        password: String,
        name: String,
        surname: String,
        age: Int,
        gender: Int,
        blood: Int,
        questionType: Int,
        questionAnswer: String,
        notes: String
    ) -> Unit = {_, _, _, _, _, _, _, _, _, _ ->},
    backButtonAction: () -> Unit = {},
    mail: String,
    password: String,
    name: String,
    surname: String,
    age: Int,
    gender: Int,
    blood: Int
) {
    val viewModel: RegisterQuestionViewModel = viewModel()
    val userAnswer = viewModel.userAnswer.observeAsState("")
    val userNotes = viewModel.userNotes.observeAsState("")
    val questionError = viewModel.questionError.observeAsState(false)
    val answerError = viewModel.answerError.observeAsState(false)
    val questionDropDownExpanded = viewModel.questionDropDownExpanded.observeAsState(false)
    val selectedQuestion = viewModel.selectedQuestion.observeAsState("")
    val textFiledSize = viewModel.textFilledSize.observeAsState(Size.Zero)

    val icon=if(questionDropDownExpanded.value){
        Icons.Filled.KeyboardArrowUp
    }else{
        Icons.Filled.KeyboardArrowDown
    }
    LazyColumn (
        modifier = Modifier
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
                text = "Sign Up",
                style = MaterialTheme.typography.headlineLarge
            )
        }

        item {
            Spacer(modifier = Modifier.padding(28.dp))
        }


        item{
            Box(modifier = Modifier.padding(all = 10.dp)) {
                OutlinedTextField(
                    value = selectedQuestion.value,
                    onValueChange = {viewModel.updateSelectedQuestion(it)},
                    modifier= Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.updateDropDownState(!questionDropDownExpanded.value) }
                        .onGloballyPositioned {
                            viewModel.updateTextFilledSize(it.size.toSize())
                        },
                    label={Text(text="Select Security Question")},
                    trailingIcon = {
                        Icon(icon,"",Modifier.clickable{ viewModel.updateDropDownState(!questionDropDownExpanded.value) })
                    },
                    readOnly = true,
                    enabled = false,
                    isError = questionError.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                DropdownMenu(
                    expanded = questionDropDownExpanded.value,
                    onDismissRequest = { viewModel.updateDropDownState(false) },
                    modifier = Modifier
                        .width(with(LocalDensity.current) {textFiledSize.value.width.toDp()})
                ) {
                    viewModel.questionList.forEach{label ->
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = {
                                viewModel.updateSelectedQuestion(label)
                                viewModel.updateDropDownState(false)
                            }
                        )
                    }
                }
            }

        }
        item {
            OutlinedTextField(
                value = userAnswer.value,
                onValueChange = { viewModel.updateUserAnswer(it) },
                label = { Text(text = "Answer") },
                placeholder = { Text(text = "Answer") },
                singleLine = true,
                isError = answerError.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.padding(25.dp))
        }

        item {
            OutlinedTextField(
                value = userNotes.value,
                onValueChange = { viewModel.updateUserNotes(it) },
                label = { Text(text = "Notes") },
                placeholder = { Text(text = "Notes") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.padding(10.dp))
        }

        item {
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp)) {
                Button(
                    onClick = {
                        if (viewModel.checkFields()) {
                            nextButtonAction(
                                mail,
                                password,
                                name,
                                surname,
                                age,
                                gender,
                                blood,
                                when (selectedQuestion.value) {
                                    User.SecurityQuestion.Pet.question -> 0
                                    User.SecurityQuestion.BookMovie.question -> 1
                                    User.SecurityQuestion.City.question -> 2
                                    User.SecurityQuestion.Mother.question -> 3
                                    else -> 4
                                },
                                userAnswer.value,
                                if (userNotes.value == "") " " else userNotes.value
                            )
                        } },
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
fun RegisterQuestionScreenPreview() {
    RegisterQuestionScreen(mail = "",
        password = "",
        name = "",
        surname = "",
        age = 0,
        blood = 0,
        gender = 0)
}