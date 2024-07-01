package com.archclient.client.event.type;

import com.archclient.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.AxisAlignedBB;

import java.util.List;

@Getter
@AllArgsConstructor
public class CollisionEvent extends EventBus.Event {
    private final List<AxisAlignedBB> boundingBoxes;
    private final int x, y, z;
}
