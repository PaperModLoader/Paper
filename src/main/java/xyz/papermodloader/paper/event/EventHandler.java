package xyz.papermodloader.paper.event;

import xyz.papermodloader.paper.event.impl.BlockEvent;
import xyz.papermodloader.paper.event.impl.GUIEvent;
import xyz.papermodloader.paper.event.impl.UpdateEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class that holds events. Every event inside is also an instance of this class. Every instance has their own listeners.
 *
 * @param <T> the {@link Event} type.
 */
public class EventHandler<T extends Event> {
    //GUI events
    public static final EventHandler<GUIEvent.Open> GUI_OPEN = new EventHandler<>();

    //Update events
    public static final EventHandler<UpdateEvent.Entity> UPDATE_ENTITY = new EventHandler<>();
    public static final EventHandler<UpdateEvent.World> UPDATE_WORLD = new EventHandler<>();

    //Block events
    public static final EventHandler<BlockEvent.Destroy> BLOCK_DESTROY = new EventHandler<>();

    private List<Consumer<T>> listeners = new ArrayList<>();

    public boolean post(T event) {
        for (Consumer<T> listener : this.listeners) {
            listener.accept(event);
            if (event.isCancelled()) {
                return false;
            }
        }
        return true;
    }

    public void listen(Consumer<T> listener) {
        this.listeners.add(listener);
    }
}
