package xyz.papermodloader.paper.mod.discovery;

import com.google.gson.JsonObject;

import java.io.File;

/**
 * A mod candidate found in one of the {@link Discoverer}s.
 */
public class ModCandidate {
    /**
     * The json object of the found mod.
     */
    private JsonObject object;

    /**
     * The file of the found mod. Null if the mod is already in the classpath.
     */
    private File file;

    public ModCandidate(JsonObject object) {
        this(object, null);
    }

    public ModCandidate(JsonObject object, File file) {
        this.object = object;
        this.file = file;
    }

    /**
     * @return the json object of the found mod.
     */
    public JsonObject getObject() {
        return this.object;
    }

    /**
     * @return the file of the found mod. Null if the mod is already in the classpath.
     */
    public File getFile() {
        return this.file;
    }
}
