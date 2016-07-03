package xyz.papermodloader.paper.mod;

import net.minecraft.util.Identifier;
import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.mod.dependency.DependencyHandler;
import xyz.papermodloader.paper.mod.dependency.Version;

import java.io.File;

/**
 * A mod container for found mods. Every instance of this class stores all known information about the mod.
 * All instances of this class exist on the {@link net.minecraft.launchwrapper.LaunchClassLoader} classloader.
 */
public interface ModContainer {
    /**
     * @return the mod instance.
     */
    Mod getInstance();

    /**
     * @return the unique mod id.
     */
    String getID();

    /**
     * @return the name of this mod.
     */
    String getName();

    /**
     * @return the version of this mod.
     */
    Version getVersion();

    /**
     * @return the minecraft version of this mod.
     */
    Version getMinecraftVersion();

    /**
     * @return the description of this mod.
     */
    String getDescription();

    /**
     * @return the author of this mod.
     */
    String getAuthor();

    /**
     * @return the side of this mod. If null, this mod is universal.
     */
    Side getSide();

    /**
     * @return the logo of the mod. If null, the default Paper logo will be used.
     */
    Identifier getLogo();

    /**
     * @return the dependency handler of this mod.
     */
    DependencyHandler getDependencies();

    /**
     * @return the source file of this mod. If null, this mod exists in the classpath.
     */
    File getModFile();
}
