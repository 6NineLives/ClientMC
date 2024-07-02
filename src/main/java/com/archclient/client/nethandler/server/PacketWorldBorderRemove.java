package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;

import java.io.IOException;

public class PacketWorldBorderRemove extends Packet {

    private String id;

    public PacketWorldBorderRemove(String id) {
        this.id = id;
    }

    public PacketWorldBorderRemove() {
    }

    public String getId() {
        return this.id;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeString(this.id);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.id = buf.readString();
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleWorldBorderRemove(this);
    }

}
