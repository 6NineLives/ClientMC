package com.archclient.client.ui.module;

import com.archclient.bridge.ref.Ref;
import com.archclient.bridge.util.EnumChatFormattingBridge;
import com.archclient.bridge.wrapper.ACGuiScreen;
import com.archclient.main.ArchClient;
import com.archclient.client.config.Profile;
import com.archclient.client.ui.element.profile.ProfileElement;
import com.archclient.client.ui.element.profile.ProfilesListElement;
import com.archclient.client.ui.util.font.FontRegistry;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.lax1dude.eaglercraft.v1_8.internal.vfs2.VFile2;
import net.minecraft.client.gui.GuiTextField;

public class ACProfileCreateGui extends ACGuiScreen {
    private final ACGuiScreen guiScreen;
    private final float IlllIIIlIlllIllIlIIlllIlI;
    private final int IIIIllIlIIIllIlllIlllllIl;
    private final ProfilesListElement parent;
    private GuiTextField textField = null;
    private String IlIlIIIlllIIIlIlllIlIllIl = "";
    private boolean IIIllIllIlIlllllllIlIlIII = false;
    private Profile profile;

    public ACProfileCreateGui(Profile profile, ACGuiScreen guiScreen, ProfilesListElement parent, int n, float f) {
        this(guiScreen, parent, n, f);
        this.profile = profile;
    }

    public ACProfileCreateGui(ACGuiScreen guiScreen, ProfilesListElement parent, int n, float f) {
        this.guiScreen = guiScreen;
        this.IlllIIIlIlllIllIlIIlllIlI = f;
        this.parent = parent;
        this.IIIIllIlIIIllIlllIlllllIl = n;
        this.IIIllIllIlIlllllllIlIlIII = true;
    }

    @Override
    public void updateScreen() {
        this.textField.updateCursorCounter();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);

        // setWorldAndResolution stuff
        this.mc = Ref.getMinecraft();
        this.fontRendererObj = this.mc.fontRendererObj;
        this.width = (int) Ref.getInstanceCreator().createScaledResolution().getScaledWidth();
        this.height = (int) Ref.getInstanceCreator().createScaledResolution().getScaledHeight();
        this.buttonList.clear();
        this.setExternalValues();

        if (!this.IIIllIllIlIlllllllIlIlIII) {
            this.mc.bridge$displayGuiScreen(this.guiScreen);
            ((ACModulesGui) this.guiScreen).currentScrollableElement = ((ACModulesGui) this.guiScreen).profilesElement;
        } else {
            this.IIIllIllIlIlllllllIlIlIII = false;
            this.textField = Ref.getInstanceCreator().createTextField(this.width / 2 - 70, this.height / 2 - 6, 140, 10);
            if (this.profile != null) {
                this.textField.setText(this.profile.getName());
            }
            this.textField.setFocused(true);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float delta) {
        this.guiScreen.drawScreen(mouseX, mouseY, delta);
        this.drawDefaultBackground();
        Ref.modified$drawRect(this.width / 2f - 73, this.height / 2f - 19, this.width / 2f + 73, this.height / 2f + 8, -11250604);
        Ref.modified$drawRect(this.width / 2f - 72, this.height / 2f - 18, this.width / 2f + 72, this.height / 2f + 7, -3881788);
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.getGlBridge().bridge$scale(this.IlllIIIlIlllIllIlIIlllIlI, this.IlllIIIlIlllIllIlIIlllIlI, this.IlllIIIlIlllIllIlIIlllIlI);
        int n3 = (int) ((float) this.width / this.IlllIIIlIlllIllIlIIlllIlI);
        int n4 = (int) ((float) this.height / this.IlllIIIlIlllIllIlIIlllIlI);
        FontRegistry.getUbuntuMedium16px().drawString("Profile Name: ", (float) (n3 / 2) - (float) 70 / this.IlllIIIlIlllIllIlIIlllIlI, (float) (n4 / 2) - (float) 17 / this.IlllIIIlIlllIllIlIIlllIlI, 0x6F000000);
        FontRegistry.getUbuntuMedium16px().drawString(this.IlIlIIIlllIIIlIlllIlIllIl, (float) (n3 / 2) - (float) 72 / this.IlllIIIlIlllIllIlIIlllIlI, (float) (n4 / 2) + (float) 8 / this.IlllIIIlIlllIllIlIIlllIlI, -1358954496);
        Ref.getGlBridge().bridge$popMatrix();
        this.textField.drawTextBox();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.textField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char c, int n) {
        switch (n) {
            case 1: {
                this.mc.bridge$displayGuiScreen(this.guiScreen);
                ((ACModulesGui) this.guiScreen).currentScrollableElement = ((ACModulesGui) this.guiScreen).profilesElement;
                break;
            }
            case 28: {
                if (this.textField.getText().length() < 3) {
                    this.IlIlIIIlllIIIlIlllIlIllIl = EnumChatFormattingBridge.RED + "Name must be at least 3 characters long.";
                    break;
                }
                if (this.textField.getText().equalsIgnoreCase("default")) {
                    this.IlIlIIIlllIIIlIlllIlIllIl = EnumChatFormattingBridge.RED + "That name is already in use.";
                    break;
                }
                if (!this.textField.getText().matches("([a-zA-Z0-9-_ \\]\\[]+)")) {
                    this.IlIlIIIlllIIIlIlllIlIllIl = EnumChatFormattingBridge.RED + "Illegal characters in name.";
                    break;
                }
                if (this.profile != null && this.profile.isEditable()) {
                    VFile2 file = new VFile2("config", "client", "profiles", this.profile.getName() + ".cfg");
                    VFile2 file2 = new VFile2("config", "client", "profiles", this.textField.getText() + ".cfg");
                    if (!file.exists()) break;
                    try {
                        file.renameTo(file2);
                        file.delete();
                        this.profile.setName(this.textField.getText());
                        this.mc.bridge$displayGuiScreen(this.guiScreen);
                        ((ACModulesGui) this.guiScreen).currentScrollableElement = ((ACModulesGui) this.guiScreen).profilesElement;
                    } catch (Exception exception) {
                        this.IlIlIIIlllIIIlIlllIlIllIl = EnumChatFormattingBridge.RED + "Could not save profile.";
                        exception.printStackTrace();
                    }
                    break;
                }
                Profile ilIIlIIlIIlllIlIIIlIllIIl = null;
                for (Profile ilIIlIIlIIlllIlIIIlIllIIl2 : ArchClient.getInstance().profiles) {
                    if (!ilIIlIIlIIlllIlIIIlIllIIl2.getName().toLowerCase().equalsIgnoreCase(this.textField.getText()))
                        continue;
                    ilIIlIIlIIlllIlIIIlIllIIl = ilIIlIIlIIlllIlIIIlIllIIl2;
                    break;
                }
                if (ilIIlIIlIIlllIlIIIlIllIIl == null) {
                    ArchClient.getInstance().configManager.writeProfile(ArchClient.getInstance().activeProfile.getName());
                    Profile ilIIlIIlIIlllIlIIIlIllIIl3 = new Profile(this.textField.getText(), true);
                    ArchClient.getInstance().profiles.add(ilIIlIIlIIlllIlIIIlIllIIl3);
                    ArchClient.getInstance().activeProfile = ilIIlIIlIIlllIlIIIlIllIIl3;
                    this.parent.lIIIIlIIllIIlIIlIIIlIIllI.add(new ProfileElement(this.parent, this.IIIIllIlIIIllIlllIlllllIl, ilIIlIIlIIlllIlIIIlIllIIl3, this.IlllIIIlIlllIllIlIIlllIlI));
                    ArchClient.getInstance().configManager.writeProfile(ArchClient.getInstance().activeProfile.getName());
                    this.mc.bridge$displayGuiScreen(this.guiScreen);
                    ((ACModulesGui) this.guiScreen).currentScrollableElement = ((ACModulesGui) this.guiScreen).profilesElement;
                    break;
                }
                this.IlIlIIIlllIIIlIlllIlIllIl = EnumChatFormattingBridge.RED + "That name is already in use.";
                break;
            }
            default: {
                this.textField.textboxKeyTyped(c, n);
            }
        }
    }
}
