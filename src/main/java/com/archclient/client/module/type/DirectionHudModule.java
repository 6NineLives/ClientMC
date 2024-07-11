package com.archclient.client.module.type;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.config.Setting;
import com.archclient.client.event.type.GuiDrawEvent;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.ui.module.ACGuiAnchor;
import com.archclient.client.ui.util.HudUtil;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class DirectionHudModule extends AbstractModule {

    private final Setting markerColor;
    private final Setting directionColor;
    private final Setting showWhileTyping;
    private final ResourceLocation texture = Ref.getInstanceCreator().createResourceLocation("textures/gui/compass.png");

    public DirectionHudModule() {
        super("Direction HUD");
        this.setDefaultAnchor(ACGuiAnchor.MIDDLE_MIDDLE);
        this.setState(false);
        this.showWhileTyping = new Setting(this, "Show While Typing").setValue(true);
        this.markerColor = new Setting(this, "Marker Color").setValue(-43691).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.directionColor = new Setting(this, "Direction Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.setPreviewIcon(Ref.getInstanceCreator().createResourceLocation("client/icons/mods/dirhud.png"), 65, 12);
        this.addEvent(GuiDrawEvent.class, this::renderReal);
    }

    private void renderReal(GuiDrawEvent guiDrawEvent) {
        if (!this.isRenderHud()) {
            return;
        }
        int backgroundColor = 0xFF212121;
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.getGlBridge().bridge$enableBlend();
        this.scaleAndTranslate(guiDrawEvent.getResolution());
        this.setDimensions(66f, 18f);
        if (!(minecraft.currentScreen instanceof GuiChat) || this.showWhileTyping.<Boolean>value()) {
            Ref.getGlBridge().bridge$color((backgroundColor >> 16 & 0xFF) / 255f,
                    (backgroundColor >> 8 & 0xFF) / 255f,
                    (backgroundColor & 0xFF) / 255f,
                    (backgroundColor >> 24 & 255) / 255f);
            this.render(guiDrawEvent.getResolution());
            Ref.getGlBridge().bridge$color((backgroundColor >> 16 & 0xFF) / 255f,
                    (backgroundColor >> 8 & 0xFF) / 255f,
                    (backgroundColor & 0xFF) / 255f,
                    (backgroundColor >> 24 & 255) / 255f);
        }
        Ref.getGlBridge().bridge$disableBlend();
        Ref.getGlBridge().bridge$popMatrix();
    }

    private int MathHelper$floor_double(double toFloor) {
        int asInt = (int)toFloor;
        return toFloor < (double)asInt ? asInt - 1 : asInt;
    }

    private void render(ScaledResolution scaledResolution) {
        int n = MathHelper$floor_double((double)(this.minecraft.thePlayer.rotationYaw * (float)256 / (float)360) + 0.5) & 0xFF;
        int n2 = 0;
        int n3 = 0;
        int backgroundColor = 0xFF212121;

        if (this.directionColor.<Integer>value() != 4095) {
            int n4 = this.directionColor.getColorValue();
            this.minecraft.getTextureManager().bindTexture(this.texture);
            if (n < 128) {
                Ref.getGlBridge().bridge$color((float)(backgroundColor >> 16 & 0xFF) / (float)255, (float)(backgroundColor >> 8 & 0xFF) / (float)255, (float)(backgroundColor & 0xFF) / (float)255, (float)(backgroundColor >> 24 & 255) / (float)255);
                HudUtil.drawTexturedModalRect(n3, n2, n, 0, 65, 12, -100);
            } else {
                HudUtil.drawTexturedModalRect(n3, n2, n - 128, 12, 65, 12, -100);
            }
            Ref.getGlBridge().bridge$color((float)(n4 >> 16 & 0xFF) / (float)255, (float)(n4 >> 8 & 0xFF) / (float)255, (float)(n4 & 0xFF) / (float)255, 1.0f);
            if (n < 128) {
                HudUtil.drawTexturedModalRect(n3, n2, n, 24, 65, 12, -100);
            } else {
                HudUtil.drawTexturedModalRect(n3, n2, n - 128, 36, 65, 12, -100);
            }
        } else {
            this.minecraft.getTextureManager().bindTexture(this.texture);
            if (n < 128) {
                HudUtil.drawTexturedModalRect(n3, n2, n, 0, 65, 12, -100);
            } else {
                HudUtil.drawTexturedModalRect(n3, n2, n - 128, 12, 65, 12, -100);
            }
        }
        this.minecraft.fontRendererObj.drawString("|", n3 + 32, n2 + 1, this.markerColor.getColorValue());
        this.minecraft.fontRendererObj.drawString("|\u00a7r", n3 + 32, n2 + 5, this.markerColor.getColorValue());
    }
}
