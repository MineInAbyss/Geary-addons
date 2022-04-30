package com.mineinabyss.geary.papermc.helpers

import com.mineinabyss.geary.datatypes.GearyEntity
import com.mineinabyss.looty.tracking.toGearyOrNull
import org.bukkit.entity.Player

val Player.heldLootyItem: GearyEntity? get() = inventory.itemInMainHand.toGearyOrNull(this)
