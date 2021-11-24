package com.mineinabyss.geary.minecraft.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.event.entity.EntityDamageEvent


/**
 * > geary:damage_amplifier
 *
 * Holds properties
 */
@Serializable
@SerialName("geary:damage_amplifier")
class DamageAmplifier(
    val amplifier: Double,
    val damageType: EntityDamageEvent.DamageCause
)

