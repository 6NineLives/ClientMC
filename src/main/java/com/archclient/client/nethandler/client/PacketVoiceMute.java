package com.archclient.client.nethandler.client;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.server.IACNetHandlerServer;
import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import java.io.IOException;

public class PacketVoiceMute extends Packet {

    private EaglercraftUUID muting;

    public PacketVoiceMute(EaglercraftUUID muting) {
        this.muting = muting;
    }

    public PacketVoiceMute() {
    }

    public EaglercraftUUID getData() {
        return this.muting;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeUUID(this.muting);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.muting = buf.readUUID();
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerServer)handler).handleVoiceMute(this);
    }

}
