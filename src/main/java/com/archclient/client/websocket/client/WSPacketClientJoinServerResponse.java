package com.archclient.client.websocket.client;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.websocket.AssetsWebSocket;
import com.archclient.client.websocket.WSPacket;

public class WSPacketClientJoinServerResponse
        extends WSPacket {
    private byte[] lIIIIlIIllIIlIIlIIIlIIllI;
    private byte[] IlllIIIlIlllIllIlIIlllIlI;

    public WSPacketClientJoinServerResponse(byte[] secretKey, byte[] publicKey, byte[] arrby) {
        this.lIIIIlIIllIIlIIlIIIlIIllI = secretKey;//xxxCryptManagerBridge.encryptData(publicKey, secretKey.getEncoded());
        this.IlllIIIlIlllIllIlIIlllIlI = publicKey;//xxxCryptManagerBridge.decryptData(publicKey, arrby);
    }

    @Override
    public void write(ByteBufWrapper bufWrapper) {
        this.writeKey(bufWrapper.buf(), this.lIIIIlIIllIIlIIlIIIlIIllI);
        this.writeKey(bufWrapper.buf(), this.IlllIIIlIlllIllIlIIlllIlI);
    }

    @Override
    public void read(ByteBufWrapper bufWrapper) {
    }

    @Override
    public void handle(AssetsWebSocket lIIlllIIlllIlIllIIlIIIIll2) {
    }
}

