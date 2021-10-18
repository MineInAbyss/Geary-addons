package com.derongan.minecraft.mineinabyss.ecs.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

/**
 * Applies the given potion effect to an entity for the given duration with the
 * given duration.
 */
class ApplyPotionEffectAction (
    private val type: PotionEffectType,
    private val duration: Int,
    private val amplifier: Int
        ) : GearyAction() {
    val GearyEntity.entity by get<LivingEntity>()

    override fun GearyEntity.run(): Boolean {
        entity.addPotionEffect(PotionEffect(type, duration, amplifier))
        return true
    }
}