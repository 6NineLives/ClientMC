package com.archclient.client.util.voicechat;

import com.archclient.bridge.ref.Ref;

import net.minecraft.client.Minecraft;

import com.archclient.client.nethandler.server.PacketVoice;

import java.util.*;

public class VoiceChatManager {

    private final Minecraft mc;
    private boolean volumeControlActive;

    private float WEATHER;
    private float RECORDS;
    private float BLOCKS;
    private float MOBS;
    private float ANIMALS;

    private static boolean existent = false;

    public VoiceChatManager() {
        this.mc = Ref.getMinecraft();
    }

    public void setTalking(boolean talking) {
    }

    public void alertEnd(final UUID uniqueId) {
    }

    public void handleIncoming(PacketVoice packetVoice) {
    }

    public String generateSource(UUID id) {
        return "" + id.hashCode();
    }

    public void giveEnd(UUID id) {
    }

    public void volumeControlStart() {
    }

    public void volumeControlStop() {
    }

    public void handlePosition(UUID uniqueID, double var3, double var5, double var7) {
    }

    public static boolean isExistent() {
        return existent;
    }
}
