package com.mineinabyss.geary.papermc.features.display.bossbar

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent
import com.mineinabyss.geary.helpers.with
import com.mineinabyss.geary.papermc.tracking.entities.toGeary
import com.mineinabyss.idofront.entities.toPlayer
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class BossBarInteractionSystem: Listener {
    @EventHandler
    fun EntityRemoveFromWorldEvent.removeOnDeath() {
        entity.toGeary().with { bossBar: DisplayBossBar ->
            bossBar.playersInRange.forEach {
                it.toPlayer()?.hideBossBar(bossBar.bossBar)
            }
        }
    }

    @EventHandler
    fun EntityDamageEvent.updateOnDamage() {
        val living = entity as? LivingEntity ?: return

        living.toGeary().with { bossBar: DisplayBossBar ->
            bossBar.setProgressToHealthOf(living)
        }
    }
}
