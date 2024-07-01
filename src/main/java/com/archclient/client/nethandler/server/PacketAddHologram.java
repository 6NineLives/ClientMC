package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Getter
public class PacketAddHologram extends Packet {

    private EaglercraftUUID uuid;
    private double x;
    private double y;
    private double z;
    private List<String> lines;

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeUUID(this.uuid);
        buf.buf().writeDouble(this.x);
        buf.buf().writeDouble(this.y);
        buf.buf().writeDouble(this.z);
        buf.writeVarInt(this.lines.size());
        this.lines.forEach(buf::writeString);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.uuid = buf.readUUID();
        this.x = buf.buf().readDouble();
        this.y = buf.buf().readDouble();
        this.z = buf.buf().readDouble();
        int linesSize = buf.readVarInt();
        this.lines = new ArrayList<>();
        for (int i = 0; i < linesSize; ++i) {
            this.lines.add(buf.readString());
        }
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleAddHologram(this);
    }

}
