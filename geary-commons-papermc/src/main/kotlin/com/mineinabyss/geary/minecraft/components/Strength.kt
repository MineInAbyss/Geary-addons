package com.mineinabyss.geary.minecraft.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Entity has a certain level of physical power.
 */
@Serializable
@SerialName("geary:strength")
class Strength(
    val amount: Double
)

