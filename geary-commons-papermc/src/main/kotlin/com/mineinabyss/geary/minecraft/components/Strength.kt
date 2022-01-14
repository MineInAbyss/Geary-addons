package com.mineinabyss.geary.minecraft.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * `geary:strength`
 * Denotes that an entity has a certain level of physical power.
 */
@Serializable
@SerialName("geary:strength")
class Strength(
    val amount: Double
)

