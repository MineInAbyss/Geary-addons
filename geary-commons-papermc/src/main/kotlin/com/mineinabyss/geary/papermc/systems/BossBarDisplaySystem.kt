package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.datatypes.family.family
import com.mineinabyss.geary.components.events.EntityRemoved
import com.mineinabyss.geary.datatypes.family.MutableFamilyOperations.Companion.has
import com.mineinabyss.geary.papermc.components.DisplayBossBar
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.TickingSystem
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.geary.systems.accessors.get
import com.mineinabyss.idofront.entities.toPlayer
import com.mineinabyss.idofront.typealiases.BukkitEntity
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import kotlin.time.Duration.Companion.seconds

/**
 * Handles displaying of boss bars to players in range.
 * Uses values from the DisplayBossBar component.
 */
@AutoScan
class BossBarDisplaySystem : TickingSystem(interval = 0.5.seconds), Listener {
    private val TargetScope.bossbar by get<DisplayBossBar>()
    private val TargetScope.bukkitentity by get<BukkitEntity>()

    override fun TargetScope.tick() {
        val bukkit = bukkitentity as? LivingEntity ?: return
        val location = bukkitentity.location
        val playersInRange = bukkitentity.getNearbyEntities(bossbar.range, bossbar.range, bossbar.range)
            .filterIsInstance<Player>().mapTo(mutableSetOf()) { it.uniqueId }

        // Gets players to add and remove
        val addPlayers = playersInRange - bossbar.playersInRange
        val removePlayers = bossbar.playersInRange - playersInRange

        // Removes and adds the necessary players
        bossbar.playersInRange.removeAll(removePlayers)
        bossbar.playersInRange.addAll(addPlayers)

        addPlayers.forEach { it.toPlayer()?.showBossBar(bossbar.bossBar) }
        removePlayers.forEach { it.toPlayer()?.hideBossBar(bossbar.bossBar) }
        bossbar.bossBar.progress(
            (bukkit.health / (bukkit.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 20.0)).toFloat()
        )
    }

    @AutoScan
    private class RemoveBossBarOnDeath : GearyListener() {
        //TODO convert to a component remove listener when those get added
        val TargetScope.bossBar by get<DisplayBossBar>()
        val TargetScope.removed by family { has<EntityRemoved>() }

        @Handler
        fun TargetScope.remove() {
            bossBar.playersInRange.forEach {
                it.toPlayer()?.hideBossBar(bossBar.bossBar)
            }
            bossBar.playersInRange.clear()
        }
    }
}
