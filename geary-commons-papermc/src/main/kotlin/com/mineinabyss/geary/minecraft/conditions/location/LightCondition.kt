package com.mineinabyss.geary.minecraft.conditions.location

import com.mineinabyss.geary.ecs.accessors.EventResultScope
import com.mineinabyss.geary.ecs.accessors.ResultScope
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.events.handlers.CheckHandler
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
    private val ResultScope.condition by get<LightCondition>()

    private inner class Check : CheckHandler() {
        private val EventResultScope.location by get<Location>()

        override fun ResultScope.check(event: EventResultScope): Boolean {
            val block = event.location.block
            // Check the current block's light level or the one above if this block is solid
            val check =
                if (block.isSolid)
                    event.location.clone().add(0.0, 1.0, 0.0).block
                else block
            return check.lightLevel in condition.range
        }
    }
}
