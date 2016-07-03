package xyz.papermodloader.paper.mod.discovery;

import java.util.List;

/**
 * Interface for classes that search for mods on specific locations.
 */
public interface Discoverer {
    /**
     * Called when Minecraft is about to launch.
     *
     * @return a {@link List} of mod candidates that were found.
     */
    List<ModCandidate> findMods();
}
