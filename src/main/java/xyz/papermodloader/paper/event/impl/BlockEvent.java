package xyz.papermodloader.paper.event.impl;

import net.minecraft.block.state.BlockState;
import net.minecraft.util.Position;
import net.minecraft.world.World;
import xyz.papermodloader.paper.event.Event;

/**
 * Base class for all block-related events.
 */
public class BlockEvent extends Event {
    /**
     * The world this event was fired in.
     */
    private World world;

    /**
     * The block state.
     */
    private BlockState blockState;

    /**
     * The position of the block.
     */
    private Position position;

    public BlockEvent(World world, BlockState blockState, Position position) {
        this.world = world;
        this.blockState = blockState;
        this.position = position;
    }

    /**
     * @return the world this event was fired in.
     */
    public World getWorld() {
        return world;
    }

    /**
     * @return the block.
     */
    public BlockState getBlockState() {
        return blockState;
    }

    /**
     * @return the position of the block.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Event fired when a block gets destroyed. Called on both sides.
     */
    public static class Destroy extends BlockEvent {
        public Destroy(World world, BlockState blockState, Position position) {
            super(world, blockState, position);
        }

        @Override
        public boolean isCancellable() {
            return true;
        }
    }
}
