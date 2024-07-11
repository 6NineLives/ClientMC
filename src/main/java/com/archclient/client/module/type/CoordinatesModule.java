package com.archclient.client.module.type;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.config.Setting;
import com.archclient.client.event.type.GuiDrawEvent;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.ui.module.ACGuiAnchor;

public class CoordinatesModule extends AbstractModule {

    private final Setting mode;
    private final Setting showWhileTyping;
    private final Setting showCoordinates;
    private final Setting hideYCoordinate;
    private final Setting showDirection;
    private final Setting coordinatesColor;
    private final Setting customLine;
    private final Setting directionColor;

    public CoordinatesModule() {
        super("Coordinates");
        this.setDefaultAnchor(ACGuiAnchor.LEFT_TOP);
        this.setDefaultTranslations(0.0f, 0.0f);
        this.setDefaultState(false);
        new Setting(this, "label").setValue("General Options");
        this.showWhileTyping = new Setting(this, "Show While Typing").setValue(true);
        this.mode = new Setting(this, "Mode").setValue("Horizontal").acceptedValues("Horizontal", "Vertical");
        this.hideYCoordinate = new Setting(this, "Hide Y Coordinate").setValue(false);
        this.showCoordinates = new Setting(this, "Show Coordinates").setValue(true);
        this.showDirection = new Setting(this, "Direction").setValue(true);
        this.customLine = new Setting(this, "Custom Line").setValue("");
        new Setting(this, "label").setValue("Color Settings");
        this.coordinatesColor = new Setting(this, "Coordinates Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.directionColor = new Setting(this, "Direction Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.setPreviewLabel("(16, 65, 120) NW", 1.0f);
        this.addEvent(GuiDrawEvent.class, this::onRender);
    }

    private int MathHelper$floor_double(double toFloor) {
        int asInt = (int)toFloor;
        return toFloor < (double)asInt ? asInt - 1 : asInt;
    }

    private double MathHelper$wrapAngleTo180_double(double par0) {
        par0 %= 360.0D;

        if (par0 >= 180.0D)
            par0 -= 360.0D;

        if (par0 < -180.0D)
            par0 += 360.0D;

        return par0;
    }

    public void onRender(GuiDrawEvent event) {
        if (!this.isRenderHud()) {
            return;
        }

        Ref.getGlBridge().bridge$pushMatrix();
        this.scaleAndTranslate(event.getResolution());
        int n = MathHelper$floor_double(this.minecraft.thePlayer.posX);
        int n2 = (int) this.minecraft.thePlayer.getEntityBoundingBox().minY;
        int n3 = MathHelper$floor_double(this.minecraft.thePlayer.posZ);

        if (!this.minecraft.ingameGUI.getChatGUI().getChatOpen()
                || this.showWhileTyping.<Boolean>value()) {
            int n4;
            String object;
            float f = 4;
            if (this.mode.<String>value().equals("Horizontal")) {
                object = this.hideYCoordinate.<Boolean>value()
                        ? this.showCoordinates.<Boolean>value()
                            ? String.format("(%1$d, %2$d) ", n, n3)
                            : ""
                        : this.showCoordinates.<Boolean>value()
                            ? String.format("(%1$d, %2$d, %3$d) ", n, n2, n3)
                            : "";
                n4 = this.minecraft.fontRendererObj.drawStringWithShadow(object, 0, 0,
                        this.coordinatesColor.getColorValue());
            } else {
                n4 = 50;
                f = this.hideYCoordinate.<Boolean>value() ? 8.066038f * 1.1777778f : (float) 16;
                this.minecraft.fontRendererObj.drawStringWithShadow("X: " + n, 0, 0,
                        this.coordinatesColor.getColorValue());
                if (!this.hideYCoordinate.<Boolean>value()) {
                    this.minecraft.fontRendererObj.drawStringWithShadow("Y: " + n2, 0, 12,
                            this.coordinatesColor.getColorValue());
                }
                this.minecraft.fontRendererObj.drawStringWithShadow("Z: " + n3, 0,
                        this.hideYCoordinate.<Boolean>value() ? 12 : 24, this.coordinatesColor.getColorValue());
            }
            if (this.showDirection.<Boolean>value()) {
                String[] directions = new String[] { "N", "NE", "E", "SE", "S", "SW", "W", "NW" };
                double d = MathHelper$wrapAngleTo180_double(this.minecraft.thePlayer
                        .rotationYaw) + 180D;
                d += 22.5D;
                d %= 360;
                String string = directions[MathHelper$floor_double(d / 45)];
                this.minecraft.fontRendererObj.drawStringWithShadow(string, n4, (int) f - 4,
                        this.directionColor.getColorValue());
                n4 += this.minecraft.fontRendererObj.getStringWidth(string);
            }
            this.setDimensions(n4, (float) 18 + f);
        }
        Ref.getGlBridge().bridge$popMatrix();
    }
}
