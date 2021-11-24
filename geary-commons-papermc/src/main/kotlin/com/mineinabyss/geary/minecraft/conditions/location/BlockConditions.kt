package com.mineinabyss.geary.minecraft.conditions.location

import com.mineinabyss.geary.ecs.accessors.ResultScope
import com.mineinabyss.geary.ecs.api.systems.GearyHandlerScope
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.events.onCheck
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

object BlockConditionChecker: GearyListener() {
    private val ResultScope.location by get<Location>()
    private val ResultScope.conditions by get<BlockConditions>()

    override fun GearyHandlerScope.register() {
        onCheck {
            location.block.type.let {
                (conditions.allow.isEmpty() || it in conditions.allow) && it !in conditions.deny
            }
        }
    }
}
