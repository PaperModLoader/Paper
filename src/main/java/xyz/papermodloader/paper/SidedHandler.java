package xyz.papermodloader.paper;

import net.minecraft.server.MinecraftServer;
import xyz.papermodloader.paper.launcher.side.Side;

/**
 * Sided handler, used to initialize Paper on a specific side.
 */
public interface SidedHandler {
    /**
     * Called when Paper initializes.
     */
    void onInitialize();

    /**
     * @return the side of this handler.
     * @see xyz.papermodloader.paper.launcher.side.Side
     */
    Side getSide();

    /**
     * @return the server instance of this specific side.
     */
    MinecraftServer getServer();
}
