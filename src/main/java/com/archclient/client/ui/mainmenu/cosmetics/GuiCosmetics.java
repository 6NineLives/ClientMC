package com.archclient.client.ui.mainmenu.cosmetics;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;

import net.minecraft.util.ResourceLocation;

import com.archclient.client.ui.mainmenu.GradientTextButton;
import com.archclient.client.ui.mainmenu.MainMenu;
import com.archclient.client.ui.mainmenu.MainMenuBase;
import com.archclient.client.ui.mainmenu.cosmetics.element.CosmeticListElement;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;
import com.archclient.client.util.cosmetic.Cosmetic;

import java.util.ArrayList;
import java.util.List;

public class GuiCosmetics extends MainMenuBase {
    private final List<CosmeticListElement> IIIIllIlIIIllIlllIlllllIl = new ArrayList<>();
    private ResourceLocation leftIcon = Ref.getInstanceCreator().createResourceLocation("client/icons/left.png");
    private ResourceLocation rightIcon = Ref.getInstanceCreator().createResourceLocation("client/icons/right.png");
    private final GradientTextButton backButton = new GradientTextButton("BACK");
    private int IllIIIIIIIlIlIllllIIllIII = 0;

    public GuiCosmetics() {
        for (Cosmetic cosmetic : ArchClient.getInstance().getCosmetics()) {
            this.IIIIllIlIIIllIlllIlllllIl.add(new CosmeticListElement(cosmetic, 1.0f));
        }
    }

    @Override
    public void drawMenu(float mouseX, float mouseY) {
        super.drawMenu(mouseX, mouseY);
        if (false) {
            FontRegistry.getPlayRegular16px().drawCenteredString("Unable to connect to the server.", this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f - (float)10, -1);
            FontRegistry.getPlayRegular16px().drawCenteredString("Please try again later.", this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f + (float)4, -1);
            this.backButton.setElementDimensions(this.getScaledWidth() / 2.0f - (float)30, this.getScaledHeight() / 2.0f + (float)28, (float)60, 12);
            this.backButton.drawElement(mouseX, mouseY, true);
        } else {
            Ref.modified$drawRect(this.getScaledWidth() / 2.0f - (float)80, this.getScaledHeight() / 2.0f - (float)78, this.getScaledWidth() / 2.0f + (float)80, this.getScaledHeight() / 2.0f + (float)100, 0x2F000000);
            this.backButton.setElementDimensions(this.getScaledWidth() / 2.0f - (float)30, this.getScaledHeight() / 2.0f + (float)105, (float)60, 12);
            this.backButton.drawElement(mouseX, mouseY, true);
            if (this.IIIIllIlIIIllIlllIlllllIl.isEmpty()) {
                FontRegistry.getPlayRegular16px().drawCenteredString("You don't own any cosmetics.", this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f + (float)4, -6381922);
            } else {
                float f3 = this.getScaledHeight() / 2.0f - (float)68;
                float f4 = this.getScaledHeight() / 2.0f + (float)92;
                float f5 = this.getScaledWidth() / 2.0f + (float)68;
                float f6 = this.getScaledWidth() / 2.0f + (float)74;
                FontRegistry.getPlayBold18px().drawCenteredString("Cosmetics (" + this.IIIIllIlIIIllIlllIlllllIl.size() + ")", this.getScaledWidth() / 2.0f, this.getScaledHeight() / 2.0f - (float)90, -1);
                int n = 0;
                float f7 = 0.0f;
                for (CosmeticListElement llIlIlIllIlIIlIlllIllIIlI2 : this.IIIIllIlIIIllIlllIlllllIl) {
                    if (++n - 1 < this.IllIIIIIIIlIlIllllIIllIII * 5 || n - 1 >= (this.IllIIIIIIIlIlIllllIIllIII + 1) * 5) continue;
                    llIlIlIllIlIIlIlllIllIIlI2.setDimensions((int)this.getScaledWidth() / 2 - 76, (int)(this.getScaledHeight() / 2.0f - (float)72 + f7), 152, llIlIlIllIlIIlIlllIllIIlI2.getHeight());
                    llIlIlIllIlIIlIlllIllIIlI2.handleDrawElement((int) mouseX, (int) mouseY, 1.0f);
                    f7 += (float)llIlIlIllIlIIlIlllIllIIlI2.getHeight();
                }
                if (this.IIIIllIlIIIllIlllIlllllIl.size() > 5) {
                    boolean bl = mouseX > this.getScaledWidth() / 2.0f - (float)40 && mouseX < this.getScaledWidth() / 2.0f - 1.0f && mouseY > this.getScaledHeight() / 2.0f + (float)80 && mouseY < this.getScaledHeight() / 2.0f + (float)100;
                    Ref.getGlBridge().bridge$color(0.0f, 0.0f, 0.0f, bl ? 0.37499997f * 1.2f : 0.8958333f * 0.27906978f);
                    RenderUtil.drawIcon(this.leftIcon, (float)4, this.getScaledWidth() / 2.0f - (float)10, this.getScaledHeight() / 2.0f + (float)84);
                    boolean bl2 = mouseX > this.getScaledWidth() / 2.0f + 1.0f && mouseX < this.getScaledWidth() / 2.0f + (float)40 && mouseY > this.getScaledHeight() / 2.0f + (float)80 && mouseY < this.getScaledHeight() / 2.0f + (float)100;
                    Ref.getGlBridge().bridge$color(0.0f, 0.0f, 0.0f, bl2 ? 0.012658228f * 35.55f : 0.7083333f * 0.3529412f);
                    RenderUtil.drawIcon(this.rightIcon, (float)4, this.getScaledWidth() / 2.0f + (float)10, this.getScaledHeight() / 2.0f + (float)84);
                }
            }
        }
    }

    @Override
    public void onMouseClicked(float f, float f2, int n) {
        super.onMouseClicked(f, f2, n);
        if (this.backButton.isMouseInside(f, f2)) {
            Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            this.mc.bridge$displayGuiScreen(new MainMenu());
        } else {
            int n2;
            if (this.IIIIllIlIIIllIlllIlllllIl.size() > 5) {
                boolean bl;
                n2 = f > this.getScaledWidth() / 2.0f - (float)40 && f < this.getScaledWidth() / 2.0f - 1.0f && f2 > this.getScaledHeight() / 2.0f + (float)80 && f2 < this.getScaledHeight() / 2.0f + (float)100 ? 1 : 0;
                boolean bl2 = bl = f > this.getScaledWidth() / 2.0f + 1.0f && f < this.getScaledWidth() / 2.0f + (float)40 && f2 > this.getScaledHeight() / 2.0f + (float)80 && f2 < this.getScaledHeight() / 2.0f + (float)100;
                if (this.IllIIIIIIIlIlIllllIIllIII > 0 && n2 != 0) {
                    --this.IllIIIIIIIlIlIllllIIllIII;
                    Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                } else if (bl && (float)(this.IllIIIIIIIlIlIllllIIllIII + 1) < (float)this.IIIIllIlIIIllIlllIlllllIl.size() / (float)5) {
                    ++this.IllIIIIIIIlIlIllllIIllIII;
                    Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                }
            }
            n2 = 0;
            for (CosmeticListElement cosmeticElement : this.IIIIllIlIIIllIlllIlllllIl) {
                if (++n2 - 1 < this.IllIIIIIIIlIlIllllIIllIII * 5 || n2 - 1 >= (this.IllIIIIIIIlIlIllllIIllIII + 1) * 5) continue;
                cosmeticElement.handleMouseClick((int)f, (int)f2, n);
            }
        }
    }
}
