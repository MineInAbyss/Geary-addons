package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.minecraft.properties.ConfigurableLocation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Makes an explosion at the given location with the given power.
 */
@Serializable
@SerialName("geary:explode")
data class ExplodeAction(
    private val at: ConfigurableLocation,
    private val explosionPower: Double,
    private val setFire: Boolean
) : GearyAction() {
    private val GearyEntity.loc by at

    override fun GearyEntity.run(): Boolean {
        loc.world.createExplosion(loc, explosionPower as Float, setFire)
        return true
    }
}