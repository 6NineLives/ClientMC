package com.archclient.client.event.type;

import com.archclient.client.event.EventBus;

public class RenderWorldEvent extends EventBus.Event {
    private final float partialTicks;

    public RenderWorldEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}
