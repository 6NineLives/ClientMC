package com.archclient.client.event.type;

import net.minecraft.client.gui.ScaledResolution;

public class RenderPreviewEvent extends GuiDrawEvent {
    public RenderPreviewEvent(ScaledResolution resolution) {
        super(resolution);
    }
}
