package com.archclient.client.ui.element.profile;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;

import net.lax1dude.eaglercraft.v1_8.internal.vfs2.VFile2;
import net.minecraft.util.ResourceLocation;

import com.archclient.client.config.Profile;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.ui.element.AbstractModulesGuiElement;
import com.archclient.client.ui.element.AbstractScrollableElement;
import com.archclient.client.ui.module.ACModulesGui;
import com.archclient.client.ui.module.ACProfileCreateGui;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;

import java.util.ArrayList;
import java.util.List;

public class ProfilesListElement extends AbstractScrollableElement {
    private final int IIIlllIIIllIllIlIIIIIIlII;
    public final List<ProfileElement> lIIIIlIIllIIlIIlIIIlIIllI;
    private final ResourceLocation plusIcon = Ref.getInstanceCreator().createResourceLocation("client/icons/plus-64.png");

    public ProfilesListElement(float f, int n, int n2, int n3, int n4) {
        super(f, n, n2, n3, n4);
        this.IIIlllIIIllIllIlIIIIIIlII = -12418828;
        this.lIIIIlIIllIIlIIlIIIlIIllI = new ArrayList<>();
        this.lIIIIIIIIIlIllIIllIlIIlIl();
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        Object object;
        int n3;
        RenderUtil.drawRoundedRect(this.x, this.y, this.x + this.width, this.y + this.height + 2, 8,
                ArchClient.getInstance().globalSettings.isDarkMode() ? -13619152 : -657931);
        this.preDraw(mouseX, mouseY);
        this.IlllIllIlIIIIlIIlIIllIIIl = 15;
        for (n3 = 0; n3 < this.lIIIIlIIllIIlIIlIIIlIIllI.size(); ++n3) {
            object = this.lIIIIlIIllIIlIIlIIIlIIllI.get(n3);
            ((AbstractModulesGuiElement)object).setDimensions(this.x + 4, this.y + 4 + n3 * 18, this.width - 12, 18);
            ((ProfileElement)object).yOffset = this.lIIIIllIIlIlIllIIIlIllIlI;
            ((ProfileElement)object).handleDrawElement(mouseX, mouseY, partialTicks);
            this.IlllIllIlIIIIlIIlIIllIIIl += ((AbstractModulesGuiElement)object).getHeight();
        }
        n3 = (float) mouseX > (float)(this.x + this.width - 92) * this.scale && (float) mouseX < (float)(this.x + this.width - 6) * this.scale && (float) mouseY > (float)(this.y + this.IlllIllIlIIIIlIIlIIllIIIl - 10 + this.lIIIIllIIlIlIllIIIlIllIlI) * this.scale && (float) mouseY < (float)(this.y + this.IlllIllIlIIIIlIIlIIllIIIl + 3 + this.lIIIIllIIlIlIllIIIlIllIlI) * this.scale ? 1 : 0;

        if (ArchClient.getInstance().globalSettings.isDarkMode()) {
            Ref.getGlBridge().bridge$color(n3 != 0 ? 0.0f : 1.0f, n3 != 0 ? 0.8f : 1.0f, n3 != 0 ? 0.0f : 1.0f, 1.0f);
        } else {
            Ref.getGlBridge().bridge$color(n3 != 0 ? 0.0f : 0.25f, n3 != 0 ? 0.8f : 0.25f, n3 != 0 ? 0.0f : 0.25f, 0.65f);
        }

        RenderUtil.drawIcon(this.plusIcon, 3.4435484f * 1.0163934f, (float)(this.x + this.width - 15), (float)(this.y + this.IlllIllIlIIIIlIIlIIllIIIl) - 0.6506024f * 9.990741f);
        object = (n3 != 0 ? "(COPIES CURRENT PROFILE) " : "") + "ADD NEW PROFILE";
        FontRegistry.getUbuntuMedium16px().drawString(
                (String)object,
                this.x + this.width - 17 - FontRegistry.getUbuntuMedium16px().getStringWidth((String)object),
                (float)(this.y + this.IlllIllIlIIIIlIIlIIllIIIl) - 64.28571f * 0.11666667f,
                n3 != 0 ? 2130738944 : (ArchClient.getInstance().globalSettings.isDarkMode() ? 0xFFFFFFFF : 2130706432));
        this.IlllIllIlIIIIlIIlIIllIIIl += 10;
        this.postDraw(mouseX, mouseY);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        for (ProfileElement iIlIlllllIIIlIIllIllIlIlI : this.lIIIIlIIllIIlIIlIIIlIIllI) {
            if (!iIlIlllllIIIlIIllIllIlIlI.isMouseInside(mouseX, mouseY)) continue;
            iIlIlllllIIIlIIllIllIlIlI.handleMouseClick(mouseX, mouseY, button);
            return;
        }
        boolean bl = (float) mouseX > (float) (this.x + this.width - 92) * this.scale && (float) mouseX < (float) (this.x + this.width - 6) * this.scale && (float) mouseY > (float) (this.y + this.IlllIllIlIIIIlIIlIIllIIIl - 20 + this.lIIIIllIIlIlIllIIIlIllIlI) * this.scale && (float) mouseY < (float) (this.y + this.IlllIllIlIIIIlIIlIIllIIIl - 7 + this.lIIIIllIIlIlIllIIIlIllIlI) * this.scale;
        if (bl) {
            Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            Ref.getMinecraft().bridge$displayGuiScreen(new ACProfileCreateGui(ACModulesGui.instance, this, this.IIIlllIIIllIllIlIIIIIIlII, this.scale));
        }
    }

    @Override
    public boolean lIIIIlIIllIIlIIlIIIlIIllI(AbstractModule aCModule) {
        return true;
    }

    @Override
    public void lIIIIIIIIIlIllIIllIlIIlIl(AbstractModule aCModule) {
    }

    public void lIIIIIIIIIlIllIIllIlIIlIl() {
        new Thread(() -> {
            this.lIIIIlIIllIIlIIlIIIlIIllI.clear();
            VFile2 file = new VFile2("config", "client", "profiles");
            if (file.exists()) {
                for (VFile2 file2 : file.listFiles(false)) {
                    if (!file2.getName().endsWith(".cfg")) continue;
                    Profile ilIIlIIlIIlllIlIIIlIllIIl = null;
                    for (Profile ilIIlIIlIIlllIlIIIlIllIIl2 : ArchClient.getInstance().profiles) {
                        if (!file2.getName().equals(ilIIlIIlIIlllIlIIIlIllIIl2.getName() + ".cfg")) continue;
                        ilIIlIIlIIlllIlIIIlIllIIl = ilIIlIIlIIlllIlIIIlIllIIl2;
                    }
                    if (ilIIlIIlIIlllIlIIIlIllIIl != null) continue;
                    ArchClient.getInstance().profiles.add(new Profile(file2.getName().replace(".cfg", ""), false));
                }
            }
            for (Profile ilIIlIIlIIlllIlIIIlIllIIl : ArchClient.getInstance().profiles) {
                this.lIIIIlIIllIIlIIlIIIlIIllI.add(new ProfileElement(this, this.IIIlllIIIllIllIlIIIIIIlII, ilIIlIIlIIlllIlIIIlIllIIl, this.scale));
            }
            this.lIIIIlIIllIIlIIlIIIlIIllI.sort((iIlIlllllIIIlIIllIllIlIlI, iIlIlllllIIIlIIllIllIlIlI2) -> {
                if (iIlIlllllIIIlIIllIllIlIlI.profile.getName().equalsIgnoreCase("default")) {
                    return 0;
                }
                if (iIlIlllllIIIlIIllIllIlIlI.profile.getIndex() < iIlIlllllIIIlIIllIllIlIlI2.profile.getIndex()) {
                    return -1;
                }
                return 1;
            });
        }).start();
    }
}