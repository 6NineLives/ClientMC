package com.archclient.client.module.type;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;
import com.archclient.client.event.type.DisconnectEvent;
import com.archclient.client.event.type.GuiDrawEvent;
import com.archclient.client.ui.util.Color;
import com.archclient.client.util.teammates.IlIlIIlllIIIIIlIlIlIIIllI;
import com.archclient.client.util.teammates.Teammate;
import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.lax1dude.eaglercraft.v1_8.internal.buffer.FloatBuffer;
import net.lax1dude.eaglercraft.v1_8.internal.buffer.IntBuffer;
import net.lax1dude.eaglercraft.v1_8.EagRuntime;

import java.util.ArrayList;
import java.util.List;

public class TeammatesModule {
    public FloatBuffer modelViewMatrixBuffer = EagRuntime.allocateFloatBuffer(16);
    public FloatBuffer projectionMatrixBuffer = EagRuntime.allocateFloatBuffer(16);
    private final List<Teammate> teammates;
    public List<Teammate> getTeammates() {
        return this.teammates;
    }
    private final int[] IIIIllIlIIIllIlllIlllllIl = new int[]{-15007996, -43234, -3603713, -16580641, -8912129, -16601345, -2786, -64828, -15629042, -10744187};
    private boolean IIIIllIIllIIIIllIllIIIlIl = false;
    private final Minecraft minecraft = Ref.getMinecraft();

    public TeammatesModule() {
        this.teammates = new ArrayList<>();
    }

    public double getDistance(double d, double d2, double d3) {
        double d4 = d - this.minecraft.thePlayer.posX;
        double d5 = d2 - this.minecraft.thePlayer.posY;
        double d6 = d3 - this.minecraft.thePlayer.posZ;
        return Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
    }

    private void onDraw(GuiDrawEvent guiDrawEvent) {
        if (this.teammates.isEmpty()) {
            return;
        }
        int[] viewport = new int[16];
        EaglercraftGPU.glGetInteger(2978, viewport); // VIEWPORT
        IntBuffer intBuffer = EagRuntime.allocateIntBuffer(16);
        intBuffer.put(viewport);
        float f2 = (float)(this.minecraft.thePlayer.lastTickPosX + (this.minecraft.thePlayer.posX - this.minecraft.thePlayer.lastTickPosX) * Ref.getMinecraft().getTimer().renderPartialTicks) - (float) Ref.getRenderManager().getRenderPosX();
        float f3 = (float)(this.minecraft.thePlayer.lastTickPosY + (this.minecraft.thePlayer.posY - this.minecraft.thePlayer.lastTickPosY) * Ref.getMinecraft().getTimer().renderPartialTicks) - (float) Ref.getRenderManager().getRenderPosY();
        float f4 = (float)(this.minecraft.thePlayer.lastTickPosZ + (this.minecraft.thePlayer.posZ - this.minecraft.thePlayer.lastTickPosZ) * Ref.getMinecraft().getTimer().renderPartialTicks) - (float) Ref.getRenderManager().getRenderPosZ();
        double d = (double)(this.minecraft.thePlayer.rotationPitch + (float)90) * (0.3249923327873289 * 9.666666984558105) / (double)180;
        double d2 = (double)(this.minecraft.thePlayer.rotationYaw + (float)90) * (7.479982742083262 * (double)0.42f) / (double)180;
        Vec3 vec3 = Ref.getInstanceCreator().createVec3(Math.sin(d) * Math.cos(d2), Math.cos(d), Math.sin(d) * Math.sin(d2));
        if (this.minecraft.gameSettings.thirdPersonView == 2) {
            vec3 = Ref.getInstanceCreator().createVec3(vec3.xCoord * (double)-1, vec3.yCoord * (double)-1, vec3.zCoord * (double)-1);
        }
        for (Teammate teammate : this.teammates) {
            EntityPlayer entityPlayer = this.minecraft.theWorld.getPlayerEntityByName(teammate.getUuid());
            if (entityPlayer == null) {
                double d3;
                if (System.currentTimeMillis() - teammate.getLastUpdated() > teammate.getWaitTime()) continue;
                double d4 = teammate.getVector3D().xCoord - (double)f2;
                double d5 = teammate.getVector3D().yCoord - (double)f3;
                double d6 = teammate.getVector3D().zCoord - (double)f4;
                double d7 = this.getDistance(teammate.getVector3D().xCoord, teammate.getVector3D().yCoord, teammate.getVector3D().zCoord);
                if (d7 > (d3 = (this.minecraft.gameSettings.renderDistanceChunks * (float)16))) {
                    d4 = d4 / d7 * d3;
                    d5 = d5 / d7 * d3;
                    d6 = d6 / d7 * d3;
                }
                this.lIIIIlIIllIIlIIlIIIlIIllI(guiDrawEvent.getResolution(), teammate, (float)d4, (float)d5, (float)d6, intBuffer, (int)d7);
                continue;
            }
            if (entityPlayer == this.minecraft.thePlayer) continue;
            float f5 = (float)(entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * Ref.getMinecraft().getTimer().renderPartialTicks - (double)f2);
            float f6 = (float)(entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * Ref.getMinecraft().getTimer().renderPartialTicks - (double)f3) + entityPlayer.height + 1.0f;
            float f7 = (float)(entityPlayer.lastTickPosZ + (entityPlayer.lastTickPosZ - entityPlayer.lastTickPosZ) * Ref.getMinecraft().getTimer().renderPartialTicks - (double)f4);
            double d8 = this.getDistance(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
            this.lIIIIlIIllIIlIIlIIIlIIllI(guiDrawEvent.getResolution(), teammate, f5, f6, f7, intBuffer, (int)d8);
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(ScaledResolution resolution, Teammate teammate,
                                           float x, float y, float z, IntBuffer viewport, int dist) {
        Vec3 vec3 = Ref.getInstanceCreator().createVec3(x, y, z);
        double d = vec3.lengthVector();
        if (vec3.dotProduct(vec3 = vec3.normalize()) <= 0.02) {
            double d3 = Math.sin(1.5533430342749535);
            double d4 = Math.cos(1.5533430342749535);
            Vec3 vec33 = vec3.crossProduct(vec3);
            double d5 = vec33.xCoord;
            double d6 = vec33.yCoord;
            double d7 = vec33.zCoord;
            double d8 = d4 + d5 * d5 * (1.0 - d4);
            double d9 = d5 * d6 * (1.0 - d4) - d7 * d3;
            double d10 = d5 * d7 * (1.0 - d4) + d6 * d3;
            double d11 = d6 * d5 * (1.0 - d4) + d7 * d3;
            double d12 = d4 + d6 * d6 * (1.0 - d4);
            double d13 = d6 * d7 * (1.0 - d4) - d5 * d3;
            double d14 = d7 * d5 * (1.0 - d4) - d6 * d3;
            double d15 = d7 * d6 * (1.0 - d4) + d5 * d3;
            double d16 = d4 + d7 * d7 * (1.0 - d4);
            x = (float)(d * (d8 * vec3.xCoord + d9 * vec3.yCoord + d10 * vec3.zCoord));
            y = (float)(d * (d11 * vec3.xCoord + d12 * vec3.yCoord + d13 * vec3.zCoord));
            z = (float)(d * (d14 * vec3.xCoord + d15 * vec3.yCoord + d16 * vec3.zCoord));
        }
        FloatBuffer floatBuffer = EagRuntime.allocateFloatBuffer(3);
        Ref.getGlBridge().bridge$gluProject(x, y, z, this.modelViewMatrixBuffer, this.projectionMatrixBuffer, viewport, floatBuffer);
        float f4 = floatBuffer.get(0) / (float)resolution.getScaleFactor();
        float f5 = floatBuffer.get(1) / (float)resolution.getScaleFactor();
        IlIlIIlllIIIIIlIlIlIIIllI ilIlIIlllIIIIIlIlIlIIIllI = null;
        int n2 = 8;
        int n3 = 10;
        int n4 = -4 - n3;
        float f6 = (float)resolution.getScaledHeight() - f5;
        if (f6 < 0.0f) {
            ilIlIIlllIIIIIlIlIlIIIllI = IlIlIIlllIIIIIlIlIlIIIllI.lIIIIlIIllIIlIIlIIIlIIllI;
            f5 = resolution.getScaledHeight() - 6;
        } else if (f6 > (float)(resolution.getScaledHeight() - n3)) {
            ilIlIIlllIIIIIlIlIlIIIllI = IlIlIIlllIIIIIlIlIlIIIllI.IlllIIIlIlllIllIlIIlllIlI;
            f5 = 6;
        }
        if (f4 - (float)n2 < 0.0f) {
            ilIlIIlllIIIIIlIlIlIIIllI = IlIlIIlllIIIIIlIlIlIIIllI.lIIIIIIIIIlIllIIllIlIIlIl;
            f4 = 6;
        } else if (f4 > (float)(resolution.getScaledWidth() - n2)) {
            ilIlIIlllIIIIIlIlIlIIIllI = IlIlIIlllIIIIIlIlIlIIIllI.IIIIllIlIIIllIlllIlllllIl;
            f4 = resolution.getScaledWidth() - 6;
        }
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.getGlBridge().bridge$translate(f4, (float)resolution.getScaledHeight() - f5, 0.0f);
        if (ilIlIIlllIIIIIlIlIlIIIllI != null) {
            if (ArchClient.getInstance().getGlobalSettings().showOffScreenMarker.<Boolean>value()) {
                this.drawOffscreenMarker(teammate, ilIlIIlllIIIIIlIlIlIIIllI, 0.0f, 0.0f);
            }
        } else {
            this.drawMarker(teammate, n2, (float)n4, (float)n3);
            if (dist > 40 && ArchClient.getInstance().getGlobalSettings().showDistance.<Boolean>value()) {
                this.minecraft.fontRendererObj.drawString("(" + dist + "m)", 0, 10, -1);
            }
        }
        Ref.getGlBridge().bridge$popMatrix();
    }

    private void drawOffscreenMarker(Teammate ilIlllIlIlIIllllIlllIlIII, IlIlIIlllIIIIIlIlIlIIIllI ilIlIIlllIIIIIlIlIlIIIllI, float translateX, float translateY) {
        Tessellator tessellator = Ref.getTessellator();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        Ref.getGlBridge().bridge$enableBlend();
        Ref.getGlBridge().bridge$disableTexture2D();
        Ref.getGlBridge().bridge$glBlendFunc(770, 771, 1, 0);
        if (ilIlllIlIlIIllllIlllIlIII.isLeader()) {
            Ref.getGlBridge().bridge$color(0.0f, 0.0f, 1.0f, 3.137931f * 0.21032967f);
        } else {
            Color color = ilIlllIlIlIIllllIlllIlIII.getColor();
            Ref.getGlBridge().bridge$color((float)color.getRed() / (float)255, (float)color.getGreen() / (float)255, (float)color.getBlue() / (float)255, 0.61285716f * 1.0769231f);
        }
        float f3 = 8;
        float f4 = 10;
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.getGlBridge().bridge$translate(translateX, translateY, 0.0f);
        switch (ilIlIIlllIIIIIlIlIlIIIllI) {
            case lIIIIIIIIIlIllIIllIlIIlIl: {
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
                worldRenderer.pos(f3 / 2.0f, f4 / 2.0f, 0.0).endVertex();
                worldRenderer.pos(-f3 / 2.0f, 0.0, 0.0).endVertex();
                worldRenderer.pos(f3 / 2.0f, -f4 / 2.0f, 0.0).endVertex();
                worldRenderer.pos(-f3 / 2.0f, 0.0, 0.0).endVertex();
                tessellator.draw();
                break;
            }
            case IIIIllIlIIIllIlllIlllllIl: {
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
                worldRenderer.pos(-f3 / 2.0f, f4 / 2.0f, 0.0).endVertex();
                worldRenderer.pos(f3 / 2.0f, 0.0, 0.0).endVertex();
                worldRenderer.pos(-f3 / 2.0f, -f4 / 2.0f, 0.0).endVertex();
                worldRenderer.pos(f3 / 2.0f, 0.0, 0.0).endVertex();
                tessellator.draw();
                break;
            }
            case IlllIIIlIlllIllIlIIlllIlI: {
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
                worldRenderer.pos(-f3 / 2.0f, -f4 / 2.0f, 0.0).endVertex();
                worldRenderer.pos(0.0, f4 / 2.0f, 0.0).endVertex();
                worldRenderer.pos(f3 / 2.0f, -f4 / 2.0f, 0.0).endVertex();
                worldRenderer.pos(0.0, f4 / 2.0f, 0.0).endVertex();
                tessellator.draw();
                break;
            }
            case lIIIIlIIllIIlIIlIIIlIIllI: {
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
                worldRenderer.pos(-f3 / 2.0f, f4 / 2.0f, 0.0).endVertex();
                worldRenderer.pos(0.0, -f4 / 2.0f, 0.0).endVertex();
                worldRenderer.pos(f3 / 2.0f, f4 / 2.0f, 0.0).endVertex();
                worldRenderer.pos(0.0, -f4 / 2.0f, 0.0).endVertex();
                tessellator.draw();
            }
        }
        Ref.getGlBridge().bridge$popMatrix();
        Ref.getGlBridge().bridge$enableTexture2D();
        Ref.getGlBridge().bridge$disableBlend();
    }

    private void drawMarker(Teammate teammate, float f, float f2, float f3) {
        Tessellator tessellator = Ref.getTessellator();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        Ref.getGlBridge().bridge$enableBlend();
        Ref.getGlBridge().bridge$disableTexture2D();
        Ref.getGlBridge().bridge$glBlendFunc(770, 771, 1, 0);
        if (teammate.isLeader()) {
            Ref.getGlBridge().bridge$color(0.0f, 0.0f, 1.0f, 0.83837837f * 0.78723407f);
        } else {
            Color color = teammate.getColor();
            Ref.getGlBridge().bridge$color((float)color.getRed() / (float)255, (float)color.getGreen() / (float)255, (float)color.getBlue() / (float)255, 1.755102f * 0.37604654f);
        }
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.getGlBridge().bridge$scale(.6f, .6f, .6f);
        Ref.getGlBridge().bridge$rotate(45f, 0f, 0f, 1f);
        Ref.getGlBridge().bridge$translate(f * 2f, 0f, 0f);
        Ref.getGlBridge().bridge$rotate(90f, 0f, 0f, -1);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-f, f2, 0.0).endVertex();
        worldRenderer.pos(-f, f2 + f3 / 2f, 0.0).endVertex();
        worldRenderer.pos(f, f2 + f3 / 2f, 0.0).endVertex();
        worldRenderer.pos(f, f2, 0.0).endVertex();
        tessellator.draw();
        Ref.getGlBridge().bridge$rotate(90, 0.0f, 0.0f, -1);
        Ref.getGlBridge().bridge$translate(f * 2f + 1f, f3 / 2f + 1f, 0f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-f / 2f + 1f, f2, 0.0).endVertex();
        worldRenderer.pos(-f / 2f + 1f, f2 + f3 / 2f, 0.0).endVertex();
        worldRenderer.pos(f, f2 + f3 / 2f, 0.0).endVertex();
        worldRenderer.pos(f, f2, 0.0).endVertex();
        tessellator.draw();
        Ref.getGlBridge().bridge$popMatrix();
        Ref.getGlBridge().bridge$enableTexture2D();
        Ref.getGlBridge().bridge$disableBlend();
    }

    private void onDisconnect(DisconnectEvent event) {
        this.teammates.clear();
    }

    public Teammate getTeammate(String uuid) {
        for (Teammate teammate : this.teammates) {
            if (teammate.getUuid().equals(uuid)) {
                return teammate;
            }
        }
        return null;
    }

    public int[] lIIIIIIIIIlIllIIllIlIIlIl() {
        return this.IIIIllIlIIIllIlllIlllllIl;
    }

    public boolean IlllIIIlIlllIllIlIIlllIlI() {
        return this.IIIIllIIllIIIIllIllIIIlIl;
    }

    public void lIIIIlIIllIIlIIlIIIlIIllI(boolean bl) {
        if (bl && !this.IIIIllIIllIIIIllIllIIIlIl) {
            this.IIIIllIIllIIIIllIllIIIlIl = true;
            ArchClient.getInstance().getEventBus().addEvent(GuiDrawEvent.class, this::onDraw);
            ArchClient.getInstance().getEventBus().addEvent(DisconnectEvent.class, this::onDisconnect);
        } else if (!bl && this.IIIIllIIllIIIIllIllIIIlIl) {
            this.IIIIllIIllIIIIllIllIIIlIl = false;
            ArchClient.getInstance().getEventBus().removeEvent(GuiDrawEvent.class, this::onDraw);
            ArchClient.getInstance().getEventBus().removeEvent(DisconnectEvent.class, this::onDisconnect);
        }
    }
}
