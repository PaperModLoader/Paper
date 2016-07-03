package xyz.papermodloader.paper.launcher.transformer.server;

import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.launcher.PaperClassTransformer;

public class PaperServerClassTransformer extends PaperClassTransformer {
    public PaperServerClassTransformer() {
        super(Side.SERVER, false);
    }
}
