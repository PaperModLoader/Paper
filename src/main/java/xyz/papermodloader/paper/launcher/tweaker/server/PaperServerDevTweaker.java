package xyz.papermodloader.paper.launcher.tweaker.server;

import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.launcher.PaperTweaker;

public class PaperServerDevTweaker extends PaperTweaker {
    public PaperServerDevTweaker() {
        super("net.minecraft.server.MinecraftServer", "xyz.papermodloader.paper.launcher.transformer.server.PaperServerDevClassTransformer", Side.SERVER);
    }
}
