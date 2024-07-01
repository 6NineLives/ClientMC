package com.archclient.client.websocket.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.websocket.AssetsWebSocket;
import com.archclient.client.websocket.WSPacket;

public class WSPacketForceCrash
        extends WSPacket {
    @Override
    public void write(ByteBufWrapper lIlIllllllllIlIIIllIIllII2) {
    }

    @Override
    public void read(ByteBufWrapper lIlIllllllllIlIIIllIIllII2) {
    }

    @Override
    public void handle(AssetsWebSocket lIIlllIIlllIlIllIIlIIIIll2) {
        lIIlllIIlllIlIllIIlIIIIll2.handleForceCrash(this);
    }
}