package com.mineinabyss.geary.papermc.components.sound

import com.mineinabyss.geary.datatypes.GearyEntity

class EmitSound(val sound: String)

//TODO think of a better place to put this, something less inheritance-ey
fun GearyEntity.makeSound(sound: String?) {
    if (sound == null) return
    callEvent {
        set(EmitSound(sound))
    }
}
