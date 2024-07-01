package com.archclient.client.event.type;

import com.archclient.client.event.EventBus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RenderWorldEvent extends EventBus.Event {
    private final float partialTicks;
}
