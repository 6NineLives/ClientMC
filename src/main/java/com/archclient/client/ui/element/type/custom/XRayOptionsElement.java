package com.archclient.client.ui.element.type.custom;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;

import com.archclient.client.ui.element.AbstractModulesGuiElement;
import com.archclient.client.ui.util.font.FontRegistry;

import java.util.List;

public class XRayOptionsElement
        extends AbstractModulesGuiElement {
    private String lIIIIlIIllIIlIIlIIIlIIllI;
    private List IllIIIIIIIlIlIllllIIllIII;
    private RenderItem itemRenderer = Ref.getInstanceCreator().createRenderItem();

    public XRayOptionsElement(List list, String string, float f) {
        super(f);
        this.height = 220;
        this.IllIIIIIIIlIlIllllIIllIII = list;
        this.lIIIIlIIllIIlIIlIIIlIIllI = string;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        FontRegistry.getUbuntuMedium16px().drawString(this.lIIIIlIIllIIlIIlIIIlIIllI.toUpperCase(), this.x + 10, (float)(this.y + 2), ArchClient.getInstance().globalSettings.isDarkMode() ? 0xFFFFFFFF : -1895825407);
        Minecraft minecraft = Ref.getMinecraft();
        List<Integer> list = ArchClient.getInstance().moduleManager.xray.lIllIllIlIIllIllIlIlIIlIl();
        Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
        Ref.getGlBridge().bridge$enableRescaleNormal();
        RenderHelper.enableStandardItemLighting();
        RenderHelper.enableGUIStandardItemLighting();
        int n3 = 0;
        int n4 = 0;
        for (Object obj : Ref.getBlockRegistry()) {
            Block block = (Block) obj;
            boolean bl;
            Item item = (Item) Item.getItemFromBlock((Block) ((Object) block));
            if (item == null) continue;
            if (n3 >= 15) {
                n3 = 0;
                ++n4;
            }
            int n5 = this.x + 12 + n3 * 20;
            int n6 = this.y + 14 + n4 * 20;
            boolean bl2 = bl = (float) mouseX > (float)(n5 - 2) * this.scale && (float) mouseX < (float)(n5 + 18) * this.scale && (float) mouseY > (float)(n6 - 2 + this.yOffset) * this.scale && (float) mouseY < (float)(n6 + 18 + this.yOffset) * this.scale;
            if (list.contains(Item.itemRegistry.getIDForObject((Item) ((Object) item)))) {
                Ref.modified$drawRect(n5 - 2, n6 - 2, n5 + 18, n6 + 18, 0x7F00FF00);
            } else if (bl) {
                Ref.modified$drawRect(n5 - 2, n6 - 2, n5 + 18, n6 + 18, 0x4F0000FF);
            }
            this.itemRenderer.renderItemIntoGUI(Ref.getInstanceCreator().createItemStack(item), n5, n6);
            ++n3;
        }
        RenderHelper.disableStandardItemLighting();
        Ref.getGlBridge().bridge$disableRescaleNormal();
        Ref.getGlBridge().bridge$disableBlend();
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        try {
            List<Integer> list = ArchClient.getInstance().moduleManager.xray.lIllIllIlIIllIllIlIlIIlIl();
            int n5 = 0;
            int n6 = 0;
            for (Object obj : Ref.getBlockRegistry()) {
                Block block = (Block) obj;
                boolean bl;
                Item item = (Item) Item.getItemFromBlock((Block) ((Object) block));
                if (item == null) continue;
                if (n5 >= 15) {
                    n5 = 0;
                    ++n6;
                }
                int n7 = this.x + 12 + n5 * 20;
                int n8 = this.y + 14 + n6 * 20;
                boolean bl2 = bl = (float) mouseX > (float)(n7 - 2) * this.scale && (float) mouseX < (float)(n7 + 18) * this.scale && (float) mouseY > (float)(n8 - 2 + this.yOffset) * this.scale && (float) mouseY < (float)(n8 + 18 + this.yOffset) * this.scale;
                if (bl && button == 0) {
                    int n9 = Item.itemRegistry.getIDForObject((Item) ((Object) item));
                    if (list.contains(n9)) {
                        ArchClient.getInstance().moduleManager.xray.lIllIllIlIIllIllIlIlIIlIl().removeIf(n2 -> n2 == n9);
                    } else {
                        ArchClient.getInstance().moduleManager.xray.lIllIllIlIIllIllIlIlIIlIl().add(n9);
                    }
                    if (ArchClient.getInstance().moduleManager.xray.isEnabled()) {
                        Ref.getMinecraft().renderGlobal.loadRenderers();
                    }
                    Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                }
                ++n5;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
