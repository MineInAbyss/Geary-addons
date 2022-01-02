package com.mineinabyss.geary.minecraft.conditions.location

import com.mineinabyss.geary.ecs.accessors.EventResultScope
import com.mineinabyss.geary.ecs.accessors.ResultScope
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.events.handlers.CheckHandler
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.Material

@Serializable
@SerialName("geary:check.block_type")
class BlockConditions(
    val allow: Set<Material> = setOf(),
    val deny: Set<Material> = setOf()
)

@AutoScan
class BlockConditionChecker : GearyListener() {
    private val ResultScope.conditions by get<BlockConditions>()

    private inner class Check : CheckHandler() {
        private val EventResultScope.location by get<Location>()

        override fun ResultScope.check(event: EventResultScope): Boolean =
            event.location.block.type.let {
                (conditions.allow.isEmpty() || it in conditions.allow) && it !in conditions.deny
            }
    }
}
