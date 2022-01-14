package com.mineinabyss.geary.commons.events.configurable.components

import com.mineinabyss.geary.ecs.api.GearyEntityId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("geary:event.trigger")
data class EventTrigger(
    val entities: Set<GearyEntityId>
)
