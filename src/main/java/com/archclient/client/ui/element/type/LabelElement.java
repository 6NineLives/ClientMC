package com.archclient.client.ui.element.type;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;
import com.archclient.client.config.Setting;
import com.archclient.client.ui.element.AbstractModulesGuiElement;
import com.archclient.client.ui.util.font.FontRegistry;

public class LabelElement
        extends AbstractModulesGuiElement {
    private final Setting settingObject;

    public LabelElement(Setting aCSetting, float f) {
        super(f);
        this.settingObject = aCSetting;
        this.height = 12;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        FontRegistry.getUbuntuMedium16px().drawString(this.settingObject.<String>value().toUpperCase(),
                this.x + 2f, this.y + 2f,
                ArchClient.getInstance().globalSettings.isDarkMode() ? -1 : 0x6F000000);
        Ref.modified$drawRect(this.x + 2f, this.y + this.height - 1f, this.x + this.width / 2f - 20f,
                this.y + this.height,
                ArchClient.getInstance().globalSettings.isDarkMode() ? -14211288 : 0x1F2F2F2F);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
    }
}