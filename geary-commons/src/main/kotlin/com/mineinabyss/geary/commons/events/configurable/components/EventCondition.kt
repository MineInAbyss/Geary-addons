package com.mineinabyss.geary.commons.events.configurable.components

import com.mineinabyss.geary.ecs.api.GearyEntityId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("geary:event.condition")
class EventCondition(
    val entity: GearyEntityId
)
