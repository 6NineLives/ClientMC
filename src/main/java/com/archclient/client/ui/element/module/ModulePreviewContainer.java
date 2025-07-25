package com.archclient.client.ui.element.module;

import com.archclient.main.ArchClient;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.ui.element.AbstractScrollableElement;
import com.archclient.client.ui.util.RenderUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ModulePreviewContainer
        extends AbstractScrollableElement {
    private final List<ModulePreviewElement> elements = new ArrayList<>();

    public ModulePreviewContainer(float f, int n, int n2, int n3, int n4) {
        super(f, n, n2, n3, n4);
        for (AbstractModule module : ArchClient.getInstance().moduleManager.modules) {
            if (module == ArchClient.getInstance().moduleManager.notifications)
                continue;
            ModulePreviewElement element = new ModulePreviewElement(this, module, f);
            this.elements.add(element);
        }
    }

    @Override
    public boolean lIIIIlIIllIIlIIlIIIlIIllI(AbstractModule aCModule) {
        return false;
    }

    @Override
    public void lIIIIIIIIIlIllIIllIlIIlIl(AbstractModule aCModule) {
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        super.handleDrawElement(mouseX, mouseY, partialTicks);
        RenderUtil.drawRoundedRect(this.x, this.y, this.x + this.width, this.y + this.height + 2, (double)8, ArchClient.getInstance().globalSettings.isDarkMode() ? -13619151 : -657930);
        this.preDraw(mouseX, mouseY);
        int n3 = 0;
        int n4 = 0;
        for (ModulePreviewElement element : this.elements) {
            element.yOffset = this.lIIIIllIIlIlIllIIIlIllIlI;
            element.setDimensions(this.x + 4 + n3 * 120, this.y + 4 + n4 * 112, 116, 108);
            element.handleDrawElement(mouseX, mouseY, partialTicks);
            if (++n3 != 3) continue;
            ++n4;
            n3 = 0;
        }
        this.IlllIllIlIIIIlIIlIIllIIIl = 4 + n4 * 112;
        this.postDraw(mouseX, mouseY);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        super.handleMouseClick(mouseX, mouseY, button);
        for (ModulePreviewElement element : this.elements) {
            if (!element.isMouseInside(mouseX, mouseY)) continue;
            element.handleMouseClick(mouseX, mouseY, button);
        }
    }
}
