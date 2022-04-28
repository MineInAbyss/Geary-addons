package com.mineinabyss.geary.commons.events.configurable.components

import com.mineinabyss.geary.datatypes.GearyEntityId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("geary:event.trigger.source")
data class TriggerWhenSource(
    val entities: Set<GearyEntityId>,
    val runAsSource: Boolean,
)

@Serializable
@SerialName("geary:event.trigger.target")
data class TriggerWhenTarget(
    val entities: Set<GearyEntityId>,
    val runAsSource: Boolean,
)
