package com.archclient.impl.ref;

import com.archclient.bridge.ref.extra.ACMovementInputHelper;
import com.archclient.impl.extra.ACMovementInputHelperImpl;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

public class RefUtils {
    public Item getMostPowerfulArmourHelmet() {
        return (Item) Items.diamond_helmet;
    }

    public Item getMostPowerfulArmourChestplate() {
        return (Item) Items.diamond_chestplate;
    }

    public Item getMostPowerfulArmourLeggings() {
        return (Item) Items.diamond_leggings;
    }

    public Item getMostPowerfulArmourBoots() {
        return (Item) Items.diamond_boots;
    }

    public Item getMostPowerfulDamageItem() {
        return (Item) Items.diamond_sword;
    }

    public Item getItemFromID(int itemId) {
        return (Item) Item.getItemById(itemId);
    }

    public ACMovementInputHelper getToggleSprintInputHelper() {
        return () -> ACMovementInputHelperImpl.toggleSprintString;
    }

    public float bridge$MathHelper$sin(float toSine) {
        return MathHelper.sin(toSine);
    }

    public Item getItemFromName(String name) {
        return (Item) Item.getByNameOrId(name);
    }

    public Class<?> getScreenClass() {
        return GuiScreen.class;
    }
}
