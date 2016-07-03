package xyz.papermodloader.paper.mod.dependency;

import xyz.papermodloader.paper.mod.ModContainer;
import xyz.papermodloader.paper.mod.ModLoader;

/**
 * A mod dependency container.
 */
public class ModDependency {
    private String id;
    private Version version;

    public ModDependency(String id, String version) {
        this.id = id;
        if (version != null) {
            this.version = new Version(version);
        }
    }

    /**
     * @return the mod id of the dependency.
     */
    public String getID() {
        return id;
    }

    /**
     * @return the minimum version of the dependency.
     */
    public Version getVersion() {
        return version;
    }

    /**
     * @return true is this dependency is installed.
     */
    public boolean isInstalled() {
        ModContainer container = ModLoader.INSTANCE.getModContainer(this.id);
        return container != null && container.getVersion().compareTo(this.version) >= 0;
    }
}
