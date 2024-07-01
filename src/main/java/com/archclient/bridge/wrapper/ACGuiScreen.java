package com.archclient.bridge.wrapper;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.List;

public class ACGuiScreen {
    protected Minecraft mc;
    public int width;
    public int height;
    protected List buttonList = new ArrayList();
    protected List labelList = new ArrayList();
    public boolean field_146291_p;
    public FontRenderer fontRendererObj;
    private int eventButton;
    private long lastMouseEvent;
    private int field_146298_h;
    public boolean allowUserInput;

    public Runnable externalValues$execute = () -> {};
    public void setExternalValues() {
        this.externalValues$execute.run();
    }

    public Runnable super$drawScreen = () -> {};
    public void drawScreen(int mouseX, int mouseY, float delta) {
        this.super$drawScreen.run();
    }

    public Runnable super$keyTyped = () -> {};
    public void keyTyped(char c, int n) {
        this.super$keyTyped.run();
    }

    public static String wrapped$getClipboardString() {
        try {
            return EagRuntime.getClipboard();
        }
        catch (Exception ignored) {
        }

        return "";
    }

    public static void wrapped$setClipboardString(String p_146275_0_) {
        EagRuntime.setClipboard(p_146275_0_);
    }
    public Runnable super$mouseClicked = () -> {};
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.super$mouseClicked.run();
    }

    public Runnable super$mouseMovedOrUp = () -> {};
    public void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        this.super$mouseMovedOrUp.run();
    }

    public Runnable super$setWorldAndResolution = () -> {};
    public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_) {
        this.super$setWorldAndResolution.run();
    }

    public void initGui() {}

    public Runnable super$handleMouseInput = () -> {};
    public void handleMouseInput() {
        this.super$handleMouseInput.run();
    }

    public void updateScreen() {}
    public void onGuiClosed() {}

    public Runnable super$drawDefaultBackground = () -> {};
    public void drawDefaultBackground() {
        this.super$drawDefaultBackground.run();
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    public static boolean wrapped$isCtrlKeyDown() {
        return Minecraft.isRunningOnMac ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) : Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
    }

    public static boolean wrapped$isShiftKeyDown() {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }
}

