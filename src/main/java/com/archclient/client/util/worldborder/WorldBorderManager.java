package com.archclient.client.util.worldborder;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;

import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import com.archclient.client.event.type.CollisionEvent;
import com.archclient.client.event.type.RenderWorldEvent;
import com.archclient.client.event.type.TickEvent;
import com.archclient.client.util.javafx.geom.Vec2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorldBorderManager {

    private final Minecraft minecraft = Ref.getMinecraft();
    private final ArchClient archclient = ArchClient.getInstance();
    private static final ResourceLocation forceFieldTexture = Ref.getInstanceCreator().createResourceLocation("textures/misc/forcefield.png");
    private final List<WorldBorder> borderList = new ArrayList<>();

    public WorldBorderManager() {
        ArchClient.getInstance().getEventBus().addEvent(TickEvent.class, this::onTick);
        ArchClient.getInstance().getEventBus().addEvent(RenderWorldEvent.class, this::onWorldRender);
        ArchClient.getInstance().getEventBus().addEvent(CollisionEvent.class, this::onCollision);
    }

    private void onCollision(CollisionEvent acCollisionEvent) {
        for (WorldBorder border : this.borderList) {
            if (border.lIIIIlIIllIIlIIlIIIlIIllI(acCollisionEvent.getX(), acCollisionEvent.getZ())) continue;
            acCollisionEvent.getBoundingBoxes().add(Ref.getInstanceCreator().createAxisAlignedBB(acCollisionEvent.getX(), acCollisionEvent.getY(), acCollisionEvent.getZ(), acCollisionEvent.getX() + 1.0, acCollisionEvent.getY() + 1.0, acCollisionEvent.getZ() + 1.0));
        }
    }

    private void onTick(TickEvent aCTickEvent) {
        this.borderList.forEach(WorldBorder::ting);
    }

    private void onWorldRender(RenderWorldEvent renderWorldEvent) {
        if (!this.borderList.isEmpty()) {
            EntityPlayerSP playerMP = this.minecraft.thePlayer;
            float f = renderWorldEvent.getPartialTicks();
            this.borderList.stream().filter(WorldBorder::worldEqualsWorld).forEach(border -> {
                Tessellator tessellator = Ref.getTessellator();
                WorldRenderer worldRenderer = tessellator.getWorldRenderer();
                double d = this.minecraft.gameSettings.renderDistanceChunks * 16;
                if (playerMP.posX >= border.IIIIllIlIIIllIlllIlllllIl() - d || playerMP.posX <= border.IlIlIIIlllIIIlIlllIlIllIl() + d || playerMP.posZ >= border.IIIIllIIllIIIIllIllIIIlIl() - d || playerMP.posZ <= border.IIIllIllIlIlllllllIlIlIII() + d) {
                    float f2;
                    double d2;
                    double d3;
                    float f3;
                    border.lIIIIlIIllIIlIIlIIIlIIllI(playerMP);
                    double d5 = playerMP.lastTickPosX + (playerMP.posX - playerMP.lastTickPosX) * (double)f;
                    double d6 = playerMP.lastTickPosY + (playerMP.posY - playerMP.lastTickPosY) * (double)f;
                    double d7 = playerMP.lastTickPosZ + (playerMP.posZ - playerMP.lastTickPosZ) * (double)f;
                    Ref.getGlBridge().bridge$enableBlend();
                    Ref.getGlBridge().bridge$blendFunc(770, 1);
                    this.minecraft.getTextureManager().bindTexture(forceFieldTexture);
                    Ref.getGlBridge().bridge$depthMask(false);
                    Ref.getGlBridge().bridge$pushMatrix();
                    float f4 = (float)(WorldBorder.IlllIIIlIlllIllIlIIlllIlI(border).getRed() & 0xFF) / (float)255;
                    float f5 = (float)(WorldBorder.IlllIIIlIlllIllIlIIlllIlI(border).getGreen() & 0xFF) / (float)255;
                    float f6 = (float)(WorldBorder.IlllIIIlIlllIllIlIIlllIlI(border).getBlue() & 0xFF) / (float)255;
                    Ref.getGlBridge().bridge$polygonOffset(-3, -3);
                    Ref.getGlBridge().bridge$enablePolygonOffset();
                    Ref.getGlBridge().bridge$alphaFunc(516, 0.097260274f * 1.028169f);
                    Ref.getGlBridge().bridge$enableAlphaTest();
                    Ref.getGlBridge().bridge$disableCullFace();
                    float f7 = (float)(Minecraft.getSystemTime() % 3000L) / (float)3000;
                    worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
                    Ref.getGlBridge().bridge$translate(-d5, -d6, -d7);
                    worldRenderer.color((int)(f4 * 255.0F), (int)(f5 * 255.0F), (int)(f6 * 255.0F), (int)(1.0f * 255.0F));
                    double d8 = Math.max(MathHelper$floor_double(d7 - d), border.IIIllIllIlIlllllllIlIlIII());
                    double d9 = Math.min(MathHelper$ceiling_double_int(d7 + d), border.IIIIllIIllIIIIllIllIIIlIl());
                    if (d5 > border.IIIIllIlIIIllIlllIlllllIl() - d) {
                        f3 = 0.0f;
                        d3 = d8;
                        while (d3 < d9) {
                            d2 = Math.min(1.0, d9 - d3);
                            f2 = (float)d2 * (0.04054054f * 12.333334f);
                            worldRenderer.pos(border.IIIIllIlIIIllIlllIlllllIl(), 256, d3).tex(f7 + f3, f7 + 0.0f).endVertex();
                            worldRenderer.pos(border.IIIIllIlIIIllIlllIlllllIl(), 256, d3 + d2).tex(f7 + f2 + f3, f7 + 0.0f).endVertex();
                            worldRenderer.pos(border.IIIIllIlIIIllIlllIlllllIl(), 0.0, d3 + d2).tex(f7 + f2 + f3, f7 + (float)128).endVertex();
                            worldRenderer.pos(border.IIIIllIlIIIllIlllIlllllIl(), 0.0, d3).tex(f7 + f3, f7 + (float)128).endVertex();
                            d3 += 1.0;
                            f3 += 0.16463414f * 3.0370371f;
                        }
                    }
                    if (d5 < border.IlIlIIIlllIIIlIlllIlIllIl() + d) {
                        f3 = 0.0f;
                        d3 = d8;
                        while (d3 < d9) {
                            d2 = Math.min(1.0, d9 - d3);
                            f2 = (float)d2 * (0.3611111f * 1.3846154f);
                            worldRenderer.pos(border.IlIlIIIlllIIIlIlllIlIllIl(), 256, d3).tex(f7 + f3, f7 + 0.0f).endVertex();
                            worldRenderer.pos(border.IlIlIIIlllIIIlIlllIlIllIl(), 256, d3 + d2).tex(f7 + f2 + f3, f7 + 0.0f).endVertex();
                            worldRenderer.pos(border.IlIlIIIlllIIIlIlllIlIllIl(), 0.0, d3 + d2).tex(f7 + f2 + f3, f7 + (float)128).endVertex();
                            worldRenderer.pos(border.IlIlIIIlllIIIlIlllIlIllIl(), 0.0, d3).tex(f7 + f3, f7 + (float)128).endVertex();
                            d3 += 1.0;
                            f3 += 1.25f * 0.4f;
                        }
                    }
                    d8 = Math.max(MathHelper$floor_double(d5 - d), border.IlIlIIIlllIIIlIlllIlIllIl());
                    d9 = Math.min(MathHelper$ceiling_double_int(d5 + d), border.IIIIllIlIIIllIlllIlllllIl());
                    if (d7 > border.IIIIllIIllIIIIllIllIIIlIl() - d) {
                        f3 = 0.0f;
                        d3 = d8;
                        while (d3 < d9) {
                            d2 = Math.min(1.0, d9 - d3);
                            f2 = (float)d2 * (0.3115942f * 1.6046512f);
                            worldRenderer.pos(d3, 256, border.IIIIllIIllIIIIllIllIIIlIl()).tex(f7 + f3, f7 + 0.0f).endVertex();
                            worldRenderer.pos(d3 + d2, 256, border.IIIIllIIllIIIIllIllIIIlIl()).tex(f7 + f2 + f3, f7 + 0.0f).endVertex();
                            worldRenderer.pos(d3 + d2, 0.0, border.IIIIllIIllIIIIllIllIIIlIl()).tex(f7 + f2 + f3, f7 + (float)128).endVertex();
                            worldRenderer.pos(d3, 0.0, border.IIIIllIIllIIIIllIllIIIlIl()).tex(f7 + f3, f7 + (float)128).endVertex();
                            d3 += 1.0;
                            f3 += 1.5882353f * 0.31481484f;
                        }
                    }
                    if (d7 < border.IIIllIllIlIlllllllIlIlIII() + d) {
                        f3 = 0.0f;
                        d3 = d8;
                        while (d3 < d9) {
                            d2 = Math.min(1.0, d9 - d3);
                            f2 = (float)d2 * (1.6071428f * 0.31111112f);
                            worldRenderer.pos(d3, 256, border.IIIllIllIlIlllllllIlIlIII()).tex(f7 + f3, f7 + 0.0f).endVertex();
                            worldRenderer.pos(d3 + d2, 256, border.IIIllIllIlIlllllllIlIlIII()).tex(f7 + f2 + f3, f7 + 0.0f).endVertex();
                            worldRenderer.pos(d3 + d2, 0.0, border.IIIllIllIlIlllllllIlIlIII()).tex(f7 + f2 + f3, f7 + (float)128).endVertex();
                            worldRenderer.pos(d3, 0.0, border.IIIllIllIlIlllllllIlIlIII()).tex(f7 + f3, f7 + (float)128).endVertex();
                            d3 += 1.0;
                            f3 += 2.2820513f * 0.21910112f;
                        }
                    }
                    tessellator.draw();
                    Ref.getGlBridge().bridge$translate(0f, 0f, 0f);
                    Ref.getGlBridge().bridge$enableCullFace();
                    Ref.getGlBridge().bridge$disableAlphaTest();
                    Ref.getGlBridge().bridge$polygonOffset(0.0f, 0.0f);
                    Ref.getGlBridge().bridge$disablePolygonOffset();
                    Ref.getGlBridge().bridge$enableAlphaTest();
                    Ref.getGlBridge().bridge$disableBlend();
                    Ref.getGlBridge().bridge$popMatrix();
                    Ref.getGlBridge().bridge$depthMask(true);
                }
            });
        }
    }

    private static int MathHelper$ceiling_double_int(double par0) {
        int var2 = (int)par0;
        return par0 > (double)var2 ? var2 + 1 : var2;
    }

    private static int MathHelper$floor_double(double par0) {
        int var2 = (int)par0;
        return par0 < (double)var2 ? var2 - 1 : var2;
    }

    public void lIIIIlIIllIIlIIlIIIlIIllI(String string, String string2, int n, double d, double d2, double d3, double d4, boolean bl, boolean bl2) {
        this.borderList.add(new WorldBorder(this, string, string2, n, d, d2, d3, d4, bl, bl2));
    }

    public void lIIIIlIIllIIlIIlIIIlIIllI(String string, double d, double d2, double d3, double d4, int n) {
        this.borderList.stream().filter(iIIlIllIIIlllIIlIIllIlIII -> Objects.equals(WorldBorder.getPlayer(iIIlIllIIIlllIIlIIllIlIII), string) && WorldBorder.lIIIIIIIIIlIllIIllIlIIlIl(iIIlIllIIIlllIIlIIllIlIII)).findFirst().ifPresent(iIIlIllIIIlllIIlIIllIlIII -> {
            WorldBorder.lIIIIlIIllIIlIIlIIIlIIllI(iIIlIllIIIlllIIlIIllIlIII, new Vec2d(d, d2));
            WorldBorder.lIIIIIIIIIlIllIIllIlIIlIl(iIIlIllIIIlllIIlIIllIlIII, new Vec2d(d3, d4));
            WorldBorder.lIIIIlIIllIIlIIlIIIlIIllI(iIIlIllIIIlllIIlIIllIlIII, 0);
            WorldBorder.lIIIIIIIIIlIllIIllIlIIlIl(iIIlIllIIIlllIIlIIllIlIII, n);
        });
    }

    public void lIIIIlIIllIIlIIlIIIlIIllI(String string) {
        this.borderList.removeIf(border -> Objects.equals(WorldBorder.getPlayer(border), string));
    }

    public void lIIIIlIIllIIlIIlIIIlIIllI() {
        this.borderList.clear();
    }

    public List<WorldBorder> lIIIIIIIIIlIllIIllIlIIlIl() {
        return this.borderList;
    }

    static ArchClient lIIIIlIIllIIlIIlIIIlIIllI(WorldBorderManager aCWorldBorder) {
        return aCWorldBorder.archclient;
    }
}
 