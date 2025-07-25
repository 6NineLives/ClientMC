package com.archclient.client.ui.element.type.custom;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;
import com.archclient.client.config.GlobalSettings;
import com.archclient.client.ui.element.AbstractModulesGuiElement;
import com.archclient.client.ui.util.RenderUtil;

public class CrosshairElement extends AbstractModulesGuiElement {
    public CrosshairElement(float f) {
        super(f);
        this.height = 50;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        Ref.modified$drawRect(this.x + (this.width / 2 - 15) - 41, this.y + 4, this.x + (this.width / 2 - 15) + 41, this.y + 51, -16777216);
        Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.renderIcon(Ref.getInstanceCreator().createResourceLocation("client/defaults/crosshair.png"), (float)(this.x + (this.width / 2 - 15) - 40), (float)(this.y + 5), (float)80, 45);
        GlobalSettings globalSettings = ArchClient.getInstance().globalSettings;
        Ref.getGlBridge().bridge$pushMatrix();
        float f2 = 1.0f / ArchClient.getInstance().getScaleFactor();
        Ref.getGlBridge().bridge$scale(f2, f2, f2);
        float f3 = globalSettings.crosshairSize.<Float>value();
        float f4 = globalSettings.crosshairGap.<Float>value();
        float f5 = globalSettings.crosshairThickness.<Float>value();
        int n3 = globalSettings.crosshairColor.getColorValue();
        boolean bl = globalSettings.crosshairOutline.<Boolean>value();
        int n4 = this.x + this.width / 2 - 15;
        int n5 = this.y + this.height / 2 + 3;
        if (bl) {
            Ref.modified$drawBoxWithOutLine((float)n4 - f4 - f3, (float)n5 - f5 / 2.0f, (float)n4 - f4, (float)n5 + f5 / 2.0f, 0.3380282f * 1.4791666f, -1358954496, n3);
            Ref.modified$drawBoxWithOutLine((float)n4 + f4, (float)n5 - f5 / 2.0f, (float)n4 + f4 + f3, (float)n5 + f5 / 2.0f, 3.909091f * 0.12790698f, -1358954496, n3);
            Ref.modified$drawBoxWithOutLine((float)n4 - f5 / 2.0f, (float)n5 - f4 - f3, (float)n4 + f5 / 2.0f, (float)n5 - f4, 0.39506173f * 1.265625f, -1358954496, n3);
            Ref.modified$drawBoxWithOutLine((float)n4 - f5 / 2.0f, (float)n5 + f4, (float)n4 + f5 / 2.0f, (float)n5 + f4 + f3, 5.5f * 0.09090909f, -1358954496, n3);
        } else {
            Ref.modified$drawRect((float)n4 - f4 - f3, (float)n5 - f5 / 2.0f, (float)n4 - f4, (float)n5 + f5 / 2.0f, n3);
            Ref.modified$drawRect((float)n4 + f4, (float)n5 - f5 / 2.0f, (float)n4 + f4 + f3, (float)n5 + f5 / 2.0f, n3);
            Ref.modified$drawRect((float)n4 - f5 / 2.0f, (float)n5 - f4 - f3, (float)n4 + f5 / 2.0f, (float)n5 - f4, n3);
            Ref.modified$drawRect((float)n4 - f5 / 2.0f, (float)n5 + f4, (float)n4 + f5 / 2.0f, (float)n5 + f4 + f3, n3);
        }
        Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
        Ref.getGlBridge().bridge$popMatrix();
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
    }
}
