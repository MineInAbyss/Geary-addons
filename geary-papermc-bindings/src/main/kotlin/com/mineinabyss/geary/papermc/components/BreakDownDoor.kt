package com.mineinabyss.geary.papermc.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
@SerialName("geary:break_down_door")
value class BreakDownDoor(val canBreakDoor: Boolean = false)
