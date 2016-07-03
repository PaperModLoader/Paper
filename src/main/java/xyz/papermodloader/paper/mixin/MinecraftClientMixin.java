package xyz.papermodloader.paper.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ScreenGUI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.papermodloader.paper.Paper;
import xyz.papermodloader.paper.client.PaperClientHandler;
import xyz.papermodloader.paper.event.EventHandler;
import xyz.papermodloader.paper.event.impl.GUIEvent;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "initialize()V", at = @At("HEAD"), remap = false)
    private void onInitialize(CallbackInfo info) {
        Paper.INSTANCE.onInitialize(new PaperClientHandler());
    }

    @Inject(method = "openGUI(Lnet/minecraft/client/gui/ScreenGUI;)V", cancellable = true, at = @At("HEAD"), remap = false)
    public void openGUI(ScreenGUI screen, CallbackInfo info) {
        GUIEvent.Open event = new GUIEvent.Open(screen);
        if (!EventHandler.GUI_OPEN.post(event)) {
            info.cancel();
        }
    }
}
