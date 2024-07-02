package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;

import java.io.IOException;

public class PacketUpdateWorld extends Packet {

    private String world;

    public PacketUpdateWorld(String world) {
        this.world = world;
    }

    public PacketUpdateWorld() {
    }

    public String getWorld() {
        return this.world;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeString(this.world);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.world = buf.readString();
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleUpdateWorld(this);
    }

}
