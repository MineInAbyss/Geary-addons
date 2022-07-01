package com.mineinabyss.geary.commons.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.commons.components.Dead
import com.mineinabyss.geary.commons.components.Spawning
import com.mineinabyss.geary.commons.components.interaction.Attacked
import com.mineinabyss.geary.commons.components.interaction.Moved
import com.mineinabyss.geary.commons.components.interaction.Splash
import com.mineinabyss.geary.commons.components.interaction.Swam
import com.mineinabyss.geary.commons.components.sound.Sounds
import com.mineinabyss.geary.commons.components.sound.makeSound
import com.mineinabyss.geary.datatypes.family.MutableFamilyOperations.Companion.has
import com.mineinabyss.geary.datatypes.family.MutableFamilyOperations.Companion.not
import com.mineinabyss.geary.datatypes.family.family
import com.mineinabyss.geary.prefabs.configuration.components.Prefab
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.TickingSystem
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.geary.systems.accessors.get
import kotlin.random.Random

class EntitySoundSystem {
    @AutoScan
    class AmbientSounds : TickingSystem() {
        private val TargetScope.sounds by get<Sounds>()
        private val TargetScope.noPrefabs by family { not { has<Prefab>() } }

        override fun TargetScope.tick() {
            if (Random.nextDouble() < sounds.ambientChance)
                entity.makeSound(sounds.ambient)
        }
    }

    @AutoScan
    class SpawnSound : GearyListener() {
        private val TargetScope.sounds by get<Sounds>()
        private val EventScope.spawning by get<Spawning>()

        @Handler
        fun TargetScope.spawn() {
            entity.makeSound(sounds.spawn)
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

        private val EventScope.damaged by family { has<Attacked>() }

        @Handler
        fun TargetScope.damaged() {
            entity.makeSound(sounds.hurt)
        }
    }

    @AutoScan
    class StepSound : GearyListener() {
        private val TargetScope.sounds by get<Sounds>()
        private val EventScope.step by family { has<Moved>() }

        @Handler
        fun TargetScope.step() {
            entity.makeSound(sounds.step)
        }
    }

    @AutoScan
    class SwimSound : GearyListener() {
        private val TargetScope.sounds by get<Sounds>()
        private val EventScope.swim by family { has<Swam>() }

        @Handler
        fun TargetScope.step() {
            entity.makeSound(sounds.swim)
        }
    }

    @AutoScan
    class SplashSound : GearyListener() {
        private val TargetScope.sounds by get<Sounds>()
        private val EventScope.splash by family { has<Splash>() }

        @Handler
        fun TargetScope.step() {
            entity.makeSound(sounds.splash)
        }
    }
}
