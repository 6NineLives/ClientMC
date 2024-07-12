package com.archclient.client.ui.mainmenu.cosmetics.element;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;

import net.minecraft.util.ResourceLocation;

import com.archclient.client.ui.element.AbstractModulesGuiElement;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;
import com.archclient.client.util.cosmetic.Cosmetic;

public class CosmeticListElement extends AbstractModulesGuiElement {
    private final Cosmetic cosmetic;
    private final ResourceLocation checkmarkIcon =
            Ref.getInstanceCreator().createResourceLocation("client/icons/checkmark-32.png");

    public CosmeticListElement(Cosmetic cosmetic, float f) {
        super(f);
        this.height = 30;
        this.cosmetic = cosmetic;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float scaleFactor) {
        boolean bl = mouseX > this.x &&
                mouseX < this.x + this.width &&
                mouseY > this.y &&
                mouseY < this.y + this.height;

        if (bl) {
            Ref.modified$drawRect(this.x, this.y, this.x + this.width, this.y + this.height,
                    0x2F000000);
        }

        Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.cosmetic.getType().equals("cape")) {
            Ref.getMinecraft().getTextureManager().bindTexture(this.cosmetic.getLocation());
            Ref.getGlBridge().bridge$pushMatrix();
            Ref.getGlBridge().bridge$translate(this.x + 20, this.y + 7, 0.0f);
            Ref.getGlBridge().bridge$scale(0.25f, 0.13f, 0.25f);
            RenderUtil.drawTexturedModalRect(0.0f, 0.0f, 2.0f, (float)7, 44, 120);
            Ref.getGlBridge().bridge$popMatrix();
        } else {
            RenderUtil.drawIcon(this.cosmetic.getPreviewLocation(), 8f, this.x + 20f, this.y + 7f);
        }

        FontRegistry.getPlayRegular14px().drawString(
                this.cosmetic.getName().replace("_", " ").toUpperCase(),
                this.x + 42, (float)(this.y + this.height / 2 - 5), -1342177281);
        if (this.cosmetic.isEquipped()) {
            Ref.getGlBridge().bridge$color(0.0f, 0.8f, 0.0f, 0.45f);
        } else {
            Ref.getGlBridge().bridge$color(0.0f, 0.0f, 0.0f, 0.25f);
        }

        RenderUtil.renderCircle(this.x + 8, this.y + this.height / 2, 3);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        boolean bl;
        boolean bl2 = bl = mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
        if (bl) {
            Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(
                    Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));

            if (this.cosmetic.isEquipped()) {
                this.cosmetic.setEquipped(false);
            } else if (this.cosmetic.getName().equals("cape")) {
                this.cosmetic.setEquipped(true);
                for (Cosmetic cosmetic : ArchClient.getInstance().getCosmetics()) {
                    if (cosmetic == this.cosmetic || !cosmetic.getName().equals("cape")) {
                        continue;
                    }

                    cosmetic.setEquipped(false);
                }
                this.cosmetic.setEquipped(true);
            } else {
                this.cosmetic.setEquipped(true);
                for (Cosmetic cosmetic : ArchClient.getInstance().getCosmetics()) {
                    if (cosmetic == this.cosmetic || cosmetic.getName().equals("cape")) {
                        continue;
                    }

                    cosmetic.setEquipped(false);
                }
                this.cosmetic.setEquipped(true);
            }
            ArchClient.getInstance().getAssetsWebSocket().sendClientCosmetics();
        }
    }
}