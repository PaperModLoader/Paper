package xyz.papermodloader.paper.mod;

import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.DynamicResource;
import net.minecraft.util.Identifier;
import xyz.papermodloader.paper.Paper;
import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.mod.dependency.DependencyHandler;
import xyz.papermodloader.paper.mod.dependency.Version;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class JSONModContainer implements ModContainer {
    private transient Mod mod;
    private transient File file;
    private transient Version versionObj;
    private transient Version minecraftVersionObj;
    private transient Side sideObj;
    private transient BufferedImage logoObj;
    private transient Identifier identifier;
    private transient DependencyHandler dependencyHandler;

    private String main_class;
    private String id;
    private String name = id;
    private String version = "1.0.0";
    private String mc_version = Paper.MINECRAFT_VERSION.getVersion();
    private String description = "";
    private String author = "";
    private String side = "";
    private String logo = "paper.png";
    private JsonArray dependencies = null;

    public JSONModContainer(File file, JsonObject object) {
        this.file = file;
        new GsonBuilder().registerTypeAdapter(this.getClass(), (InstanceCreator<JSONModContainer>) type -> this).create().fromJson(object, this.getClass());
        this.versionObj = new Version(this.version);
        this.minecraftVersionObj = new Version(this.mc_version);
        this.sideObj = this.side.isEmpty() ? null : Side.valueOf(this.side.toUpperCase(Locale.ENGLISH));
        try {
            this.logoObj = ImageIO.read(this.getClass().getResourceAsStream("/" + this.logo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dependencyHandler = new DependencyHandler(this.dependencies);
    }

    @Override
    public Mod getInstance() {
        if (this.mod == null) {
            try {
                this.mod = (Mod) Class.forName(this.main_class).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.mod;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Version getVersion() {
        return this.versionObj;
    }

    @Override
    public Version getMinecraftVersion() {
        return this.minecraftVersionObj;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getAuthor() {
        return this.author;
    }

    @Override
    public Side getSide() {
        return this.sideObj;
    }

    @Override
    public Identifier getLogo() {
        if (this.identifier == null) {
            this.identifier = MinecraftClient.get().getResourceHandler().a("mods/" + this.getID(), new DynamicResource(this.logoObj));
        }
        return this.identifier;
    }

    @Override
    public DependencyHandler getDependencies() {
        return this.dependencyHandler;
    }

    @Override
    public File getModFile() {
        return this.file;
    }
}
