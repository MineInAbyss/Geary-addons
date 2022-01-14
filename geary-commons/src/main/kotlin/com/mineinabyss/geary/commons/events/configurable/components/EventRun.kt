package com.mineinabyss.geary.commons.events.configurable.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
@SerialName("geary:event.run")
value class EventRunBuilder(val expression: String)

@Serializable
@SerialName("geary:event.run_relation")
object EventRun
