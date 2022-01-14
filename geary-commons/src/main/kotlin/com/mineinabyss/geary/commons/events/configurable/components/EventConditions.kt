package com.mineinabyss.geary.commons.events.configurable.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
@SerialName("geary:event.conditions")
value class EventConditions(
    val expressions: List<String>
)
