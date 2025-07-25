package com.archclient.client.module.type.armourstatus;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.config.Setting;
import com.archclient.client.event.type.GuiDrawEvent;
import com.archclient.client.event.type.RenderPreviewEvent;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.ui.module.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ArmourStatusModule extends AbstractModule {
    private Setting generalOptionsLabel;
    public static Setting listMode;
    public static Setting itemName;
    public static Setting itemCount;
    public static Setting showWhileTying;
    public static Setting equippedItem;
    private Setting damageOptionsLabel;
    public static Setting damageOverlay;
    public static Setting showItemDamage;
    public static Setting showArmourDamage;
    public static Setting showMaxDamage;
    private Setting damageDisplay;
    public static Setting damageDisplayType;
    public static Setting damageThreshold;
    public static RenderItem renderItem;
    public static final List<ArmourStatusDamageComparable> damageColors;
    private static List<ArmourStatusItem> items;
    private static ScaledResolution scaledResolution;

    public ArmourStatusModule() {
        super("Armor Status");
        this.setDefaultAnchor(ACGuiAnchor.RIGHT_BOTTOM);
        this.setDefaultState(false);
        this.generalOptionsLabel = new Setting(this, "label").setValue("General Options");
        listMode = new Setting(this, "List Mode").setValue("vertical").acceptedValues("vertical", "horizontal");
        itemName = new Setting(this, "Item Name").setValue(false);
        itemCount = new Setting(this, "Item Count").setValue(true);
        equippedItem = new Setting(this, "Equipped Item").setValue(true);
        showWhileTying = new Setting(this, "Show While Typing").setValue(false);
        this.damageOptionsLabel = new Setting(this, "label").setValue("Damage Options");
        damageOverlay = new Setting(this, "Damage Overlay").setValue(true);
        showItemDamage = new Setting(this, "Show Item Damage").setValue(true);
        showArmourDamage = new Setting(this, "Show Armor Damage").setValue(true);
        showMaxDamage = new Setting(this, "Show Max Damage").setValue(false);
        this.damageDisplay = new Setting(this, "label").setValue("Damage Display");
        damageDisplayType = new Setting(this, "Damage Display Type").setValue("value").acceptedValues("value", "percent", "none");
        damageThreshold = new Setting(this, "Damage Threshold Type").setValue("percent").acceptedValues("percent", "value");
        damageColors.add(new ArmourStatusDamageComparable(10, "4"));
        damageColors.add(new ArmourStatusDamageComparable(25, "c"));
        damageColors.add(new ArmourStatusDamageComparable(40, "6"));
        damageColors.add(new ArmourStatusDamageComparable(60, "e"));
        damageColors.add(new ArmourStatusDamageComparable(80, "7"));
        damageColors.add(new ArmourStatusDamageComparable(100, "f"));
        this.setPreviewIcon(Ref.getInstanceCreator().createResourceLocation("client/icons/mods/diamond_chestplate.png"), 34, 34);
        this.addEvent(RenderPreviewEvent.class, this::renderPreview);
        this.addEvent(GuiDrawEvent.class, this::renderReal);
    }

    private void renderPreview(GuiDrawEvent event) {
        if (!this.isRenderHud()) {
            return;
        }
        ArrayList<ArmourStatusItem> arrayList = new ArrayList<>();
        for (int i = 3; i >= 0; --i) {
            ItemStack stack = this.minecraft.thePlayer.inventory.armorInventory[i];
            arrayList.add(new ArmourStatusItem(stack, 16, 16, 2, true));
        }
        if (arrayList.isEmpty()) {
            arrayList.add(new ArmourStatusItem(Ref.getInstanceCreator().createItemStack(Ref.getUtils().getMostPowerfulArmourHelmet()), 16, 16, 2, true));
            arrayList.add(new ArmourStatusItem(Ref.getInstanceCreator().createItemStack(Ref.getUtils().getMostPowerfulArmourChestplate()), 16, 16, 2, true));
            arrayList.add(new ArmourStatusItem(Ref.getInstanceCreator().createItemStack(Ref.getUtils().getMostPowerfulArmourLeggings()), 16, 16, 2, true));
            arrayList.add(new ArmourStatusItem(Ref.getInstanceCreator().createItemStack(Ref.getUtils().getMostPowerfulArmourBoots()), 16, 16, 2, true));
        }
        if (equippedItem.<Boolean>value() && this.minecraft.thePlayer.getCurrentEquippedItem() != null) {
            arrayList.add(new ArmourStatusItem(this.minecraft.thePlayer.getCurrentEquippedItem(), 16, 16, 2, false));
        } else if (equippedItem.<Boolean>value()) {
            arrayList.add(new ArmourStatusItem(Ref.getInstanceCreator().createItemStack(Ref.getUtils().getMostPowerfulDamageItem()), 16, 16, 2, false));
        }
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
        scaledResolution = event.getResolution();
        this.scaleAndTranslate(scaledResolution);
        this.lIIIIlIIllIIlIIlIIIlIIllI(this.minecraft, arrayList);
        Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
        Ref.getGlBridge().bridge$popMatrix();
    }

    private void renderReal(GuiDrawEvent event) {
        if (!this.isRenderHud()) {
            return;
        }
        if (!(this.minecraft.bridge$getCurrentScreen() instanceof ACModulesGui || this.minecraft.bridge$getCurrentScreen() instanceof ACModulePlaceGui || this.minecraft.currentScreen instanceof GuiChat && !showWhileTying.<Boolean>value())) {
            this.updateItems(this.minecraft);
            if (!items.isEmpty()) {
                Ref.getGlBridge().bridge$pushMatrix();
                Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
                scaledResolution = event.getResolution();
                this.scaleAndTranslate(scaledResolution);
                this.lIIIIlIIllIIlIIlIIIlIIllI(this.minecraft, items);
                Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
                Ref.getGlBridge().bridge$popMatrix();
            }
        }
    }

    private void updateItems(Minecraft minecraft) {
        items.clear();
        for (int i = 3; i >= -1; --i) {
            ItemStack stack = null;
            if (i == -1 && equippedItem.<Boolean>value()) {
                stack = minecraft.thePlayer.getCurrentEquippedItem();
            } else if (i != -1) {
                stack = minecraft.thePlayer.inventory.armorInventory[i];
            }
            if (stack == null) continue;
            items.add(new ArmourStatusItem(stack, 16, 16, 2, i > -1));
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(Minecraft minecraft, List<ArmourStatusItem> list) {
        if (list.size() > 0) {
            int n = itemName.<Boolean>value() ? 18 : 16;
            if (listMode.<String>value().equalsIgnoreCase("vertical")) {
                int n3 = 0;
                int n4 = 0;
                boolean bl = ACAnchorHelper.getHorizontalPositionEnum(this.getGuiAnchor()) == ACPositionEnum.RIGHT;
                for (ArmourStatusItem armourStatusItem : list) {
                    armourStatusItem.lIIIIlIIllIIlIIlIIIlIIllI(bl ? this.width : 0.0f, n3);
                    n3 += n;
                    if (armourStatusItem.lIIIIlIIllIIlIIlIIIlIIllI() <= n4) continue;
                    n4 = armourStatusItem.lIIIIlIIllIIlIIlIIIlIIllI();
                }
                this.height = n3;
                this.width = n4;
            } else if (listMode.<String>value().equalsIgnoreCase("horizontal")) {
                boolean bl = false;
                int n5 = 0;
                int n6 = 0;
                boolean bl2 = ACAnchorHelper.getHorizontalPositionEnum(this.getGuiAnchor()) == ACPositionEnum.RIGHT;
                for (ArmourStatusItem armourStatusItem : list) {
                    if (bl2) {
                        n5 += armourStatusItem.lIIIIlIIllIIlIIlIIIlIIllI();
                    }
                    armourStatusItem.lIIIIlIIllIIlIIlIIIlIIllI(n5, bl2 ? this.height : 0.0f);
                    if (!bl2) {
                        n5 += armourStatusItem.lIIIIlIIllIIlIIlIIIlIIllI();
                    }
                    if (armourStatusItem.lIIIIIIIIIlIllIIllIlIIlIl() <= n6) continue;
                    n6 += armourStatusItem.lIIIIIIIIIlIllIIllIlIIlIl();
                }
                this.height = n6;
                this.width = n5;
            }
        }
    }

    static {
        renderItem = Ref.getInstanceCreator().createRenderItem();
        damageColors = new ArrayList();
        items = new ArrayList();
    }

}
