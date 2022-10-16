package com.mineinabyss.geary.papermc

import com.mineinabyss.geary.addon.autoscan
import com.mineinabyss.geary.papermc.dsl.gearyAddon
import com.mineinabyss.geary.papermc.systems.*
import com.mineinabyss.geary.papermc.systems.bridge.DeathBridge
import com.mineinabyss.geary.papermc.systems.bridge.ItemActionsBridge
import com.mineinabyss.geary.papermc.systems.bridge.MobActionsBridge
import com.mineinabyss.idofront.platforms.Platforms
import com.mineinabyss.idofront.plugin.listeners
import org.bukkit.plugin.java.JavaPlugin

val gearyCommonsPlugin by lazy { JavaPlugin.getPlugin(GearyCommonsPlugin::class.java) }

class GearyCommonsPlugin : JavaPlugin() {
    override fun onLoad() {
        Platforms.load(this, "mineinabyss")
    }

    override fun onEnable() {
        gearyAddon {
            autoscan("com.mineinabyss") {
                all()
            }
        }

        listeners(
            DeathMessageSystem(),
            WearableItemSystem(),
            CustomFoodSystem(),
            ItemActionsBridge(),
            MobActionsBridge(),
            NoMobInteractionsSystem(),
            DeathBridge(),
            NoBreedingSystem()
        )
    }
}
