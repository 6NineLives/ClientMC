package com.archclient.main.identification;

public enum MinecraftVersion {
    v1_8_9("1.8.9");

    private final String asString;

    MinecraftVersion(String asString) {
        this.asString = asString;
    }

    public String toString() {
        return this.asString;
    }

    public boolean isLatestVersion() {
        return this.asString.split("-")[0].equals(MinecraftVersion.values()[MinecraftVersion.values().length - 1].asString.split("-")[0]);
    }

    public boolean isNewerThan(MinecraftVersion version) {
        return this.ordinal() > version.ordinal();
    }
}
