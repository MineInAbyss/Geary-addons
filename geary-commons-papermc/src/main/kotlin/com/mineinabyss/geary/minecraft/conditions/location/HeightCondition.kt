package com.mineinabyss.geary.minecraft.conditions.location

import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.get
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.Handler
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
    private val TargetScope.condition by get<HeightCondition>()

    private val EventScope.location by get<Location>()

    @Handler
    fun TargetScope.check(event: EventScope): Boolean =
        event.location.y.toInt() in condition.range
}
