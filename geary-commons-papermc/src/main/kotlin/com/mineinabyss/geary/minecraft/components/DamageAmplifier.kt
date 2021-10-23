package com.mineinabyss.geary.minecraft.components

import com.mineinabyss.geary.ecs.api.autoscan.AutoscanComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.event.entity.EntityDamageEvent


/**
 * > geary:damageamplifier
 *
 * Holds properties
 */
@Serializable
@SerialName("geary:damageamplifier")
@AutoscanComponent
class DamageAmplifier(
    val amplifier: Double,
    val damageType: EntityDamageEvent.DamageCause
)

