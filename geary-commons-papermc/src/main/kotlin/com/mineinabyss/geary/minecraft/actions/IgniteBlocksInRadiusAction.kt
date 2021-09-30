package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity

class IgniteBlocksInRadiusAction(
    private val radius: Double  // Again not really a radius but close enough
) : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        val intRadius = radius.toInt()
        for (x in -intRadius..intRadius) {
            for (y in -intRadius..intRadius) {
                for (z in -intRadius..intRadius) {
                    val location = Location(
                        entity.world,
                        entity.location.x + x,
                        entity.location.y + y,
                        entity.location.z + z
                    )
                    if (location.block.isEmpty) {
                        val belowLocation = Location(
                            location.world,
                            location.x,
                            location.y + 1,
                            location.z
                        )
                        if (!belowLocation.block.isEmpty || !belowLocation.block.isLiquid)
                            location.block.type = Material.FIRE
                    }
                }
            }
        }
        return true
    }

}