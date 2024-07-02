package com.archclient.client.event.type;

import com.archclient.client.event.EventBus;

public class PluginMessageEvent extends EventBus.Event {
    private final String channel;
    private final byte[] payload;

    public PluginMessageEvent(String channel, byte[] payload) {
        this.channel = channel;
        this.payload = payload;
    }

    public String getChannel() {
        return this.channel;
    }

    public byte[] getPayload() {
        return this.payload;
    }
}
