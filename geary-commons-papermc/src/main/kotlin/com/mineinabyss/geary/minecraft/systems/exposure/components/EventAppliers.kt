package com.mineinabyss.geary.minecraft.systems.exposure.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("geary:event.append")
class EventAppend()

@Serializable
@SerialName("geary:event.run")
class EventRun(
    val entity: String
)

@Serializable
@SerialName("geary:event.condition")
class EventCondition(
    val entity: String
)
