package com.example.mobileclient.model

data class Ambulance (
    val licensePlate: String
        )

data class AmbulanceEquipment(val item: Item,val itemData: ItemData )

data class Item(
    val itemId: Int,
    val type: String,
    val name: String,
    val description: String
)

data class ItemData(
    var count: Int,
    val unit: String,
    val updatedAt: String
)

//TODO: Adjust to match backend
data class Incident(
    val incidentId: Int,
    val dangerScale: String,
    val incidentStatusType: String,
    val reactionJustification: String,
    val accidentReport: AccidentReportFromApi
)

data class AccidentReportFromApi(
    val accidentId: Int,
    val date: String,
    val location: Location,
    val address: String,
    val bandCode: String,
    val emergencyType: String,
    val victimCount: Int,
    val consciousness: Boolean,
    val breathing: Boolean,
    val description: String
)