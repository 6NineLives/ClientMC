package com.archclient.client.nethandler.client;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.server.IACNetHandlerServer;

import java.io.IOException;

public class PacketClientVoice extends Packet {

    private byte[] data;

    public PacketClientVoice(byte[] data) {
        this.data = data;
    }

    public PacketClientVoice() {
    }

    public byte[] getData() {
        return this.data;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        this.writeBlob(buf, this.data);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.data = this.readBlob(buf);
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerServer)handler).handleVoice(this);
    }

}
