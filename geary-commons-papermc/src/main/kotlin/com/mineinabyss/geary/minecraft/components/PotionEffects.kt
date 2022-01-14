package com.mineinabyss.geary.minecraft.components

import com.mineinabyss.idofront.serialization.PotionEffectSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.potion.PotionEffect

@JvmInline
@Serializable
@SerialName("geary:potion_effects")
value class PotionEffects(
    val effects: List<@Serializable(with = PotionEffectSerializer::class) PotionEffect>
)
