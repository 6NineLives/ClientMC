package com.archclient.client.event.type;

import com.archclient.client.event.EventBus;

public class KeyboardEvent extends EventBus.Event {
    private final int keyboardKey;

    public KeyboardEvent(int keyboardKey) {
        this.keyboardKey = keyboardKey;
    }

    public int getKeyboardKey() {
        return this.keyboardKey;
    }
}
