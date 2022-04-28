@file:UseSerializers(
    DoubleRangeSerializer::class
)

package com.mineinabyss.geary.papermc.conditions

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.geary.systems.accessors.get
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

@AutoScan
class HealthConditionChecker : GearyListener() {
    private val TargetScope.bukkit by get<BukkitEntity>()
    private val TargetScope.health by get<HealthConditions>()

    @Handler
    fun TargetScope.checkHealth(): Boolean {
        val living = bukkit as? LivingEntity ?: return false

        return (health.within nullOr { living.health in it }
                && health.withinPercent nullOr {
            living.health / (living.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: return false) in it
        })
    }
}
