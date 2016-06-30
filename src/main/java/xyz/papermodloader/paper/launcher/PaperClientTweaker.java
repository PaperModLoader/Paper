package xyz.papermodloader.paper.launcher;

public class PaperClientTweaker extends PaperTweaker {
    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }
}
