package xyz.papermodloader.paper.event;

import net.minecraft.launchwrapper.Launch;
import xyz.papermodloader.paper.mod.ModLoader;

import java.net.MalformedURLException;

/**
 * Class that holds all of Papers Minecraft hooks.
 */
public class PaperHooks {
    public static void loadMods(boolean dev) {
        ModLoader.INSTANCE.discoverMods(dev);
        ModLoader.INSTANCE.getCandidates().stream().filter(candidate -> candidate.getFile() != null).forEach(candidate -> {
            try {
                Launch.classLoader.addURL(candidate.getFile().toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }
}
