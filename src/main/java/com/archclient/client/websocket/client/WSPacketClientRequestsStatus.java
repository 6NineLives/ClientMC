package com.archclient.client.websocket.client;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.websocket.AssetsWebSocket;
import com.archclient.client.websocket.WSPacket;

public class WSPacketClientRequestsStatus
        extends WSPacket {
    private boolean accepting;

    public WSPacketClientRequestsStatus(boolean bl) {
        this.accepting = bl;
    }

    public WSPacketClientRequestsStatus() {
    }

    @Override
    public void write(ByteBufWrapper lIlIllllllllIlIIIllIIllII2) {
        lIlIllllllllIlIIIllIIllII2.buf().writeBoolean(this.accepting);
    }

    @Override
    public void read(ByteBufWrapper lIlIllllllllIlIIIllIIllII2) {
        this.accepting = lIlIllllllllIlIIIllIIllII2.buf().readBoolean();
    }

    @Override
    public void handle(AssetsWebSocket lIIlllIIlllIlIllIIlIIIIll2) {
    }

    public boolean isAccepting() {
        return this.accepting;
    }
}

