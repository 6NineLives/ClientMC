package com.archclient.client.websocket.client;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.websocket.AssetsWebSocket;
import com.archclient.client.websocket.WSPacket;

import java.io.IOException;

public class WSPacketClientFriendRemove extends WSPacket {
    private String playerId;

    public WSPacketClientFriendRemove() {
    }

    public WSPacketClientFriendRemove(String string) {
        this.playerId = string;
    }

    @Override
    public void write(ByteBufWrapper buf) {
        buf.writeString(this.playerId);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.playerId = buf.readStringFromBuffer(52);
    }

    @Override
    public void handle(AssetsWebSocket lIIlllIIlllIlIllIIlIIIIll2) {
        lIIlllIIlllIlIllIIlIIIIll2.handleFriendRemove(this);
    }

    public String getPlayerId() {
        return this.playerId;
    }
}
