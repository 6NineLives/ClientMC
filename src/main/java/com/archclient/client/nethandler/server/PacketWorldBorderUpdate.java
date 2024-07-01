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
public class PacketWorldBorderUpdate extends Packet {

    private String id;
    private double minX;
    private double minZ;
    private double maxX;
    private double maxZ;
    private int durationTicks;

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeString(this.id);
        buf.buf().writeDouble(this.minX);
        buf.buf().writeDouble(this.minZ);
        buf.buf().writeDouble(this.maxX);
        buf.buf().writeDouble(this.maxZ);
        buf.buf().writeInt(this.durationTicks);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.id = buf.readString();
        this.minX = buf.buf().readDouble();
        this.minZ = buf.buf().readDouble();
        this.maxX = buf.buf().readDouble();
        this.maxZ = buf.buf().readDouble();
        this.durationTicks = buf.buf().readInt();
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleWorldBorderUpdate(this);
    }

}
