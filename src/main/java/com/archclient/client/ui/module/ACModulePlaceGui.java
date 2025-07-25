package com.archclient.client.ui.module;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.minecraft.client.gui.ScaledResolution;

public class ACModulePlaceGui extends ACModulesGui {
    private final AbstractModule module;
    private final ACModulesGui modulesGui;

    public ACModulePlaceGui(ACModulesGui modulesGui, AbstractModule aCModule) {
        aCModule.setState(true);
        this.module = aCModule;
        this.modulesGui = modulesGui;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float delta) {
        this.drawDefaultBackground();
        RenderUtil.drawRoundedRect(0.0, this.height / 3f, this.width, (float)(this.height / 3) + 2.1086957f * 0.23711339f, 0.0, 0x6F000000);
        RenderUtil.drawRoundedRect(0.0, this.height / 3f * 2, this.width, (float)(this.height / 3 * 2) + 1.1388888f * 0.43902442f, 0.0, 0x6F000000);
        RenderUtil.drawRoundedRect(this.width / 3f, 0.0, (float)(this.width / 3) + 0.42073172f * 1.1884058f, this.height, 0.0, 0x6F000000);
        RenderUtil.drawRoundedRect(this.width / 3f * 2, 0.0, (float)(this.width / 3 * 2) + 0.28070176f * 1.78125f, this.height, 0.0, 0x6F000000);
        RenderUtil.drawRoundedRect(this.width / 3f + this.width / 6f, this.height / 3f * 2, (float)(this.width / 3 + this.width / 6) + 6.7000003f * 0.07462686f, this.height, 0.0, 0x6F000000);
        float f2 = 1.0f / ArchClient.getInstance().getScaleFactor();
        float f3 = (float)(FontRegistry.getUbuntuMedium16px().getStringWidth(this.module.getName()) + 6) * f2;
        if (this.module.width < f3) {
            this.module.width = (int)f3;
        }
        if (this.module.height < (float)18) {
            this.module.height = 18;
        }
        ScaledResolution scaledResolution = Ref.getInstanceCreator().createScaledResolution();
        float[] positions = ACAnchorHelper.getPositions(mouseX, mouseY, scaledResolution);
        ACGuiAnchor aCGuiAnchor = ACAnchorHelper.getAnchor(mouseX, mouseY, scaledResolution);
        if (aCGuiAnchor != ACGuiAnchor.MIDDLE_MIDDLE) {
            if (aCGuiAnchor == ACGuiAnchor.MIDDLE_BOTTOM_LEFT || aCGuiAnchor == ACGuiAnchor.MIDDLE_BOTTOM_RIGHT) {
                Ref.modified$drawRect(positions[0], positions[1], positions[0] + (scaledResolution.getScaledWidth() / 6), positions[1] + (scaledResolution.getScaledHeight() / 3), 0x2F000000);
            } else {
                Ref.modified$drawRect(positions[0], positions[1], positions[0] + (scaledResolution.getScaledWidth() / 3), positions[1] + (scaledResolution.getScaledHeight() / 3), 0x2F000000);
            }
        }
        int n3 = (int) scaledResolution.getScaledWidth();
        int n4 = (int) scaledResolution.getScaledHeight();
        float[] arrf2 = ACAnchorHelper.getPositions(this.module, mouseX, mouseY, scaledResolution);
        if (aCGuiAnchor != this.module.getGuiAnchor()) {
            this.module.setAnchor(aCGuiAnchor);
            this.module.setTranslations(0.0f, 0.0f);
        }
        if (!Mouse.isButtonDown(1)) {
            RenderUtil.drawRoundedRect(2, 0.0, 1.8636363192038112 * 1.3414634466171265, n4, 0.0, -15599126);
            RenderUtil.drawRoundedRect((float)n3 - 1.1197916f * 2.2325583f, 0.0, n3 - 2, n4, 0.0, -15599126);
            RenderUtil.drawRoundedRect(0.0, 2, n3, 0.4375 * 5.714285714285714, 0.0, -15599126);
            RenderUtil.drawRoundedRect(0.0, (float)n4 - 0.557971f * 6.2727275f, n3, n4 - 3, 0.0, -15599126);
        }
        float f4 = (float)mouseX - positions[0] - arrf2[0];
        float f5 = (float)mouseY - positions[1] - arrf2[1];
        if (!Mouse.isButtonDown(1)) {
            float[] scaledPoints = this.module.getScaledPoints(scaledResolution, false);
            f4 = this.getXTranslation(this.module, f4, scaledPoints, (float)((int)(this.module.width * this.module.scale.<Float>value())));
            f5 = this.getYTranslation(this.module, f5, scaledPoints, (float)((int)(this.module.height * this.module.scale.<Float>value())));
        }
        this.module.setTranslations(f4, f5);
        Ref.getGlBridge().bridge$pushMatrix();
        this.module.scaleAndTranslate(scaledResolution);
        RenderUtil.drawRoundedRect(-2, -2, this.module.width + 2.0f, this.module.height + 2.0f, 4, 551805923);
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.getGlBridge().bridge$scale(f2, f2, f2);
        FontRegistry.getUbuntuMedium16px().drawString(this.module.getName(), 0.0f, -1f, 0x6F000000); // 0x6F000000
        Ref.getGlBridge().bridge$popMatrix();
        Ref.getGlBridge().bridge$popMatrix();
    }

    private float getXTranslation(AbstractModule aCModule, float f, float[] arrf, float f2) {
        if (f + arrf[0] < 3f) {
            f = -arrf[0] + 3f;
        } else if (f + arrf[0] * aCModule.scale.<Float>value() + f2 > (this.width - 3f)) {
            f = (int)((float)this.width - arrf[0] * aCModule.scale.<Float>value() - f2 - 3f);
        }
        return f;
    }

    private float getYTranslation(AbstractModule aCModule, float f, float[] arrf, float f2) {
        if (f + arrf[1] < 2f) {
            f = -arrf[1] + 2f;
        } else if (f + arrf[1] * aCModule.scale.<Float>value() + f2 > (this.height - 2f)) {
            f = (int)((float)this.height - arrf[1] * aCModule.scale.<Float>value() - f2 - 2f);
        }
        return f;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton != 0) {
            return;
        }
        ScaledResolution scaledResolution = Ref.getInstanceCreator().createScaledResolution();
        ACGuiAnchor aCGuiAnchor = ACAnchorHelper.getAnchor(mouseX, mouseY, scaledResolution);
        this.module.setAnchor(aCGuiAnchor);
        Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
        this.module.setState(true);
        ACModulesGui modulesGui = new ACModulesGui();
        this.mc.bridge$displayGuiScreen(modulesGui);
        modulesGui.currentScrollableElement = modulesGui.modulesElement;
        modulesGui.currentScrollableElement.lIIlIlIllIIlIIIlIIIlllIII = false;
        modulesGui.currentScrollableElement.lIIIIllIIlIlIllIIIlIllIlI = this.modulesGui.modulesElement.lIIIIllIIlIlIllIIIlIllIlI;
        modulesGui.currentScrollableElement.yOffset = 0;
    }

}
