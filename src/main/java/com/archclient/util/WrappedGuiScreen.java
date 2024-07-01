package com.archclient.util;

import com.archclient.bridge.wrapper.ACGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class WrappedGuiScreen extends GuiScreen {
    private final ACGuiScreen acScreen;
    public WrappedGuiScreen(ACGuiScreen acScreen) {
        this.acScreen = acScreen;

        this.acScreen.externalValues$execute = () -> {
            this.mc = Minecraft.getMinecraft();
            this.fontRendererObj = (FontRenderer) this.acScreen.fontRendererObj;
            this.width = this.acScreen.width;
            this.height = this.acScreen.height;
        };
    }

    private boolean setDrawScreen = false;
    public void drawScreen(int mouseX, int mouseY, float delta) {
        //this.allowUserInput = this.acScreen.allowUserInput;
        if (!this.setDrawScreen) {
            this.acScreen.super$drawScreen = () -> super.drawScreen(mouseX, mouseY, delta);
            this.setDrawScreen = true;
        }
        this.acScreen.drawScreen(mouseX, mouseY, delta);
    }

    private boolean setKeyTyped = false;
    protected void keyTyped(char c, int n) {
        if (!this.setKeyTyped) {
            this.acScreen.super$keyTyped = () -> {
                try {
                    super.keyTyped(c, n);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
            this.setKeyTyped = true;
        }
        this.acScreen.keyTyped(c, n);
    }

    private boolean setMouseClicked = false;
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.setMouseClicked) {
            this.acScreen.super$mouseClicked = () -> {
                try {
                    super.mouseClicked(mouseX, mouseY, mouseButton);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
            this.setMouseClicked = true;
        }
        this.acScreen.mouseClicked(mouseX, mouseY, mouseButton);
    }

    // 1.7.10 - mouseMovedOrUp
    // 1.8+   - mouseReleased
    private boolean setMouseMovedOrUp = false;
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (!this.setMouseMovedOrUp) {
            this.acScreen.super$mouseMovedOrUp = () -> super.mouseReleased(mouseX, mouseY, state);
            this.setMouseMovedOrUp = true;
        }
        this.acScreen.mouseMovedOrUp(mouseX, mouseY, state);
    }

    private boolean setSetWorldAndResolution = false;
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        if (!this.setSetWorldAndResolution) {
            this.acScreen.super$setWorldAndResolution = () -> {
                super.setWorldAndResolution(mc, width, height);
            };
            this.setSetWorldAndResolution = true;
        }
        this.acScreen.setWorldAndResolution((Minecraft) mc, width, height);
    }

    public void initGui() {
        this.acScreen.initGui();
    }

    private boolean setHandleMouseInput = false;
    public void handleMouseInput() {
        //if (!this.setHandleMouseInput)
        {
            this.acScreen.super$handleMouseInput = () -> {
                try {
                    super.handleMouseInput();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            };
            this.setHandleMouseInput = true;
        }
        this.acScreen.handleMouseInput();
    }

    public void updateScreen() {
        this.acScreen.updateScreen();
    }

    public void onGuiClosed() {
        this.acScreen.onGuiClosed();
    }

    private boolean setDrawDefaultBackground = false;
    public void drawDefaultBackground() {
        if (!this.setDrawDefaultBackground) {
            this.acScreen.super$drawDefaultBackground = super::drawDefaultBackground;
            this.setDrawDefaultBackground = true;
        }
        this.acScreen.drawDefaultBackground();
    }

    public boolean doesGuiPauseGame() {
        return this.acScreen.doesGuiPauseGame();
    }
}
