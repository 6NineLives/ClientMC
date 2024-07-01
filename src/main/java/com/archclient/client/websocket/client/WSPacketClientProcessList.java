package com.archclient.client.websocket.client;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.websocket.AssetsWebSocket;
import com.archclient.client.websocket.WSPacket;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WSPacketClientProcessList
        extends WSPacket {
    private List<String> processList;

    @Override
    public void write(ByteBufWrapper buf) {
        buf.buf().writeInt(this.processList.size());
        for (String string : this.processList) {
            buf.writeString(string);
        }
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        int listSize = buf.buf().readInt();
        this.processList = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            this.processList.add(buf.readStringFromBuffer(512));
        }
    }

    @Override
    public void handle(AssetsWebSocket lIIlllIIlllIlIllIIlIIIIll2) {
    }

    public WSPacketClientProcessList() {
    }

    @ConstructorProperties(value={"processes"})
    public WSPacketClientProcessList(List<String> list) {
        this.processList = list;
    }

    public List<String> getProcessList() {
        return this.processList;
    }
}

