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
@SerialName("geary:check.height")
class HeightCondition(
    val range: @Serializable(with = IntRangeSerializer::class) IntRange,
)

@AutoScan
class HeightConditionChecker : GearyListener() {
    private val ResultScope.condition by get<HeightCondition>()

    private inner class Check : CheckHandler() {
        private val EventResultScope.location by get<Location>()

        override fun ResultScope.check(event: EventResultScope): Boolean =
            event.location.y.toInt() in condition.range
    }
}
