package com.archclient.client.websocket.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.websocket.AssetsWebSocket;
import com.archclient.client.websocket.WSPacket;

public class WSPacketKeyRequest extends WSPacket {
    private byte[] publicKey;

    @Override
    public void write(ByteBufWrapper buf) {
        this.writeKey(buf.buf(), this.publicKey);
    }

    @Override
    public void read(ByteBufWrapper buf) {
        this.publicKey = this.readKey(buf.buf());
    }

    @Override
    public void handle(AssetsWebSocket lIIlllIIlllIlIllIIlIIIIll2) {
        lIIlllIIlllIlIllIIlIIIIll2.handleKeyRequest(this);
    }

    public WSPacketKeyRequest() {
    }

    public WSPacketKeyRequest(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getPublicKey() {
        return this.publicKey;
    }
}