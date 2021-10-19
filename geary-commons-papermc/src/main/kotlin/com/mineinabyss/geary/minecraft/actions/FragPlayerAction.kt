package com.mineinabyss.geary.minecraft.actions

import com.mineinabyss.geary.ecs.api.actions.GearyAction
import com.mineinabyss.geary.ecs.api.entities.GearyEntity
import com.mineinabyss.geary.minecraft.gearyCommonsPlugin
import com.okkero.skedule.schedule
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Sound
import org.bukkit.entity.Creeper
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import kotlin.random.Random

/**
 * Spawns a bunch of powered creepers around a player.
 */
@Serializable
@SerialName("geary:frag")
class FragPlayerAction(
    private val howMuch: Int
) : GearyAction() {
    val GearyEntity.player by get<Player>()

    override fun GearyEntity.run(): Boolean {
        player.playSound(player.location, Sound.ENTITY_GHAST_SCREAM, 1F, 0.5F)
        gearyCommonsPlugin.schedule {
            for (i in 0..howMuch) {
                if (player.isDead)
                    break;
                val creeper = (player.world.spawnEntity(
                    player.location.add(
                        Vector(
                            Random.nextDouble(-2.0, 2.0),
                            Random.nextDouble(0.0, 2.0),
                            Random.nextDouble(-2.0, 2.0),
                        )
                    ),
                    EntityType.CREEPER
                ) as Creeper)
                creeper.isPowered = true
                waitFor(2)
            }
        }
        return true
    }
}
