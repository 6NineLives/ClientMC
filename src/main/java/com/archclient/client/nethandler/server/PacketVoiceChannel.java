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
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor @NoArgsConstructor @Getter
public class PacketVoiceChannel extends Packet {

    private EaglercraftUUID uuid;
    private String name;
    private Map<EaglercraftUUID, String> players;
    private Map<EaglercraftUUID, String> listening;

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeUUID(this.uuid);
        buf.writeString(this.name);
        this.writeMap(buf, this.players);
        this.writeMap(buf, this.listening);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.uuid = buf.readUUID();
        this.name = buf.readString();
        this.players = this.readMap(buf);
        this.listening = this.readMap(buf);
    }

    private void writeMap(ByteBufWrapper out, Map<EaglercraftUUID, String> players) {
        out.writeVarInt(players.size());
        players.forEach((key, value) -> {
            out.writeUUID(key);
            out.writeString(value);
        });
    }

    private Map<EaglercraftUUID, String> readMap(ByteBufWrapper in) {
        int size = in.readVarInt();
        HashMap<EaglercraftUUID, String> players = new HashMap<>();
        for (int i = 0; i < size; ++i) {
            EaglercraftUUID uuid = in.readUUID();
            String name = in.readString();
            players.put(uuid, name);
        }
        return players;
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleVoiceChannels(this);
    }

}
