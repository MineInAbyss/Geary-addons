package com.mineinabyss.geary.minecraft

import com.mineinabyss.geary.minecraft.dsl.gearyAddon
import com.mineinabyss.geary.minecraft.events.ItemActionsListener
import com.mineinabyss.geary.minecraft.systems.MobActionsListener
import com.mineinabyss.geary.minecraft.systems.WearableItemListener
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
            ItemStackingListener,
        )
    }
}
