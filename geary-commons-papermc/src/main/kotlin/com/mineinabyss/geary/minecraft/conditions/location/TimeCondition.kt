package com.mineinabyss.geary.minecraft.conditions.location

import com.mineinabyss.geary.ecs.accessors.ResultScope
import com.mineinabyss.geary.ecs.api.systems.GearyHandlerScope
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.events.onCheck
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

object TimeConditionChecker : GearyListener() {
    private val ResultScope.location by get<Location>()
    private val ResultScope.condition by get<TimeCondition>()

    override fun GearyHandlerScope.register() {
        onCheck {
            val time = location.world.time

            // support these two possibilities
            // ====max-----min====
            // ----min=====max----
            with(condition) {
                if (min < max) time in min..max
                else time !in max..min
            }
        }
    }
}
