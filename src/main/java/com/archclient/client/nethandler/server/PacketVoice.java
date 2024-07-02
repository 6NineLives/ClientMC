package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;
import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import java.io.IOException;

public class PacketVoice extends Packet {

    private EaglercraftUUID uuid;
    private byte[] data;

    public PacketVoice(EaglercraftUUID uuid, byte[] data) {
        this.uuid = uuid;
        this.data = data;
    }

    public PacketVoice() {
    }

    public EaglercraftUUID getUuid() {
        return this.uuid;
    }

    public byte[] getData() {
        return this.data;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeUUID(this.uuid);
        this.writeBlob(buf, this.data);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.uuid = buf.readUUID();
        this.data = this.readBlob(buf);
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleVoice(this);
    }

}
