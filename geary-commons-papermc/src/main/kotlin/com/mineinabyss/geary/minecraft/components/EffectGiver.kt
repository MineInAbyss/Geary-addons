package com.mineinabyss.geary.minecraft.components

import com.mineinabyss.idofront.serialization.PotionEffectSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.potion.PotionEffect

/**
 * Entity should give effects when physically touching another.
 */
@Serializable
@SerialName("geary:effect_giver")
class EffectGiver(
    val effects: Set<@Serializable(with = PotionEffectSerializer::class) PotionEffect>
)
