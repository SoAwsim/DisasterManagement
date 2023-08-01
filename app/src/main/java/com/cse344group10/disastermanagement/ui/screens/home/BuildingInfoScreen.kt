package com.cse344group10.disastermanagement.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.models.map.EmergencyDepot
import com.cse344group10.disastermanagement.models.map.MedicalSupplyDepot
import com.cse344group10.disastermanagement.models.map.SarCenter
import com.cse344group10.disastermanagement.models.token.SelectedBuildingInfo
import com.cse344group10.disastermanagement.ui.viewmodels.home.BuildingInfoScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildingInfoScreen(
    modifier: Modifier = Modifier,
    navigateDetailedBuilding: () -> Unit = {}
) {
    val viewModel: BuildingInfoScreenViewModel = viewModel()
    val repo = DbRepo(LocalContext.current)

    val showProgress = viewModel.showProgress.observeAsState(false)

    val expandDropDownList = viewModel.expandDropDownList.observeAsState(false)
    val textFilledSize = viewModel.textFilledSize.observeAsState(Size.Zero)
    val selectedDropDownItem = viewModel.selectedDropDownItem.observeAsState("Sector 0")
    val dropDownIcon = if (expandDropDownList.value) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    LaunchedEffect(key1 = true) {
        viewModel.populateBuildingDataBase(repo)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showProgress.value) {
            Box(modifier = Modifier.padding(all = 10.dp)) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.updateExpandDropList(!expandDropDownList.value) }
                        .onGloballyPositioned {
                            viewModel.updateTextFilledSize(it.size.toSize())
                        },
                    value = selectedDropDownItem.value,
                    onValueChange = { viewModel.updateSelectedDropDownItem(it) },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable { viewModel.updateExpandDropList(!expandDropDownList.value) },
                            imageVector = dropDownIcon,
                            contentDescription = null
                        )
                    },
                    readOnly = true,
                    enabled = false,
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
                    modifier = Modifier
                        .width(with(LocalDensity.current) {textFilledSize.value.width.toDp()}),
                    expanded = expandDropDownList.value,
                    onDismissRequest = { viewModel.updateExpandDropList(false) }
                ) {
                    viewModel.dropDownList.forEach { label ->
                        DropdownMenuItem(
                            onClick = { viewModel.dropDownMenuItemSelect(label) },
                            text = { Text(text = label) }
                        )
                    }
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(5)
            ) {
                items(viewModel.buildingUiElements) { currentBuilding ->
                    Card(
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .size(50.dp)
                            .clickable {
                                SelectedBuildingInfo.selectedBuilding = currentBuilding
                                navigateDetailedBuilding()
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        val imageVector: ImageVector = when (currentBuilding) {
                            is EmergencyDepot -> Icons.Filled.Menu
                            is MedicalSupplyDepot -> Icons.Filled.ThumbUp
                            is SarCenter -> Icons.Filled.Warning
                            else -> Icons.Filled.Home
                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = imageVector,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .padding(all = 10.dp)
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Generating Map Data Please Wait..",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BuildingInfoPreview() {
    BuildingInfoScreen()
}