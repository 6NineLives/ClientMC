package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;
import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import java.io.IOException;

public class PacketRemoveHologram extends Packet {

    private EaglercraftUUID uuid;

    public PacketRemoveHologram(EaglercraftUUID uuid) {
        this.uuid = uuid;
    }

    public PacketRemoveHologram() {
    }

    public EaglercraftUUID getUuid() {
        return this.uuid;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeUUID(this.uuid);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.uuid = buf.readUUID();
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleRemoveHologram(this);
    }

}
