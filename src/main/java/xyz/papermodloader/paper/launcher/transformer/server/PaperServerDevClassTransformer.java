package xyz.papermodloader.paper.launcher.transformer.server;

import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.launcher.PaperClassTransformer;

public class PaperServerDevClassTransformer extends PaperClassTransformer {
    public PaperServerDevClassTransformer() {
        super(Side.SERVER, true);
    }
}
