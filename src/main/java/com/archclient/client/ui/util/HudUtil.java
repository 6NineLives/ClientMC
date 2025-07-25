package com.archclient.client.ui.util;

import com.archclient.bridge.ref.Ref;

import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class HudUtil {

    public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, float zLevel) {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator tessellator = Ref.getTessellator();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, zLevel).tex(u * var7, v + height * var8).endVertex();
        worldrenderer.pos(x + width, y + height, zLevel).tex(u + width * var7, v + height * var8).endVertex();
        worldrenderer.pos(x + width, y, zLevel).tex(u + width * var7, v * var8).endVertex();
        worldrenderer.pos(x, y, zLevel).tex(u * var7, v * var8).endVertex();
        tessellator.draw();
    }

    /**
     * Renders the item's overlay information. Examples being stack count or damage on top of the item's image at the
     * specified position.
     */
    public static void renderItemOverlayIntoGUI(FontRenderer fontRenderer, ItemStack itemStack, int x, int y, boolean showDamageBar, boolean showCount) {
        if (itemStack != null && (showDamageBar || showCount)) {
            if (itemStack.isItemDamaged() && showDamageBar) {
                int var11 = (int) Math
                        .round(13.0D - itemStack.getItemDamage() * 13.0D / itemStack.getMaxDamage());
                int var7 = (int) Math
                        .round(255.0D - itemStack.getItemDamage() * 255.0D / itemStack.getMaxDamage());
                Ref.getGlBridge().bridge$disableLighting();
                Ref.getGlBridge().bridge$disableDepthTest();
                Ref.getGlBridge().bridge$disableTexture2D();
                Tessellator tessellator = Ref.getTessellator();
                int var9 = 255 - var7 << 16 | var7 << 8;
                int var10 = (255 - var7) / 4 << 16 | 16128;
                renderQuad(tessellator, x + 2, y + 13, 13, 2, 0);
                renderQuad(tessellator, x + 2, y + 13, 12, 1, var10);
                renderQuad(tessellator, x + 2, y + 13, var11, 1, var9);
                Ref.getGlBridge().bridge$enableTexture2D();
                Ref.getGlBridge().bridge$enableLighting();
                Ref.getGlBridge().bridge$enableDepthTest();
                Ref.getGlBridge().bridge$color(1.0F, 1.0F, 1.0F, 1.0F);
            }

            if (showCount) {
                int count = 0;

                if (itemStack.getMaxStackSize() > 1) {
                    count = HudUtil.countInInventory(Ref.getMinecraft().thePlayer, itemStack.getItem(),
                            itemStack.getItemDamage());
                } else if (itemStack.getItem().equals(Ref.getUtils().getItemFromName("bow"))) {
                    count = HudUtil.countInInventory(Ref.getMinecraft().thePlayer, Ref.getUtils().getItemFromName("arrow"));
                }

                if (count > 1) {
                    String var6 = "" + count;
                    Ref.getGlBridge().bridge$disableLighting();
                    Ref.getGlBridge().bridge$disableDepthTest();
                    fontRenderer.drawStringWithShadow(var6, x + 19 - 2 - fontRenderer.getStringWidth(var6), y + 6 + 3,
                            16777215);
                    Ref.getGlBridge().bridge$enableLighting();
                    Ref.getGlBridge().bridge$enableDepthTest();
                }
            }
        }
    }

    /**
     * Adds a quad to the tesselator at the specified position with the set width and height and color. Args:
     * tesselator, x, y, width,
     * height, color
     */
    public static void renderQuad(Tessellator tessellator, int x, int y, int width, int height, int color) {
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((x), (y), 0.0D).setColorOpaque_I(color).endVertex();
        worldrenderer.pos((x), (y + height), 0.0D).setColorOpaque_I(color).endVertex();
        worldrenderer.pos((x + width), (y + height), 0.0D).setColorOpaque_I(color).endVertex();
        worldrenderer.pos((x + width), (y), 0.0D).setColorOpaque_I(color).endVertex();
        tessellator.draw();
    }

    public static int countInInventory(EntityPlayer player, Item item) {
        return countInInventory(player, item, -1);
    }

    public static int countInInventory(EntityPlayer player, Item item, int md) {
        int count = 0;
        for (int i = 0; i < player.inventory.mainInventory.length; i++) {
            if (player.inventory.mainInventory[i] != null && item.equals(player.inventory.mainInventory[i].getItem()) &&
                    (md == -1 || player.inventory.mainInventory[i].getItemDamage() == md)) {
                count += player.inventory.mainInventory[i].stackSize;
            }
        }
        return count;
    }
}