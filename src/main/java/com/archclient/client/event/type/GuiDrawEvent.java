package com.archclient.client.event.type;

import com.archclient.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

@Getter
@AllArgsConstructor
public class GuiDrawEvent extends EventBus.Event {
    private final ScaledResolution resolution;
}
