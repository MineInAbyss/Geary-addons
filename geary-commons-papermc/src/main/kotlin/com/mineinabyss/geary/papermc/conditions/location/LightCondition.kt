package com.mineinabyss.geary.papermc.conditions.location

import com.mineinabyss.geary.autoscan.AutoScan
import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.building.get
import com.mineinabyss.geary.ecs.api.annotations.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.provideDelegate
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
