package com.archclient.client.util.hologram;

import com.archclient.bridge.ref.Ref;
import lombok.Getter;
import lombok.Setter;
import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Hologram {
    private final EaglercraftUUID uuid;
    @Setter
    private String[] texts;
    private final double x;
    private final double y;
    private final double z;
    @Getter
    private static final List<Hologram> holograms = new ArrayList<>();

    public Hologram(EaglercraftUUID uuid, double x, double y, double z) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void lIIIIlIIllIIlIIlIIIlIIllI() {
        FontRenderer fontRenderer = Ref.getMinecraft().fontRendererObj;
        for (Hologram hologram : holograms) {
            if (hologram.getTexts() == null || hologram.getTexts().length == 0) continue;
            for (int i = hologram.getTexts().length - 1; i >= 0; --i) {
                String string = hologram.getTexts()[hologram.getTexts().length - i - 1];
                float f = (float)(hologram.getX() - Ref.getRenderManager().getRenderPosX());
                float f2 = (float)(hologram.getY() + 1.0 + (double)((float)i * (0.25f)) - Ref.getRenderManager().getRenderPosY());
                float f3 = (float)(hologram.getZ() - Ref.getRenderManager().getRenderPosZ());
                float f5 = 0.02666667f;
                Ref.getGlBridge().bridge$pushMatrix();
                Ref.getGlBridge().bridge$translate(f, f2, f3);
                Ref.getGlBridge().bridge$normal3f(0f, 1f, 0f);
                GlStateManager.rotate(-Ref.getRenderManager().getPlayerViewY(), 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(Ref.getRenderManager().getPlayerViewX(), 1.0f, 0.0f, 0.0f);
                Ref.getGlBridge().bridge$scale(-f5, -f5, f5);
                Ref.getGlBridge().bridge$disableLighting();
                Ref.getGlBridge().bridge$depthMask(false);
                Ref.getGlBridge().bridge$disableDepthTest();
                Ref.getGlBridge().bridge$enableBlend();
                Ref.getGlBridge().bridge$glBlendFunc(770, 771, 1, 0);
                Tessellator tessellator = Ref.getTessellator();
                WorldRenderer worldRenderer = tessellator.getWorldRenderer();
                int n = 0;
                Ref.getGlBridge().bridge$disableTexture2D();
                worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
                int n2 = fontRenderer.getStringWidth(string) / 2;
                worldRenderer.pos(-n2 - 1, -1 + n, 0.0).color(0f, 0f, 0f, 0.25f).endVertex();
                worldRenderer.pos(-n2 - 1, 8 + n, 0.0).color(0f, 0f, 0f, 0.25f).endVertex();
                worldRenderer.pos(n2 + 1, 8 + n, 0.0).color(0f, 0f, 0f, 0.25f).endVertex();
                worldRenderer.pos(n2 + 1, -1 + n, 0.0).color(0f, 0f, 0f, 0.25f).endVertex();
                tessellator.draw();
                Ref.getGlBridge().bridge$enableTexture2D();
                fontRenderer.drawString(string, -fontRenderer.getStringWidth(string) / 2, n, 0x20FFFFFF);
                Ref.getGlBridge().bridge$enableDepthTest();
                Ref.getGlBridge().bridge$depthMask(true);
                fontRenderer.drawString(string, -fontRenderer.getStringWidth(string) / 2, n, -1);
                Ref.getGlBridge().bridge$enableLighting();
                Ref.getGlBridge().bridge$disableBlend();
                Ref.getGlBridge().bridge$color(1.0F, 1.0F, 1.0F, 1.0F);
                Ref.getGlBridge().bridge$popMatrix();
            }
        }
    }
}