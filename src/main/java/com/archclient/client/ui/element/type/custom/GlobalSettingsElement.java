package com.archclient.client.ui.element.type.custom;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;

import net.minecraft.util.ResourceLocation;

import com.archclient.client.ui.element.AbstractModulesGuiElement;
import com.archclient.client.ui.element.AbstractScrollableElement;
import com.archclient.client.ui.module.ACModulesGui;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;

public class GlobalSettingsElement
        extends AbstractModulesGuiElement {
    private final int lIIIIlIIllIIlIIlIIIlIIllI;
    private final AbstractScrollableElement IllIIIIIIIlIlIllllIIllIII;
    private int lIIIIllIIlIlIllIIIlIllIlI = 0;
    private ResourceLocation IlllIllIlIIIIlIIlIIllIIIl = Ref.getInstanceCreator().createResourceLocation("client/icons/right.png");

    public GlobalSettingsElement(AbstractScrollableElement lllIllIllIlIllIlIIllllIIl2, int n, float f) {
        super(f);
        this.IllIIIIIIIlIlIllllIIllIII = lllIllIllIlIllIlIIllllIIl2;
        this.lIIIIlIIllIIlIIlIIIlIIllI = n;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        boolean bl = this.isMouseInside(mouseX, mouseY);
        int n3 = 75;
        Ref.modified$drawRect(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, 0x2F2F2F2F);
        float f2 = ACModulesGui.getSmoothFloat(790);
        if (bl) {
            if (this.lIIIIllIIlIlIllIIIlIllIlI < n3) {
                this.lIIIIllIIlIlIllIIIlIllIlI = (int)((float)this.lIIIIllIIlIlIllIIIlIllIlI + f2);
                if (this.lIIIIllIIlIlIllIIIlIllIlI > n3) {
                    this.lIIIIllIIlIlIllIIIlIllIlI = n3;
                }
            }
        } else if (this.lIIIIllIIlIlIllIIIlIllIlI > 0) {
            this.lIIIIllIIlIlIllIIIlIllIlI = (float)this.lIIIIllIIlIlIllIIIlIllIlI - f2 < 0.0f ? 0 : (int)((float)this.lIIIIllIIlIlIllIIIlIllIlI - f2);
        }
        if (this.lIIIIllIIlIlIllIIIlIllIlI > 0) {
            float f3 = (float)this.lIIIIllIIlIlIllIIIlIllIlI / (float)n3 * (float)100;
            Ref.modified$drawRect(this.x, (int)((float)this.y + ((float)this.height - (float)this.height * f3 / (float)100)), this.x + this.width, this.y + this.height, this.lIIIIlIIllIIlIIlIIIlIIllI);
        }
        if (ArchClient.getInstance().globalSettings.isDarkMode()) {
            Ref.getGlBridge().bridge$color(1f, 1f, 1f, 0.35f);
        } else {
            Ref.getGlBridge().bridge$color(0f, 0f, 0f, 0.35f);
        }
        RenderUtil.drawIcon(this.IlllIllIlIIIIlIIlIIllIIIl, 2.2f * 1.1363636f, (float)(this.x + 6), (float)this.y + (float)6);
        FontRegistry.getPlayBold18px().drawString("ArchClient Settings".toUpperCase(), (float)this.x + 14f, (float)this.y + 3f, ArchClient.getInstance().globalSettings.isDarkMode() ? -1 : -818991313);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
    }
}
