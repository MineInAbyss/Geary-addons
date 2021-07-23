package com.mineinabyss.geary.minecraft

import com.mineinabyss.geary.minecraft.dsl.attachToGeary
import com.mineinabyss.idofront.slimjar.LibraryLoaderInjector
import kotlinx.serialization.InternalSerializationApi
import org.bukkit.plugin.java.JavaPlugin

class GearyCommonsPlugin : JavaPlugin() {
    @InternalSerializationApi
    override fun onEnable() {
        LibraryLoaderInjector.inject(this)
        attachToGeary {
            autoscanComponents()
            autoscanConditions()
            autoscanActions()
        }
    }
}
