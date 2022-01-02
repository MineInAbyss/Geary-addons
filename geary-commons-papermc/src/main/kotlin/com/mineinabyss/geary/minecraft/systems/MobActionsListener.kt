package com.mineinabyss.geary.minecraft.systems

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent
import com.mineinabyss.geary.minecraft.access.toGeary
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.ProjectileHitEvent

object MobActionsListener : Listener {
    @EventHandler(ignoreCancelled = true)
    fun ProjectileCollideEvent.onCollision() {
        entity.toGeary().callEvent(this)
    }

    @EventHandler(ignoreCancelled = true)
    fun ProjectileHitEvent.onProjectileLand() {
        entity.toGeary().callEvent(this)
    }

    @EventHandler(ignoreCancelled = true)
    fun EntityDamageByEntityEvent.onDamage() {
        entity.toGeary().callEvent(this)
    }

    @EventHandler(ignoreCancelled = true)
    fun EntityDamageEvent.onDamaged() {
        entity.toGeary().callEvent(this)
    }
}
