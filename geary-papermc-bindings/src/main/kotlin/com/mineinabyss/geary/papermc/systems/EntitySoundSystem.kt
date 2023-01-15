package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.commons.components.Dead
import com.mineinabyss.geary.commons.components.interaction.Attacked
import com.mineinabyss.geary.commons.components.sound.Sounds
import com.mineinabyss.geary.commons.components.sound.makeSound
import com.mineinabyss.geary.datatypes.family.family
import com.mineinabyss.geary.game.components.Dead
import com.mineinabyss.geary.game.components.interaction.Attacked
import com.mineinabyss.geary.game.components.sound.Sounds
import com.mineinabyss.geary.game.components.sound.makeSound
import com.mineinabyss.geary.prefabs.configuration.components.Prefab
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.RepeatingSystem
import com.mineinabyss.geary.systems.accessors.EventScope
import com.mineinabyss.geary.systems.accessors.TargetScope
import kotlin.random.Random

class EntitySoundSystem {
    @AutoScan
    class AmbientSounds : RepeatingSystem() {
        private val TargetScope.sounds by get<Sounds>()
        private val TargetScope.noPrefabs by family { not { has<Prefab>() } }

        override fun TargetScope.tick() {
            if (Random.nextDouble() < sounds.ambientChance)
                entity.makeSound(sounds.ambient)
        }
    }

    @AutoScan
    class DeathSound : GearyListener() {
        private val TargetScope.sounds by get<Sounds>()
        private val TargetScope.dead by onSet<Dead>()

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
}
