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