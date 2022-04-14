package com.mineinabyss.geary.papermc.conditions

import com.mineinabyss.geary.autoscan.AutoScan
import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.building.get
import com.mineinabyss.geary.ecs.api.annotations.Handler
import com.mineinabyss.geary.ecs.api.systems.GearyListener
import com.mineinabyss.geary.ecs.api.systems.provideDelegate
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

@AutoScan
class PlayerConditionsChecker : GearyListener() {
    private val TargetScope.player by get<Player>()
    private val TargetScope.conditions by get<PlayerConditions>()

    @Handler
    fun TargetScope.check(): Boolean =
        conditions.isSneaking nullOrEquals player.isSneaking &&
                conditions.isSprinting nullOrEquals player.isSprinting
}

internal infix fun <T> T?.nullOrEquals(other: T?): Boolean =
    this == null || this == other

internal inline infix fun <T> T?.nullOr(check: (T) -> Boolean): Boolean =
    this == null || check(this)
