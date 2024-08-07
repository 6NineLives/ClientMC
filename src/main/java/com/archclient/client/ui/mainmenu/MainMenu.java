package com.archclient.client.ui.mainmenu;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import com.archclient.client.ui.fading.CosineFade;
import com.archclient.client.ui.fading.MinMaxFade;
import com.archclient.client.ui.util.RenderUtil;

public class MainMenu extends MainMenuBase {
    private final ResourceLocation outerLogo = Ref.getInstanceCreator().createResourceLocation("client/logo-full.png");
    private final ResourceLocation innerLogo = Ref.getInstanceCreator().createResourceLocation("client/logo_108_inner_remake.png");
    private final GradientTextButton singleplayerButton = new GradientTextButton("SINGLEPLAYER");
    private final GradientTextButton multiplayerButton = new GradientTextButton("MULTIPLAYER");
    private final MinMaxFade logoPositionFade = new MinMaxFade(750L);
    //private final CosineFade logoTurnAmount;
    private final MinMaxFade loadingScreenBackgroundFade = new MinMaxFade(400L);
    private static int loadCount;

    public MainMenu() {
        //this.logoTurnAmount = new CosineFade(4000L);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (this.isFirstOpened() && !this.logoPositionFade.hasStartTime()) {
            this.logoPositionFade.reset();
        }
        //if (!(this.isFirstOpened() && !this.logoPositionFade.isExpired()) || this.logoTurnAmount.hasStartTime()) {
        if (!(this.isFirstOpened() && this.logoPositionFade.isExpired())) {
            this.loadingScreenBackgroundFade.reset();
        //    this.logoTurnAmount.reset();
        //    this.logoTurnAmount.enableShouldResetOnceCalled();
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        this.singleplayerButton.setElementDimensions(this.getScaledWidth() / 2f - 50f, this.getScaledHeight() / 2f + 5f, 100f, 12f);
        this.singleplayerButton.setClickAction(() -> {
            this.mc.getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            this.mc.bridge$displayInternalGuiScreen(Minecraft.InternalScreen.SINGLEPLAYER, this);
        });

        this.multiplayerButton.setElementDimensions(this.getScaledWidth() / 2f - 50f, this.getScaledHeight() / 2f + 24f, 100f, 12f);
        this.multiplayerButton.setClickAction(() -> {
            this.mc.getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            this.mc.bridge$displayInternalGuiScreen(Minecraft.InternalScreen.MULTIPLAYER, this);
        });

        ++loadCount;
    }

    @Override
    public void drawMenu(float mouseX, float mouseY) {
        float logoY = this.isFirstOpened() ? this.logoPositionFade.getCurrentValue() : 1.0f;
        super.drawMenu(mouseX, mouseY);
        Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
        this.singleplayerButton.drawElement(mouseX, mouseY, true);
        this.multiplayerButton.drawElement(mouseX, mouseY, true);

        GradientTextButton topButton = this.singleplayerButton;
        GradientTextButton bottomButton = this.multiplayerButton;
        Ref.modified$drawRect(topButton.getX() - (float) 20, this.getScaledHeight() / 2.0f - (float) 80, topButton.getX() + topButton.getWidth() + (float) 20, bottomButton.getY() + bottomButton.getHeight() + (float) 14, 0x2F000000);

        if (this.isFirstOpened() && this.loadingScreenBackgroundFade.getCurrentValue() != 1f) {
            Ref.modified$drawRect(0f, 0f, this.getScaledWidth(), this.getScaledHeight(), (((int)((1f - this.loadingScreenBackgroundFade.getCurrentValue())*255+0.5) & 0xFF) << 24) |
                    (((int)(1f*255+0.5) & 0xFF) << 16) |
                    (((int)(1f*255+0.5) & 0xFF) << 8)  |
                    (((int)(1f*255+0.5) & 0xFF) << 0));
        }

        this.drawArchClientLogo(this.getScaledWidth(), this.getScaledHeight(), logoY);

        if (ArchClient.getInstance().isInDebugMode()) {
            this.fontRendererObj.drawString("[p] " + RenderUtil.getTimeAccurateFrameRate() + " FPS (" + RenderUtil.getFrameTimeAsMs() + "ms/frame) ", 5, 55, -1);
            this.fontRendererObj.drawString("[p] Min/Max FPS: " + RenderUtil.minFps + "/" + RenderUtil.maxFps, 5, 65, -1);
            this.fontRendererObj.drawString("[i] Press §cF9 §fto reset the Min/Max values.", 5, 75, -1);
        }
    }

    private void drawArchClientLogo(double dispWidth, double dispHeight, float f) {
        float halfSize = 27;
        double x = dispWidth / 2d - halfSize;
        double y = dispHeight / 2d - halfSize - (35f * f);
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.getGlBridge().bridge$color(1f, 1f, 1f, 1f);
        Ref.getGlBridge().bridge$translate(x, y, 1f);
        Ref.getGlBridge().bridge$translate(halfSize, halfSize, halfSize);
        //GlStateManager.rotate(180f * this.logoTurnAmount.getCurrentValue(), 0f, 0f, 1f);
        Ref.getGlBridge().bridge$translate(-halfSize, -halfSize, -halfSize);
        RenderUtil.drawIcon(this.outerLogo, halfSize, 0f, 0f);
        Ref.getGlBridge().bridge$popMatrix();
        RenderUtil.drawIcon(this.innerLogo, halfSize, (float) x, (float) y);
    }

    @Override
    public void onMouseClicked(float mouseX, float mouseY, int button) {
        super.onMouseClicked(mouseX, mouseY, button);
        this.singleplayerButton.handleElementMouseClicked(mouseX, mouseY, button, true);
        this.multiplayerButton.handleElementMouseClicked(mouseX, mouseY, button, true);
    }

    public boolean isFirstOpened() {
        return loadCount == 1;
    }
}
