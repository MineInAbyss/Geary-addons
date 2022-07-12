package com.mineinabyss.geary.papermc.systems.bridge

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent
import com.mineinabyss.geary.commons.components.Spawning
import com.mineinabyss.geary.commons.components.interaction.*
import com.mineinabyss.geary.papermc.access.toGeary
import com.mineinabyss.geary.papermc.events.GearyMinecraftSpawnEvent
import com.mineinabyss.geary.papermc.helpers.setBukkitEvent
import org.bukkit.GameEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause.*
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.world.GenericGameEvent

class MobActionsBridge : Listener {
    @EventHandler(ignoreCancelled = true)
    fun ProjectileCollideEvent.onCollision() {
        collidedWith.toGeary().callEvent(source = entity.toGeary()) {
            //TODO check direction
            if ((collidedWith as? Player)?.isBlocking != true)
                set(Touched())
            setBukkitEvent(this@onCollision)
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun ProjectileHitEvent.onProjectileLand() {
        entity.toGeary().callEvent {
            set(Landed())
            setBukkitEvent(this@onProjectileLand)
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun EntityDamageByEntityEvent.onDamage() {
        entity.toGeary().callEvent(source = damager.toGeary()) {
            if (this@onDamage.cause in setOf(CONTACT, ENTITY_ATTACK, ENTITY_SWEEP_ATTACK))
                setAll(setOf(Touched(), Attacked()))
            setBukkitEvent(this@onDamage)
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun GenericGameEvent.onMove() {
        val comp: Any = when (event) {
            GameEvent.STEP -> Moved()
            GameEvent.SWIM -> Swam()
            GameEvent.HIT_GROUND -> Fell()
            GameEvent.SPLASH -> Splash()
            else -> return
        }

        entity?.toGeary()?.callEvent {
            set(comp)
            setBukkitEvent(this@onMove)
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun GearyMinecraftSpawnEvent.onSpawn() {
        entity.callEvent {
            set(Spawning())
            setBukkitEvent(this@onSpawn)
        }
    }

//    @EventHandler(ignoreCancelled = true)
//    fun EntityDamageEvent.onDamaged() {
//        entity.toGeary().callEvent {
//            setBukkitEvent(this@onDamaged)
//        }
//    }
}
