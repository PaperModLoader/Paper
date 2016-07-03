package xyz.papermodloader.paper.mod;

import com.google.common.collect.ImmutableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.papermodloader.paper.Paper;
import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.mod.dependency.ModDependency;
import xyz.papermodloader.paper.mod.discovery.ClasspathDiscoverer;
import xyz.papermodloader.paper.mod.discovery.DirectoryDiscoverer;
import xyz.papermodloader.paper.mod.discovery.Discoverer;
import xyz.papermodloader.paper.mod.discovery.ModCandidate;

import java.util.*;

public enum ModLoader {
    INSTANCE;

    public static final Logger LOGGER = LogManager.getLogger("PaperModLoader");

    private Map<String, ModContainer> containers = new HashMap<>();
    private Map<String, ModCandidate> candidates = new HashMap<>();

    public void discoverMods(boolean classpath) {
        if (!this.candidates.isEmpty()) {
            return;
        }
        ModLoader.LOGGER.info("Searching for mods");
        List<Discoverer> discoverers = new ArrayList<>();
        if (classpath) {
            discoverers.add(new ClasspathDiscoverer());
        }
        discoverers.add(new DirectoryDiscoverer());
        for (Discoverer discoverer : discoverers) {
            for (ModCandidate candidate : discoverer.findMods()) {
                String id = candidate.getObject().get("id").getAsString();
                if (this.candidates.containsKey(id)) {
                    ModLoader.LOGGER.warn("Ignoring duplicate mod " + id);
                } else {
                    this.candidates.put(id, candidate);
                }
            }
        }
        this.candidates = Collections.unmodifiableMap(this.candidates);
    }

    public void onInitialize(Side side) {
        if (!this.containers.isEmpty()) {
            return;
        } else if (this.candidates.isEmpty()) {
            return;
        }
        for (Map.Entry<String, ModCandidate> entry : this.candidates.entrySet()) {
            Paper.LOGGER.info("Loading mod " + entry.getKey());
            ModCandidate candidate = entry.getValue();
            try {
                Class<?> modClass = Class.forName(candidate.getObject().get("main_class").getAsString());
                if (!Mod.class.isAssignableFrom(modClass)) {
                    Paper.LOGGER.error("Class " + candidate.getObject().get("main_class").getAsString() + " does not implement Mod");
                    continue;
                }
                ModContainer container = new JSONModContainer(candidate.getFile(), candidate.getObject());
                if (container.getSide() == null || container.getSide() == side) {
                    List<ModDependency> missing = container.getDependencies().getMissingDependencies();
                    if (missing.isEmpty()) {
                        this.containers.put(container.getID(), container);
                        container.getInstance().onInitialize();
                    } else {
                        Paper.LOGGER.error("Mod " + container.getID() + " is missing " + missing.size() + " " + (missing.size() == 1 ? "dependency" : "dependencies") + "!");
                    }
                }
            } catch (Exception e) {
                Paper.LOGGER.error("Unable to initialize mod ", entry.getKey(), e);
            }
        }
    }

    public List<ModCandidate> getCandidates() {
        return ImmutableList.copyOf(this.candidates.values());
    }

    public List<ModContainer> getContainers() {
        return ImmutableList.copyOf(this.containers.values());
    }

    public ModContainer getModContainer(String id) {
        return this.containers.get(id);
    }

    public boolean isModLoaded(String id) {
        return this.containers.containsKey(id);
    }
}
