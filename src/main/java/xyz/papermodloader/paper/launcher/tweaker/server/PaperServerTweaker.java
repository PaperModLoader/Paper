package xyz.papermodloader.paper.launcher.tweaker.server;

import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.launcher.PaperTweaker;

public class PaperServerTweaker extends PaperTweaker {
    public PaperServerTweaker() {
        super("net.minecraft.server.MinecraftServer", "xyz.papermodloader.paper.launcher.transformer.server.PaperServerClassTransformer", Side.SERVER);
    }
}
