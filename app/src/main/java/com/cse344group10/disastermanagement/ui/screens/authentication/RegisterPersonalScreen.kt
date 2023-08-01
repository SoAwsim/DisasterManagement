package com.cse344group10.disastermanagement.ui.screens.authentication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cse344group10.disastermanagement.ui.viewmodels.authentication.RegisterPersonalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPersonalScreen(
    nextButtonAction: (
        mail: String,
        password: String,
        name: String,
        surname: String,
        age: Int,
        gender: Int,
        bloodType: Int
    ) -> Unit = {_, _, _, _, _, _, _ -> },
    backButtonAction: () -> Unit = {},
    mail: String,
    password: String
) {
    val viewModel: RegisterPersonalViewModel = viewModel()
    val userName = viewModel.userName.observeAsState("")
    val userSurname = viewModel.userSurname.observeAsState("")
    val userAge = viewModel.userAge.observeAsState("")
    val chosenIndex = viewModel.chosenIndex.observeAsState(0)
    val ageError = viewModel.ageError.observeAsState(false)
    val nameError = viewModel.nameError.observeAsState(false)
    val surnameError = viewModel.surnameError.observeAsState(false)
    val bloodError = viewModel.bloodError.observeAsState(false)
    val expandBloodTypeDropDown = viewModel.expandBloodTypeDropDown.observeAsState(false)
    val selectedBloodType = viewModel.selectedBloodType.observeAsState("")
    val textFiledSize = viewModel.textFilledSize.observeAsState(Size.Zero)

    val icon = if (expandBloodTypeDropDown.value){
        Icons.Filled.KeyboardArrowUp
    } else {
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

        item {
            OutlinedTextField(
                value = userName.value,
                onValueChange = { viewModel.updateUserName(it) },
                label = { Text(text = "Name") },
                placeholder = { Text(text = "Name") },
                singleLine = true,
                isError = nameError.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

        item {
            OutlinedTextField(
                value = userSurname.value,
                onValueChange = { viewModel.updateUserSurname(it) },
                label = { Text(text = "Surname") },
                placeholder = { Text(text = "Surname") },
                singleLine = true,
                isError = surnameError.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

        item {
            OutlinedTextField(
                value = userAge.value,
                onValueChange = { viewModel.updateUserAge(it) },
                label = { Text(text = "Age") },
                placeholder = { Text(text = "Age") },
                singleLine = true,
                isError = ageError.value,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
        
        item {
            Row(modifier=Modifier.fillMaxWidth()) {
                Text(
                    text = "Gender:",
                    modifier = Modifier
                        .padding(horizontal = 40.dp, vertical = 15.dp),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                viewModel.genderList.forEachIndexed{index,gender ->
                    Text(
                        text = gender,
                        modifier=Modifier.padding(vertical=15.dp),
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic
                    )

                    RadioButton(
                        selected =(gender == viewModel.genderList[chosenIndex.value]) ,
                        onClick = {
                            viewModel.updateChosenIndex(index)
                        }
                    )
                }
            }
        }

        item{
            Box(modifier = Modifier.padding(10.dp)) {
                OutlinedTextField(
                    value = selectedBloodType.value,
                    onValueChange = { viewModel.updateSelectedBloodType(it) },
                    modifier= Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.updateExpandBloodTypeDropDown(!expandBloodTypeDropDown.value)
                        }
                        .onGloballyPositioned { coordinates ->
                            viewModel.updateTextFilledSize(coordinates.size.toSize())
                        },
                    label={Text(text="Select Blood Type")},
                    readOnly = true,
                    enabled = false,
                    isError = bloodError.value,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable{
                                    viewModel.updateExpandBloodTypeDropDown(!expandBloodTypeDropDown.value)
                                }
                        )
                    }
                )
                DropdownMenu(
                    expanded = expandBloodTypeDropDown.value,
                    onDismissRequest = { viewModel.updateExpandBloodTypeDropDown(false) },
                    modifier=Modifier.width(with(LocalDensity.current) {textFiledSize.value.width.toDp()})
                ) {
                    viewModel.bloodTypeList.forEach{label ->
                        DropdownMenuItem(text = { Text(label) },
                            onClick = {
                                viewModel.updateSelectedBloodType(label)
                                viewModel.updateExpandBloodTypeDropDown(false)
                            }
                        )
                    }
                }
            }
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
                        if (viewModel.checkFields()) {
                            nextButtonAction(
                                mail,
                                password,
                                userName.value.trim(),
                                userSurname.value.trim(),
                                userAge.value.toInt(),
                                chosenIndex.value,
                                when (selectedBloodType.value) {
                                    "A+" -> 0
                                    "A-" -> 1
                                    "B+" -> 2
                                    "B-" -> 3
                                    "AB+" -> 4
                                    "AB-" -> 5
                                    "0+" -> 6
                                    else -> 7
                                }
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
fun RegisterPersonalScreenPreview() {
    RegisterPersonalScreen(mail = "", password = "")
}