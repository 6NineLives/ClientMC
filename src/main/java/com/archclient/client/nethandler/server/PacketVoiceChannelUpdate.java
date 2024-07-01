package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import java.io.IOException;

@AllArgsConstructor @NoArgsConstructor @Getter @ToString
public class PacketVoiceChannelUpdate extends Packet {

    public int status;
    private EaglercraftUUID channelUuid;
    private EaglercraftUUID uuid;
    private String name;

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeVarInt(this.status);
        buf.writeUUID(this.channelUuid);
        buf.writeUUID(this.uuid);
        buf.writeString(this.name);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.status = buf.readVarInt();
        this.channelUuid = buf.readUUID();
        this.uuid = buf.readUUID();
        this.name = buf.readString();
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleVoiceChannelUpdate(this);
    }

}
