package com.archclient.client.ui.element.profile;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;

import net.lax1dude.eaglercraft.v1_8.internal.vfs2.VFile2;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.util.ResourceLocation;

import com.archclient.client.config.Profile;
import com.archclient.client.ui.element.AbstractModulesGuiElement;
import com.archclient.client.ui.element.AbstractScrollableElement;
import com.archclient.client.ui.module.ACModulesGui;
import com.archclient.client.ui.module.ACProfileCreateGui;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;

import java.util.Collections;

// IIlIlllllIIIlIIllIllIlIlI
public class ProfileElement extends AbstractModulesGuiElement {
    private final int IllIIIIIIIlIlIllllIIllIII;
    public final Profile profile;
    private final AbstractScrollableElement parent;
    private int IlllIllIlIIIIlIIlIIllIIIl = 0;
    private final ResourceLocation deleteIcon = Ref.getInstanceCreator().createResourceLocation("client/icons/delete-64.png");
    private final ResourceLocation arrowIcon = Ref.getInstanceCreator().createResourceLocation("client/icons/right.png");
    private final ResourceLocation pencilIcon = Ref.getInstanceCreator().createResourceLocation("client/icons/pencil-64.png");

    public ProfileElement(AbstractScrollableElement parent, int n, Profile profile, float f) {
        super(f);
        this.parent = parent;
        this.IllIIIIIIIlIlIllllIIllIII = n;
        this.profile = profile;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        boolean bl;
        boolean bl2;
        float f2;
        boolean bl3 = mouseX > this.x + 12 && this.isMouseInside(mouseX, mouseY);
        int n3 = 75;
        Ref.modified$drawRect(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, ArchClient.getInstance().globalSettings.isDarkMode() ? -14211289 : 791621423);
        if (bl3) {
            if (this.IlllIllIlIIIIlIIlIIllIIIl < n3) {
                f2 = ACModulesGui.getSmoothFloat(790);
                this.IlllIllIlIIIIlIIlIIllIIIl = (int)((float)this.IlllIllIlIIIIlIIlIIllIIIl + f2);
                if (this.IlllIllIlIIIIlIIlIIllIIIl > n3) {
                    this.IlllIllIlIIIIlIIlIIllIIIl = n3;
                }
            }
        } else if (this.IlllIllIlIIIIlIIlIIllIIIl > 0) {
            f2 = ACModulesGui.getSmoothFloat(790);
            this.IlllIllIlIIIIlIIlIIllIIIl = (float)this.IlllIllIlIIIIlIIlIIllIIIl - f2 < 0.0f ? 0 : (int)((float)this.IlllIllIlIIIIlIIlIIllIIIl - f2);
        }
        if (this.IlllIllIlIIIIlIIlIIllIIIl > 0) {
            f2 = (float)this.IlllIllIlIIIIlIIlIIllIIIl / (float)n3 * (float)100;
            Ref.modified$drawRect(this.x + 12, (int)((float)this.y + ((float)this.height - (float)this.height * f2 / (float)100)), this.x + this.width - (this.profile.isEditable() ? 0 : 30), this.y + this.height, this.IllIIIIIIIlIlIllllIIllIII);
        }
        boolean bl4 = (float) mouseX > (float)this.x * this.scale && (float) mouseX < (float)(this.x + 12) * this.scale && (float) mouseY >= (float)(this.y + this.yOffset) * this.scale && (float) mouseY <= (float)(this.y + this.height / 2 + this.yOffset) * this.scale;
        boolean bl5 = (float) mouseX > (float)this.x * this.scale && (float) mouseX < (float)(this.x + 12) * this.scale && (float) mouseY > (float)(this.y + this.height / 2 + this.yOffset) * this.scale && (float) mouseY < (float)(this.y + this.height + this.yOffset) * this.scale;
        Ref.getGlBridge().bridge$color(0.0f, 0.0f, 0.0f, 0.1919192f * 1.8236842f);
        float f3 = 6.571429f * 0.38043478f;
        if (this.profile.isEditable()) {
            bl2 = false;
            bl = false;
            ProfilesListElement parent = (ProfilesListElement)this.parent;
            if (parent.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) != 0 && parent.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) > 1) {
                bl2 = true;
                Ref.getGlBridge().bridge$pushMatrix();
                if (bl4) {
                    Ref.getGlBridge().bridge$color(0.0f, 0.0f, 0.0f, 0.14444444f * 4.5f);
                }
                Ref.getGlBridge().bridge$translate((float)(this.x + 6) - f3, (float)this.y + (float)5, 0.0f);
                GlStateManager.rotate(-90, 0.0f, 0.0f, 1.0f);
                RenderUtil.drawIcon(this.arrowIcon, f3, (float)-1, 0.0f);
                Ref.getGlBridge().bridge$popMatrix();
                Ref.getGlBridge().bridge$color(0.0f, 0.0f, 0.0f, 1.0952381f * 0.3195652f);
            }
            if (parent.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) != parent.lIIIIlIIllIIlIIlIIIlIIllI.size() - 1) {
                bl = true;
                Ref.getGlBridge().bridge$pushMatrix();
                if (bl5) {
                    Ref.getGlBridge().bridge$color(0.0f, 0.0f, 0.0f, 1.2112676f * 0.5366279f);
                }
                Ref.getGlBridge().bridge$translate((float)(this.x + 6) + f3, (float)this.y + (float)7, 0.0f);
                GlStateManager.rotate(90, 0.0f, 0.0f, 1.0f);
                RenderUtil.drawIcon(this.arrowIcon, f3, 2.0f, 0.0f);
                Ref.getGlBridge().bridge$popMatrix();
            }
            if (!bl2 && !bl) {
                RenderUtil.drawIcon(this.arrowIcon, 1.173913f * 2.1296296f, (float)(this.x + 4), (float)this.y + (float)6);
            }
        } else {
            RenderUtil.drawIcon(this.arrowIcon, 6.6666665f * 0.375f, (float)(this.x + 4), (float)this.y + (float)6);
        }
        if (ArchClient.getInstance().activeProfile == this.profile) {
            FontRegistry.getPlayBold18px().drawString(this.profile.getName().toUpperCase(), (float)this.x + (float)16, (float)(this.y + 4), ArchClient.getInstance().globalSettings.isDarkMode() ? 0xFFFFFFFF : -818991312);
        } else {
            FontRegistry.getPlayRegular16px().drawString(this.profile.getName().toUpperCase(), (float)this.x + (float)16, (float)this.y + 4, ArchClient.getInstance().globalSettings.isDarkMode() ? 0xFFFFFFFF : -818991312);
        }
        if (ArchClient.getInstance().activeProfile == this.profile) {
            FontRegistry.getPlayRegular14px().drawString(" (Active)", (float)this.x + (float)17 + (float) FontRegistry.getPlayBold18px().getStringWidth(this.profile.getName().toUpperCase()), (float)this.y + (float)7, ArchClient.getInstance().globalSettings.isDarkMode() ? 0xFFFFFFFF : 1865363247);
        }
        if (this.profile.isEditable()) {
            bl2 = (float) mouseX > (float)(this.x + this.width - 30) * this.scale && (float) mouseX < (float)(this.x + this.width - 13) * this.scale && (float) mouseY > (float)(this.y + this.yOffset) * this.scale && (float) mouseY < (float)(this.y + this.height + this.yOffset) * this.scale;
            //Ref.getGlBridge().bridge$color(bl2 ? 0.0f : 1.1707317f * 0.21354167f, bl2 ? 0.0f : 0.101648346f * 2.4594595f, bl2 ? 0.48876402f * 1.0229886f : 0.5647059f * 0.4427083f, 0.5675676f * 1.145238f);
            if (ArchClient.getInstance().globalSettings.isDarkMode()) {
                Ref.getGlBridge().bridge$color(bl2 ? 0.0f : 1.0f, bl2 ? 0.0f : 1.0f, bl2 ? 0.5f : 1.0f, 1.0f);
            } else {
                Ref.getGlBridge().bridge$color(bl2 ? 0.0f : 0.25f, bl2 ? 0.0f : 0.25f, bl2 ? 0.5f : 0.25f, 0.65f);
            }
            RenderUtil.drawIcon(this.pencilIcon, (float)5, (float)(this.x + this.width - 26), (float)this.y + 5.1916666f * 0.6741573f);
            bl = (float) mouseX > (float)(this.x + this.width - 17) * this.scale && (float) mouseX < (float)(this.x + this.width - 2) * this.scale && (float) mouseY > (float)(this.y + this.yOffset) * this.scale && (float) mouseY < (float)(this.y + this.height + this.yOffset) * this.scale;
            //Ref.getGlBridge().bridge$color(bl ? 0.8f : 0.96875f * 0.2580645f, bl ? 0.0f : 0.17553192f * 1.4242424f, bl ? 0.0f : 15.250001f * 0.016393442f, 0.44444445f * 1.4625f);

            if (ArchClient.getInstance().globalSettings.isDarkMode()) {
                Ref.getGlBridge().bridge$color(bl ? 0.8f : 1.0f, bl ? 0.0f : 1.0f, bl ? 0.0f : 1.0f, 1.0f);
            } else {
                Ref.getGlBridge().bridge$color(bl ? 0.8f : 0.25f, bl ? 0.0f : 0.25f, bl ? 0.0f : 0.25f, 0.65f);
            }

            RenderUtil.drawIcon(this.deleteIcon, (float)5, (float)(this.x + this.width - 13), (float)this.y + 0.7653061f * 4.5733333f);
        }
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        boolean bl = (float) mouseX > (float)(this.x + this.width - 17) * this.scale && (float) mouseX < (float)(this.x + this.width - 2) * this.scale && (float) mouseY > (float)(this.y + this.yOffset) * this.scale && (float) mouseY < (float)(this.y + this.height + this.yOffset) * this.scale;
        boolean bl2 = (float) mouseX > (float)(this.x + this.width - 30) * this.scale && (float) mouseX < (float)(this.x + this.width - 13) * this.scale && (float) mouseY > (float)(this.y + this.yOffset) * this.scale && (float) mouseY < (float)(this.y + this.height + this.yOffset) * this.scale;
        boolean bl3 = (float) mouseX > (float)this.x * this.scale && (float) mouseX < (float)(this.x + 12) * this.scale && (float) mouseY >= (float)(this.y + this.yOffset) * this.scale && (float) mouseY <= (float)(this.y + this.height / 2 + this.yOffset) * this.scale;
        boolean bl4 = (float) mouseX > (float)this.x * this.scale && (float) mouseX < (float)(this.x + 12) * this.scale && (float) mouseY > (float)(this.y + this.height / 2 + this.yOffset) * this.scale && (float) mouseY < (float)(this.y + this.height + this.yOffset) * this.scale;
        ProfilesListElement iIlIlIlllIllIIlIllIIlIIlI = (ProfilesListElement)this.parent;
        if (this.profile.isEditable() && (bl3 || bl4)) {
            if (bl3 && ((ProfilesListElement)this.parent).lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) != 0 && ((ProfilesListElement)this.parent).lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) > 1) {
                Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                this.profile.setIndex(iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) - 1);
                iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.get((int)(iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.indexOf((Object)this) - 1)).profile.setIndex(iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this));
                Collections.swap(iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI, iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this), iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) - 1);
            }
            if (bl4 && iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) != iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.size() - 1) {
                Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                this.profile.setIndex(iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) + 1);
                iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.get((int)(iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.indexOf((Object)this) + 1)).profile.setIndex(iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this));
                Collections.swap(iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI, iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this), iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.indexOf(this) + 1);
            }
        } else if (this.profile.isEditable() && bl) {
            VFile2 file;
            VFile2 file2;
            Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            if (ArchClient.getInstance().activeProfile == this.profile) {
                ArchClient.getInstance().activeProfile = ArchClient.getInstance().profiles.get(0);
                ArchClient.getInstance().configManager.readProfile(ArchClient.getInstance().activeProfile.getName());
                ArchClient.getInstance().moduleManager.keyStrokes.initialize();
            }
            if (this.profile.isEditable() && (file2 = (file = new VFile2("config", "client", "profiles")) != null ? new VFile2(file, this.profile.getName().toLowerCase() + ".cfg") : null).exists() && file2.delete()) {
                ArchClient.getInstance().profiles.removeIf(ilIIlIIlIIlllIlIIIlIllIIl -> ilIIlIIlIIlllIlIIIlIllIIl == this.profile);
                iIlIlIlllIllIIlIllIIlIIlI.lIIIIlIIllIIlIIlIIIlIIllI.removeIf(iIlIlllllIIIlIIllIllIlIlI -> iIlIlllllIIIlIIllIllIlIlI == this);
            }
        } else if (this.profile.isEditable() && bl2) {
            Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            Ref.getMinecraft().bridge$displayGuiScreen(new ACProfileCreateGui(this.profile, ACModulesGui.instance, (ProfilesListElement)this.parent, this.IllIIIIIIIlIlIllllIIllIII, this.scale));
        } else if (ArchClient.getInstance().activeProfile != this.profile) {
            Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            ArchClient.getInstance().configManager.writeProfile(ArchClient.getInstance().activeProfile.getName());
            ArchClient.getInstance().activeProfile = this.profile;
            ArchClient.getInstance().configManager.readProfile(ArchClient.getInstance().activeProfile.getName());
            ArchClient.getInstance().moduleManager.keyStrokes.initialize();
        }
    }
}