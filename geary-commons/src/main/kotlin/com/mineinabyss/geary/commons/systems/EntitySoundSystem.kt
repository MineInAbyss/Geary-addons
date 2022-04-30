package com.mineinabyss.geary.commons.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.commons.components.Dead
import com.mineinabyss.geary.commons.components.interaction.Attacked
import com.mineinabyss.geary.commons.components.sound.Sounds
import com.mineinabyss.geary.commons.components.sound.makeSound
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.TickingSystem
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.geary.systems.accessors.get
import com.mineinabyss.geary.systems.accessors.has
import kotlin.random.Random

class EntitySoundSystem {
    @AutoScan
    class AmbientSounds : TickingSystem() {
        private val TargetScope.sounds by get<Sounds>()

        override fun TargetScope.tick() {
            if (Random.nextDouble() < sounds.ambientChance)
                entity.makeSound(sounds.ambient)
        }
    }

    @AutoScan
    class DeathSound : GearyListener() {
        private val TargetScope.sounds by get<Sounds>()
        private val TargetScope.dead by added<Dead>()

        @Handler
        fun TargetScope.dead() {
            entity.makeSound(sounds.death)
        }
    }

    @AutoScan
    class DamageSound : GearyListener() {
        private val TargetScope.sounds by get<Sounds>()

        private val EventScope.damaged by has<Attacked>()

        @Handler
        fun TargetScope.damaged() {
            entity.makeSound(sounds.hurt)
        }
    }
}
