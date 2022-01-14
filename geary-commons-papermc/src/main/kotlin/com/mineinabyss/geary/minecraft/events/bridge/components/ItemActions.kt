package com.mineinabyss.geary.minecraft.events.bridge.components

import com.mineinabyss.looty.tracking.toGearyOrNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Player

val Player.heldLootyItem get() = inventory.itemInMainHand.toGearyOrNull(this)

@Serializable
@SerialName("geary:left_clicked")
class LeftClicked

@Serializable
@SerialName("geary:right_clicked")
class RightClicked

@Serializable
@SerialName("geary:item_interacted")
data class Interacted(
    val leftClick: Boolean,
    val rightClick: Boolean,
)

class ItemBroke

class ItemDropped
