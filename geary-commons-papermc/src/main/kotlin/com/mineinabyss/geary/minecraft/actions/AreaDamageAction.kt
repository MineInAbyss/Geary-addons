package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.minecraft.access.toGeary
import com.mineinabyss.idofront.typealiases.BukkitEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.LivingEntity

/**
 * Deals the given damage in a square of the specified size
 */
@Serializable
@SerialName("geary:area_damage")
class AreaDamage(
    val damage: Double,
    val size: Double,
    val knockBackPower: Double = 0.0,
    val knockBackYAngle: Double = 0.0,
    val scaleKnockBackWithDistance: Boolean = true,
)

fun GearyEntity.damageInArea(damage: AreaDamage, entity: BukkitEntity? = get()): Boolean {
    entity ?: return false

    entity.getNearbyEntities(damage.size, damage.size, damage.size)
        .filterIsInstance<LivingEntity>()
        .forEach { targetEntity ->
            targetEntity.damage(damage.damage)
            targetEntity.toGeary().knockBack(
                from = entity.location,
                conf = KnockBackFromLocation(
                    damage.knockBackPower,
                    damage.knockBackYAngle,
                    damage.scaleKnockBackWithDistance,
                    false
                )
            )
        }
    return true
}
