package com.cse344group10.disastermanagement.ui.uimodels

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertInput(
    dismiss: () -> Unit,
    title: String,
    textValue: String,
    textValueChange: (String) -> Unit,
    confirmButtonAction: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = dismiss,
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center
            )
        },
        text = {
            OutlinedTextField(
                value = textValue,
                onValueChange = {name -> textValueChange(name)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        },
        confirmButton = {
            Text(
                text = "Confirm",
                Modifier.clickable {
                    confirmButtonAction()
                }
            )
        },
        dismissButton = {
            Text(
                text = "Cancel",
                modifier = Modifier.clickable {
                    dismiss()
                }
            )
        }
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AlertInputPreview() {
    AlertInput(
        textValue = "Content",
        title = "Title",
        dismiss = {},
        confirmButtonAction = {},
        textValueChange = {}
    )
}