package com.mineinabyss.geary.papermc.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.event.entity.EntityDamageEvent


/**
 * `geary:damage_amplifier`
 * Multiplies damage from a specific cause by an [amplifier].
 */
@Serializable
@SerialName("geary:damage_amplifier")
class DamageAmplifier(
    val amplifier: Double,
    val damageType: EntityDamageEvent.DamageCause
)

