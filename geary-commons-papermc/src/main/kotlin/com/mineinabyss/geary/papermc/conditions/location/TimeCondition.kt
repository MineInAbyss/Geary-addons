package com.mineinabyss.geary.papermc.conditions.location

import com.mineinabyss.geary.autoscan.AutoScan
import com.mineinabyss.geary.ecs.accessors.EventScope
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.building.get
import com.mineinabyss.geary.ecs.api.annotations.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.provideDelegate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location

@Serializable
@SerialName("geary:check.time")
class TimeCondition(
    //TODO change to range
    val min: Long = -1,
    val max: Long = 10000000,
)

@AutoScan
class TimeConditionChecker : GearyListener() {
    private val TargetScope.condition by get<TimeCondition>()

    val EventScope.location by get<Location>()

    @Handler
    fun TargetScope.check(event: EventScope): Boolean {
        val time = event.location.world.time

        // support these two possibilities
        // ====max-----min====
        // ----min=====max----
        return with(condition) {
            if (min < max) time in min..max
            else time !in max..min
        }
    }
}
