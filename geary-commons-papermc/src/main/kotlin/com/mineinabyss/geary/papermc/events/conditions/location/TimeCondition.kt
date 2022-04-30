package com.mineinabyss.geary.papermc.events.conditions.location

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.geary.systems.accessors.get
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
