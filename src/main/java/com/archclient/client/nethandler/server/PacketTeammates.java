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
public class PacketTeammates extends Packet {

    private EaglercraftUUID leader;
    private long lastMs;
    private Map<EaglercraftUUID, Map<String, Double>> players;

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.buf().writeBoolean(this.leader != null);
        if (this.leader != null) {
            buf.writeUUID(this.leader);
        }
        buf.buf().writeLong(this.lastMs);
        buf.writeVarInt(this.players.values().size());
        for (Map.Entry<EaglercraftUUID, Map<String, Double>> playerMap : this.players.entrySet()) {
            buf.writeUUID(playerMap.getKey());
            buf.writeVarInt(playerMap.getValue().values().size());
            for (Map.Entry<String, Double> posMap : playerMap.getValue().entrySet()) {
                buf.writeString(posMap.getKey());
                buf.buf().writeDouble(posMap.getValue());
            }
        }
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        boolean hasLeader = buf.buf().readBoolean();
        if (hasLeader) {
            this.leader = buf.readUUID();
        }
        this.lastMs = buf.buf().readLong();
        int playersSize = buf.readVarInt();
        this.players = new HashMap<>();
        for (int i = 0; i < playersSize; ++i) {
            EaglercraftUUID uuid = buf.readUUID();
            int posMapSize = buf.readVarInt();
            HashMap<String, Double> posMap = new HashMap<>();
            for (int j = 0; j < posMapSize; ++j) {
                String key = buf.readString();
                double val = buf.buf().readDouble();
                posMap.put(key, val);
            }
            this.players.put(uuid, posMap);
        }
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleTeammates(this);
    }

}
