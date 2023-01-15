package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.papermc.access.toBukkit
import com.mineinabyss.geary.papermc.components.DontSpawnWithArmor
import com.mineinabyss.geary.papermc.events.GearyMinecraftSpawnEvent
import org.bukkit.entity.Mob
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

@AutoScan
class DontSpawnWithArmorSystem : Listener {

    @EventHandler
    fun GearyMinecraftSpawnEvent.onSpawnWithArmor() {
        val mob = entity.toBukkit<Mob>() ?: return
        if (entity.has<DontSpawnWithArmor>()) mob.equipment.clear()
    }
}
