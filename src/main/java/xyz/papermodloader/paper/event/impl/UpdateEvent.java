package xyz.papermodloader.paper.event.impl;

import xyz.papermodloader.paper.event.Event;

/**
 * Base class for all update-related events.
 */
public class UpdateEvent extends Event {
    /**
     * Event fired before an entity ticks.
     */
    public static class Entity extends UpdateEvent {
        /**
         * The entity being ticked.
         */
        private net.minecraft.entity.Entity entity;

        public Entity(net.minecraft.entity.Entity entity) {
            this.entity = entity;
        }

        /**
         * @return the entity being ticked.
         */
        public net.minecraft.entity.Entity getEntity() {
            return this.entity;
        }
    }

    /**
     * Event fired before the world ticks.
     */
    public static class World extends UpdateEvent {
        /**
         * The ticking world.
         */
        private net.minecraft.world.World world;

        public World(net.minecraft.world.World world) {
            this.world = world;
        }

        /**
         * @return the ticking world.
         */
        public net.minecraft.world.World getWorld() {
            return this.world;
        }
    }
}
