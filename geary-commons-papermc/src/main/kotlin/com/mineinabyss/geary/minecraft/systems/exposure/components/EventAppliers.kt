package com.mineinabyss.geary.minecraft.systems.exposure.components

import com.mineinabyss.geary.ecs.api.GearyEntityId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("geary:event.append")
class EventAppend()

@Serializable
@SerialName("geary:event.trigger")
data class EventTrigger(
    val entities: Set<GearyEntityId>
)

@Serializable
@SerialName("geary:event.condition")
class EventCondition(
    val entity: String
)
