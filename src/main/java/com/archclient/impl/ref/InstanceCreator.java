package com.archclient.impl.ref;

import net.lax1dude.eaglercraft.v1_8.netty.ByteBuf;
import net.lax1dude.eaglercraft.v1_8.netty.Unpooled;
import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
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
}
