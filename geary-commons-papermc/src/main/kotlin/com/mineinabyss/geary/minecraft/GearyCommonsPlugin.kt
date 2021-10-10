package com.mineinabyss.geary.minecraft

import com.mineinabyss.geary.ecs.systems.PassiveActionsSystem
import com.mineinabyss.geary.minecraft.dsl.gearyAddon
import com.mineinabyss.geary.minecraft.systems.BossBarDisplaySystem
import com.mineinabyss.geary.minecraft.systems.InventoryListener
import com.mineinabyss.idofront.plugin.registerEvents
import org.bukkit.plugin.java.JavaPlugin

class GearyCommonsPlugin : JavaPlugin() {
    override fun onEnable() {
        gearyAddon {
            systems(
                BossBarDisplaySystem,
                PassiveActionsSystem
            )
            autoscanAll()
        }

        registerEvents(InventoryListener)
    }
}
