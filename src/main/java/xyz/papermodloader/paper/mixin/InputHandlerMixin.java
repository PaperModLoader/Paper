package xyz.papermodloader.paper.mixin;

import net.minecraft.util.InputHandler;
import net.minecraft.util.Position;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.papermodloader.paper.event.EventHandler;
import xyz.papermodloader.paper.event.impl.BlockEvent;

@Mixin(InputHandler.class)
public abstract class InputHandlerMixin {
    @Shadow
    public World world;

    @Inject(method = "canDestroyBlock(Lnet/minecraft/util/Position;)Z", cancellable = true, at = @At(value = "HEAD"), remap = false)
    public void canDestroyBlock(Position position, CallbackInfoReturnable info) {
        BlockEvent.Destroy event = new BlockEvent.Destroy(this.world, this.world.getBlockState(position), position);
        if (!EventHandler.BLOCK_DESTROY.post(event)) {
            info.cancel();
        }
    }
}
