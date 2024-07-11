package com.archclient.client.module.type.bossbar;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.event.type.GuiDrawEvent;
import com.archclient.client.event.type.RenderPreviewEvent;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.ui.module.ACGuiAnchor;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;

public class BossBarModule extends AbstractModule {
    private final ResourceLocation icons = Ref.getInstanceCreator().createResourceLocation("textures/gui/icons.png");

    public BossBarModule() {
        super("Boss bar");
        this.setDefaultAnchor(ACGuiAnchor.MIDDLE_TOP);
        this.addEvent(GuiDrawEvent.class, this::renderReal);
        this.addEvent(RenderPreviewEvent.class, this::renderPreview);
        this.setPreviewLabel("Boss Bar", 1.0f);
        this.setDefaultState(true);
    }

    public void renderPreview(RenderPreviewEvent renderPreviewEvent) {
        Ref.getGlBridge().bridge$pushMatrix();
        this.scaleAndTranslate(renderPreviewEvent.getResolution());
        if (BossStatus.bossName == null || BossStatus.statusBarTime <= 0) {
            this.minecraft.getTextureManager().bindTexture(this.icons);
            FontRenderer fontRenderer = this.minecraft.fontRendererObj;
            int n2 = 182;
            int n3 = 0;
            int n4 = n2 + 1;
            int n5 = 13;
            Ref.bridge$scaledTessellator(n3, n5, 0, 74, n2, 5);
            Ref.bridge$scaledTessellator(n3, n5, 0, 74, n2, 5);
            Ref.bridge$scaledTessellator(n3, n5, 0, 79, n4, 5);
            String bossName = "Wither";
            fontRenderer.drawStringWithShadow(bossName, (int) (this.width / 2 - (fontRenderer.getStringWidth(bossName) / 2)), n5 - 10, 0xFFFFFF);
            Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
            this.minecraft.getTextureManager().bindTexture(this.icons);
            this.setDimensions(182, 20);
        }
        Ref.getGlBridge().bridge$popMatrix();
    }

    public void renderReal(GuiDrawEvent guiDrawEvent) {
        Ref.getGlBridge().bridge$pushMatrix();
        this.scaleAndTranslate(guiDrawEvent.getResolution());
        if (BossStatus.bossName != null || BossStatus.statusBarTime > 0) {
            this.minecraft.getTextureManager().bindTexture(this.icons);
            FontRenderer fontRenderer = this.minecraft.fontRendererObj;
            int n2 = 182;
            int n3 = 0;
            int n4 = n2 + 1;
            int n5 = 13;
            Ref.bridge$scaledTessellator(n3, n5, 0, 74, n2, 5);
            Ref.bridge$scaledTessellator(n3, n5, 0, 74, n2, 5);
            Ref.bridge$scaledTessellator(n3, n5, 0, 79, n4, 5);
            String bossName = BossStatus.bossName;
            fontRenderer.drawStringWithShadow(bossName, (int) (this.width / 2.0f - (float)(fontRenderer.getStringWidth(bossName) / 2)), n5 - 10, 0xFFFFFF);
            Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
            this.minecraft.getTextureManager().bindTexture(this.icons);
            this.setDimensions(182, 20);
        }
        Ref.getGlBridge().bridge$popMatrix();
    }


}
