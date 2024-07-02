package com.archclient.client.config.types;

public class PinnedServer {
    public final String displayName;
    public final String address;

    public PinnedServer(String displayName, String address) {
        this.displayName = displayName;
        this.address = address;
    }
}
