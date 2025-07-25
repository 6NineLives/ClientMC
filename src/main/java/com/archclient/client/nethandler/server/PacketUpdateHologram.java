package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;
import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketUpdateHologram extends Packet {

    private EaglercraftUUID uuid;
    private List<String> lines;

    public PacketUpdateHologram(EaglercraftUUID uuid, List<String> lines) {
        this.uuid = uuid;
        this.lines = lines;
    }

    public PacketUpdateHologram() {
    }

    public EaglercraftUUID getUuid() {
        return this.uuid;
    }

    public List<String> getLines() {
        return this.lines;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeUUID(this.uuid);
        buf.writeVarInt(this.lines.size());
        this.lines.forEach(buf::writeString);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.uuid = buf.readUUID();
        int linesSize = buf.readVarInt();
        this.lines = new ArrayList<>();
        for (int i = 0; i < linesSize; ++i) {
            this.lines.add(buf.readString());
        }
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleUpdateHologram(this);
    }

}
