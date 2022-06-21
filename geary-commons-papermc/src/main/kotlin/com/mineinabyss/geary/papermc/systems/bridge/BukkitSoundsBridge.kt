package com.mineinabyss.geary.papermc.systems.bridge

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.commons.components.sound.EmitSound
import com.mineinabyss.geary.commons.components.sound.Sounds
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.idofront.destructure.component1
import com.mineinabyss.idofront.destructure.component2
import com.mineinabyss.idofront.destructure.component3
import com.mineinabyss.idofront.typealiases.BukkitEntity
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import org.bukkit.entity.Player

class BukkitSoundsBridge {
    @AutoScan
    class SilenceVanilla : GearyListener() {
        private val TargetScope.bukkit by onSet<BukkitEntity>()
        private val TargetScope.sounds by onSet<Sounds>()

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
            val (x, y, z) = bukkit.location
            bukkit.getNearbyEntities(32.0, 32.0, 32.0).filterIsInstance<Player>().forEach {
                val dist = bukkit.location.distance(it.location).toFloat()
                val volume = sounds.volume * ((32F - dist).coerceAtLeast(0F) / 32F)
                it.playSound(
                    Sound.sound(
                        Key.key(event.emit.sound),
                        Sound.Source.AMBIENT,
                        volume,
                        sounds.adjustedPitch()
                    ), x, y, z
                )
            }
        }
    }
}
