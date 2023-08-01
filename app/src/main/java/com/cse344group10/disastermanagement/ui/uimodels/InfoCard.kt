package com.cse344group10.disastermanagement.ui.uimodels

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoCard(
    imageVector: ImageVector,
    contentDescription: String?,
    textContent: String,
    fontSize: TextUnit,
    iconSize: Dp,
    cardSize: Dp
) {
    Card (
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.size(cardSize).padding(10.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                modifier = Modifier.size(iconSize)
            )
            Text(
                text = textContent,
                fontSize = fontSize
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun InfoCardPreview() {
    InfoCard(Icons.Filled.Home, null, "100", 25.sp, 80.dp, 100.dp)
}