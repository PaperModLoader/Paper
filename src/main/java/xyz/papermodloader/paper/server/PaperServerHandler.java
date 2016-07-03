package xyz.papermodloader.paper.server;

import net.minecraft.server.MinecraftServer;
import xyz.papermodloader.paper.SidedHandler;
import xyz.papermodloader.paper.launcher.side.Side;

/**
 * Sided handler, used to initialize Paper on a specific side.
 */
public class PaperServerHandler implements SidedHandler {
    private MinecraftServer server;

    public PaperServerHandler(MinecraftServer server) {
        this.server = server;
    }

    /**
     * Called when Paper initializes.
     */
    @Override
    public void onInitialize() {

    }

    /**
     * @return the side of this handler.
     * @see xyz.papermodloader.paper.launcher.side.Side
     */
    @Override
    public Side getSide() {
        return Side.SERVER;
    }

    /**
     * @return the server instance of this specific side.
     */
    @Override
    public MinecraftServer getServer() {
        return this.server;
    }
}
