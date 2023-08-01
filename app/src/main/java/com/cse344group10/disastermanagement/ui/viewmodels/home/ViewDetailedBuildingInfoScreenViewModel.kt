package com.cse344group10.disastermanagement.ui.viewmodels.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cse344group10.disastermanagement.database.repository.DbRepo
import com.cse344group10.disastermanagement.models.map.EmergencyDepot
import com.cse344group10.disastermanagement.models.map.MedicalSupplyDepot
import com.cse344group10.disastermanagement.models.map.SarCenter
import com.cse344group10.disastermanagement.models.token.SelectedBuildingInfo
import kotlinx.coroutines.launch

class ViewDetailedBuildingInfoScreenViewModel: ViewModel() {

    val isEmergencyDepotClickable = MutableLiveData(false)

    val isMedicalSupplyDepotClickableForHuman = MutableLiveData(false)
    val isMedicalSupplyDepotClickableForAnimal = MutableLiveData(false)

    val isSarCenterClickableForTeam = MutableLiveData(false)
    val isSarCenterClickableForEquipment = MutableLiveData(false)

    val foodUnitsDialog = MutableLiveData(false)
    val foodUnitsValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.foodCount.toString()
            }
            else -> ""
        }
    )

    val mobileSheltersDialog = MutableLiveData(false)
    val mobileSheltersValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.shelterCount.toString()
            }
            else -> ""
        }
    )

    val transportVehiclesDialog = MutableLiveData(false)
    val transportVehiclesValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.transportVehicleCount.toString()
            }
            else -> ""
        }
    )

    val utilCountDialog = MutableLiveData(false)
    val utilCountValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.utilCount.toString()
            }
            else -> ""
        }
    )

    val babySuppliesDialog = MutableLiveData(false)
    val babySuppliesValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.babySupplyCount.toString()
            }
            else -> ""
        }
    )

    val serumAmountDialog = MutableLiveData(false)
    val serumAmountValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.liquidAmount.toString()
            }
            else -> ""
        }
    )

    val waterAmountDialog = MutableLiveData(false)
    val waterAmountValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.waterAmount.toString()
            }
            else -> ""
        }
    )

    val portableToiletsDialog = MutableLiveData(false)
    val portableToiletsValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.toiletCount.toString()
            }
            else -> ""
        }
    )

    val portableBatteriesDialog = MutableLiveData(false)
    val portableBatteriesValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is EmergencyDepot -> {
                building.powerSupplyCount.toString()
            }
            else -> ""
        }
    )

    val humanMedicineADialog = MutableLiveData(false)
    val humanMedicineAValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is MedicalSupplyDepot -> {
                building.medicineHumanACount.toString()
            }
            else -> ""
        }
    )

    val humanMedicineBDialog = MutableLiveData(false)
    val humanMedicineBValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is MedicalSupplyDepot -> {
                building.medicineHumanBCount.toString()
            }
            else -> ""
        }
    )

    val humanMedicineCDialog = MutableLiveData(false)
    val humanMedicineCValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is MedicalSupplyDepot -> {
                building.medicineHumanCCount.toString()
            }
            else -> ""
        }
    )

    val animalMedicineADialog = MutableLiveData(false)
    val animalMedicineAValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is MedicalSupplyDepot -> {
                building.medicineAnimalACount.toString()
            }
            else -> ""
        }
    )

    val animalMedicineBDialog = MutableLiveData(false)
    val animalMedicineBValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is MedicalSupplyDepot -> {
                building.medicineAnimalBCount.toString()
            }
            else -> ""
        }
    )

    val animalMedicineCDialog = MutableLiveData(false)
    val animalMedicineCValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is MedicalSupplyDepot -> {
                building.medicineAnimalCCount.toString()
            }
            else -> ""
        }
    )

    val sarTeamCountDialog = MutableLiveData(false)
    val sarTeamCountValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is SarCenter -> {
                building.sarHumanCount.toString()
            }
            else -> ""
        }
    )

    val sarVehicleCountDialog = MutableLiveData(false)
    val sarVehicleCountValue = MutableLiveData(
        when (val building = SelectedBuildingInfo.selectedBuilding!!) {
            is SarCenter -> {
                building.sarVehicleCount.toString()
            }
            else -> ""
        }
    )

    fun updateSarCenter(repo: DbRepo) {
        viewModelScope.launch {
            val building = SelectedBuildingInfo.selectedBuilding!!
            if (building is SarCenter) {
                building.sarHumanCount = sarTeamCountValue.value!!.toInt()
                building.sarVehicleCount = sarVehicleCountValue.value!!.toInt()
            }
            repo.updateBuilding(building)
        }
    }

    fun updateSarVehicleCountValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            sarVehicleCountValue.value = value
        }
    }

    fun updateSarVehicleCountDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        sarVehicleCountValue.value = if (building is SarCenter) building.sarVehicleCount.toString() else sarVehicleCountValue.value
        sarVehicleCountDialog.value = state
    }

    fun updateSarTeamCountValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            sarTeamCountValue.value = value
        }
    }

    fun updateSarTeamCountDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        sarTeamCountValue.value = if (building is SarCenter) building.sarHumanCount.toString() else sarTeamCountValue.value
        sarTeamCountDialog.value = state
    }

    fun updateMedicalSupplyDepot(repo: DbRepo) {
        viewModelScope.launch {
            val building = SelectedBuildingInfo.selectedBuilding!!
            if (building is MedicalSupplyDepot) {
                building.medicineHumanACount = humanMedicineAValue.value!!.toInt()
                building.medicineHumanBCount = humanMedicineBValue.value!!.toInt()
                building.medicineHumanCCount = humanMedicineCValue.value!!.toInt()
                building.medicineAnimalACount = animalMedicineAValue.value!!.toInt()
                building.medicineAnimalBCount = animalMedicineBValue.value!!.toInt()
                building.medicineAnimalCCount = animalMedicineCValue.value!!.toInt()
            }
            repo.updateBuilding(building)
        }
    }

    fun updateAnimalMedicineCValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            animalMedicineCValue.value = value
        }
    }

    fun updateAnimalMedicineCDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        animalMedicineCValue.value = if (building is MedicalSupplyDepot) building.medicineAnimalCCount.toString() else animalMedicineCValue.value
        animalMedicineCDialog.value = state
    }


    fun updateAnimalMedicineBValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            animalMedicineBValue.value = value
        }
    }

    fun updateAnimalMedicineBDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        animalMedicineBValue.value = if (building is MedicalSupplyDepot) building.medicineAnimalBCount.toString() else animalMedicineBValue.value
        animalMedicineBDialog.value = state
    }

    fun updateAnimalMedicineAValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            animalMedicineAValue.value = value
        }
    }

    fun updateAnimalMedicineADialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        animalMedicineAValue.value = if (building is MedicalSupplyDepot) building.medicineAnimalACount.toString() else animalMedicineAValue.value
        animalMedicineADialog.value = state
    }


    fun updateHumanMedicineCValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            humanMedicineCValue.value = value
        }
    }

    fun updateHumanMedicineCDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        humanMedicineCValue.value = if (building is MedicalSupplyDepot) building.medicineHumanCCount.toString() else humanMedicineCValue.value
        humanMedicineCDialog.value = state
    }

    fun updateHumanMedicineBValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            humanMedicineBValue.value = value
        }
    }

    fun updateHumanMedicineBDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        humanMedicineBValue.value = if (building is MedicalSupplyDepot) building.medicineHumanBCount.toString() else humanMedicineBValue.value
        humanMedicineBDialog.value = state
    }


    fun updateHumanMedicineAValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            humanMedicineAValue.value = value
        }
    }

    fun updateHumanMedicineADialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        humanMedicineAValue.value = if (building is MedicalSupplyDepot) building.medicineHumanACount.toString() else humanMedicineAValue.value
        humanMedicineADialog.value = state
    }




    fun updateEmergencySupplyDepot(repo: DbRepo) {
        viewModelScope.launch {
            val building = SelectedBuildingInfo.selectedBuilding!!
            if (building is EmergencyDepot) {
                building.foodCount = foodUnitsValue.value!!.toInt()
                building.shelterCount = mobileSheltersValue.value!!.toInt()
                building.transportVehicleCount = transportVehiclesValue.value!!.toInt()
                building.utilCount = utilCountValue.value!!.toInt()
                building.babySupplyCount = babySuppliesValue.value!!.toInt()
                building.liquidAmount = serumAmountValue.value!!.toInt()
                building.waterAmount = waterAmountValue.value!!.toInt()
                building.toiletCount = portableToiletsValue.value!!.toInt()
                building.powerSupplyCount = portableBatteriesValue.value!!.toInt()
            }
            repo.updateBuilding(building)
        }
    }

    fun updatePortableBatteriesValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            portableBatteriesValue.value = value
        }
    }

    fun updatePortableBatteriesDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        portableBatteriesValue.value = if (building is EmergencyDepot) building.powerSupplyCount.toString() else portableBatteriesValue.value
        portableBatteriesDialog.value = state
    }

    fun updatePortableToiletsValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            portableToiletsValue.value = value
        }
    }

    fun updatePortableToiletsDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        portableToiletsValue.value = if (building is EmergencyDepot) building.toiletCount.toString() else portableToiletsValue.value
        portableToiletsDialog.value = state
    }


    fun updateWaterAmountValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            waterAmountValue.value = value
        }
    }

    fun updateWaterAmountDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        waterAmountValue.value = if (building is EmergencyDepot) building.waterAmount.toString() else waterAmountValue.value
        waterAmountDialog.value = state
    }



    fun updateSerumAmountValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            serumAmountValue.value = value
        }
    }

    fun updateSerumAmountDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        serumAmountValue.value = if (building is EmergencyDepot) building.liquidAmount.toString() else serumAmountValue.value
        serumAmountDialog.value = state
    }

    fun updateBabySuppliesValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            babySuppliesValue.value = value
        }
    }

    fun updateBabySuppliesDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        babySuppliesValue.value = if (building is EmergencyDepot) building.babySupplyCount.toString() else babySuppliesValue.value
        babySuppliesDialog.value = state
    }

    fun updateUtilCountValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            utilCountValue.value = value
        }
    }

    fun updateUtilCountDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        utilCountValue.value = if (building is EmergencyDepot) building.utilCount.toString() else utilCountValue.value
        utilCountDialog.value = state
    }

    fun updateTransportVehiclesValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            transportVehiclesValue.value = value
        }
    }

    fun updateTransportVehiclesDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        transportVehiclesValue.value = if (building is EmergencyDepot) building.transportVehicleCount.toString() else transportVehiclesValue.value
        transportVehiclesDialog.value = state
    }

    fun updateMobileSheltersValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            mobileSheltersValue.value = value
        }
    }
    fun updateMobileSheltersDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        mobileSheltersValue.value = if (building is EmergencyDepot) building.shelterCount.toString() else mobileSheltersValue.value
        mobileSheltersDialog.value = state
    }


    fun updateFoodUnitsValue(value: String) {
        val regex = """[0-9]+""".toRegex()
        if (value.matches(regex)) {
            foodUnitsValue.value = value
        }
    }

    fun updateFoodUnitsDialog(state: Boolean) {
        val building = SelectedBuildingInfo.selectedBuilding!!
        foodUnitsValue.value = if (building is EmergencyDepot) building.foodCount.toString() else foodUnitsValue.value
        foodUnitsDialog.value = state
    }

    fun updateIsEmergencyDepotClickable(state: Boolean) {
        isEmergencyDepotClickable.value = state
    }

    fun updateIsMedicalSupplyDepotClickableForHuman(state: Boolean) {
        isMedicalSupplyDepotClickableForHuman.value = state
    }
    fun updateIsMedicalSupplyDepotClickableForAnimal(state: Boolean) {
        isMedicalSupplyDepotClickableForAnimal.value = state
    }

    fun updateIsSarCenterClickableForTeam(state: Boolean) {
        isSarCenterClickableForTeam.value = state
    }
    fun updateIsSarCenterClickableForEquipment(state: Boolean) {
        isSarCenterClickableForEquipment.value = state
    }
}