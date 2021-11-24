package com.mineinabyss.geary.minecraft.conditions.location

import com.mineinabyss.geary.ecs.accessors.ResultScope
import com.mineinabyss.geary.ecs.api.systems.GearyHandlerScope
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.events.onCheck
import com.mineinabyss.idofront.serialization.IntRangeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location

@Serializable
@SerialName("geary:check.light")
class LightCondition(
    @Serializable(with = IntRangeSerializer::class) val range: IntRange = 0..15,
)

object LightConditionChecker : GearyListener() {
    private val ResultScope.location by get<Location>()
    private val ResultScope.condition by get<LightCondition>()

    override fun GearyHandlerScope.register() {
        onCheck {
            // TODO Adding 1 so we check the block above a given block since the block itself always has light level 0.
            //  This will probably cause confusion, figure out a fix that works with mobzy's spawning system.
            //  Once we flesh out the event system, register a listener that runs after this one and overrides its effect.
            location.clone().add(0.0, 1.0, 0.0).block.lightLevel in condition.range
        }
    }
}
