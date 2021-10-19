package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.idofront.serialization.VectorSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Entity
import org.bukkit.util.Vector
import kotlin.math.cos

/**
 * Like PropelFromLocation but on the XZ plane with a given Y angle.
 * Primarily made for attacks which want to knock back players in a minecraft-y
 * way.
 */
@Serializable
@SerialName("geary:knockback")
class KnockBackFromLocationAction(
    private val power: Double,
    private val yAngle: Double,
    @Serializable(with = VectorSerializer::class)
    private val location: Vector,  // Not sure if this should be a Vector or Location
    private val scaleWithDistance: Boolean,
    private val cancelCurrentVelocity: Boolean
) : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        var velocity = location.subtract(entity.location.toVector())
        velocity.y = 0.0
        velocity = velocity.normalize()
        velocity.y = cos(yAngle)  // Not sure about this, should probably get all of the
        // relevant angles and set the velocity based on those
        var distance = location.distance(entity.location.toVector())

        // This is wacky placeholder math, probably change it at some point
        val maxForce = (power * 5.0)
        var scalar: Double
        if (scaleWithDistance) {
            if (distance == 0.0) {
                scalar = maxForce
            } else {
                scalar = power / (distance * distance * distance)
                if (scalar > maxForce)
                    scalar = maxForce
            }
        } else
            scalar = power

        velocity.multiply(scalar)

        if (cancelCurrentVelocity)
            entity.velocity = velocity
        else
            entity.velocity.add(velocity)

        return true
    }

}
