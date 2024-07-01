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
import java.util.*;

@AllArgsConstructor @NoArgsConstructor @Getter
public class PacketUpdateNametags extends Packet {

    private Map<EaglercraftUUID, List<String>> playersMap;

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeVarInt(this.playersMap == null ? -1 : this.playersMap.size());
        if (this.playersMap != null) {
            for (Map.Entry<EaglercraftUUID, List<String>> entry : this.playersMap.entrySet()) {
                EaglercraftUUID uuid = entry.getKey();
                List<String> tags = entry.getValue();
                buf.writeUUID(uuid);
                buf.writeVarInt(tags.size());
                tags.forEach(buf::writeString);
            }
        }
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        int playersMapSize = buf.readVarInt();
        if (playersMapSize == -1) {
            this.playersMap = null;
        } else {
            this.playersMap = new HashMap<>();
            for (int i = 0; i < playersMapSize; ++i) {
                EaglercraftUUID uuid = buf.readUUID();
                int tagsSize = buf.readVarInt();
                ArrayList<String> tags = new ArrayList<String>();
                for (int j = 0; j < tagsSize; ++j) {
                    tags.add(buf.readString());
                }
                this.playersMap.put(uuid, tags);
            }
        }
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleNametagsUpdate(this);
    }

}
