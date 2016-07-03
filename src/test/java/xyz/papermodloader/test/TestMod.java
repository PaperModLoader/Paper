package xyz.papermodloader.test;

import net.minecraft.block.DirtBlock;
import net.minecraft.client.gui.SelectLanguageGUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.papermodloader.paper.event.EventHandler;
import xyz.papermodloader.paper.mod.Mod;

public class TestMod implements Mod {
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        TestMod.LOGGER.info("TestMod#onInitialize()");

        EventHandler.GUI_OPEN.listen(event -> {
            System.out.println("Opening gui " + (event.getGUI() != null ? event.getGUI().getClass().getName() : null));
            event.setCancelled(event.getGUI() instanceof SelectLanguageGUI);
        });

        EventHandler.BLOCK_DESTROY.listen(event -> {
            System.out.println("Destroying block " + event.getBlockState().getBlock().c());
            event.setCancelled(event.getBlockState().getBlock() instanceof DirtBlock);
        });
    }

    @Override
    public void onPostInitialize() {

    }
}
