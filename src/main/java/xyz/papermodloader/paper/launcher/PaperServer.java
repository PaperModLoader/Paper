package xyz.papermodloader.paper.launcher;

import net.minecraft.launchwrapper.Launch;

public class PaperServer {
    public static void main(String[] args) {
        LaunchArguments arguments = new LaunchArguments(args);

        arguments.addArgument("version", "1.10.2");
        arguments.addArgument("tweakClass", "xyz.papermodloader.paper.launcher.PaperServerTweaker");

        Launch.main(arguments.getArguments());
    }
}
