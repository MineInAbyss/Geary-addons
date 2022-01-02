package com.mineinabyss.geary.minecraft.systems

import com.mineinabyss.geary.minecraft.access.toGearyOrNull
import com.mineinabyss.geary.minecraft.components.DamageAmplifier
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class DamageAmplifierListener : Listener {
    @EventHandler(ignoreCancelled = true)
    fun EntityDamageEvent.onDamage() {
        val gearyEntity = entity.toGearyOrNull() ?: return
        val damageAmplifier = gearyEntity.get<DamageAmplifier>() ?: return
        if (cause == damageAmplifier.damageType)
            damage *= damageAmplifier.amplifier
    }
}