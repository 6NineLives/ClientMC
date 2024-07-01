package com.archclient.client.websocket.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.websocket.AssetsWebSocket;
import com.archclient.client.websocket.WSPacket;

import java.io.IOException;

public class WSPacketBulkFriends
        extends WSPacket {
    private String rawString;

    public WSPacketBulkFriends() {
    }

    public WSPacketBulkFriends(String string) {
        this.rawString = string;
    }

    @Override
    public void write(ByteBufWrapper buf) {
        buf.writeString(this.rawString);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.rawString = buf.readStringFromBuffer(32767);
    }

    @Override
    public void handle(AssetsWebSocket websocket) {
        websocket.handleBulkFriends(this);
    }

    public String getBulkArray() {
        return this.rawString;
    }
}
