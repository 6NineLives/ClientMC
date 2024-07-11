package com.archclient.client.module.type.keystrokes;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.ui.module.ACModulesGui;
import com.archclient.client.ui.util.Color;

import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;

public class Key {
    private final String displayString;
    private int keyCode;
    private final float width;
    private final float height;
    private float fadeTime;
    private boolean pressed;
    private long lastPressed;
    private Color pressedColor;
    private Color unpressedColor;

    public Key(String string, int keyCode, float width, float height, float fadeTime) {
        this.displayString = string;
        this.keyCode = keyCode;
        this.width = width;
        this.height = height;
        this.fadeTime = fadeTime;
    }

    public void render(final float n, final float n2, final int n3, final int n4, final int n5, final int n6) {
        final Minecraft minecraft = Ref.getMinecraft();
        final int n7 = (this.keyCode == -99 || this.keyCode == -100) ? ((this.keyCode == -99) ? 1 : 0) : -1;
        final boolean pressed = (minecraft.bridge$isIngame() || minecraft.currentScreen instanceof GuiContainer || minecraft.bridge$getCurrentScreen() instanceof ACModulesGui) && ((n7 != -1) ? Mouse.isButtonDown(n7) : Keyboard.isKeyDown(this.keyCode));
        if (pressed && !this.pressed) {
            this.pressed = true;
            this.lastPressed = System.currentTimeMillis();
            this.pressedColor = new Color(n5, true);
            this.unpressedColor = new Color(n6, true);
        }
        else if (this.pressed && !pressed) {
            this.pressed = false;
            this.lastPressed = System.currentTimeMillis();
            this.pressedColor = new Color(n6, true);
            this.unpressedColor = new Color(n5, true);
        }
//        if (pressed) {
//            System.out.println(displayString);
//        }
        int rgb;
        if (System.currentTimeMillis() - this.lastPressed < fadeTime) {
            final float n9 = (System.currentTimeMillis() - this.lastPressed) / fadeTime;
            rgb = new Color(
                    (int)Math.abs(n9 * this.unpressedColor.getRed() + (1.0f - n9) * this.pressedColor.getRed()),
                    (int)Math.abs(n9 * this.unpressedColor.getGreen() + (1.0f - n9) * this.pressedColor.getGreen()),
                    (int)Math.abs(n9 * this.unpressedColor.getBlue() + (1.0f - n9) * this.pressedColor.getBlue()),
                    (int)Math.abs(n9 * this.unpressedColor.getAlpha() + (1.0f - n9) * this.pressedColor.getAlpha())
            ).getRGB();
        }
        else {
            rgb = (pressed ? n6 : n5);
        }
        Ref.modified$drawRect(n, n2, n + this.width, n2 + this.height, rgb);
        if (this.keyCode == minecraft.gameSettings.keyBindJump.getKeyCode()) {
            Ref.modified$drawRect(n + this.width / 2 - this.width / 8, n2 + this.height / 2, n + this.width / 2.0f + this.width / 8, n2 + this.height / 2 + 1, 0xFF000000 | (pressed ? n4 : n3));
        }
        else {
            // added pixel perfect aligning
            int width = Ref.getMinecraft().fontRendererObj.getStringWidth(displayString);
            int offset = displayString.length() == 1 ? 1 : (displayString.contains("R") ? 2 : 1);
            Ref.getMinecraft().fontRendererObj.drawString(this.displayString, (n + this.width / 2.0f) - width / 2f + offset, (n2 + this.height / 2.0f - 9 / 2f + 1.0f), pressed ? n4 : n3);
        }
    }

    public void setKeyCode(int n) {
        this.keyCode = n;
    }

    public String getDisplayString() {
        return this.displayString;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public int getKeyCode() {
        return this.keyCode;
    }
}
