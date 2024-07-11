package com.archclient.bridge.ext;

import net.lax1dude.eaglercraft.v1_8.internal.buffer.FloatBuffer;
import net.lax1dude.eaglercraft.v1_8.internal.buffer.IntBuffer;

public interface GLBridge {
    void bridge$enableBlend();
    void bridge$disableBlend();
    void bridge$enableTexture2D();
    void bridge$disableTexture2D();
    void bridge$color(float r, float g, float b, float a);
    void bridge$glBlendFunc(int i, int i1, int i2, int i3);
    void bridge$enableAlphaTest();
    void bridge$disableAlphaTest();
    void bridge$setShadeModel(int i);
    boolean bridge$isFramebufferEnabled(); // OpenGlUtils
    boolean bridge$isShadersSupported(); // OpenGlUtils
    void bridge$gluProject(float objX, float objY, float objZ, FloatBuffer modelMatrix, FloatBuffer projMatrix, IntBuffer viewport, FloatBuffer win_pos); // GLU
    void bridge$blendFunc(int i, int i1);
    void bridge$pushMatrix();
    void bridge$popMatrix();
    void bridge$scale(float x, float y, float z);
    void bridge$enableDepthTest();
    void bridge$disableFog();
    void bridge$disableLighting();
    void bridge$matrixMode(int mode);
    void bridge$loadIdentity();
    void bridge$translate(float x, float y, float z);
    void bridge$ortho(double left, double right, double bottom, double top, double zNear, double zFar);
    void bridge$flush();
    void bridge$alphaFunc(int func, float ref);
    void bridge$enableLineSmooth();
    void bridge$disableLineSmooth();
    void bridge$popAttrib();
    void bridge$pushAttrib(int mode);
    void bridge$scissor(int x, int y, int width, int height);
    void bridge$enableScissoring();
    void bridge$disableScissoring();
    void bridge$rotate(float angle, float x, float y, float z);
    void bridge$bindTexture(int id);
    void bridge$disableDepthTest();
    void bridge$depthMask(boolean b);
    void bridge$enableLighting();
    void bridge$normal3f(float x, float y, float z);
    void bridge$enableCullFace();
    void bridge$polygonOffset(float factor, float units);
    void bridge$disableCullFace();
    default void bridge$translate(double x, double y, double z) {
        this.bridge$translate((float) x, (float) y, (float) z);
    }
    void bridge$disableColorLogic();
    void bridge$enableColorLogic();
    void bridge$colorLogicOp(int code);
    void bridge$enableRescaleNormal();
    void bridge$disableRescaleNormal();
    void bridge$enablePolygonOffset();
    void bridge$disablePolygonOffset();
    void bridge$lineWidth(float width);
}
