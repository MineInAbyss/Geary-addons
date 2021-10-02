package com.mineinabyss.geary.minecraft

import com.mineinabyss.geary.minecraft.dsl.gearyAddon
import kotlinx.serialization.InternalSerializationApi
import org.bukkit.plugin.java.JavaPlugin

class GearyCommonsPlugin : JavaPlugin() {
    @InternalSerializationApi
    override fun onEnable() {
        gearyAddon {
            autoscanAll()
        }
    }
}
