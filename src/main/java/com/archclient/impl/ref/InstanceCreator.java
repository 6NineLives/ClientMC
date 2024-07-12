package com.archclient.impl.ref;

import net.lax1dude.eaglercraft.v1_8.netty.ByteBuf;
import net.lax1dude.eaglercraft.v1_8.netty.Unpooled;
import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.*;

public class InstanceCreator {
    public ResourceLocation createResourceLocation(String domain, String path) {
        return (ResourceLocation) new ResourceLocation(domain, path);
    }
    public ResourceLocation createResourceLocation(String path) {
        return this.createResourceLocation("minecraft", path);
    }

    public ScaledResolution createScaledResolution() {
        return (ScaledResolution) new ScaledResolution(Minecraft.getMinecraft());
    }

    public DynamicTexture createDynamicTexture(ImageData img) {
        return new DynamicTexture(img);
    }

    public DynamicTexture createDynamicTexture(int width, int height) {
        return new DynamicTexture(width, height);
    }

    public ISound createSoundFromPSR(ResourceLocation location, float pitch) {
        return (ISound) PositionedSoundRecord.create((ResourceLocation) location, pitch);
    }

    public KeyBinding createKeyBinding(String description, int keyCode, String category) {
        return (KeyBinding) new KeyBinding(description, keyCode, category);
    }

    public Vec3 createVec3(double x, double y, double z) {
        return (Vec3) new Vec3(x, y, z);
    }

    public ItemStack createItemStack(Item item) {
        return new ItemStack((Item) item);
    }

    public RenderItem createRenderItem() {
        return (RenderItem) Minecraft.getMinecraft().getRenderItem();
    }

    public ChatComponentText createChatComponentText(String initialString) {
        return (ChatComponentText) new ChatComponentText(initialString);
    }

    public AxisAlignedBB createAxisAlignedBB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return (AxisAlignedBB) AxisAlignedBB.fromBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public GuiTextField createTextField(int x, int y, int width, int height) {
        return new GuiTextField(0x530, Minecraft.getMinecraft().fontRendererObj, x, y, width, height);
    }

    public PacketBuffer createPacketBuffer(ByteBuf buffer) {
        return (PacketBuffer) new PacketBuffer(buffer);
    }

    public C17PacketCustomPayload createC17PacketCustomPayload(String channel, byte[] data) {
        return (C17PacketCustomPayload) new C17PacketCustomPayload(channel, new PacketBuffer(Unpooled.buffer(data, data.length)));
    }

    public C17PacketCustomPayload createC17PacketCustomPayload(String channel, PacketBuffer data) {
        return (C17PacketCustomPayload) new C17PacketCustomPayload(channel, (PacketBuffer) data);
    }

    public PotionEffect createPotionEffect(String idName, int duration, int multiplier) {
        int id = -1337;

        switch (idName) {
            case "FIRE_RESISTANCE":
                id = Potion.fireResistance.getId();
                break;
            case "MOVE_SPEED":
                id = Potion.moveSpeed.getId();
                break;
        }

        return (PotionEffect) new PotionEffect(id, duration, multiplier);
    }

    public Scoreboard createScoreboard() {
        return (Scoreboard) new Scoreboard();
    }

    public ScoreObjective createScoreObjective(Scoreboard scoreboard, String name, String type) {
        IScoreObjectiveCriteria criteria = type.equalsIgnoreCase("dummy") ? IScoreObjectiveCriteria.DUMMY : IScoreObjectiveCriteria.TRIGGER;
        return (ScoreObjective) new ScoreObjective((Scoreboard) scoreboard, name, criteria);
    }

    public ScoreObjective createScoreObjective(String name, String type) {
        IScoreObjectiveCriteria criteria = type.equalsIgnoreCase("dummy") ? IScoreObjectiveCriteria.DUMMY : IScoreObjectiveCriteria.TRIGGER;
        return (ScoreObjective) new ScoreObjective(new Scoreboard(), name, criteria);
    }
}
