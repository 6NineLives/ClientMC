package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

@AllArgsConstructor @NoArgsConstructor @Getter
public class PacketServerUpdate extends Packet {

    private String server;

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeString(this.server);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.server = buf.readString();
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleServerUpdate(this);
    }

}
