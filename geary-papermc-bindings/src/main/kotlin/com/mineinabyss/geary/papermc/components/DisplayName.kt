package com.mineinabyss.geary.papermc.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * `geary:prefab_name`
 * Displays a bossbar to nearby players when added to a bukkit entity.
 */
@JvmInline
@Serializable
@SerialName("geary:display_name")
value class DisplayName(val name: String)
