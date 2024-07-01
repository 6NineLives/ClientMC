package com.archclient.impl.ref;

import com.archclient.bridge.ext.GLBridge;

import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;

import static net.lax1dude.eaglercraft.v1_8.internal.PlatformOpenGL.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BridgedGL implements GLBridge {
    public void bridge$enableBlend() {
        GlStateManager.enableBlend();
    }

    public void bridge$disableBlend() {
        GlStateManager.disableBlend();
    }

    public void bridge$enableTexture2D() {
        GlStateManager.enableTexture2D();
    }

    public void bridge$disableTexture2D() {
        GlStateManager.disableTexture2D();
    }

    public void bridge$color(float r, float g, float b, float a) {
        GlStateManager.color(r, g, b, a);
    }

    public void bridge$glBlendFunc(int i, int i1, int i2, int i3) {
        _wglBlendFunc(i, i1);
    }

    public void bridge$enableAlphaTest() {
        GlStateManager.enableAlpha();
    }

    public void bridge$disableAlphaTest() {
        GlStateManager.disableAlpha();
    }

    public void bridge$setShadeModel(int i) {
        GlStateManager.shadeModel(i);
    }

    public boolean bridge$isFramebufferEnabled() {
        return false;//OpenGlHelper.isFramebufferEnabled();
    }

    public boolean bridge$isShadersSupported() {
        return false;//OpenGlHelper.shadersSupported;
    }

    public void bridge$gluProject(float objX, float objY, float objZ, FloatBuffer modelMatrix, FloatBuffer projMatrix, IntBuffer viewport, FloatBuffer win_pos) {
        //GLU.gluProject(objX, objY, objZ, modelMatrix, projMatrix, viewport, win_pos); XXX
    }

    public void bridge$blendFunc(int i, int i1) {
        GlStateManager.blendFunc(i, i1);
    }

    public void bridge$pushMatrix() {
        GlStateManager.pushMatrix();
    }

    public void bridge$popMatrix() {
        GlStateManager.popMatrix();
    }

    public void bridge$scale(float x, float y, float z) {
        GlStateManager.scale(x, y, z);
    }

    public void bridge$enableDepthTest() {
        GlStateManager.enableDepth();
    }

    public void bridge$disableFog() {
        GlStateManager.disableFog();
    }

    public void bridge$disableLighting() {
        GlStateManager.disableLighting();
    }

    public void bridge$matrixMode(int mode) {
        GlStateManager.matrixMode(mode);
    }

    public void bridge$loadIdentity() {
        GlStateManager.loadIdentity();
    }

    public void bridge$translate(float x, float y, float z) {
        GlStateManager.translate(x, y, z);
    }

    public void bridge$ortho(double left, double right, double bottom, double top, double zNear, double zFar) {
        GlStateManager.ortho(left, right, bottom, top, zNear, zFar);
    }

    public void bridge$flush() {
        //_wglFlush();
    }

    public void bridge$alphaFunc(int func, float ref) {
        GlStateManager.alphaFunc(func, ref);
    }

    public void bridge$enableLineSmooth() {
        //xxx_wglEnable(GL_LINE_SMOOTH);
    }

    public void bridge$disableLineSmooth() {
        //xxx_wglDisable(GL_LINE_SMOOTH);
    }

    public void bridge$popAttrib() {
        //xxxGlStateManager.popAttrib();
    }

    public void bridge$pushAttrib(int mode) {
        //xxx_wglPushAttrib(mode);
    }

    public void bridge$scissor(int x, int y, int width, int height) {
        _wglScissor(x, y, width, height);
    }

    public void bridge$enableScissoring() {
        _wglEnable(0xC11);
    }

    public void bridge$disableScissoring() {
        _wglDisable(0xC11);
    }

    public void bridge$bindTexture(int id) {
        GlStateManager.bindTexture(id);
    }

    public void bridge$disableDepthTest() {
        GlStateManager.disableDepth();
    }

    public void bridge$depthMask(boolean b) {
        GlStateManager.depthMask(b);
    }

    public void bridge$enableLighting() {
        GlStateManager.enableLighting();
    }

    public void bridge$normal3f(float x, float y, float z) {
        EaglercraftGPU.glNormal3f(x, y, z);
    }

    public void bridge$enableCullFace() {
        GlStateManager.enableCull();
    }

    public void bridge$polygonOffset(float factor, float units) {
        GlStateManager.doPolygonOffset(factor, units);
    }

    public void bridge$disableCullFace() {
        GlStateManager.disableCull();
    }

    public void bridge$disableColorLogic() {
        GlStateManager.disableColorLogic();
    }

    public void bridge$enableColorLogic() {
        GlStateManager.enableColorLogic();
    }

    public void bridge$colorLogicOp(int code) {
        GlStateManager.colorLogicOp(code);
    }

    public void bridge$enableRescaleNormal() {
        GlStateManager.enableRescaleNormal();
    }

    public void bridge$disableRescaleNormal() {
        GlStateManager.disableRescaleNormal();
    }

    public void bridge$enablePolygonOffset() {
        GlStateManager.enablePolygonOffset();
    }

    public void bridge$disablePolygonOffset() {
        GlStateManager.disablePolygonOffset();
    }

    public void bridge$lineWidth(float width) {
        _wglLineWidth(width);
    }
}
