package com.mineinabyss.geary.minecraft

import com.mineinabyss.geary.minecraft.dsl.gearyAddon
import com.mineinabyss.geary.minecraft.systems.BossBarDisplaySystem
import org.bukkit.plugin.java.JavaPlugin

class GearyCommonsPlugin : JavaPlugin() {
    override fun onEnable() {
        gearyAddon {
            systems(BossBarDisplaySystem)
            autoscanAll()
        }
    }
}
