package com.mineinabyss.geary.minecraft.systems.exposure.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
@SerialName("geary:event.triggers")
value class EventTriggers(
    val expressions: List<String>
)

@JvmInline
@Serializable
@SerialName("geary:event.conditions")
value class EventConditions(
    val expressions: List<String>
)
