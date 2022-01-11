package com.mineinabyss.geary.minecraft.systems.exposure.components

import com.mineinabyss.idofront.serialization.PotionEffectSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.potion.PotionEffect

@Serializable
@SerialName("geary:apply_relation")
object Apply

@JvmInline
@Serializable
@SerialName("geary:apply")
value class ApplyBuild(val entityExpression: String)

@JvmInline
@Serializable
@SerialName("geary:potion_effects")
value class PotionEffects(
    val effects: List<@Serializable(with = PotionEffectSerializer::class) PotionEffect>
)

@Serializable
@SerialName("geary:event.ingested")
class Ingested

@Serializable
@SerialName("geary:event.touched")
class Touched



