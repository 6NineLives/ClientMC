package com.archclient.client.ui.element.module;

import com.archclient.bridge.ref.Ref;
import com.archclient.bridge.util.EnumChatFormattingBridge;
import com.archclient.main.ArchClient;

import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerFontRenderer;

import com.archclient.client.config.Setting;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.ui.element.AbstractModulesGuiElement;
import com.archclient.client.ui.element.AbstractScrollableElement;
import com.archclient.client.ui.module.ACModulePlaceGui;
import com.archclient.client.ui.module.ACModulesGui;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;

import java.util.Objects;

public class ModulePreviewElement extends AbstractModulesGuiElement {
    public final AbstractModule module;
    private final ModulesGuiButtonElement optionsButton;
    private final ModulesGuiButtonElement toggleOrHideFromHud;
    private final ModulesGuiButtonElement toggle;
    private final AbstractScrollableElement IlIlllIIIIllIllllIllIIlIl;

    public ModulePreviewElement(AbstractScrollableElement parent, AbstractModule module, float f) {
        super(f);
        this.module = module;
        this.IlIlllIIIIllIllllIllIIlIl = parent;
        EaglerFontRenderer optionsFontRenderer = FontRegistry.getPlayBold18px();
        EaglerFontRenderer hideOrToggleFontRenderer = FontRegistry.getPlayRegular14px();
        this.optionsButton = new ModulesGuiButtonElement(optionsFontRenderer, null, "Options", this.x + 4, this.y + this.height - 20, this.x + this.width - 4, this.y + this.height - 6, -12418828, f);
        this.toggleOrHideFromHud = new ModulesGuiButtonElement(hideOrToggleFontRenderer, null, module.getGuiAnchor() == null ? (module.isRenderHud() ? "Disable" : "Enable") : (module.isRenderHud() ? "Hide from HUD" : "Add to HUD"), this.x + 4, this.y + this.height - 38, this.x + this.width / 2 - 2, this.y + this.height - 24, module.isRenderHud() ? -5756117 : -13916106, f);
        //XXXthis.toggleOrHideFromHud.setField1(module != ArchClient.getInstance().moduleManager.notifications);
        this.toggle = new ModulesGuiButtonElement(hideOrToggleFontRenderer, null, module.isEnabled() ? "Disable" : "Enable", this.x + this.width / 2 + 2, this.y + this.height - 38, this.x + this.width - 4, this.y + this.height - 24, module.isEnabled() ? -5756117 : -13916106, f);
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        float f2;
        Object object;
        if (this.module.isEnabled()) {
            Ref.modified$drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -13916106);
        } else {
            Ref.modified$drawRect(this.x, this.y, this.x + this.width, this.y + this.height, ArchClient.getInstance().globalSettings.isDarkMode() ? -14211289 : -1347374928);
        }
        EaglerFontRenderer playBold18px = FontRegistry.getPlayBold18px();
        Ref.getGlBridge().bridge$pushMatrix();
        int n3 = 0;
        int n4 = 0;
        /*XXX
        if (this.module == ArchClient.getInstance().moduleManager.armourStatus) {
            n3 = -10;
            object = "329/329";
            f2 = Ref.getMinecraft().fontRendererObj.getStringWidth((String)object);
            Ref.getMinecraft().fontRendererObj.drawStringWithShadow((String)object, (int)((float)(this.x + 1 + this.width / 2) - f2 / 2.0f), this.y + this.height / 2 - 18, -1);
        } else if (this.module == ArchClient.getInstance().moduleManager.potionStatus) {
            n4 = -30;
            Ref.getMinecraft().fontRendererObj.drawStringWithShadow("Speed II", this.x + 8 + this.width / 2 - 20, this.y + this.height / 2 - 36, -1);
            Ref.getMinecraft().fontRendererObj.drawStringWithShadow("0:42", this.x + 8 + this.width / 2 - 20, this.y + this.height / 2 - 26, -1);
        } else if (this.module == ArchClient.getInstance().moduleManager.scoreboard) {
            Ref.modified$drawRect(this.x + 20, this.y + this.height / 2f - 44, this.x + this.width - 20, this.y + this.height / 2 - 6, 0x6F000000);
            Ref.getMinecraft().fontRendererObj.drawString("Score", this.x + this.width / 2, this.y + this.height / 2 - 40, -1);
            Ref.getMinecraft().fontRendererObj.drawStringWithShadow("Steve", this.x + 24, this.y + this.height / 2 - 28, -1);
            Ref.getMinecraft().fontRendererObj.drawStringWithShadow("Alex", this.x + 24, this.y + this.height / 2 - 18, -1);
            Ref.getMinecraft().fontRendererObj.drawString(EnumChatFormattingBridge.RED + "0", this.x + this.width - 26, this.y + this.height / 2 - 18, -1);
            Ref.getMinecraft().fontRendererObj.drawString(EnumChatFormattingBridge.RED + "1", this.x + this.width - 26, this.y + this.height / 2 - 28, -1);
        }
        if (this.module == ArchClient.getInstance().moduleManager.cooldowns) {
            object = new CooldownRenderer("EnderPearl", 368, 9000L);
            ((CooldownRenderer)object).lIIIIlIIllIIlIIlIIIlIIllI(ArchClient.getInstance().moduleManager.cooldowns.colorTheme, this.x + this.width / 2 - 18, this.y + this.height / 2 - 26 - 18, -1);
        } else if ((this.module.getPreviewType() == null || this.module.getPreviewType() == AbstractModule.PreviewType.LABEL) && this.module != ArchClient.getInstance().moduleManager.scoreboard) {
            object = "";
            if (this.module.getPreviewType() == null) {
                f2 = 2.0f;
                for (String string : this.module.getName().split(" ")) {
                    String string2 = string.substring(0, 1);
                    object = (String)object + (Objects.equals(object, "") ? string2 : string2.toLowerCase());
                }
            } else {
                f2 = this.module.getPreviewLabelSize();
                object = this.module.getPreviewLabel();
            }
            Ref.getGlBridge().bridge$scale(f2, f2, f2);
            float f3 = (float)Ref.getMinecraft().fontRendererObj.getStringWidth((String)object) * f2;
            if (this.module.getPreviewType() == null) {
                Ref.getMinecraft().fontRendererObj.drawString((String)object, (int)(((float)(this.x + 1 + this.width / 2) - f3 / 2.0f) / f2), (int)((float)(this.y + this.height / 2 - 32) / f2), -13750738);
            } else {
                Ref.getMinecraft().fontRendererObj.drawStringWithShadow((String)object, (int)(((float)(this.x + 1 + this.width / 2) - f3 / 2.0f) / f2), (int)((float)(this.y + this.height / 2 - 32) / f2), -1);
            }
        } else if (this.module.getPreviewType() == AbstractModule.PreviewType.ICON) {
            float f4 = this.module.getPreviewIconWidth();
            f2 = this.module.getPreviewIconHeight();
            Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
            RenderUtil.renderIcon(this.module.getPreviewIcon(), (float)(this.x + this.width / 2) - f4 / 2.0f + (float)n4, (float)(this.y + n3 + this.height / 2 - 26) - f2 / 2.0f, f4, f2);
        }
        */
        Ref.getGlBridge().bridge$popMatrix();
        float moduleNameOffset = this.y + this.height / 2f;
        playBold18px.drawCenteredString(this.module.getName(), (float)(this.x + this.width / 2) - 1.0681819f * 0.46808508f, moduleNameOffset + 1, 0x5F000000);
        playBold18px.drawCenteredString(this.module.getName(), (float)(this.x + this.width / 2) - 1.125f * 1.3333334f, moduleNameOffset, -1);
        this.toggle.text = this.module.isEnabled() ? "Disable" : "Enable";
        this.toggle.yOffset = this.yOffset;
        int n5 = this.toggle.highlightColor = this.module.isEnabled() ? -5756117 : -13916106;
        this.toggleOrHideFromHud.text = this.module.getGuiAnchor() == null ? (this.module.isRenderHud() && this.module.isEnabled() ? "Disable" : "Enable") : (this.module.isRenderHud() && this.module.isEnabled() ? "Hide from HUD" : "Add to HUD");
        this.toggleOrHideFromHud.highlightColor = this.module.isRenderHud() && this.module.isEnabled() ? -5756117 : -13916106;
        this.optionsButton.setDimensions(this.x + 4, this.y + this.height - 20, this.width - 8, 16);
        this.optionsButton.yOffset = this.yOffset;
        this.optionsButton.handleDrawElement(mouseX, mouseY, partialTicks);
        this.toggleOrHideFromHud.setDimensions(this.x + 4, this.y + this.height - 38, this.module.isEditable ? this.width - 8 : this.width / 2 + 2, this.y + this.height - 24 - (this.y + this.height - 38));
        this.toggleOrHideFromHud.yOffset = this.yOffset;
        this.toggleOrHideFromHud.handleDrawElement(mouseX, mouseY, partialTicks);
        if (!this.module.isEditable) {
            this.toggle.setDimensions(this.x + this.width / 2 + 8, this.y + this.height - 38, this.width / 2 - 12, this.y + this.height - 24 - (this.y + this.height - 38));
            this.toggle.handleDrawElement(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        if (this.optionsButton.isMouseInside(mouseX, mouseY)) {
            Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            ((ModuleListElement) ACModulesGui.instance.settingsElement).llIlIIIlIIIIlIlllIlIIIIll = false;
            ((ModuleListElement)ACModulesGui.instance.settingsElement).scrollable = this.IlIlllIIIIllIllllIllIIlIl;
            ((ModuleListElement)ACModulesGui.instance.settingsElement).module = this.module;
            ACModulesGui.instance.currentScrollableElement = ACModulesGui.instance.settingsElement;
        } else if (!this.module.isEditable && this.toggle.isMouseInside(mouseX, mouseY)) {
            Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            this.module.setState(!this.module.isEnabled());
            this.toggle.text = this.module.isEnabled() ? "Disable" : "Enable";
            int n4 = this.toggle.highlightColor = this.module.isEnabled() ? -5756117 : -13916106;
            if (this.module.isEnabled()) {
                this.lIIIIIIIIIlIllIIllIlIIlIl();
                this.module.setState(true);
            }
        } else if (this.toggleOrHideFromHud.field_1 && this.toggleOrHideFromHud.isMouseInside(mouseX, mouseY)) {
            Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            if (!this.module.isEnabled()) {
                this.module.setRenderHud(true);
                this.lIIIIIIIIIlIllIIllIlIIlIl();
                if (this.module.getGuiAnchor() == null) {
                    this.module.setState(true);
                } else {
                    Ref.getMinecraft().bridge$displayGuiScreen(new ACModulePlaceGui(ACModulesGui.instance, this.module));
                }
            } else {
                this.module.setRenderHud(!this.module.isRenderHud());
                if (this.module.isRenderHud()) {
                    this.lIIIIIIIIIlIllIIllIlIIlIl();
                    if (this.module.getGuiAnchor() == null) {
                        this.module.setState(true);
                    } else {
                        Ref.getMinecraft().bridge$displayGuiScreen(new ACModulePlaceGui(ACModulesGui.instance, this.module));
                    }
                } else if (this.module.isEditable && this.module.isEnabled()) {
                    this.module.setState(false);
                }
            }
            this.toggleOrHideFromHud.text = this.module.getGuiAnchor() == null ? (this.module.isRenderHud() && this.module.isEnabled() ? "Disable" : "Enable") : (this.module.isRenderHud() && this.module.isEnabled() ? "Hide from HUD" : "Add to HUD");
            this.toggleOrHideFromHud.highlightColor = this.module.isRenderHud() && this.module.isEnabled() ? -5756117 : -13916106;
        }
    }

    private void lIIIIIIIIIlIllIIllIlIIlIl() {
        if (this.module == ArchClient.getInstance().moduleManager.llIIlllIIIIlllIllIlIlllIl) {
            return;
        }
        for (Setting setting : this.module.getSettingsList()) {
            if (setting.getType() != Setting.Type.INTEGER
                    || !setting.getLabel().toLowerCase().contains("color")
                    || setting.getLabel().toLowerCase().contains("background")
                    || setting.getLabel().toLowerCase().contains("pressed")) {
                continue;
            }

            Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator()
                    .createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"),
                            1.0f));
            setting.setValue(ArchClient.getInstance().globalSettings.defaultColor.value());
        }
    }
}
