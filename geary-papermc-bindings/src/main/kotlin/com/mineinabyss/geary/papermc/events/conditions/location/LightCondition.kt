package com.mineinabyss.geary.papermc.events.conditions.location

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.idofront.serialization.IntRangeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location

@Serializable
@SerialName("geary:check.light")
class LightCondition(
    @Serializable(with = IntRangeSerializer::class) val range: IntRange = 0..15,
)

@AutoScan
class LightConditionChecker : GearyListener() {
    private val TargetScope.condition by get<LightCondition>()

    private val EventScope.location by get<Location>()

    @Handler
    fun TargetScope.check(event: EventScope): Boolean {
        val block = event.location.block
        // Check the current block's light level or the one above if this block is solid
        val check =
            if (block.isSolid)
                event.location.clone().add(0.0, 1.0, 0.0).block
            else block
        return check.lightLevel in condition.range
    }
}
