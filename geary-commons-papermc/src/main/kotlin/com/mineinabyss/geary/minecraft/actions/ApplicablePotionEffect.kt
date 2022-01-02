package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.idofront.serialization.PotionEffectSerializer
import com.mineinabyss.idofront.typealiases.BukkitEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect
import kotlin.random.Random

/**
 * @param effect The potion effect to apply.
 * @param applyChance Chance of applying the effect.
 */
@Serializable
@SerialName("geary:applicable_potion_effect")
data class ApplicablePotionEffect(
    val effect: @Serializable(with = PotionEffectSerializer::class) PotionEffect,
    val applyChance: Double = 1.0,
)

/**
 * Applies potion effects to the target entity with a certain (optional) chance.
 */
fun GearyEntity.applyPotionEffect(
    effect: ApplicablePotionEffect,
    entity: LivingEntity? = get<BukkitEntity>() as? LivingEntity
): Boolean {
    entity ?: return false

    if (Random.nextDouble() <= effect.applyChance) {
        entity.addPotionEffect(effect.effect)
        return true
    }
    return false
}
