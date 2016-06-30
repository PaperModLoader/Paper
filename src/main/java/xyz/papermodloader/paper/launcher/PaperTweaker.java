package xyz.papermodloader.paper.launcher;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PaperTweaker implements ITweaker {
    private Map<String, String> args = new HashMap<>();

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        if (!this.args.containsKey("--version")) {
            this.args.put("--version", profile != null ? profile : "Paper");
        }
        if (!this.args.containsKey("--gameDir") && gameDir != null) {
            this.args.put("--gameDir", gameDir.getAbsolutePath());
        }
        if (!this.args.containsKey("--assetsDir") && assetsDir != null) {
            this.args.put("--assetsDir", assetsDir.getAbsolutePath());
        }
        if (!this.args.containsKey("--accessToken")) {
            this.args.put("--accessToken", "Paper");
        }
        for (int i = 0; i < args.size(); i++) {
            String arg = args.get(i);
            if (arg.startsWith("--")) {
                this.args.put(arg, args.get(i + 1));
            }
        }
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        classLoader.addTransformerExclusion("xyz.papermodloader.paper.launcher");
    }

    @Override
    public String[] getLaunchArguments() {
        List<String> launchArgs = new ArrayList<>();
        for (Map.Entry<String, String> arg : this.args.entrySet()) {
            launchArgs.add(arg.getKey());
            launchArgs.add(arg.getValue());
        }
        return launchArgs.toArray(new String[launchArgs.size()]);
    }
}
