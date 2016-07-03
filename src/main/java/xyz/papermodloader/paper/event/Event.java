package xyz.papermodloader.paper.event;

import xyz.papermodloader.paper.Paper;

/**
 * Base class for all events.
 */
public class Event {
    private boolean cancelled;

    /**
     * @return if this event is cancellable via {@link Event#setCancelled(boolean)}.
     */
    public boolean isCancellable() {
        return false;
    }

    /**
     * @return if this event has been cancelled via {@link Event#setCancelled(boolean)}.
     */
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Sets this event's 'cancelled' value. If this event is not cancellable, an error will be logged.
     *
     * @param cancelled the new cancelled value for this event.
     */
    public void setCancelled(boolean cancelled) {
        if (!this.isCancellable()) {
            Paper.LOGGER.error("Tried to cancel " + this.getClass().getName() + " but it is not cancellable");
            return;
        }
        this.cancelled = cancelled;
    }
}
