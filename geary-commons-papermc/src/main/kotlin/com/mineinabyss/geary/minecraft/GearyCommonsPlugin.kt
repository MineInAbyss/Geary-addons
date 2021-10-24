package com.mineinabyss.geary.minecraft

import com.mineinabyss.geary.ecs.systems.PassiveActionsSystem
import com.mineinabyss.geary.minecraft.dsl.gearyAddon
import com.mineinabyss.geary.minecraft.systems.*
import com.mineinabyss.idofront.plugin.registerEvents
import org.bukkit.plugin.java.JavaPlugin

val gearyCommonsPlugin by lazy { JavaPlugin.getPlugin(GearyCommonsPlugin::class.java) }

class GearyCommonsPlugin : JavaPlugin() {
    override fun onEnable() {
        gearyAddon {
            systems(
                BossBarDisplaySystem,
                CooldownDisplaySystem,
                PassiveActionsSystem,
                ItemStackingSystem,
            )
            autoscanAll()
        }

        registerEvents(
            WearableItemListener,
            ItemActionsListener,
            MobActionsListener,
        )
    }
}
