package xyz.papermodloader.paper.event.impl;

import net.minecraft.client.gui.ScreenGUI;
import xyz.papermodloader.paper.event.Event;

/**
 * Base class for all GUI-related events.
 */
public class GUIEvent extends Event {
    /**
     * The GUI this event occurred on.
     */
    private ScreenGUI gui;

    public GUIEvent(ScreenGUI gui) {
        this.gui = gui;
    }

    /**
     * @return the GUI this event occurred on.
     */
    public ScreenGUI getGUI() {
        return gui;
    }

    /**
     * Event fired when a GUI is first opened.
     */
    public static class Open extends GUIEvent {
        public Open(ScreenGUI gui) {
            super(gui);
        }

        @Override
        public boolean isCancellable() {
            return true;
        }
    }
}
