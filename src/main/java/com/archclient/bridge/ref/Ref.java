package com.archclient.bridge.ref;

import com.archclient.bridge.ext.GLBridge;
import com.archclient.bridge.ref.implementations.Implementations;
import com.archclient.impl.ref.DrawingUtils;
import com.archclient.impl.ref.InstanceCreator;
import com.archclient.main.identification.MinecraftVersion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;

public class Ref {
    // These fields can be set from the version specific mod and be used equally throughout all versions without having to change much.
    private static MinecraftVersion minecraftVersion = null;
    private static GLBridge glBridge = null;
    private static DrawingUtils drawingUtils = null;
    private static Minecraft minecraft = null;
    private static RenderManager renderManager = null;
    private static Tessellator tessellator = null;
    private static InstanceCreator instanceCreator;
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
}
