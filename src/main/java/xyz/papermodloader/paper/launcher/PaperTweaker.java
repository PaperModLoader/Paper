package xyz.papermodloader.paper.launcher;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.util.Arguments;
import xyz.papermodloader.paper.util.LoggerPrintStream;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public class PaperTweaker implements ITweaker {
    private Arguments arguments;
    private String target;
    private String tweaker;
    private Side side;

    public PaperTweaker(String target, String tweaker, Side side) {
        System.setOut(new LoggerPrintStream(LogManager.getLogger("SYSOUT"), System.out));
        System.setErr(new LoggerPrintStream(LogManager.getLogger("SYSERR"), System.err));
        this.target = target;
        this.tweaker = tweaker;
        this.side = side;
    }

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        ((List<String>) Launch.blackboard.get("TweakClasses")).add("org.spongepowered.asm.launch.MixinTweaker");

        this.arguments = new Arguments(args);
        if (!this.arguments.has("version")) {
            this.arguments.add("version", profile != null ? profile : "Paper");
        }
        if (!this.arguments.has("gameDir") && gameDir != null) {
            this.arguments.add("gameDir", gameDir.getAbsolutePath());
        }
        if (this.side == Side.CLIENT) {
            if (!this.arguments.has("assetsDir") && assetsDir != null) {
                this.arguments.add("assetsDir", assetsDir.getAbsolutePath());
            }
        }

        MixinBootstrap.init();
        Mixins.addConfiguration("mixin.paper.json");
        MixinEnvironment.getDefaultEnvironment().setSide(this.side == Side.CLIENT ? MixinEnvironment.Side.CLIENT : MixinEnvironment.Side.SERVER);
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        classLoader.addClassLoaderExclusion("xyz.papermodloader.paper.launcher");
        classLoader.registerTransformer(this.tweaker);

        try {
            Class<?> paperTweaker = Class.forName("xyz.papermodloader.paper.event.PaperHooks", true, Launch.classLoader);
            Method loadMods = paperTweaker.getDeclaredMethod("loadMods", boolean.class);
            loadMods.invoke(null, this.tweaker.contains("Dev"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLaunchTarget() {
        return this.target;
    }

    @Override
    public String[] getLaunchArguments() {
        return this.arguments.build();
    }
}
