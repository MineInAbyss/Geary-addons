package com.mineinabyss.geary.papermc.components.interaction

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("geary:event.left_clicked")
class LeftClicked

@Serializable
@SerialName("geary:event.right_clicked")
class RightClicked

@Serializable
@SerialName("geary:event.item_interacted")
data class Interacted(
    val leftClick: Boolean,
    val rightClick: Boolean,
)

class ItemBroke

class ItemDropped
