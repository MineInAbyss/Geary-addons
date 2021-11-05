package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.minecraft.access.toGeary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity

/**
 * Deals the given damage in a square of the specified size
 */
@Serializable
@SerialName("geary:area_damage")
class AreaDamageAction(
    private val damage: Double,
    private val size: Double,
) : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        for (targetEntity in entity.getNearbyEntities(size, size, size)) {
            if (targetEntity is LivingEntity) {
                targetEntity.damage(damage)
            }
        }
        return true
    }

}

/**
 * Deals the given damage and knockback in a square of the specified size
 */
@Serializable
@SerialName("geary:area_damage_knockback")
class AreaDamageWithKnockBackAction(
    private val damage: Double,
    private val size: Double,  // Not really size as it's a box but whatever
    private val knockBackPower: Double,
    private val knockBackYAngle: Double,
    private val scaleKnockBackWithDistance: Boolean
) : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        for (targetEntity in entity.getNearbyEntities(size, size, size)) {
            if (targetEntity is LivingEntity) {
                targetEntity.damage(damage)
                val knockBackAction = KnockBackFromLocationAction(
                    knockBackPower,
                    knockBackYAngle,
                    entity.location.toVector(),
                    scaleKnockBackWithDistance,
                    false
                )
                //knockBackAction.invoke(entity.toGeary(targetEntity))
                knockBackAction(targetEntity.toGeary())
            }
        }
        return true
    }

}