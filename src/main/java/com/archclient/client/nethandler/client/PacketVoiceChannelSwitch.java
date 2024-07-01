package com.archclient.client.nethandler.client;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.server.IACNetHandlerServer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import java.io.IOException;

@AllArgsConstructor @NoArgsConstructor @Getter
public class PacketVoiceChannelSwitch extends Packet {

    private EaglercraftUUID switchingTo;

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeUUID(this.switchingTo);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.switchingTo = buf.readUUID();
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerServer)handler).handleVoiceChannelSwitch(this);
    }

}
