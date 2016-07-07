package xyz.papermodloader.paper.mixin;

import net.minecraft.client.gui.ButtonGUI;
import net.minecraft.client.gui.MainMenuGUI;
import net.minecraft.client.gui.ScreenGUI;
import net.minecraft.client.translate.Translator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainMenuGUI.class)
public abstract class MainMenuGUIMixin extends ScreenGUI {
    @Shadow
    private ButtonGUI K;

    private void b(int a1, int a2) {
        this.addButton(new ButtonGUI(1, this.l / 2 - 100, a1, Translator.translate("menu.singleplayer", new Object[]{})));
        this.addButton(new ButtonGUI(2, this.l / 2 - 100, a1 + a2, Translator.translate("menu.multiplayer", new Object[]{})));
        this.K = this.addButton(new ButtonGUI(14, this.l / 2 + 2, a1 + a2 * 2, 98, 20, Translator.translate("menu.online", new Object[]{}).replace("Minecraft", "").trim()));
        this.addButton(new ButtonGUI(100, this.l / 2 - 100, a1 + a2 * 2, 98, 20, "Paper"));
    }

    @Inject(method = "onButtonClicked(Lnet/minecraft/client/gui/ButtonGUI;)V", at = @At("RETURN"), remap = false)
    public void onButtonClicked(ButtonGUI button, CallbackInfo info) {
        if (button.id == 100) {
            System.out.println("Paper");
        }
    }
}
