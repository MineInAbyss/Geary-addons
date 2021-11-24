package com.mineinabyss.geary.minecraft

import com.mineinabyss.geary.minecraft.dsl.gearyAddon
import com.mineinabyss.geary.minecraft.events.ItemActionsListener
import com.mineinabyss.geary.minecraft.systems.BossBarDisplaySystem
import com.mineinabyss.geary.minecraft.systems.CooldownDisplaySystem
import com.mineinabyss.geary.minecraft.systems.MobActionsListener
import com.mineinabyss.geary.minecraft.systems.WearableItemListener
import com.mineinabyss.idofront.plugin.registerEvents
import com.mineinabyss.idofront.slimjar.IdofrontSlimjar
import org.bukkit.plugin.java.JavaPlugin

val gearyCommonsPlugin by lazy { JavaPlugin.getPlugin(GearyCommonsPlugin::class.java) }

class GearyCommonsPlugin : JavaPlugin() {
    override fun onEnable() {
        IdofrontSlimjar.loadToLibraryLoader(this)

        gearyAddon {
            systems(
                BossBarDisplaySystem,
                CooldownDisplaySystem,
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
