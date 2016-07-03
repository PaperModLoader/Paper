package xyz.papermodloader.paper.launcher.transformer.client;

import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.launcher.PaperClassTransformer;

public class PaperClientClassTransformer extends PaperClassTransformer {
    public PaperClientClassTransformer() {
        super(Side.CLIENT, false);
    }
}
