package com.archclient.client.ui.util;

import com.archclient.bridge.ext.GLColor;
import com.archclient.bridge.ref.Ref;

import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderUtil {
    protected static float zLevel = 0.0f;

    public static void drawTexturedModalRect(float left, float top, float right, float bottom, int u, int v) {
        float multiplier = 0.00390625f;
        Tessellator tessellator = Ref.getTessellator();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(left, top + v, zLevel).tex(right * multiplier, (bottom + v) * multiplier).endVertex();
        worldrenderer.pos(left + u, top + v, zLevel).tex((right + u) * multiplier, (bottom + v) * multiplier).endVertex();
        worldrenderer.pos(left + u, top, zLevel).tex((right + u) * multiplier, bottom * multiplier).endVertex();
        worldrenderer.pos(left, top, zLevel).tex(right * multiplier, bottom * multiplier).endVertex();
        tessellator.draw();
    }

    public static void drawIcon(ResourceLocation location, float size, float x, float y) {
        float imageSize = size * 2.0f;
        renderIcon(location, x, y, imageSize, imageSize);
    }

    public static void drawIcon(String location, float size, float x, float y) {
        drawIcon(Ref.getInstanceCreator().createResourceLocation(location), size, x, y);
    }

    public static void renderIcon(ResourceLocation location, float x, float y, float width, float height) {
        Ref.getGlBridge().bridge$enableBlend();
        Ref.getMinecraft().getTextureManager().bindTexture(location);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(Tessellator.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y, 0.0F).tex(0f, 0f).endVertex();
        worldrenderer.pos(x, y + height, 0.0F).tex(0f, 1f).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0F).tex(1f, 1f).endVertex();
        worldrenderer.pos(x + width, y, 0.0F).tex(1f, 0f).endVertex();
        tessellator.draw();
        Ref.getGlBridge().bridge$disableBlend();
    }

    public static void scissorArea(int left, int top, int right, int bottom, float scale, int scaledHeight) {
        int width = right - left;
        int height = bottom - top;
        int invertedBottomPos = scaledHeight - bottom;
        Ref.getGlBridge().bridge$scissor((int)((float)left * scale), (int)((float)invertedBottomPos * scale), (int)((float)width * scale), (int)((float)height * scale));
    }

    public static void drawRoundedRect(double left, double top, double right, double bottom, double rounding, int color) {
        int i;
        float a = (color >> 24 & 0xFF) / 255f;
        float r = (color >> 16 & 0xFF) / 255f;
        float g = (color >> 8 & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        Ref.getGlBridge().bridge$pushAttrib(0);
        Ref.getGlBridge().bridge$scale(.5f, .5f, .5f);

        left *= 2;
        top *= 2;
        right *= 2;
        bottom *= 2;

        Ref.getGlBridge().bridge$enableBlend();
        Ref.getGlBridge().bridge$disableTexture2D();
        Ref.getGlBridge().bridge$color(r, g, b, a);
        Ref.getGlBridge().bridge$enableLineSmooth();
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(Tessellator.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        for (i = 0; i <= 90; i += 3) {
            worldrenderer.pos(left + rounding + Math.sin((double)i * (Math.PI) / 180) * (rounding * -1), top + rounding + Math.cos((double)i * (Math.PI) / 180) * (rounding * -1), 0.0f).color(r, g, b, a).endVertex();
        }
        for (i = 90; i <= 180; i += 3) {
            worldrenderer.pos(left + rounding + Math.sin((double)i * (Math.PI) / 180) * (rounding * -1), bottom - rounding + Math.cos((double)i * (Math.PI) / 180) * (rounding * -1), 0.0f).color(r, g, b, a).endVertex();
        }
        for (i = 0; i <= 90; i += 3) {
            worldrenderer.pos(right - rounding + Math.sin((double)i * (Math.PI) / 180) * rounding, bottom - rounding + Math.cos((double)i * (Math.PI) / 180) * rounding, 0.0f).color(r, g, b, a).endVertex();
        }
        for (i = 90; i <= 180; i += 3) {
            worldrenderer.pos(right - rounding + Math.sin((double)i * (Math.PI) / 180) * rounding, top + rounding + Math.cos((double)i * (Math.PI) / 180) * rounding, 0.0f).color(r, g, b, a).endVertex();
        }
        tessellator.draw();
        Ref.getGlBridge().bridge$enableTexture2D();
        Ref.getGlBridge().bridge$disableBlend();
        Ref.getGlBridge().bridge$disableLineSmooth();
        Ref.getGlBridge().bridge$disableBlend();
        Ref.getGlBridge().bridge$enableTexture2D();
        Ref.getGlBridge().bridge$scale(2, 2, 2);
        Ref.getGlBridge().bridge$popAttrib();
    }

    public static void renderCircle(double x, double y, double radius) {
        Ref.getGlBridge().bridge$enableBlend();
        Ref.getGlBridge().bridge$disableTexture2D();
        Ref.getGlBridge().bridge$blendFunc(770, 771);
        Tessellator tessellator = Ref.getTessellator();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(6 /* GL_TRIANGLE_FAN */, "position");
        worldrenderer.pos(x, y, 1.0D).endVertex();

        int numSides = 30;

        double doublePi = Math.PI * 2D;
        double vertexGap = doublePi / numSides;

        for (double angle = -vertexGap; angle < doublePi; angle += vertexGap) {
            worldrenderer.pos(x + radius * Math.cos(-angle), y + radius * Math.sin(-angle), 10.0D)
                    .endVertex();
        }

        tessellator.draw();
        Ref.getGlBridge().bridge$enableTexture2D();
        Ref.getGlBridge().bridge$disableBlend();
    }

    public static void renderHollowCircle(double d, double d2, double d3, double d4, double d5, int n, double d6) {
        Ref.getGlBridge().bridge$enableBlend();
        Ref.getGlBridge().bridge$disableTexture2D();
        Ref.getGlBridge().bridge$enableLineSmooth();
        d5 = (d5 + n) % n;
        Tessellator tessellator = Ref.getTessellator();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        for (double d7 = 360d / n * d5; d7 < 360d / n * (d5 + d6); d7 += 1.0) {
            double d8 = d7 * Math.PI / 180d;
            double d9 = (d7 - 1.0) * Math.PI / 180d;

            double[] arrd = new double[] {
                    Math.cos(d8) * d3,
                    -Math.sin(d8) * d3,
                    Math.cos(d9) * d3,
                    -Math.sin(d9) * d3
            };

            double[] arrd2 = new double[] {
                    Math.cos(d8) * d4,
                    -Math.sin(d8) * d4,
                    Math.cos(d9) * d4,
                    -Math.sin(d9) * d4
            };

            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(d + arrd2[0], d2 + arrd2[1], 0.0).color(GLColor.glsmCurrentColor.getRed(), GLColor.glsmCurrentColor.getGreen(), GLColor.glsmCurrentColor.getBlue(), GLColor.glsmCurrentColor.getAlpha()).endVertex();
            worldrenderer.pos(d + arrd2[2], d2 + arrd2[3], 0.0).color(GLColor.glsmCurrentColor.getRed(), GLColor.glsmCurrentColor.getGreen(), GLColor.glsmCurrentColor.getBlue(), GLColor.glsmCurrentColor.getAlpha()).endVertex();
            worldrenderer.pos(d + arrd[2], d2 + arrd[3], 0.0).color(GLColor.glsmCurrentColor.getRed(), GLColor.glsmCurrentColor.getGreen(), GLColor.glsmCurrentColor.getBlue(), GLColor.glsmCurrentColor.getAlpha()).endVertex();
            worldrenderer.pos(d + arrd[0], d2 + arrd[1], 0.0).color(GLColor.glsmCurrentColor.getRed(), GLColor.glsmCurrentColor.getGreen(), GLColor.glsmCurrentColor.getBlue(), GLColor.glsmCurrentColor.getAlpha()).endVertex();
            tessellator.draw();
        }
        Ref.getGlBridge().bridge$enableTexture2D();
        Ref.getGlBridge().bridge$disableBlend();
        Ref.getGlBridge().bridge$disableLineSmooth();
        Ref.getGlBridge().bridge$disableBlend();
        Ref.getGlBridge().bridge$enableTexture2D();
    }

    public static void drawHorizontalLine(float left, float right, float top, int color) {
        if (right < left) {
            float temp = left;
            left = right;
            right = temp;
        }
        Ref.modified$drawRect(left, top, right + 1.0f, top + 1.0f, color);
    }

    public static void drawVerticalLine(float left, float top, float bottom, int color) {
        if (bottom < top) {
            float temp = top;
            top = bottom;
            bottom = temp;
        }
        Ref.modified$drawRect(left, top + 1.0f, left + 1.0f, bottom, color);
    }

    public static void drawCorneredGradientRectWithOutline(float left, float top, float right, float bottom,
                                                           int outlineColour, int gradientStart, int gradientEnd) {
        float scaleFactor = Ref.getInstanceCreator().createScaledResolution().getScaleFactor();
        float scaleModifier = 1f / Ref.getInstanceCreator().createScaledResolution().getScaleFactor();

        Ref.getGlBridge().bridge$scale(scaleModifier, scaleModifier, scaleModifier);
        Ref.modified$drawGradientRect((left *= scaleFactor) + 1.0f,
                (top *= scaleFactor) + 1f,
                (right *= scaleFactor) - 1f,
                (bottom *= scaleFactor) - 1f,
                gradientStart, gradientEnd);
        RenderUtil.drawVerticalLine(left, top + 1f, bottom - 2.0f, outlineColour);
        RenderUtil.drawVerticalLine(right - 1f, top + 1f, bottom - 2f, outlineColour);
        RenderUtil.drawHorizontalLine(left + 2f, right - 3f, top, outlineColour);
        RenderUtil.drawHorizontalLine(left + 2f, right - 3f, bottom - 1f, outlineColour);
        RenderUtil.drawHorizontalLine(left + 1f, left + 1f, top + 1f, outlineColour);
        RenderUtil.drawHorizontalLine(right - 2f, right - 2f, top + 1f, outlineColour);
        RenderUtil.drawHorizontalLine(right - 2f, right - 2f, bottom - 2f, outlineColour);
        RenderUtil.drawHorizontalLine(left + 1f, left + 1f, bottom - 2f, outlineColour);
        Ref.getGlBridge().bridge$scale(scaleFactor, scaleFactor, scaleFactor);
    }

    public static long timeOfPreviousFrame = 0L;
    public static long timeOfCurrentFrame = 0L;
    public static long frameTimeNs = 0L;

    public static int minFps = 2147483647;
    public static int maxFps = 0;

    public static void updateFrameTime(long previous) {
        timeOfPreviousFrame = previous;
        timeOfCurrentFrame = System.nanoTime();
        frameTimeNs = timeOfCurrentFrame - timeOfPreviousFrame;
    }

    public static int getTimeAccurateFrameRate() {
        float frameRateAsFloat = 1000000000f / (frameTimeNs == 0 ? 1 : frameTimeNs);
        if (frameTimeNs == 0) {
            frameRateAsFloat = 0f;
        }
        int frameRateAsInt = (int) frameRateAsFloat;
        if (frameRateAsInt > maxFps) {
            maxFps = frameRateAsInt;
        }
        if (frameRateAsInt < minFps) {
            minFps = frameRateAsInt;
        }
        return frameRateAsInt;
    }

    public static float getFrameTimeAsMs() {
        return frameTimeNs == 0 ? 0f : frameTimeNs / 1000000f;
    }
}
