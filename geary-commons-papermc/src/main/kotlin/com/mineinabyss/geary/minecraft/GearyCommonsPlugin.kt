package com.mineinabyss.geary.minecraft

import com.mineinabyss.geary.ecs.systems.PassiveActionsSystem
import com.mineinabyss.geary.minecraft.dsl.gearyAddon
import com.mineinabyss.geary.minecraft.systems.*
import com.mineinabyss.idofront.plugin.registerEvents
import com.mineinabyss.idofront.slimjar.IdofrontSlimjar
import org.bukkit.plugin.java.JavaPlugin

class GearyCommonsPlugin : JavaPlugin() {
    override fun onEnable() {
        IdofrontSlimjar.loadToLibraryLoader(this)

        gearyAddon {
            systems(
                BossBarDisplaySystem,
                CooldownDisplaySystem,
                PassiveActionsSystem,
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
