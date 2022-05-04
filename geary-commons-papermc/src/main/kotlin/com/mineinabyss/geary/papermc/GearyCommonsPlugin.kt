package com.mineinabyss.geary.papermc

import com.mineinabyss.geary.addon.autoscan
import com.mineinabyss.geary.papermc.dsl.gearyAddon
import com.mineinabyss.geary.papermc.systems.DeathMessageSystem
import com.mineinabyss.geary.papermc.systems.NoBreedingSystem
import com.mineinabyss.geary.papermc.systems.NoMobInteractionsSystem
import com.mineinabyss.geary.papermc.systems.WearableItemSystem
import com.mineinabyss.geary.papermc.systems.bridge.DeathBridge
import com.mineinabyss.geary.papermc.systems.bridge.ItemActionsBridge
import com.mineinabyss.geary.papermc.systems.bridge.MobActionsBridge
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
            autoscan("com.mineinabyss") {
                all()
            }
        }

        registerEvents(
            DeathMessageSystem(),
            WearableItemSystem(),
            ItemActionsBridge(),
            MobActionsBridge(),
            NoMobInteractionsSystem(),
            DeathBridge(),
            NoBreedingSystem()
        )
    }
}
