package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.minecraft.properties.ConfigurableLocation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Material

/**
 * > geary:place_block
 *
 * Summons a block at given location.
 *
 * @param cancel If block placing should be cancelled.
 * @param at The location to spawn block at.
 * @param blockType The block to spawn.
 */
@Serializable
@SerialName("geary:place_block")
data class PlaceBlockAction(
    val cancel: Boolean = false,
    val at: ConfigurableLocation,
    val blockType: Material,
) : GearyAction() {
    private val GearyEntity.location by at

    override fun GearyEntity.run(): Boolean {
        if (!cancel)
        location.block.type = blockType
        return true
    }
}
