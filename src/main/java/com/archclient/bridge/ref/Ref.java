package com.archclient.bridge.ref;

import com.archclient.bridge.client.resources.I18nBridge;
import com.archclient.bridge.ext.GLBridge;
import com.archclient.bridge.ref.implementations.Implementations;
import com.archclient.impl.ref.DrawingUtils;
import com.archclient.impl.ref.InstanceCreator;
import com.archclient.impl.ref.RefUtils;
import com.archclient.main.identification.MinecraftVersion;

import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.BossStatus;

public class Ref {
    // These fields can be set from the version specific mod and be used equally throughout all versions without having to change much.
    private static MinecraftVersion minecraftVersion = null;
    private static GLBridge glBridge = null;
    private static DrawingUtils drawingUtils = null;
    private static Minecraft minecraft = null;
    private static I18nBridge i18n = null;
    private static Iterable<Block> blockRegistry = null;
    private static BossStatus bossStatus = null;
    private static RenderHelper renderHelper = null;
    private static RenderManager renderManager = null;
    private static Tessellator tessellator = null;
    private static InstanceCreator instanceCreator;
    private static RefUtils utils;
    private static final Implementations implementations = new Implementations();

    public static MinecraftVersion getMinecraftVersion() {
        return minecraftVersion;
    }
    public static void setMinecraftVersion(MinecraftVersion minecraftVersion) {
        Ref.minecraftVersion = minecraftVersion;
    }
    public static GLBridge getGlBridge() {
        return glBridge;
    }
    public static void setGlBridge(GLBridge glBridge) {
        Ref.glBridge = glBridge;
    }
    public static DrawingUtils getDrawingUtils() {
        return drawingUtils;
    }
    public static void setDrawingUtils(DrawingUtils drawingUtils) {
        Ref.drawingUtils = drawingUtils;
    }
    public static Minecraft getMinecraft() {
        return minecraft;
    }
    public static void setMinecraft(Minecraft minecraft) {
        Ref.minecraft = minecraft;
    }
    public static I18nBridge getI18n() {
        return i18n;
    }
    public static void setI18n(I18nBridge i18n) {
        Ref.i18n = i18n;
    }
    public static Iterable<Block> getBlockRegistry() {
        return blockRegistry;
    }
    public static void setBlockRegistry(Iterable<Block> blockRegistry) {
        Ref.blockRegistry = blockRegistry;
    }
    public static BossStatus getBossStatus() {
        return bossStatus;
    }
    public static void setBossStatus(BossStatus bossStatus) {
        Ref.bossStatus = bossStatus;
    }
    public static RenderHelper getRenderHelper() {
        return renderHelper;
    }
    public static void setRenderHelper(RenderHelper renderHelper) {
        Ref.renderHelper = renderHelper;
    }
    public static RenderManager getRenderManager() {
        return renderManager;
    }
    public static void setRenderManager(RenderManager renderManager) {
        Ref.renderManager = renderManager;
    }
    public static Tessellator getTessellator() {
        return tessellator;
    }
    public static void setTessellator(Tessellator tessellator) {
        Ref.tessellator = tessellator;
    }
    public static InstanceCreator getInstanceCreator() {
        return instanceCreator;
    }
    public static void setUtils(RefUtils utils) {
        Ref.utils = utils;
    }
    public static RefUtils getUtils() {
        return utils;
    }
    public static void setInstanceCreator(InstanceCreator instanceCreator) {
        Ref.instanceCreator = instanceCreator;
    }
    public static Implementations getImplementations() {
        return implementations;
    }

    public static void modified$drawRect(float left, float top, float right, float bottom, int color) {
        getDrawingUtils().drawRect(left, top, right, bottom, color);
    }

    public static void modified$drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        getDrawingUtils().drawGradientRect(left, top, right, bottom, startColor, endColor);
    }

    public static void modified$drawBoxWithOutLine(float f, float f2, float f3, float f4, float f5, int n, int n2) {
        modified$drawRect(f, f2, f3, f4, n2);
        modified$drawRect(f - f5, f2 - f5, f, f4 + f5, n);
        modified$drawRect(f3, f2 - f5, f3 + f5, f4 + f5, n);
        modified$drawRect(f, f2 - f5, f3, f2, n);
        modified$drawRect(f, f4, f3, f4 + f5, n);
    }

    public static void modified$drawRectWithOutline(float f, float f2, float f3, float f4, float f5, int n, int n2) {
        modified$drawRect(f + f5, f2 + f5, f3 - f5, f4 - f5, n2);
        modified$drawRect(f, f2 + f5, f + f5, f4 - f5, n);
        modified$drawRect(f3 - f5, f2 + f5, f3, f4 - f5, n);
        modified$drawRect(f, f2, f3, f2 + f5, n);
        modified$drawRect(f, f4 - f5, f3, f4, n);
    }

    public static void bridge$scaledTessellator(int x, int y, int u, int v, int width, int height) {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Ref.getTessellator();
        WorldRenderer worldrenderer = var9.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0D).tex((float)(u) * var7, (float)(v + height) * var8).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0D).tex((float)(u + width) * var7, (float)(v + height) * var8).endVertex();
        worldrenderer.pos(x + width, y, 0.0D).tex((float)(u + width) * var7, (float)(v) * var8).endVertex();
        worldrenderer.pos(x, y, 0.0D).tex((float)(u) * var7, (float)(v) * var8).endVertex();
        var9.draw();
    }
}
