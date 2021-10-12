package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity

/**
 * Ignites a radius around the player.
 *
 * @param radius The area to ignite.
 */
@Serializable
@SerialName("geary:ingite_around")
class IgniteBlocksInRadiusAction(
    private val radius: Int = 5
) : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        for (x in -radius..radius) {
            for (y in -radius..radius) {
                for (z in -radius..radius) {
                    val location = Location(
                        entity.world,
                        entity.location.x + x,
                        entity.location.y + y,
                        entity.location.z + z
                    )
                    if (!location.block.isEmpty) return true
                    val belowLocation = location.subtract(0.0, 1.0, 0.0)
                    if (!belowLocation.block.isEmpty || !belowLocation.block.isLiquid)
                        location.block.type = Material.FIRE
                }
            }
        }
        return true
    }

}