package com.example.mobileclient.model

import java.util.*

data class AmbulanceEquipment(val item: Item,val itemData: ItemData )

data class Item(
    val itemId: Int,
    val type: String,
    val name: String,
    val description: String
)

data class ItemData(
    val count: Int,
    val unit: String,
    val updatedAt: Date
)