package com.mineinabyss.geary.papermc.helpers

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.entity.Player

@Serializable
@SerialName("geary:location.target")
class ConfigurableTargetLocation(
    val maxDist: Int = 3,
    val allowAir: Boolean = false,
    val onFace: Boolean = false,
)

/**
 * Gets the location of the target block the player associated with the entity is looking at.
 *
 * @param maxDist The maximum distance this can extend.
 * @param allowAir Whether to allow clicking on nothing.
 */
fun Player.atTargetBlock(
    conf: ConfigurableTargetLocation,
): Location? {
    with(conf) {
        val block =
            if (onFace) getLastTwoTargetBlocks(null, maxDist).first()
            else getTargetBlock(maxDist) ?: return null

        val secondBlock =
            if (onFace) getLastTwoTargetBlocks(null, maxDist).last()
            else getTargetBlock(maxDist) ?: return null
        if (!allowAir && block.isEmpty && secondBlock.isEmpty) return null

        return block.location
    }
}
