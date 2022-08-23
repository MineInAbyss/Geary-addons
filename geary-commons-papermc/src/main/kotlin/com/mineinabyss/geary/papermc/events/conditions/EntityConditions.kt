package com.mineinabyss.geary.papermc.events.conditions

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.TargetScope
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

/**
 * Conditions that can be checked against a [LivingEntity].
 *
 * This does not count [Player], for that use [PlayerConditions](PlayerConditions.kt)
 */
@Serializable
@SerialName("geary:check.entity")
class EntityConditions(
    val isSleeping: Boolean? = null,
    val isSwimming: Boolean? = null,
    val isClimbing: Boolean? = null,
    val isJumping: Boolean? = null,
    val isInLava: Boolean? = null,
    val isInWater: Boolean? = null,
    val isInBubbleColumn: Boolean? = null,
    val isInRain: Boolean? = null,
    val isOnGround: Boolean? = null,
    val isGliding: Boolean? = null,
    val isFrozen: Boolean? = null,
    val isInPowderedSnow: Boolean? = null,
    val isInCobweb: Boolean? = null,
    val isInsideVehicle: Boolean? = null,
    val isRiptiding: Boolean? = null,
    val isInvisible: Boolean? = null,
    val isGlowing: Boolean? = null,
    val isInvurnerable: Boolean? = null,
    val isSilent: Boolean? = null,
    val isLeashed: Boolean? = null,

)

@AutoScan
class EntityConditionsChecker : GearyListener() {
    private val TargetScope.livingEntity by get<LivingEntity>()
    private val TargetScope.conditions by get<EntityConditions>()

    @Handler
    fun TargetScope.check(): Boolean = livingEntity !is Player &&
            conditions.isSleeping nullOrEquals livingEntity.isSleeping &&
            conditions.isSwimming nullOrEquals livingEntity.isSwimming &&
            conditions.isClimbing nullOrEquals livingEntity.isClimbing &&
            conditions.isJumping nullOrEquals livingEntity.isJumping &&
            conditions.isInLava nullOrEquals livingEntity.isInLava &&
            conditions.isInWater nullOrEquals livingEntity.isInWater &&
            conditions.isInBubbleColumn nullOrEquals livingEntity.isInBubbleColumn &&
            conditions.isInRain nullOrEquals livingEntity.isInRain &&
            conditions.isOnGround nullOrEquals livingEntity.isOnGround &&
            conditions.isGliding nullOrEquals livingEntity.isGliding &&
            conditions.isFrozen nullOrEquals livingEntity.isFrozen &&
            conditions.isInPowderedSnow nullOrEquals livingEntity.isInPowderedSnow &&
            conditions.isInCobweb nullOrEquals (livingEntity.location.block.type == Material.COBWEB) &&
            conditions.isInsideVehicle nullOrEquals livingEntity.isInsideVehicle &&
            conditions.isRiptiding nullOrEquals livingEntity.isRiptiding &&
            conditions.isInvisible nullOrEquals livingEntity.isInvisible &&
            conditions.isGlowing nullOrEquals livingEntity.isGlowing &&
            conditions.isInvurnerable nullOrEquals livingEntity.isInvulnerable &&
            conditions.isSilent nullOrEquals livingEntity.isSilent &&
            conditions.isLeashed nullOrEquals livingEntity.isLeashed

}
