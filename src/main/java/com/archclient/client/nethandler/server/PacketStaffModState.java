package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;

import java.io.IOException;

public class PacketStaffModState extends Packet {

    private String mod;
    private boolean state;

    public PacketStaffModState(String mod, boolean state) {
        this.mod = mod;
        this.state = state;
    }

    public PacketStaffModState() {
    }

    public String getMod() {
        return this.mod;
    }

    public boolean isState() {
        return this.state;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeString(this.mod);
        buf.buf().writeBoolean(this.state);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.mod = buf.readString();
        this.state = buf.buf().readBoolean();
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleStaffModState(this);
    }

}
