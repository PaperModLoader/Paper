package xyz.papermodloader.paper.launcher;

public class PaperServerTweaker extends PaperTweaker {
    @Override
    public String getLaunchTarget() {
        return "net.minecraft.server.MinecraftServer";
    }
}
