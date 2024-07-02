package com.archclient.client.ui.mainmenu.element;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.ui.fading.ColorFade;
import com.archclient.client.ui.mainmenu.AbstractElement;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;
import net.minecraft.util.ResourceLocation;

public class IconButtonElement extends AbstractElement {
    private ResourceLocation icon;
    public void setIcon(ResourceLocation icon) {
        this.icon = icon;
    }
    private String text;
    public void setText(String text) {
        this.text = text;
    }
    private boolean usesText;
    private final ColorFade outlineFade;
    private final ColorFade upperBackgroundFade;
    private final ColorFade lowerBackgroundFade;
    private float iconSize = 4;

    public IconButtonElement(ResourceLocation icon) {
        this.icon = icon;
        this.outlineFade = new ColorFade(0x4FFFFFFF, 0xAF50A05C);
        this.upperBackgroundFade = new ColorFade(0x1A858585, 0x3F64B96E);
        this.lowerBackgroundFade = new ColorFade(0x1A858585, 0x3F55A562);
    }

    public IconButtonElement(float iconSize, ResourceLocation icon) {
        this.icon = icon;
        this.iconSize = iconSize;
        this.outlineFade = new ColorFade(0x4FFFFFFF, 0xAF50A05C);
        this.upperBackgroundFade = new ColorFade(0x1A858585, 0x3F64B96E);
        this.lowerBackgroundFade = new ColorFade(0x1A858585, 0x3F55A562);
    }

    public IconButtonElement(String string) {
        this.text = string;
        this.usesText = true;
        this.outlineFade = new ColorFade(0x4FFFFFFF, 0xAF50A05C);
        this.upperBackgroundFade = new ColorFade(0x1A858585, 0x3F64B96E);
        this.lowerBackgroundFade = new ColorFade(0x1A858585, 0x3F55A562);
    }

    @Override
    protected void handleElementDraw(float mouseX, float mouseY, boolean enableMouse) {
        boolean useSecondary = enableMouse && this.isMouseInside(mouseX, mouseY);
        RenderUtil.drawCorneredGradientRectWithOutline(this.x, this.y,
                this.x + this.width, this.y + this.height,
                this.outlineFade.get(useSecondary),
                this.upperBackgroundFade.get(useSecondary),
                this.lowerBackgroundFade.get(useSecondary));
        if (this.usesText) {
            FontRegistry.getRobotoRegular13px().drawString(this.text,
                    this.x + this.width / 2f, this.y + 2f, -1);
        } else {
            Ref.getGlBridge().bridge$color(1f, 1f, 1f, .8f);
            RenderUtil.drawIcon(this.icon, this.iconSize,
                    this.x + this.width / 2f - this.iconSize, this.y + this.height / 2f - this.iconSize);
        }
    }

    @Override
    public boolean handleElementMouseClicked(float mouseX, float mouseY, int mouseButton, boolean enableMouse) {
        return false;
    }
}
