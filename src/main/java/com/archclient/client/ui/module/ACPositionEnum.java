package com.archclient.client.ui.module;

public enum ACPositionEnum {
    BOTTOM("BOTTOM"),
    TOP("TOP"),
    CENTER("CENTER"),
    LEFT("LEFT"),
    RIGHT("RIGHT");

    private final String identifier;

    private ACPositionEnum(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }
}
