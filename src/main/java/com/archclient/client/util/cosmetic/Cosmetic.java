package com.archclient.client.util.cosmetic;

import com.archclient.bridge.ref.Ref;

import net.minecraft.util.ResourceLocation;

public class Cosmetic {
    private final float scale;
    private final String name;
    private final String type;
    private final ResourceLocation location;
    private final ResourceLocation previewLocation;
    private boolean equipped;
    private final String playerId;

    public Cosmetic(String playerId, String name, float scale, boolean equipped, String location) {
        this(playerId, name, "cape", scale, equipped, location);
    }

    public Cosmetic(String playerId, String name, String type, float scale, boolean equipped, String location) {
        this.playerId = playerId;
        this.name = name;
        this.type = type;
        this.scale = scale;
        this.equipped = equipped;
        this.location = Ref.getInstanceCreator().createResourceLocation(location);
        this.previewLocation = Ref.getInstanceCreator().createResourceLocation("preview/" + (location.equals("") ? "unknown.png" : location));
    }

    public String getName() {
        return this.name;
    }

    public ResourceLocation getLocation() {
        return this.location;
    }

    public float getScale() {
        return this.scale;
    }

    public String getPlayerId() {
        return this.playerId;
    }

    public ResourceLocation getPreviewLocation() {
        return this.previewLocation;
    }

    public boolean isEquipped() {
        return this.equipped;
    }

    public void setEquipped(boolean bl) {
        this.equipped = bl;
    }

    public String getType() {
        return this.type;
    }
}