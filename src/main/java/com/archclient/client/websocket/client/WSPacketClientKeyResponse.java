package com.archclient.client.websocket.client;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.websocket.AssetsWebSocket;
import com.archclient.client.websocket.WSPacket;

public class WSPacketClientKeyResponse
        extends WSPacket {
    private byte[] data;

    @Override
    public void write(ByteBufWrapper buf) {
        this.writeKey(buf.buf(), this.data);
    }

    @Override
    public void read(ByteBufWrapper buf) {
        this.data = this.readKey(buf.buf());
    }

    @Override
    public void handle(AssetsWebSocket lIIlllIIlllIlIllIIlIIIIll2) {
    }

    public byte[] getData() {
        return this.data;
    }

    public WSPacketClientKeyResponse(byte[] data) {
        this.data = data;
    }

    public WSPacketClientKeyResponse() {
    }
}

