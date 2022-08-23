package com.mineinabyss.geary.papermc.events.conditions

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.TargetScope
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * Conditions that can be checked against a [Player].
 */
@Serializable
@SerialName("geary:check.player")
class PlayerConditions(
    val isSneaking: Boolean? = null,
    val isSprinting: Boolean? = null,
    val isBlocking: Boolean? = null,
    val isSleeping: Boolean? = null,
    val isDeeplySleeping: Boolean? = null,
    val isSwimming: Boolean? = null,
    val isClimbing: Boolean? = null,
    val isJumping: Boolean? = null,
    val isInLava: Boolean? = null,
    val isInWater: Boolean? = null,
    val isInBubbleColumn: Boolean? = null,
    val isInRain: Boolean? = null,
    val isFlying: Boolean? = null,
    val isGliding: Boolean? = null,
    val isFrozen: Boolean? = null,
    val isFreezeTickingLocked: Boolean? = null,
    val isInPowderedSnow: Boolean? = null,
    val isInCobweb: Boolean? = null,
    val isInsideVehicle: Boolean? = null,
    val isConversing: Boolean? = null,
    val isRiptiding: Boolean? = null,
    val isInvisible: Boolean? = null,
    val isGlowing: Boolean? = null,
    val isInvurnerable: Boolean? = null,
    val isSilent: Boolean? = null,
    val isOp: Boolean? = null,

)

@AutoScan
class PlayerConditionsChecker : GearyListener() {
    private val TargetScope.player by get<Player>()
    private val TargetScope.conditions by get<PlayerConditions>()

    @Handler
    fun TargetScope.check(): Boolean = player.isOnline && // Just to align syntax below
            conditions.isSneaking nullOrEquals player.isSneaking &&
            conditions.isSprinting nullOrEquals player.isSprinting &&
            conditions.isBlocking nullOrEquals player.isBlocking &&
            conditions.isSleeping nullOrEquals player.isSleeping &&
            conditions.isDeeplySleeping nullOrEquals player.isDeeplySleeping &&
            conditions.isSwimming nullOrEquals player.isSwimming &&
            conditions.isClimbing nullOrEquals player.isClimbing &&
            conditions.isJumping nullOrEquals player.isJumping &&
            conditions.isInLava nullOrEquals player.isInLava &&
            conditions.isInWater nullOrEquals player.isInWater &&
            conditions.isInBubbleColumn nullOrEquals player.isInBubbleColumn &&
            conditions.isInRain nullOrEquals player.isInRain &&
            conditions.isFlying nullOrEquals player.isFlying &&
            conditions.isGliding nullOrEquals player.isGliding &&
            conditions.isFrozen nullOrEquals player.isFrozen &&
            conditions.isFreezeTickingLocked nullOrEquals player.isFreezeTickingLocked &&
            conditions.isInPowderedSnow nullOrEquals player.isInPowderedSnow &&
            conditions.isInCobweb nullOrEquals (player.location.block.type == Material.COBWEB) &&
            conditions.isInsideVehicle nullOrEquals player.isInsideVehicle &&
            conditions.isConversing nullOrEquals player.isConversing &&
            conditions.isRiptiding nullOrEquals player.isRiptiding &&
            conditions.isInvisible nullOrEquals player.isInvisible &&
            conditions.isGlowing nullOrEquals player.isGlowing &&
            conditions.isInvurnerable nullOrEquals player.isInvulnerable &&
            conditions.isSilent nullOrEquals player.isSilent &&
            conditions.isOp nullOrEquals player.isOp
}

internal infix fun <T> T?.nullOrEquals(other: T?): Boolean =
    this == null || this == other

internal inline infix fun <T> T?.nullOr(check: (T) -> Boolean): Boolean =
    this == null || check(this)
