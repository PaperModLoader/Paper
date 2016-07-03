package xyz.papermodloader.paper.mixin;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.papermodloader.paper.event.EventHandler;
import xyz.papermodloader.paper.event.impl.UpdateEvent;

@Mixin(World.class)
public abstract class WorldMixin {
    @Inject(method = "handleUpdate()V", at = @At("HEAD"), remap = false)
    public void onUpdate(CallbackInfo info) {
        EventHandler.UPDATE_WORLD.post(new UpdateEvent.World((World) (Object) this));
    }
}
