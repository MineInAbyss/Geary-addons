package com.mineinabyss.geary.papermc.systems.bridge

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent
import com.mineinabyss.geary.commons.components.interaction.Attacked
import com.mineinabyss.geary.commons.components.interaction.Landed
import com.mineinabyss.geary.commons.components.interaction.Touched
import com.mineinabyss.geary.papermc.access.toGeary
import com.mineinabyss.geary.papermc.helpers.setBukkitEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause.*
import org.bukkit.event.entity.ProjectileHitEvent

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

//    @EventHandler(ignoreCancelled = true)
//    fun EntityDamageEvent.onDamaged() {
//        entity.toGeary().callEvent {
//            setBukkitEvent(this@onDamaged)
//        }
//    }
}
