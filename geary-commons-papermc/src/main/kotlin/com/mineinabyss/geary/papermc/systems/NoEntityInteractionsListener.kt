package com.mineinabyss.geary.papermc.systems

import com.mineinabyss.geary.annotations.AutoScan
import com.mineinabyss.geary.annotations.Handler
import com.mineinabyss.geary.papermc.GearyMCContext
import com.mineinabyss.geary.papermc.GearyMCContextKoin
import com.mineinabyss.geary.papermc.access.toGeary
import com.mineinabyss.geary.papermc.components.NoVanillaInteractions
import com.mineinabyss.geary.systems.GearyListener
import com.mineinabyss.geary.systems.accessors.TargetScope
import com.mineinabyss.idofront.typealiases.BukkitEntity
import io.papermc.paper.event.entity.EntityMoveEvent
import org.bukkit.entity.ArmorStand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

object NoEntityInteractionsListener : Listener, GearyMCContext by GearyMCContextKoin() {
    @EventHandler
    fun EntityMoveEvent.cancelMovement() {
        if(!entity.toGeary().has<NoVanillaInteractions>()) return
        isCancelled = true
    }

    @AutoScan
    class SetInvulnerable: GearyListener() {
        val TargetScope.bukkit by added<BukkitEntity>()
        val TargetScope.cancel by added<NoVanillaInteractions>()

        @Handler
        fun TargetScope.handle() {
            bukkit.isInvulnerable = true
            val armorstand = bukkit as? ArmorStand ?: return
            armorstand.isMarker = true
            armorstand.setGravity(false)
        }
    }
}
