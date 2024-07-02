package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;
import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketOverrideNametags extends Packet {

    private EaglercraftUUID player;
    private List<String> tags;

    public PacketOverrideNametags(EaglercraftUUID player, List<String> tags) {
        this.player = player;
        this.tags = tags;
    }

    public PacketOverrideNametags() {
    }

    public EaglercraftUUID getPlayer() {
        return this.player;
    }

    public List<String> getTags() {
        return this.tags;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeUUID(this.player);
        buf.writeOptional(this.tags, t -> {
            buf.writeVarInt(t.size());
            t.forEach(buf::writeString);
        });
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.player = buf.readUUID();
        this.tags = buf.readOptional(() -> {
            int tagsSize = buf.readVarInt();
            ArrayList<String> tags = new ArrayList<>();
            for (int i = 0; i < tagsSize; ++i) {
                tags.add(buf.readString());
            }
            return tags;
        });
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleOverrideNametags(this);
    }

}
