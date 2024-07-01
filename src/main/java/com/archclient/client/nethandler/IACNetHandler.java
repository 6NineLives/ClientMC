package com.archclient.client.nethandler;

import com.archclient.client.nethandler.shared.PacketAddWaypoint;
import com.archclient.client.nethandler.shared.PacketRemoveWaypoint;

public interface IACNetHandler {

    void handleAddWaypoint(PacketAddWaypoint var1);

    void handleRemoveWaypoint(PacketRemoveWaypoint var1);

//    void voiceChannelSend(VoiceChannelSentPacket var1);
//
//    void voiceChannelAdd(VoiceChannelAddedPacket var1);
//
//    void voiceChannelReceived(VoiceChannelReceivedPacket var1);

}
