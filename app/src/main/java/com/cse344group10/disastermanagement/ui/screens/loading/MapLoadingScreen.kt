package com.cse344group10.disastermanagement.ui.screens.loading

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.models.map.Building
import com.cse344group10.disastermanagement.models.map.EmergencyDepot
import com.cse344group10.disastermanagement.models.map.MedicalSupplyDepot
import com.cse344group10.disastermanagement.models.map.SarCenter
import com.cse344group10.disastermanagement.ui.viewmodels.loading.MapLoadingScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapLoadingScreen(
    nextPage: () -> Unit
) {
    val repo = DbRepo(LocalContext.current)
    BackHandler {
        // do nothing when back action is used
    }

    val viewModel : MapLoadingScreenViewModel = viewModel()

    val showProgress = viewModel.showProgress.observeAsState(false)

    val expandDropDownList = viewModel.expandDropDownList.observeAsState(false)
    val selectedDropDownItem = viewModel.selectedDropDownItem.observeAsState("Sector 0")
    val textFilledSize = viewModel.textFilledSize.observeAsState(Size.Zero)
    val dropDownIcon = if (expandDropDownList.value) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    val showAlertDialog = viewModel.showAlertDialog.observeAsState(false)

    LaunchedEffect(key1 = true) {
        viewModel.populateBuildingDataBase(repo)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
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
                            .clickable { viewModel.alertDialogShow(currentBuilding) },
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

            if (showAlertDialog.value) {
                AlertDialog(
                    onDismissRequest = { viewModel.alertDialogHide() },
                    title = { Text(text = "BuildingId = ${viewModel.alertDialogBuilding.buildingId}") },
                    text = { AlertDialogText(alertDialogBuilding = viewModel.alertDialogBuilding) },
                    confirmButton = {
                        Text(
                            modifier = Modifier.clickable { viewModel.alertDialogHide() },
                            text = "OK",
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }

            Button(
                onClick = nextPage
            ) {
                Text(text = "Next")
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

@Composable
private fun AlertDialogText(alertDialogBuilding: Building) {
    when(alertDialogBuilding) {
        is EmergencyDepot -> {
            Text(
                text = "Building Year = ${alertDialogBuilding.buildingYear}\n" +
                        "Building Type = Emergency Supply Depot\n" +
                        "Building Durability = ${alertDialogBuilding.buildingDurability}\n" +
                        "Building I Coordinate = ${alertDialogBuilding.buildingI}\n" +
                        "Building J Coordinate = ${alertDialogBuilding.buildingJ}\n" +
                        "Emergency Supply Depot Id = ${alertDialogBuilding.emergencyInfoId}\n" +
                        "Food Count = ${alertDialogBuilding.foodCount}\n" +
                        "Shelter Count = ${alertDialogBuilding.shelterCount}\n" +
                        "Transport Vehicle Count = ${alertDialogBuilding.transportVehicleCount}\n" +
                        "Util Count = ${alertDialogBuilding.utilCount}\n" +
                        "Baby Supply Count = ${alertDialogBuilding.babySupplyCount}\n" +
                        "Liquid Amount = ${alertDialogBuilding.liquidAmount}\n" +
                        "Water Amount = ${alertDialogBuilding.waterAmount}\n" +
                        "Toilet Count = ${alertDialogBuilding.toiletCount}\n" +
                        "Power Supply Count = ${alertDialogBuilding.powerSupplyCount}"
            )
        }
        is MedicalSupplyDepot -> {
            Text(
                text = "Building Year = ${alertDialogBuilding.buildingYear}\n" +
                        "Building Type = Medical Center\n" +
                        "Building Durability = ${alertDialogBuilding.buildingDurability}\n" +
                        "Building I Coordinate = ${alertDialogBuilding.buildingI}\n" +
                        "Building J Coordinate = ${alertDialogBuilding.buildingJ}\n" +
                        "Medical Center Id = ${alertDialogBuilding.medicalInfoId}\n" +
                        "Medicine Animal A = ${alertDialogBuilding.medicineAnimalACount}\n" +
                        "Medicine Animal B = ${alertDialogBuilding.medicineAnimalBCount}\n" +
                        "Medicine Animal C = ${alertDialogBuilding.medicineAnimalCCount}\n" +
                        "Medicine Human A = ${alertDialogBuilding.medicineHumanACount}\n" +
                        "Medicine Human B = ${alertDialogBuilding.medicineHumanBCount}\n" +
                        "Medicine Human C = ${alertDialogBuilding.medicineHumanCCount}"
            )
        }
        is SarCenter -> {
            Text(
                text = "Building Year = ${alertDialogBuilding.buildingYear}\n" +
                        "Building Type = SAR Center\n" +
                        "Building Durability = ${alertDialogBuilding.buildingDurability}\n" +
                        "Building I Coordinate = ${alertDialogBuilding.buildingI}\n" +
                        "Building J Coordinate = ${alertDialogBuilding.buildingJ}\n" +
                        "SAR Center Id = ${alertDialogBuilding.sarInfoId}\n" +
                        "SAR Human Count = ${alertDialogBuilding.sarHumanCount}\n" +
                        "SAR Vehicle Count = ${alertDialogBuilding.sarVehicleCount}"
            )
        }
        else -> {
            Text(
                text = "Building Year = ${alertDialogBuilding.buildingYear}\n" +
                        "Building Resident Count = ${alertDialogBuilding.residentCount}\n" +
                        "Building Type = Normal Building\n" +
                        "Building Durability = ${alertDialogBuilding.buildingDurability}\n" +
                        "Building I Coordinate = ${alertDialogBuilding.buildingI}\n" +
                        "Building J Coordinate = ${alertDialogBuilding.buildingJ}"
            )
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMapLoadingScreen() {
    MapLoadingScreen {}
}