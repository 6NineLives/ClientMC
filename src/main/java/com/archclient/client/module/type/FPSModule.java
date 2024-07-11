package com.archclient.client.module.type;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.config.Setting;
import com.archclient.client.event.type.GuiDrawEvent;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.ui.module.ACGuiAnchor;

import net.minecraft.client.Minecraft;

public class FPSModule extends AbstractModule {

    private final Setting showBackground;
    private final Setting textColor;
    private final Setting backgroundColor;

    public FPSModule() {
        super("FPS");
        this.setDefaultAnchor(ACGuiAnchor.MIDDLE_TOP);
        this.setDefaultTranslations(0.0f, 0.0f);
        this.setDefaultState(false);
        this.showBackground = new Setting(this, "Show Background").setValue(true);
        this.textColor = new Setting(this, "Text Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.backgroundColor = new Setting(this, "Background Color").setValue(0x6F000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.setPreviewLabel("[144 FPS]", 1.6978723f * 0.8245614f);
        this.addEvent(GuiDrawEvent.class, this::onRender);
    }
    private void onRender(GuiDrawEvent drawEvent) {
        if (!this.isRenderHud()) {
            return;
        }
        Ref.getGlBridge().bridge$pushMatrix();
        this.scaleAndTranslate(drawEvent.getResolution());
        if (this.showBackground.<Boolean>value()) {
            this.setDimensions(56, 18);
            Ref.modified$drawRect(0.0f, 0.0f, 56, 13, this.backgroundColor.getColorValue());
            String string = Minecraft.getDebugFPS() + " FPS";
            this.minecraft.fontRendererObj.drawString(string, (int)(this.width / 2.0f - (float)(this.minecraft.fontRendererObj.getStringWidth(string) / 2)), 3, this.textColor.getColorValue());
        } else {
            String string = "[" + Minecraft.getDebugFPS() + " FPS]";
            this.minecraft.fontRendererObj.drawStringWithShadow(string, (int)(this.width / 2.0f - (float)(this.minecraft.fontRendererObj.getStringWidth(string) / 2)), 3, this.textColor.getColorValue());
            this.setDimensions(this.minecraft.fontRendererObj.getStringWidth(string), 18);
        }
        Ref.getGlBridge().bridge$popMatrix();
    }
}
