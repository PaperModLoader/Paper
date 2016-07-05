package xyz.papermodloader.paper.launcher;

import net.minecraft.launchwrapper.IClassTransformer;
import xyz.papermodloader.paper.launcher.side.Side;

public class PaperClassTransformer implements IClassTransformer {
    private Side side;
    private boolean dev;

    public PaperClassTransformer(Side side, boolean dev) {
        this.side = side;
        this.dev = dev;
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        return bytes;
    }
}

