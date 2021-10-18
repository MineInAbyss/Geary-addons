package com.derongan.minecraft.mineinabyss.ecs.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlTag
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

/**
 * Ignites blocks in a cube around a given location
 */
class IgniteBlocksInCubeAction(
    private val size: Double,
    private val location: Location
) : GearyAction() {

    override fun GearyEntity.run(): Boolean {
        val intSize = size as Int
        for (x in -intSize..intSize) {
            for (y in -intSize..intSize) {
                for (z in -intSize..intSize) {
                    val igniteLocation = Location(
                        location.world,
                        location.x + x,
                        location.y + y,
                        location.z + z
                    )
                    if (location.block.isEmpty) {
                        val belowLocation = Location(
                            igniteLocation.world,
                            igniteLocation.x,
                            igniteLocation.y - 1,
                            igniteLocation.z
                        )
                        if (!(belowLocation.block.isEmpty || belowLocation.block.isLiquid))
                            location.block.type = Material.FIRE
                    }
                }
            }
        }
        return true
    }

}
