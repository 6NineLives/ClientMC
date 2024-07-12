package com.archclient.client.ui.element.type;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;

import net.minecraft.util.ResourceLocation;

import com.archclient.client.config.Setting;
import com.archclient.client.ui.element.AbstractModulesGuiElement;
import com.archclient.client.ui.module.ACModulesGui;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;

public class ToggleElement
        extends AbstractModulesGuiElement {
    private Setting setting;
    private ResourceLocation leftArrow = Ref.getInstanceCreator().createResourceLocation("client/icons/left.png");
    private ResourceLocation rightArrow = Ref.getInstanceCreator().createResourceLocation("client/icons/right.png");
    private int IlllIllIlIIIIlIIlIIllIIIl = 0;
    private float IlIlllIIIIllIllllIllIIlIl = 0.0f;
    private String displayString;

    public ToggleElement(Setting aCSetting, float f) {
        super(f);
        this.setting = aCSetting;
        this.height = 12;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        boolean rightHovered = (float) mouseX > (float) (this.x + this.width - 48) * this.scale && (float) mouseX < (float) (this.x + this.width - 10) * this.scale && (float) mouseY > (float) (this.y + this.yOffset) * this.scale && (float) mouseY < (float) (this.y + 10 + this.yOffset) * this.scale;
        boolean leftHovered = (float) mouseX > (float) (this.x + this.width - 92) * this.scale && (float) mouseX < (float) (this.x + this.width - 48) * this.scale && (float) mouseY > (float) (this.y + this.yOffset) * this.scale && (float) mouseY < (float) (this.y + 10 + this.yOffset) * this.scale;
        boolean dark = ArchClient.getInstance().globalSettings.isDarkMode();
        FontRegistry.getUbuntuMedium16px().drawString(this.setting.getLabel().toUpperCase(), this.x + 10, (float) (this.y + 2), dark ? -1 : (leftHovered || rightHovered ? -1090519040 : -1895825408));
        if (this.IlllIllIlIIIIlIIlIIllIIIl == 0) {
            FontRegistry.getUbuntuMedium16px().drawCenteredString(this.setting.<Boolean>value() ? "ON" : "OFF", this.x + this.width - 48, this.y + 2, dark ? -1 : -1895825408);
        } else {
            boolean bl3 = this.IlllIllIlIIIIlIIlIIllIIIl == 1;
            FontRegistry.getUbuntuMedium16px().drawCenteredString(this.displayString, (float) (this.x + this.width - 48) - (bl3 ? -this.IlIlllIIIIllIllllIllIIlIl : this.IlIlllIIIIllIllllIllIIlIl), this.y + 2, dark ? -1 : -1895825408);
            if (bl3) {
                FontRegistry.getUbuntuMedium16px().drawCenteredString(this.setting.<Boolean>value() ? "ON" : "OFF", (float) (this.x + this.width - 98) + this.IlIlllIIIIllIllllIllIIlIl, this.y + 2, dark ? -1 : -1895825408);
            } else {
                FontRegistry.getUbuntuMedium16px().drawCenteredString(this.setting.<Boolean>value() ? "ON" : "OFF", (float) (this.x + this.width + 2) - this.IlIlllIIIIllIllllIllIIlIl, this.y + 2, dark ? -1 : -1895825408);
            }
            if (this.IlIlllIIIIllIllllIllIIlIl >= (float) 50) {
                this.IlllIllIlIIIIlIIlIIllIIIl = 0;
                this.IlIlllIIIIllIllllIllIIlIl = 0.0f;
            } else {
                float f2 = ACModulesGui.getSmoothFloat((float) 50 + this.IlIlllIIIIllIllllIllIIlIl * (float) 15);
                this.IlIlllIIIIllIllllIllIIlIl = this.IlIlllIIIIllIllllIllIIlIl + f2 >= (float) 50 ? (float) 50 : (this.IlIlllIIIIllIllllIllIIlIl += f2);
            }
            Ref.modified$drawRect(this.x + this.width - 130, this.y + 2, this.x + this.width - 72, this.y + 12, dark ? -13619151 : -723724);
            Ref.modified$drawRect(this.x + this.width - 22, this.y + 2, this.x + this.width + 4, this.y + 12, dark ? -13619151 : -723724);
        }
        float alphaLeft = dark ? (leftHovered ? 0.7174193f : 1.0f) : (leftHovered ? 0.74000007f * 1.081081f : 0.288f * 1.5625f);
        float alphaRight = dark ? (rightHovered ? 0.7174193f : 1.0f) : (rightHovered ? 0.74000007f * 1.081081f : 0.288f * 1.5625f);
        Ref.getGlBridge().bridge$color(dark ? 1f : 0f, dark ? 1f : 0f, dark ? 1f : 0f, alphaLeft);
        RenderUtil.drawIcon(this.leftArrow, (float) 4, (float) (this.x + this.width - 82), (float) (this.y + 3));
        Ref.getGlBridge().bridge$color(dark ? 1f : 0f, dark ? 1f : 0f, dark ? 1f : 0f, alphaRight);
        RenderUtil.drawIcon(this.rightArrow, (float) 4, (float) (this.x + this.width - 22), (float) (this.y + 3));
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        boolean bl;
        boolean bl2 = (float) mouseX > (float) (this.x + this.width - 48) * this.scale && (float) mouseX < (float) (this.x + this.width - 10) * this.scale && (float) mouseY > (float) (this.y + this.yOffset) * this.scale && (float) mouseY < (float) (this.y + 10 + this.yOffset) * this.scale;
        bl = (float) mouseX > (float) (this.x + this.width - 92) * this.scale && (float) mouseX < (float) (this.x + this.width - 48) * this.scale && (float) mouseY > (float) (this.y + this.yOffset) * this.scale && (float) mouseY < (float) (this.y + 10 + this.yOffset) * this.scale;
        if ((bl || bl2) && this.IlllIllIlIIIIlIIlIIllIIIl == 0) {
            this.IlllIllIlIIIIlIIlIIllIIIl = bl ? 1 : 2;
            this.IlIlllIIIIllIllllIllIIlIl = 0.0f;
            this.displayString = this.setting.<Boolean>value() ? "ON" : "OFF";
            Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            this.setting.setValue(!this.setting.<Boolean>value());
            if (this.setting == ArchClient.getInstance().moduleManager.keyStrokes.replaceNamesWithArrows) {
                ArchClient.getInstance().moduleManager.keyStrokes.initialize();
            } else if (this.setting == ArchClient.getInstance().globalSettings.enableTeamView && !ArchClient.getInstance().globalSettings.enableTeamView.<Boolean>value()) {
                ArchClient.getInstance().globalSettings.enableTeamView.setValue(false);
            }
        }
    }
}