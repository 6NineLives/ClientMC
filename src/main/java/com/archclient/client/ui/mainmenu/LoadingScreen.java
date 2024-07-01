package com.archclient.client.ui.mainmenu;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.ui.AbstractGui;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.main.ArchClient;

import static net.lax1dude.eaglercraft.v1_8.internal.PlatformOpenGL.*;
import static net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.ExtGLEnums.*;

import net.lax1dude.eaglercraft.v1_8.internal.IFramebufferGL;
import net.lax1dude.eaglercraft.v1_8.internal.IRenderbufferGL;
import net.lax1dude.eaglercraft.v1_8.internal.buffer.ByteBuffer;
import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerFontRenderer;
import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class LoadingScreen extends AbstractGui {

    private final ResourceLocation logo = Ref.getInstanceCreator().createResourceLocation("client/logo_108.png");
    private final Minecraft mc = Ref.getMinecraft();
    private final ScaledResolution res;
    private final IFramebufferGL framebufferObject;
    private final int framebufferTexture;
    private final int amountOfCalls;
    private int amountOfCallsDone;
    private String message;
    private final EaglerFontRenderer font = new EaglerFontRenderer(Ref.getMinecraft().gameSettings,
            Ref.getInstanceCreator().createResourceLocation("client/font/ubuntu/medium.ttf"), Ref.getMinecraft().getTextureManager(), false);//xxx 16);

    public LoadingScreen(int capacity) {
        this.amountOfCalls = capacity;
        this.res = Ref.getInstanceCreator().createScaledResolution();
        int n2 = this.res.getScaleFactor();
        GlStateManager.enableDepth();
        this.framebufferObject = _wglCreateFramebuffer();
        this.framebufferTexture = TextureUtil.glGenTextures();
        IRenderbufferGL depthBuffer = _wglCreateRenderbuffer();
        GlStateManager.bindTexture(this.framebufferTexture);
        _wglTexParameterf(3553, 10241, 9728);
        _wglTexParameterf(3553, 10240, 9728);
        _wglTexParameterf(3553, 10242, 10496.0f);
        _wglTexParameterf(3553, 10243, 10496.0f);
        GlStateManager.bindTexture(0);
        GlStateManager.bindTexture(this.framebufferTexture);
        _wglTexImage2D(3553, 0, 32856, res.getScaledWidth() * n2, res.getScaledHeight() * n2, 0, 6408, 5121, (ByteBuffer)null);
        _wglBindFramebuffer(_GL_FRAMEBUFFER, this.framebufferObject);
        _wglFramebufferTexture2D(_GL_FRAMEBUFFER, _GL_COLOR_ATTACHMENT0, 3553, EaglercraftGPU.getNativeTexture(this.framebufferTexture), 0);
        _wglBindRenderbuffer(_GL_RENDERBUFFER, depthBuffer);
        _wglRenderbufferStorage(_GL_RENDERBUFFER, 33190, res.getScaledWidth() * n2, res.getScaledHeight() * n2);
        _wglFramebufferRenderbuffer(_GL_FRAMEBUFFER, _GL_DEPTH_ATTACHMENT, _GL_RENDERBUFFER, depthBuffer);
        _wglBindFramebuffer(_GL_FRAMEBUFFER, this.framebufferObject);
        GlStateManager.viewport(0, 0, res.getScaledWidth() * n2, res.getScaledHeight() * n2);
        GlStateManager.clearColor(1f, 1f, 1f, 1f);
        int i = 16384;
        GlStateManager.clearDepth(1.0f);
        i |= 0x100;
        GlStateManager.clear(i);
        _wglBindFramebuffer(_GL_FRAMEBUFFER, null);
        GlStateManager.bindTexture(0);
        _wglBindFramebuffer(_GL_FRAMEBUFFER, null);
    }

    public void newMessage(String string) {
        this.message = string;
        ++this.amountOfCallsDone;
        this.drawMenu(0.0f, 0.0f);
    }

    private void drawLogo(double width, double height) {
        float size = 27.0f;
        double x = width / 2.0 - (double) size;
        double y = height / 2.0 - (double) size;
        RenderUtil.drawIcon(this.logo, size, (float) x, (float) y);
    }

    private void clearMenu() {
        _wglBindFramebuffer(_GL_FRAMEBUFFER, this.framebufferObject);
        Ref.getGlBridge().bridge$matrixMode(5889);
        Ref.getGlBridge().bridge$loadIdentity();
        Ref.getGlBridge().bridge$ortho(0, res.getScaledWidth(), res.getScaledHeight(), 0, 1000, 3000);
        Ref.getGlBridge().bridge$matrixMode(5888);
        Ref.getGlBridge().bridge$loadIdentity();
        Ref.getGlBridge().bridge$translate(0f, 0f, -2000f);
        Ref.getGlBridge().bridge$disableLighting();
        Ref.getGlBridge().bridge$disableFog();
        Ref.getGlBridge().bridge$enableDepthTest();
        Ref.getGlBridge().bridge$enableTexture2D();
        Ref.getGlBridge().bridge$enableAlphaTest();
    }

    private void getMenuReady() {
        int n = this.res.getScaleFactor();
        _wglBindFramebuffer(_GL_FRAMEBUFFER, null);
        GlStateManager.colorMask(true, true, true, false);
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, this.res.getScaledWidth() * n, this.res.getScaledHeight() * n, 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
        GlStateManager.viewport(0, 0, this.res.getScaledWidth() * n, this.res.getScaledHeight() * n);
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableColorMaterial();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.bindTexture(this.framebufferTexture);
        float f = this.res.getScaledWidth() * n;
        float f1 = this.res.getScaledHeight() * n;
        float f2 = (float)(this.res.getScaledWidth() * n) / (float)(this.res.getScaledWidth() * n);
        float f3 = (float)(this.res.getScaledHeight() * n) / (float)(this.res.getScaledHeight() * n);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0, f1, 0.0).tex(0.0, 0.0).color(255, 255, 255, 255).endVertex();
        worldrenderer.pos(f, f1, 0.0).tex(f2, 0.0).color(255, 255, 255, 255).endVertex();
        worldrenderer.pos(f, 0.0, 0.0).tex(f2, f3).color(255, 255, 255, 255).endVertex();
        worldrenderer.pos(0.0, 0.0, 0.0).tex(0.0, f3).color(255, 255, 255, 255).endVertex();
        tessellator.draw();
        GlStateManager.bindTexture(0);
        GlStateManager.depthMask(true);
        GlStateManager.colorMask(true, true, true, true);
        Ref.getGlBridge().bridge$alphaFunc(516, 0.1F);
        Ref.getGlBridge().bridge$flush();
    }

    @Override
    public void drawMenu(float mouseX, float mouseY) {
        clearMenu();
        double scaledWidth = res.getScaledWidth_double();
        double scaledHeight = res.getScaledHeight_double();
        Ref.modified$drawRect(0.0f, 0.0f, (float) scaledWidth, (float) scaledHeight, -1);
        drawLogo(scaledWidth, scaledHeight);
        float width = 160.0f; // width of the progressbar
        float height = 10.0F; // height of the progressbar
        float x = (float) scaledWidth / 2.0f - 80.0f; // x position of the progressbar
        float y = (float) scaledHeight - 40.0f; // y position of the progressbar
        RenderUtil.drawRoundedRect(x, y, x + width, y + height, 8.0, ((255 & 0xFF) << 24) |
                ((245 & 0xFF) << 16) |
                ((245 & 0xFF) << 8)  |
                ((245 & 0xFF) << 0));
        float loadedWidth = width * ((float) amountOfCallsDone / (float) amountOfCalls);
        if (message != null) {
            font.drawCenteredString(message.toLowerCase(), (float) scaledWidth / 2.0f, y - 11.0f, -3092272);
        }
        RenderUtil.drawRoundedRect(x, y, x + loadedWidth, y + height, 8.0, ((255 & 0xFF) << 24) |
                ((218 & 0xFF) << 16) |
                ((66 & 0xFF) << 8)  |
                ((83 & 0xFF) << 0));
        this.getMenuReady();
        this.mc.updateDisplay(); // resetSize
    }

    @Override
    protected void onMouseClicked(float mouseX, float mouseY, int button) {
    }

    @Override
    public void onMouseMovedOrUp(float mouseX, float mouseY, int button) {
    }


}
