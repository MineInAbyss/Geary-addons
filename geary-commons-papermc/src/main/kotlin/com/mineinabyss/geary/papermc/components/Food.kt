package com.mineinabyss.geary.papermc.components

import com.mineinabyss.idofront.serialization.PotionEffectSerializer
import com.mineinabyss.idofront.serialization.SerializableItemStack
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.potion.PotionEffect

/**
 * `geary:food`
 * Lets an item have custom food properties.
 *
 * @param hunger The amount of hunger this item restores.
 * @param saturation The amount of saturation this item gives.
 * @param replacement The item to replace with after consuming. If null it will subtract one from the stack.
 */
@Serializable
@SerialName("geary:food")
class Food(
    val hunger: Int,
    val saturation: Int,
    val replacement: SerializableItemStack? = null,
    val effectChance: Double = 0.0,
    val effectList: List<@Serializable(with = PotionEffectSerializer::class) PotionEffect> = emptyList(),
)
