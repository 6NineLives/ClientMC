package com.archclient.client.util.voicechat;

import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;

public class VoiceUser {
    private EaglercraftUUID uuid;
    private String username;

    public VoiceUser(EaglercraftUUID uuid, String string) {
        this.uuid = uuid;
        this.username = string;
    }

    public EaglercraftUUID getUUID() {
        return this.uuid;
    }

    public String getUsername() {
        return this.username;
    }
}
 