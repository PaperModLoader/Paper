package xyz.papermodloader.paper.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.papermodloader.paper.event.EventHandler;
import xyz.papermodloader.paper.event.impl.UpdateEvent;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "handleUpdate()V", at = @At("HEAD"), remap = false)
    public void handleUpdate(CallbackInfo info) {
        EventHandler.UPDATE_ENTITY.post(new UpdateEvent.Entity((Entity) (Object) this));
    }
}
