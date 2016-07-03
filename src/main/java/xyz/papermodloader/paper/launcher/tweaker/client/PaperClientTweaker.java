package xyz.papermodloader.paper.launcher.tweaker.client;

import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.launcher.PaperTweaker;

public class PaperClientTweaker extends PaperTweaker {
    public PaperClientTweaker() {
        super("net.minecraft.client.main.Main", "xyz.papermodloader.paper.launcher.transformer.client.PaperClientClassTransformer", Side.CLIENT);
    }
}
