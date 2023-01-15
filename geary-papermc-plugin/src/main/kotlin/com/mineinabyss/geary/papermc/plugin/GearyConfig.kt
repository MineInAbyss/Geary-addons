package com.mineinabyss.geary.papermc.plugin

import kotlinx.serialization.Serializable

@Serializable
class GearyConfig(
    val debug: Boolean = false,
    val webConsole: Boolean = true,
)
