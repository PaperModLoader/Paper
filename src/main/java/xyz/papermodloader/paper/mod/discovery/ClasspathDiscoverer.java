package xyz.papermodloader.paper.mod.discovery;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.launchwrapper.Launch;
import xyz.papermodloader.paper.mod.ModLoader;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Mod discoverer that searches in the classpath for {@code mod.json} files.
 */
public class ClasspathDiscoverer implements Discoverer {
    /**
     * Called when Minecraft is about to launch.
     *
     * @return a {@link List} of mod candidates that were found.
     */
    @Override
    public List<ModCandidate> findMods() {
        List<ModCandidate> mods = new ArrayList<>();
        JsonParser parser = new JsonParser();
        try {
            URL roots;
            Enumeration<URL> resources = Launch.classLoader.getResources("");
            while (resources.hasMoreElements()) {
                roots = resources.nextElement();
                File root = new File(roots.getPath());
                File[] files = root.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().equals("mod.json")) {
                            InputStream stream = new FileInputStream(file);
                            JsonArray array = parser.parse(new InputStreamReader(stream)).getAsJsonArray();
                            stream.close();
                            for (JsonElement element : array) {
                                JsonObject object = element.getAsJsonObject();
                                if (!object.has("id")) {
                                    ModLoader.LOGGER.error("Mod in file " + root.getName() + " doesn't have an id");
                                    continue;
                                } else if (!object.has("main_class")) {
                                    ModLoader.LOGGER.error("Mod in file " + root.getName() + " doesn't have a main class");
                                    continue;
                                }
                                ModCandidate candidate = new ModCandidate(object);
                                ModLoader.LOGGER.info("Found mod with id " + object.get("id").getAsString());
                                mods.add(candidate);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mods;
    }
}
