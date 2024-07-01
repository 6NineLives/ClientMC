package com.archclient.client.event.type;

import com.archclient.client.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PluginMessageEvent extends EventBus.Event {
    private final String channel;
    private final byte[] payload;
}
