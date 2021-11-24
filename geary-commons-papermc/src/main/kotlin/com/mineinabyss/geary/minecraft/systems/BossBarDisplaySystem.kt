package com.mineinabyss.geary.minecraft.systems

import com.mineinabyss.geary.ecs.accessors.ResultScope
import com.mineinabyss.geary.ecs.api.systems.TickingSystem
import com.mineinabyss.geary.minecraft.components.DisplayBossBar
import com.mineinabyss.idofront.typealiases.BukkitEntity

/**
 * Handles displaying of boss bars to players in range.
 * Uses values from the DisplayBossBar component.
 */
object BossBarDisplaySystem : TickingSystem(interval = 10) {
    private val ResultScope.bossbar by get<DisplayBossBar>()
    private val ResultScope.bukkitentity by get<BukkitEntity>()

    override fun ResultScope.tick() {
        val location = bukkitentity.location
        val playersInRange = location.getNearbyPlayers(bossbar.range).map { it.uniqueId }

        // Gets players to add and remove
        val addPlayers = playersInRange - bossbar.playersInRange
        val removePlayers = bossbar.playersInRange - playersInRange

        // Removes and adds the necessary players
        bossbar.playersInRange.removeAll(removePlayers)
        bossbar.playersInRange.addAll(addPlayers)

    }
}
