@file:UseSerializers(
    DoubleRangeSerializer::class
)

package com.mineinabyss.geary.minecraft.conditions

import com.mineinabyss.geary.ecs.accessors.ResultScope
import com.mineinabyss.geary.ecs.api.systems.GearyHandlerScope
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.events.onCheck
import com.mineinabyss.idofront.serialization.DoubleRangeSerializer
import com.mineinabyss.idofront.typealiases.BukkitEntity
import com.mineinabyss.idofront.util.DoubleRange
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

/**
 * Conditions that can be checked against a [Player].
 */
//TODO add more!
@Serializable
@SerialName("geary:check.health")
class HealthConditions(
    val within: DoubleRange? = null,
    val withinPercent: DoubleRange? = null,
)

object HealthConditionChecker : GearyListener() {
    private val ResultScope.bukkit by get<BukkitEntity>()
    private val ResultScope.health by get<HealthConditions>()

    override fun GearyHandlerScope.register() {
        onCheck {
            val living = bukkit as? LivingEntity ?: return@onCheck false

            (health.within nullOr { living.health in it }
                    && health.withinPercent nullOr {
                living.health / (living.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: return@onCheck false) in it
            })

        }
    }
}
