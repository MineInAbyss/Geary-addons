package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.minecraft.properties.AtPlayerLocation
import com.mineinabyss.geary.minecraft.properties.ConfigurableLocation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.Material

/**
 * Ignites blocks in a cube around a given location
 */
@Serializable
@SerialName("geary:ignite_area")
class IgniteBlocksInCubeAction(
    private val size: Double,
    private val at: ConfigurableLocation = AtPlayerLocation()
) : GearyAction() {
    private val GearyEntity.loc by at

    override fun GearyEntity.run(): Boolean {
        val intSize = size as Int
        for (x in -intSize..intSize) {
            for (y in -intSize..intSize) {
                for (z in -intSize..intSize) {
                    val igniteLocation = Location(
                        loc.world,
                        loc.x + x,
                        loc.y + y,
                        loc.z + z
                    )
                    if (loc.block.isEmpty) {
                        val belowLocation = Location(
                            igniteLocation.world,
                            igniteLocation.x,
                            igniteLocation.y - 1,
                            igniteLocation.z
                        )
                        if (!(belowLocation.block.isEmpty || belowLocation.block.isLiquid))
                            loc.block.type = Material.FIRE
                    }
                }
            }
        }
        return true
    }

}
