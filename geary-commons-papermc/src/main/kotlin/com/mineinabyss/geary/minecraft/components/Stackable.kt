package com.mineinabyss.geary.minecraft.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("geary:stackable")
class Stackable {
    val stackSize: Int = 64
}