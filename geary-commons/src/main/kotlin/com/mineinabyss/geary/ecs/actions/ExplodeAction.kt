package com.derongan.minecraft.mineinabyss.ecs.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import org.bukkit.Location
import org.bukkit.entity.Damageable
import org.bukkit.entity.Entity

/**
 * Makes an explosion at the given location with the given power.
 */
class ExplodeAction(
    private val location: Location,
    private val explosionPower: Double,
    private val setFire: Boolean
) : GearyAction() {
    override fun GearyEntity.run(): Boolean {
        location.world.createExplosion(location, explosionPower as Float, setFire)
        return true
    }
}