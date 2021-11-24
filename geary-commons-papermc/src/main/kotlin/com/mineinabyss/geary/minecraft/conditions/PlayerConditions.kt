package com.mineinabyss.geary.minecraft.conditions

import com.mineinabyss.geary.ecs.accessors.ResultScope
import com.mineinabyss.geary.ecs.api.systems.GearyHandlerScope
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.events.onCheck
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.entity.Player

/**
 * Conditions that can be checked against a [Player].
 */
//TODO add more!
@Serializable
@SerialName("geary:check.player")
class PlayerConditions(
    val isSneaking: Boolean? = null,
    val isSprinting: Boolean? = null,
)

object PlayerConditionsChecker : GearyListener() {
    private val ResultScope.player by get<Player>()
    private val ResultScope.conditions by get<PlayerConditions>()


    override fun GearyHandlerScope.register() {
        onCheck {
            conditions.isSneaking nullOrEquals player.isSneaking &&
                    conditions.isSprinting nullOrEquals player.isSprinting
        }
    }
}

internal infix fun <T> T?.nullOrEquals(other: T?): Boolean =
    this == null || this == other

internal inline infix fun <T> T?.nullOr(check: (T) -> Boolean): Boolean =
    this == null || check(this)
