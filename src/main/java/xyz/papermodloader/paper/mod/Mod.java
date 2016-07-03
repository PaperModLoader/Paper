package xyz.papermodloader.paper.mod;

/**
 * Interface for mods. Mods *MUST* implement this interface in order to work.
 */
public interface Mod {
    /**
     * Called when Minecraft initializes.
     */
    void onInitialize();

    /**
     * Called when Minecraft finishes initializing.
     */
    void onPostInitialize();
}
