package com.archclient.client.event.type;

import com.archclient.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.multiplayer.WorldClient;

@Getter
@AllArgsConstructor
public class LoadWorldEvent extends EventBus.Event {
    private final WorldClient world;
}
