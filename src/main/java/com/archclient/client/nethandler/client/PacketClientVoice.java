package com.archclient.client.nethandler.client;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.server.IACNetHandlerServer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

@AllArgsConstructor @NoArgsConstructor @Getter
public class PacketClientVoice extends Packet {

    private byte[] data;

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
