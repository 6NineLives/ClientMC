package com.archclient.client.event.type;

import com.archclient.client.event.EventBus;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

public class CollisionEvent extends EventBus.Event {
    private final List<AxisAlignedBB> boundingBoxes;
    private final int x, y, z;

    public CollisionEvent(List<AxisAlignedBB> boundingBoxes, int x, int y, int z) {
        this.boundingBoxes = boundingBoxes;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public List<AxisAlignedBB> getBoundingBoxes() {
        return this.boundingBoxes;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }
}
