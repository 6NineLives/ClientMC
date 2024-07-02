package com.archclient.client.event.type;

import com.archclient.client.event.EventBus;

public class ClickEvent extends EventBus.Event {
    private final int mouseButton;

    public ClickEvent(int mouseButton) {
        this.mouseButton = mouseButton;
    }

    public int getMouseButton() {
        return this.mouseButton;
    }
}
