package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.idofront.serialization.VectorSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Entity
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

/**
 * Applies a force with the given power and angles on an entity.
 */
@Serializable
@SerialName("geary:propel_angle")
class PropelActionAngle(
    private val power: Double,
    private val angleX: Double,  // 0 is forward
    private val angleY: Double,   // 0 is forward, - is down, + is up
    private val cancelCurrentVelocity: Boolean
) : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        var velocity = Vector(
            power * cos(angleY) * sin(angleX),
            power * cos(angleY) * cos(angleX),
            power * sin(angleY)
        )
        if (cancelCurrentVelocity)
            entity.velocity = velocity;
        else
            entity.velocity.add(velocity);

        return true
    }

}

/**
 * Applies a force given its vector on an entity.
 */
@Serializable
@SerialName("geary:propel_vector")
class PropelActionVector(
    @Serializable(with = VectorSerializer::class)
    private val force: Vector,
    private val cancelCurrentVelocity: Boolean
) : GearyAction() {
    val GearyEntity.entity by get<Entity>()

    override fun GearyEntity.run(): Boolean {
        if (cancelCurrentVelocity)
            entity.velocity = force;
        else
            entity.velocity.add(force);

        return true
    }

}
