package com.mineinabyss.geary.commons.events.configurable.components

import com.mineinabyss.geary.datatypes.GearyType

data class TriggerWhenSource(
    val runEvents: GearyType,
    val runAsSource: Boolean,
)

data class TriggerWhenTarget(
    val runEvents: GearyType,
    val runAsSource: Boolean,
)
