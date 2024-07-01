package com.archclient.bridge.ref;

import com.archclient.bridge.ext.GLBridge;
import com.archclient.bridge.ref.implementations.Implementations;
import com.archclient.impl.ref.DrawingUtils;
import com.archclient.impl.ref.InstanceCreator;
import com.archclient.main.identification.MinecraftVersion;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;

public class Ref {
    // These fields can be set from the version specific mod and be used equally throughout all versions without having to change much.
    @Getter @Setter
    private static MinecraftVersion minecraftVersion = null;
    @Getter @Setter
    private static GLBridge glBridge = null;
    @Getter @Setter
    private static DrawingUtils drawingUtils = null;
    @Getter @Setter
    private static Minecraft minecraft = null;
    @Getter @Setter
    private static RenderManager renderManager = null;
    @Getter @Setter
    private static Tessellator tessellator = null;
    @Getter @Setter
    private static InstanceCreator instanceCreator;
    @Getter
    private static final Implementations implementations = new Implementations();

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
