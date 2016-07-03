package xyz.papermodloader.paper.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ClientInputHandler;
import net.minecraft.util.Position;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.papermodloader.paper.event.EventHandler;
import xyz.papermodloader.paper.event.impl.BlockEvent;

@Mixin(ClientInputHandler.class)
public abstract class ClientInputHandlerMixin {
    @Inject(method = "a(Lnet/minecraft/util/Position;)Z", cancellable = true, at = @At("HEAD"), remap = false)
    public void canDestroyBlock(Position position, CallbackInfoReturnable info) {
        World world = MinecraftClient.get().clientWorld;
        BlockEvent.Destroy event = new BlockEvent.Destroy(world, world.getBlockState(position), position);
        if (!EventHandler.BLOCK_DESTROY.post(event)) {
            info.cancel();
        }
    }
}
