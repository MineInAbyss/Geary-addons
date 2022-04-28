package com.mineinabyss.geary.papermc.conditions.location

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.geary.systems.accessors.get
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
    private val TargetScope.conditions by get<BlockConditions>()

    private val EventScope.location by get<Location>()

    @Handler
    fun TargetScope.check(event: EventScope): Boolean =
        event.location.block.type.let {
            (conditions.allow.isEmpty() || it in conditions.allow) && it !in conditions.deny
        }
}
