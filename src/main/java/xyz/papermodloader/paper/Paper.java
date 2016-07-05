package xyz.papermodloader.paper;

import net.minecraft.launchwrapper.Launch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.mod.dependency.Version;

import java.lang.reflect.Method;

/**
 * The main class for Paper
 */
public enum Paper {
    /**
     * The singleton instance of Paper.
     */
    INSTANCE;

    /**
     * The current version of Paper, without build number.
     */
    public static final Version VERSION = new Version("0.1.0");

    /**
     * The current version of minecraft.
     */
    public static final Version MINECRAFT_VERSION = new Version("1.10.2");

    /**
     * Paper's builtin logger. Mods shouldn't use this logger, but should define their own.
     */
    public static final Logger LOGGER = LogManager.getLogger("Paper");

    private SidedHandler sidedHandler;

    /**
     * Called when Minecraft initializes.
     *
     * @param sidedHandler the sided handler.
     */
    public void onInitialize(SidedHandler sidedHandler) {
        Paper.LOGGER.info("Initializing Paper " + Paper.VERSION);
        this.sidedHandler = sidedHandler;
        this.sidedHandler.onInitialize();
        try {
            Class<?> modLoader = Class.forName("xyz.papermodloader.paper.mod.ModLoader", true, Launch.classLoader);
            Enum instance = Enum.valueOf((Class<Enum>) modLoader, "INSTANCE");
            Method onInitialize = modLoader.getDeclaredMethod("onInitialize", Side.class);
            onInitialize.invoke(instance, this.getSidedHandler().getSide());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the sided handler of this Paper installation.
     */
    public SidedHandler getSidedHandler() {
        return this.sidedHandler;
    }
}