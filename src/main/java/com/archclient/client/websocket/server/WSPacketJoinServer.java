package com.archclient.client.websocket.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.websocket.AssetsWebSocket;
import com.archclient.client.websocket.WSPacket;

public class WSPacketJoinServer
        extends WSPacket {
    private byte[] publicKey;
    private byte[] encryptedPublicKey;

    @Override
    public void write(ByteBufWrapper buf) {
    }

    @Override
    public void read(ByteBufWrapper buf) {
        this.encryptedPublicKey = this.readKey(buf.buf());
        this.publicKey = new byte[1024];
    }

    @Override
    public void handle(AssetsWebSocket websocket) {
        websocket.handleJoinServer(this);
    }

    public byte[] getPublicKey() {
        return this.publicKey;
    }

    public byte[] getEncryptedPublicKey() {
        return this.encryptedPublicKey;
    }
}

