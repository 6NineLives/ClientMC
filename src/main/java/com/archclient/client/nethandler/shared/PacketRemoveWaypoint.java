package com.archclient.client.nethandler.shared;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;

import java.io.IOException;

public class PacketRemoveWaypoint extends Packet {

    private String name;
    private String world;

    public PacketRemoveWaypoint(String name, String world) {
        this.name = name;
        this.world = world;
    }

    public PacketRemoveWaypoint() {
    }

    public String getName() {
        return this.name;
    }

    public String getWorld() {
        return this.world;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeString(this.name);
        buf.writeString(this.world);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.name = buf.readString();
        this.world = buf.readString();
    }

    @Override
    public void process(IACNetHandler handler) {
        handler.handleRemoveWaypoint(this);
    }

}
