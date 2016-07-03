package xyz.papermodloader.paper.mod.discovery;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.papermodloader.paper.mod.ModLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Mod discoverer that searches in the {@code .minecraft/mods/} directory for jars with a {@code mod.json} file.
 */
public class DirectoryDiscoverer implements Discoverer {
    /**
     * Jar filename filter.
     */
    public static final FilenameFilter JAR_FILTER = (dir, name) -> name.endsWith(".jar");

    /**
     * Called when Minecraft is about to launch.
     *
     * @return a {@link List} of mod candidates that were found.
     */
    @Override
    public List<ModCandidate> findMods() {
        List<ModCandidate> mods = new ArrayList<>();
        List<File> files = new ArrayList<>();
        File dirMods = new File("mods");
        if (dirMods.exists() && dirMods.isDirectory()) {
            Collections.addAll(files, dirMods.listFiles(DirectoryDiscoverer.JAR_FILTER));
        }
        JsonParser parser = new JsonParser();
        for (File file : files) {
            ModLoader.LOGGER.debug("Checking jar file " + file.getAbsolutePath());
            try (JarFile jarFile = new JarFile(file)) {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith("mod.json")) {
                        InputStream stream = jarFile.getInputStream(entry);
                        JsonArray array = parser.parse(new InputStreamReader(stream)).getAsJsonArray();
                        stream.close();
                        for (JsonElement element : array) {
                            JsonObject object = element.getAsJsonObject();
                            if (!object.has("id")) {
                                ModLoader.LOGGER.error("Mod in file " + file.getName() + " doesn't have an id");
                                continue;
                            } else if (!object.has("main_class")) {
                                ModLoader.LOGGER.error("Mod in file " + file.getName() + " doesn't have a main class");
                                continue;
                            }
                            ModCandidate candidate = new ModCandidate(object, file);
                            ModLoader.LOGGER.info("Found mod with id " + object.get("id").getAsString());
                            mods.add(candidate);
                        }
                        jarFile.close();
                        break;
                    }
                }
            } catch (IOException e) {
                ModLoader.LOGGER.warn("Unable to check jar file", e);
            }
        }

        return mods;
    }
}
