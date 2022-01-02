package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.idofront.serialization.DoubleRangeSerializer
import com.mineinabyss.idofront.typealiases.BukkitEntity
import com.mineinabyss.idofront.util.DoubleRange
import com.mineinabyss.idofront.util.randomOrMin
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.LivingEntity

/**
 * Deals damage to the target entity.
 *
 * @param damage The damage amount.
 */
@Serializable
@SerialName("geary:deal_damage")
data class DealDamageConf(
    val damage: @Serializable(with = DoubleRangeSerializer::class) DoubleRange,
    val minHealth: Double = 0.0,
    val ignoreArmor: Boolean = false,
)

fun GearyEntity.damage(
    other: GearyEntity,
    conf: DealDamageConf,
    damager: BukkitEntity? = get(),
    target: BukkitEntity? = other.get(),
): Boolean {
    if (damager !is LivingEntity) return false
    if (target !is LivingEntity) return false

    with(conf) {
        val chosenDamage = damage.randomOrMin()
        //if true, damage dealt ignores armor, otherwise factors armor into damage calc
        if (ignoreArmor) target.health = (target.health - chosenDamage).coerceAtLeast(minHealth)
        else target.damage(chosenDamage, damager);
    }
    return true
}
