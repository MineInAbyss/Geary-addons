package com.mineinabyss.geary.papermc

import com.mineinabyss.geary.papermc.dsl.gearyAddon
import com.mineinabyss.geary.papermc.events.bridge.ItemActionsListener
import com.mineinabyss.geary.papermc.events.bridge.MobActionsListener
import com.mineinabyss.geary.papermc.systems.WearableItemListener
import com.mineinabyss.idofront.platforms.IdofrontPlatforms
import com.mineinabyss.idofront.plugin.registerEvents
import org.bukkit.plugin.java.JavaPlugin

val gearyCommonsPlugin by lazy { JavaPlugin.getPlugin(GearyCommonsPlugin::class.java) }

class GearyCommonsPlugin : JavaPlugin() {
    override fun onLoad() {
        IdofrontPlatforms.load(this, "mineinabyss")
    }

    override fun onEnable() {
        gearyAddon {
            autoScanAll()
        }

        registerEvents(
            WearableItemListener,
            ItemActionsListener,
            MobActionsListener,
        )
    }
}
