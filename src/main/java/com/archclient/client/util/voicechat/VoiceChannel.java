package com.archclient.client.util.voicechat;

import com.archclient.bridge.util.EnumChatFormattingBridge;

import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

import java.util.ArrayList;
import java.util.List;

public class VoiceChannel {
    private final EaglercraftUUID uuid;
    public EaglercraftUUID getUuid() {
        return this.uuid;
    }
    private final String channelName;
    public String getChannelName() {
        return this.channelName;
    }
    private final List<VoiceUser> voiceUsers = new ArrayList<>();
    public List<VoiceUser> getVoiceUsers() {
        return this.voiceUsers;
    }
    private final List<EaglercraftUUID> listeningList = new ArrayList<>();

    public VoiceChannel(EaglercraftUUID uuid, String channelName) {
        this.uuid = uuid;
        this.channelName = channelName;
    }

    public VoiceUser getOrCreateVoiceUser(EaglercraftUUID uuid, String string) {
        VoiceUser voiceUser = null;
        if (!this.isInChannel(uuid)) {
            System.out.println("[AC Voice] Created the user client side (" + uuid.toString() + ", " + string + ").");
            voiceUser = new VoiceUser(uuid, EnumChatFormattingBridge.getTextWithoutFormattingCodes(string));
            this.listeningList.add(voiceUser.getUUID());
            this.voiceUsers.add(voiceUser);
        }
        return voiceUser;
    }

    public void removeUser(EaglercraftUUID uuid) {
        this.voiceUsers.removeIf(voiceUser -> voiceUser.getUUID().equals(uuid));
    }

    public void addToListening(EaglercraftUUID uuid) {
        if (this.isInChannel(uuid)) {
            this.listeningList.add(uuid);
        }
    }

    public void removeListener(EaglercraftUUID uuid) {
        this.listeningList.removeIf(voiceUser -> voiceUser.equals(uuid));
    }

    public boolean isListening(EaglercraftUUID uuid) {
        return this.listeningList.stream().anyMatch(voiceUser -> voiceUser.equals(uuid));
    }

    public boolean isInChannel(EaglercraftUUID uuid) {
        return this.voiceUsers.stream().anyMatch(voiceUser -> voiceUser.getUUID().equals(uuid));
    }
}