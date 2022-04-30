package com.mineinabyss.geary.papermc.systems.bridge

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.commons.components.sound.EmitSound
import com.mineinabyss.geary.commons.components.sound.Sounds
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.geary.systems.accessors.get
import com.mineinabyss.idofront.typealiases.BukkitEntity
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound

class BukkitSoundsBridge {
    @AutoScan
    class SilenceVanilla : GearyListener() {
        private val TargetScope.bukkit by added<BukkitEntity>()
        private val TargetScope.sounds by added<Sounds>()

        @Handler
        fun TargetScope.removeSounds() {
            bukkit.isSilent = true
        }
    }

    @AutoScan
    class HandleEmittedSound : GearyListener() {
        private val TargetScope.bukkit by get<BukkitEntity>()
        private val TargetScope.sounds by get<Sounds>()

        private val EventScope.emit by get<EmitSound>()

        @Handler
        fun TargetScope.makeSound(event: EventScope) {
            bukkit.world.playSound(
                Sound.sound(
                    Key.key(event.emit.sound),
                    Sound.Source.AMBIENT,
                    sounds.volume,
                    sounds.adjustedPitch()
                ),
                bukkit
            )
        }
    }
}
