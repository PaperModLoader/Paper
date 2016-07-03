package xyz.papermodloader.paper.launcher.transformer.client;

import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.launcher.PaperClassTransformer;

public class PaperClientDevClassTransformer extends PaperClassTransformer {
    public PaperClientDevClassTransformer() {
        super(Side.CLIENT, true);
    }
}
