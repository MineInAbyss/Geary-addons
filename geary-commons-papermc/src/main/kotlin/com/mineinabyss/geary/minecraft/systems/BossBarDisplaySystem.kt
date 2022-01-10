package com.mineinabyss.geary.minecraft.systems

import com.mineinabyss.geary.ecs.accessors.TargetScope
import com.mineinabyss.geary.ecs.accessors.get
import com.mineinabyss.geary.ecs.api.autoscan.AutoScan
import com.mineinabyss.geary.ecs.api.systems.TickingSystem
import com.mineinabyss.geary.minecraft.components.DisplayBossBar
import com.mineinabyss.idofront.typealiases.BukkitEntity

/**
 * Handles displaying of boss bars to players in range.
 * Uses values from the DisplayBossBar component.
 */
@AutoScan
class BossBarDisplaySystem : TickingSystem(interval = 10) {
    private val TargetScope.bossbar by get<DisplayBossBar>()
    private val TargetScope.bukkitentity by get<BukkitEntity>()

    override fun TargetScope.tick() {
        val location = bukkitentity.location
        val playersInRange = location.getNearbyPlayers(bossbar.range).mapTo(mutableSetOf()) { it.uniqueId }

        // Gets players to add and remove
        val addPlayers = playersInRange - bossbar.playersInRange
        val removePlayers = bossbar.playersInRange - playersInRange

        // Removes and adds the necessary players
        bossbar.playersInRange.removeAll(removePlayers)
        bossbar.playersInRange.addAll(addPlayers)

    }
}
