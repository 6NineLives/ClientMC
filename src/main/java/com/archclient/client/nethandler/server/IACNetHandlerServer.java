package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.client.PacketClientVoice;
import com.archclient.client.nethandler.client.PacketVoiceChannelSwitch;
import com.archclient.client.nethandler.client.PacketVoiceMute;

public interface IACNetHandlerServer extends IACNetHandler {

    void handleVoice(PacketClientVoice var1);

    void handleVoiceChannelSwitch(PacketVoiceChannelSwitch var1);

    void handleVoiceMute(PacketVoiceMute var1);

}
