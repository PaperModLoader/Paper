package xyz.papermodloader.paper.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.papermodloader.paper.Paper;
import xyz.papermodloader.paper.server.PaperServerHandler;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "run()V", at = @At("HEAD"), remap = false)
    private void onInitialize(CallbackInfo info) {
        Paper.INSTANCE.onInitialize(new PaperServerHandler((MinecraftServer) (Object) this));
    }
}
