package com.cse344group10.disastermanagement.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.models.map.EmergencyDepot
import com.cse344group10.disastermanagement.models.map.MedicalSupplyDepot
import com.cse344group10.disastermanagement.models.map.SarCenter
import com.cse344group10.disastermanagement.models.token.SelectedBuildingInfo
import com.cse344group10.disastermanagement.models.token.SessionToken
import com.cse344group10.disastermanagement.models.user.User
import com.cse344group10.disastermanagement.ui.uimodels.AlertInput
import com.cse344group10.disastermanagement.ui.viewmodels.home.ViewDetailedBuildingInfoScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewDetailedBuildingInfoScreen(
    backAction: () -> Unit = {}
) {
    val viewModel:ViewDetailedBuildingInfoScreenViewModel = viewModel()

    val isEmergencyDepotClickable = viewModel.isEmergencyDepotClickable.observeAsState(false)
    if(
        SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.EmergencySupplyManager ||
        SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.SystemAdministrator
    ) {
        viewModel.updateIsEmergencyDepotClickable(true)
    }

    val isMedicalSupplyDepotClickableForHuman = viewModel.isMedicalSupplyDepotClickableForHuman.observeAsState(false)
    if(
        SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.HumanMedicalManager ||
        SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.SystemAdministrator
    ) {
        viewModel.updateIsMedicalSupplyDepotClickableForHuman(true)
    }
    val isMedicalSupplyDepotClickableForAnimal = viewModel.isMedicalSupplyDepotClickableForAnimal.observeAsState(false)
    if(
        SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.AnimalMedicalManager ||
        SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.SystemAdministrator
    ) {
        viewModel.updateIsMedicalSupplyDepotClickableForAnimal(true)
    }

    val isSarCenterClickableForTeam = viewModel.isSarCenterClickableForTeam.observeAsState(false)
    if(
        SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.HRManagerSAR ||
        SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.SystemAdministrator
    ) {
        viewModel.updateIsSarCenterClickableForTeam(true)
    }
    val isSarCenterClickableForEquipment = viewModel.isSarCenterClickableForEquipment.observeAsState(false)
    if(
        SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.EquipmentManagerSAR ||
        SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.SystemAdministrator
    ) {
        viewModel.updateIsSarCenterClickableForEquipment(true)
    }

    val repo = DbRepo(LocalContext.current)

    val foodUnitsDialog = viewModel.foodUnitsDialog.observeAsState(false)
    val foodUnitsValue = viewModel.foodUnitsValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.foodCount.toString()
            }
            else -> ""
        }
    )

    val mobileSheltersDialog = viewModel.mobileSheltersDialog.observeAsState(false)
    val mobileSheltersValue = viewModel.mobileSheltersValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.shelterCount.toString()
            }
            else -> ""
        }
    )

    val transportVehiclesDialog = viewModel.transportVehiclesDialog.observeAsState(false)
    val transportVehiclesValue = viewModel.transportVehiclesValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.transportVehicleCount.toString()
            }
            else -> ""
        }
    )

    val utilCountDialog = viewModel.utilCountDialog.observeAsState(false)
    val utilCountValue = viewModel.utilCountValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.utilCount.toString()
            }
            else -> ""
        }
    )

    val babySuppliesDialog = viewModel.babySuppliesDialog.observeAsState(false)
    val babySuppliesValue = viewModel.babySuppliesValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.babySupplyCount.toString()
            }
            else -> ""
        }
    )

    val serumAmountDialog = viewModel.serumAmountDialog.observeAsState(false)
    val serumAmountValue = viewModel.serumAmountValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.liquidAmount.toString()
            }
            else -> ""
        }
    )

    val waterAmountDialog = viewModel.waterAmountDialog.observeAsState(false)
    val waterAmountValue = viewModel.waterAmountValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.waterAmount.toString()
            }
            else -> ""
        }
    )

    val portableToiletsDialog = viewModel.portableToiletsDialog.observeAsState(false)
    val portableToiletsValue = viewModel.portableToiletsValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.toiletCount.toString()
            }
            else -> ""
        }
    )

    val portableBatteriesDialog = viewModel.portableBatteriesDialog.observeAsState(false)
    val portableBatteriesValue = viewModel.portableBatteriesValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.powerSupplyCount.toString()
            }
            else -> ""
        }
    )

    val humanMedicineADialog = viewModel.humanMedicineADialog.observeAsState(false)
    val humanMedicineAValue = viewModel.humanMedicineAValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is MedicalSupplyDepot -> {
                building.medicineHumanACount.toString()
            }
            else -> ""
        }
    )

    val humanMedicineBDialog = viewModel.humanMedicineBDialog.observeAsState(false)
    val humanMedicineBValue = viewModel.humanMedicineBValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is MedicalSupplyDepot -> {
                building.medicineHumanBCount.toString()
            }
            else -> ""
        }
    )

    val humanMedicineCDialog = viewModel.humanMedicineCDialog.observeAsState(false)
    val humanMedicineCValue = viewModel.humanMedicineCValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is MedicalSupplyDepot -> {
                building.medicineHumanCCount.toString()
            }
            else -> ""
        }
    )

    val animalMedicineADialog = viewModel.animalMedicineADialog.observeAsState(false)
    val animalMedicineAValue = viewModel.animalMedicineAValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is MedicalSupplyDepot -> {
                building.medicineAnimalACount.toString()
            }
            else -> ""
        }
    )

    val animalMedicineBDialog = viewModel.animalMedicineBDialog.observeAsState(false)
    val animalMedicineBValue = viewModel.animalMedicineBValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is MedicalSupplyDepot -> {
                building.medicineAnimalBCount.toString()
            }
            else -> ""
        }
    )

    val animalMedicineCDialog = viewModel.animalMedicineCDialog.observeAsState(false)
    val animalMedicineCValue = viewModel.animalMedicineCValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is MedicalSupplyDepot -> {
                building.medicineAnimalCCount.toString()
            }
            else -> ""
        }
    )

    val sarTeamCountDialog = viewModel.sarTeamCountDialog.observeAsState(false)
    val sarTeamCountValue = viewModel.sarTeamCountValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is SarCenter -> {
                building.sarHumanCount.toString()
            }
            else -> ""
        }
    )

    val sarVehicleCountDialog = viewModel.sarVehicleCountDialog.observeAsState(false)
    val sarVehicleCountValue = viewModel.sarVehicleCountValue.observeAsState(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is SarCenter -> {
                building.sarVehicleCount.toString()
            }
            else -> ""
        }
    )

    if (foodUnitsDialog.value) {
        AlertInput(
            title = "Enter new food unit number",
            textValue = foodUnitsValue.value,
            confirmButtonAction = {
                viewModel.updateEmergencySupplyDepot(repo)
                viewModel.updateFoodUnitsDialog(false)
            },
            dismiss = {
                viewModel.updateFoodUnitsDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateFoodUnitsValue(name)
            }
        )
    }

    if (mobileSheltersDialog.value) {
        AlertInput(
            title = "Enter new mobile shelter number",
            textValue = mobileSheltersValue.value,
            confirmButtonAction = {
                viewModel.updateEmergencySupplyDepot(repo)
                viewModel.updateMobileSheltersDialog(false)
            },
            dismiss = {
                viewModel.updateMobileSheltersDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateMobileSheltersValue(name)
            }
        )
    }

    if (transportVehiclesDialog.value) {
        AlertInput(
            title = "Enter new transport vehicle number",
            textValue = transportVehiclesValue.value,
            confirmButtonAction = {
                viewModel.updateEmergencySupplyDepot(repo)
                viewModel.updateTransportVehiclesDialog(false)
            },
            dismiss = {
                viewModel.updateTransportVehiclesDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateTransportVehiclesValue(name)
            }
        )
    }

    if (utilCountDialog.value) {
        AlertInput(
            title = "Enter new util count number",
            textValue = utilCountValue.value,
            confirmButtonAction = {
                viewModel.updateEmergencySupplyDepot(repo)
                viewModel.updateUtilCountDialog(false)
            },
            dismiss = {
                viewModel.updateUtilCountDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateUtilCountValue(name)
            }
        )
    }

    if (babySuppliesDialog.value) {
        AlertInput(
            title = "Enter new baby supply number",
            textValue = babySuppliesValue.value,
            confirmButtonAction = {
                viewModel.updateEmergencySupplyDepot(repo)
                viewModel.updateBabySuppliesDialog(false)
            },
            dismiss = {
                viewModel.updateBabySuppliesDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateBabySuppliesValue(name)
            }
        )
    }

    if (serumAmountDialog.value) {
        AlertInput(
            title = "Enter new serum amount number",
            textValue = serumAmountValue.value,
            confirmButtonAction = {
                viewModel.updateEmergencySupplyDepot(repo)
                viewModel.updateSerumAmountDialog(false)
            },
            dismiss = {
                viewModel.updateSerumAmountDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateSerumAmountValue(name)
            }
        )
    }

    if (waterAmountDialog.value) {
        AlertInput(
            title = "Enter new water amount number",
            textValue = waterAmountValue.value,
            confirmButtonAction = {
                viewModel.updateEmergencySupplyDepot(repo)
                viewModel.updateWaterAmountDialog(false)
            },
            dismiss = {
                viewModel.updateWaterAmountDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateWaterAmountValue(name)
            }
        )
    }

    if (portableToiletsDialog.value) {
        AlertInput(
            title = "Enter new portable toilet number",
            textValue = portableToiletsValue.value,
            confirmButtonAction = {
                viewModel.updateEmergencySupplyDepot(repo)
                viewModel.updatePortableToiletsDialog(false)
            },
            dismiss = {
                viewModel.updatePortableToiletsDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updatePortableToiletsValue(name)
            }
        )
    }

    if (portableBatteriesDialog.value) {
        AlertInput(
            title = "Enter new portable battery number",
            textValue = portableBatteriesValue.value,
            confirmButtonAction = {
                viewModel.updateEmergencySupplyDepot(repo)
                viewModel.updatePortableBatteriesDialog(false)
            },
            dismiss = {
                viewModel.updatePortableBatteriesDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updatePortableBatteriesValue(name)
            }
        )
    }

    if (humanMedicineADialog.value) {
        AlertInput(
            title = "Enter new human medicine A number",
            textValue = humanMedicineAValue.value,
            confirmButtonAction = {
                viewModel.updateMedicalSupplyDepot(repo)
                viewModel.updateHumanMedicineADialog(false)
            },
            dismiss = {
                viewModel.updateHumanMedicineADialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateHumanMedicineAValue(name)
            }
        )
    }

    if (humanMedicineBDialog.value) {
        AlertInput(
            title = "Enter new human medicine B number",
            textValue = humanMedicineBValue.value,
            confirmButtonAction = {
                viewModel.updateMedicalSupplyDepot(repo)
                viewModel.updateHumanMedicineBDialog(false)
            },
            dismiss = {
                viewModel.updateHumanMedicineBDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateHumanMedicineBValue(name)
            }
        )
    }

    if (humanMedicineCDialog.value) {
        AlertInput(
            title = "Enter new human medicine C number",
            textValue = humanMedicineCValue.value,
            confirmButtonAction = {
                viewModel.updateMedicalSupplyDepot(repo)
                viewModel.updateHumanMedicineCDialog(false)
            },
            dismiss = {
                viewModel.updateHumanMedicineCDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateHumanMedicineCValue(name)
            }
        )
    }

    if (animalMedicineADialog.value) {
        AlertInput(
            title = "Enter new animal medicine A number",
            textValue = animalMedicineAValue.value,
            confirmButtonAction = {
                viewModel.updateMedicalSupplyDepot(repo)
                viewModel.updateAnimalMedicineADialog(false)
            },
            dismiss = {
                viewModel.updateAnimalMedicineADialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateAnimalMedicineAValue(name)
            }
        )
    }

    if (animalMedicineBDialog.value) {
        AlertInput(
            title = "Enter new animal medicine B number",
            textValue = animalMedicineBValue.value,
            confirmButtonAction = {
                viewModel.updateMedicalSupplyDepot(repo)
                viewModel.updateAnimalMedicineBDialog(false)
            },
            dismiss = {
                viewModel.updateAnimalMedicineBDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateAnimalMedicineBValue(name)
            }
        )
    }

    if (animalMedicineCDialog.value) {
        AlertInput(
            title = "Enter new animal medicine C number",
            textValue = animalMedicineCValue.value,
            confirmButtonAction = {
                viewModel.updateMedicalSupplyDepot(repo)
                viewModel.updateAnimalMedicineCDialog(false)
            },
            dismiss = {
                viewModel.updateAnimalMedicineCDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateAnimalMedicineCValue(name)
            }
        )
    }

    if (sarTeamCountDialog.value) {
        AlertInput(
            title = "Enter new SAR Team Count number",
            textValue = sarTeamCountValue.value,
            confirmButtonAction = {
                viewModel.updateSarCenter(repo)
                viewModel.updateSarTeamCountDialog(false)
            },
            dismiss = {
                viewModel.updateSarTeamCountDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateSarTeamCountValue(name)
            }
        )
    }

    if (sarVehicleCountDialog.value) {
        AlertInput(
            title = "Enter new SAR Vehicle Count number",
            textValue = sarVehicleCountValue.value,
            confirmButtonAction = {
                viewModel.updateSarCenter(repo)
                viewModel.updateSarVehicleCountDialog(false)
            },
            dismiss = {
                viewModel.updateSarVehicleCountDialog(false)
            },
            textValueChange = {
                    name -> viewModel.updateSarVehicleCountValue(name)
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Building Id ${SelectedBuildingInfo.selectedBuilding!!.buildingId}") },
                navigationIcon = {
                    IconButton(onClick = backAction) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            val building = SelectedBuildingInfo.selectedBuilding!!

            item {
                Card(
                    modifier = Modifier.padding(5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Construction Year",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(10.dp)
                        )
                        Text(
                            text = "${building.buildingYear}",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.padding(5.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Building Location",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(10.dp)
                        )
                        Text(
                            text = "${building.buildingI} , ${building.buildingJ}",
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }

            when (building) {
                is EmergencyDepot -> {
                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Emergency Depot Id",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Text(
                                    text = "${building.emergencyInfoId}",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isEmergencyDepotClickable.value,
                                        onClick = { viewModel.updateFoodUnitsDialog(true) }

                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,

                            ) {
                                Text(
                                    text = "Food Units",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Box {
                                    Row(verticalAlignment = Alignment.CenterVertically){
                                        Text(
                                            text = "${building.foodCount}",
                                            textAlign = TextAlign.Justify,
                                            style = MaterialTheme.typography.labelLarge,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                        if(isEmergencyDepotClickable.value) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowForward,
                                                contentDescription = null,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                    }

                                }

                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isEmergencyDepotClickable.value,
                                        onClick = { viewModel.updateMobileSheltersDialog(true) }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Mobile Shelters",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Box {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${building.shelterCount}",
                                            textAlign = TextAlign.Justify,
                                            style = MaterialTheme.typography.labelLarge,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                        if(isEmergencyDepotClickable.value) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowForward,
                                                contentDescription = null,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }

                                    }
                                }

                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isEmergencyDepotClickable.value,
                                        onClick = { viewModel.updateTransportVehiclesDialog(true) }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Transport Vehicles",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Box {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${building.transportVehicleCount}",
                                            textAlign = TextAlign.Justify,
                                            style = MaterialTheme.typography.labelLarge,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                        if(isEmergencyDepotClickable.value) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowForward,
                                                contentDescription = null,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isEmergencyDepotClickable.value,
                                        onClick = { viewModel.updateUtilCountDialog(true) }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Util Count",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Box {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${building.utilCount}",
                                            textAlign = TextAlign.Justify,
                                            style = MaterialTheme.typography.labelLarge,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                        if(isEmergencyDepotClickable.value) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowForward,
                                                contentDescription = null,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isEmergencyDepotClickable.value,
                                        onClick = { viewModel.updateBabySuppliesDialog(true) }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Baby Supplies",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Box {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${building.babySupplyCount}",
                                            textAlign = TextAlign.Justify,
                                            style = MaterialTheme.typography.labelLarge,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                        if(isEmergencyDepotClickable.value) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowForward,
                                                contentDescription = null,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isEmergencyDepotClickable.value,
                                        onClick = { viewModel.updateSerumAmountDialog(true) }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Serum Amount",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Box {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${building.liquidAmount} L",
                                            textAlign = TextAlign.Justify,
                                            style = MaterialTheme.typography.labelLarge,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                        if(isEmergencyDepotClickable.value) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowForward,
                                                contentDescription = null,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isEmergencyDepotClickable.value,
                                        onClick = { viewModel.updateWaterAmountDialog(true) }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Water Amount",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Box {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${building.waterAmount} L",
                                            textAlign = TextAlign.Justify,
                                            style = MaterialTheme.typography.labelLarge,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                        if(isEmergencyDepotClickable.value) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowForward,
                                                contentDescription = null,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isEmergencyDepotClickable.value,
                                        onClick = { viewModel.updatePortableToiletsDialog(true) }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Portable Toilets",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Box {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${building.toiletCount}",
                                            textAlign = TextAlign.Justify,
                                            style = MaterialTheme.typography.labelLarge,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                        if(isEmergencyDepotClickable.value) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowForward,
                                                contentDescription = null,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isEmergencyDepotClickable.value,
                                        onClick = { viewModel.updatePortableBatteriesDialog(true) }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Portable Batteries",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Box {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${building.powerSupplyCount}",
                                            textAlign = TextAlign.Justify,
                                            style = MaterialTheme.typography.labelLarge,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                        if(isEmergencyDepotClickable.value) {
                                            Icon(
                                                imageVector = Icons.Filled.ArrowForward,
                                                contentDescription = null,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

                is MedicalSupplyDepot -> {
                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Medical Supply Depot Id",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Text(
                                    text = "${building.medicalInfoId}",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isMedicalSupplyDepotClickableForHuman.value,
                                        onClick = { viewModel.updateHumanMedicineADialog(true) }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Human Medicine A",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                if (SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.User) {
                                    Icon(
                                        imageVector = if (building.medicineHumanACount != 0) Icons.Filled.Check else Icons.Filled.Close,
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                } else {
                                    Box {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "${building.medicineHumanACount}",
                                                textAlign = TextAlign.Justify,
                                                style = MaterialTheme.typography.labelLarge,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                            if(isMedicalSupplyDepotClickableForHuman.value) {
                                                Icon(
                                                    imageVector = Icons.Filled.ArrowForward,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(10.dp)
                                                )
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isMedicalSupplyDepotClickableForHuman.value,
                                        onClick = { viewModel.updateHumanMedicineBDialog(true) }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Human Medicine B",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                if (SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.User) {
                                    Icon(
                                        imageVector = if (building.medicineHumanBCount != 0) Icons.Filled.Check else Icons.Filled.Close,
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                } else {
                                    Box {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "${building.medicineHumanBCount}",
                                                textAlign = TextAlign.Justify,
                                                style = MaterialTheme.typography.labelLarge,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                            if(isMedicalSupplyDepotClickableForHuman.value) {
                                                Icon(
                                                    imageVector = Icons.Filled.ArrowForward,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(10.dp)
                                                )
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isMedicalSupplyDepotClickableForHuman.value,
                                        onClick = { viewModel.updateHumanMedicineCDialog(true) }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Human Medicine C",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                if (SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.User) {
                                    Icon(
                                        imageVector = if (building.medicineHumanCCount != 0) Icons.Filled.Check else Icons.Filled.Close,
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                } else {
                                    Box {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "${building.medicineHumanCCount}",
                                                textAlign = TextAlign.Justify,
                                                style = MaterialTheme.typography.labelLarge,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                            if(isMedicalSupplyDepotClickableForHuman.value) {
                                                Icon(
                                                    imageVector = Icons.Filled.ArrowForward,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(10.dp)
                                                )
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isMedicalSupplyDepotClickableForAnimal.value,
                                        onClick = { viewModel.updateAnimalMedicineADialog(true) }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = "Animal Medicine A",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                if (SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.User) {
                                    Icon(
                                        imageVector = if (building.medicineAnimalACount != 0) Icons.Filled.Check else Icons.Filled.Close,
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                } else {
                                    Box {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "${building.medicineAnimalACount}",
                                                textAlign = TextAlign.Justify,
                                                style = MaterialTheme.typography.labelLarge,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                            if(isMedicalSupplyDepotClickableForAnimal.value) {
                                                Icon(
                                                    imageVector = Icons.Filled.ArrowForward,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(10.dp)
                                                )
                                            }
                                        }
                                    }

                                }

                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isMedicalSupplyDepotClickableForAnimal.value,
                                        onClick = { viewModel.updateAnimalMedicineBDialog(true) }
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Animal Medicine B",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                if (SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.User) {
                                    Icon(
                                        imageVector = if (building.medicineAnimalBCount != 0) Icons.Filled.Check else Icons.Filled.Close,
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                } else {
                                    Box {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "${building.medicineAnimalBCount}",
                                                textAlign = TextAlign.Justify,
                                                style = MaterialTheme.typography.labelLarge,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                            if(isMedicalSupplyDepotClickableForAnimal.value) {
                                                Icon(
                                                    imageVector = Icons.Filled.ArrowForward,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(10.dp)
                                                )
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(
                                        enabled = isMedicalSupplyDepotClickableForAnimal.value,
                                        onClick = { viewModel.updateAnimalMedicineCDialog(true) }
                                    ),

                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Animal Medicine C",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                if (SessionToken.currentlyLoggedUser!!.userPermissionLevel == User.PermissionLevel.User) {
                                    Icon(
                                        imageVector = if (building.medicineAnimalCCount != 0) Icons.Filled.Check else Icons.Filled.Close,
                                        contentDescription = null,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                } else {
                                    Box {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "${building.medicineAnimalCCount}",
                                                textAlign = TextAlign.Justify,
                                                style = MaterialTheme.typography.labelLarge,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                            if(isMedicalSupplyDepotClickableForAnimal.value) {
                                                Icon(
                                                    imageVector = Icons.Filled.ArrowForward,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(10.dp)
                                                )
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }

                is SarCenter -> {
                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "SAR Center Id",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Text(
                                    text = "${building.sarInfoId}",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }

                    if (SessionToken.currentlyLoggedUser!!.userPermissionLevel != User.PermissionLevel.User) {
                        item {
                            Card(
                                modifier = Modifier.padding(5.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            enabled = isSarCenterClickableForTeam.value,
                                            onClick = { viewModel.updateSarTeamCountDialog(true) }
                                        ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "SAR Team Count",
                                        textAlign = TextAlign.Justify,
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    Box {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "${building.sarHumanCount}",
                                                textAlign = TextAlign.Justify,
                                                style = MaterialTheme.typography.labelLarge,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                            if(isSarCenterClickableForTeam.value) {
                                                Icon(
                                                    imageVector = Icons.Filled.ArrowForward,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(10.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (SessionToken.currentlyLoggedUser!!.userPermissionLevel != User.PermissionLevel.User) {
                        item {
                            Card(
                                modifier = Modifier.padding(5.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            enabled = isSarCenterClickableForEquipment.value,
                                            onClick = { viewModel.updateSarVehicleCountDialog(true) }
                                        ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "SAR Vehicle Count",
                                        textAlign = TextAlign.Justify,
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                    Box {
                                        Row(verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "${building.sarVehicleCount}",
                                                textAlign = TextAlign.Justify,
                                                style = MaterialTheme.typography.labelLarge,
                                                modifier = Modifier.padding(10.dp)
                                            )
                                            if(isSarCenterClickableForEquipment.value) {
                                                Icon(
                                                    imageVector = Icons.Filled.ArrowForward,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(10.dp)
                                                )
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }

                else -> {
                    item {
                        Card(
                            modifier = Modifier.padding(5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Resident Count",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.headlineSmall,
                                    modifier = Modifier.padding(10.dp)
                                )
                                Text(
                                    text = "${building.residentCount}",
                                    textAlign = TextAlign.Justify,
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}