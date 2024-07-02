package com.archclient.client.ui.mainmenu;

import com.archclient.client.ui.fading.ColorFade;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;

public class GradientTextButton extends AbstractElement {
    private String text;
    private final ColorFade outlineFade;
    private final ColorFade upperBackgroundFade;
    private final ColorFade lowerBackgroundFade;
    private int[] colors;

    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public GradientTextButton(String string) {
        this.text = string;
        this.outlineFade = new ColorFade(
                0xff000000 | 0xFF262626,
                0xff000000 | 0xFF50A05C
        );

        this.upperBackgroundFade = new ColorFade(
                0xff000000 | 0xFF323232,
                0xff000000 | 0xFF64B96E
        );

        this.lowerBackgroundFade = new ColorFade(
                0xff000000 | 0xFF2A2A2A,
                0xff000000 | 0xFF55A562
        );
    }

    public void applyLighterColorState() {
        this.setColors(new int[] {
                0xff000000 | 0xFF565656,
                0xff000000 | 0xFF50A05C,

                0xff000000 | 0xFF626262,
                0xff000000 | 0xFF64B96E,

                0xff000000 | 0xFF4F4F4F,
                0xff000000 | 0xFF55A562
        });
    }

    public void applySelectedColorState() {
        this.setColors(new int[] {
                0xff000000 | 0xFF50A05C,
                0xff000000 | 0xFF50A05C,

                0xff000000 | 0xFF64B96E,
                0xff000000 | 0xFF64B96E,

                0xff000000 | 0xFF55A562,
                0xff000000 | 0xFF55A562
        });
    }

    public void applyDefaultColorState() {
        this.setColors(new int[] {
                0xff000000 | 0xFF262626,
                0xff000000 | 0xFF50A05C,

                0xff000000 | 0xFF323232,
                0xff000000 | 0xFF64B96E,

                0xff000000 | 0xFF2A2A2A,
                0xff000000 | 0xFF55A562
        });
    }

    private void setColors(int[] arrn) {
        this.colors = arrn;
    }

    @Override
    protected void handleElementDraw(float mouseX, float mouseY, boolean enableMouse) {
        boolean bl2 = enableMouse && this.isMouseInside(mouseX, mouseY);
        if (this.colors != null && this.outlineFade.isExpired()) {
            this.outlineFade.setStartColor(this.colors[0]);
            this.outlineFade.setEndColor(this.colors[1]);
            this.upperBackgroundFade.setStartColor(this.colors[2]);
            this.upperBackgroundFade.setEndColor(this.colors[3]);
            this.lowerBackgroundFade.setStartColor(this.colors[4]);
            this.lowerBackgroundFade.setEndColor(this.colors[5]);
            this.colors = null;
        }
        RenderUtil.drawCorneredGradientRectWithOutline(this.x, this.y, this.x + this.width, this.y + this.height, this.outlineFade.get(bl2), this.upperBackgroundFade.get(bl2), this.lowerBackgroundFade.get(bl2));
        FontRegistry.getRobotoRegular13px().drawCenteredString(this.text, this.x + this.width / 2.0f, this.y + 2.0f, -1);
    }

    public void publicDraw(float mouseX, float mouseY, boolean enabled) {
        this.handleElementDraw(mouseX, mouseY, enabled);
    }

    @Override
    public boolean handleElementMouseClicked(float mouseX, float mouseY, int mouseButton, boolean enableMouse) {
        if (!enableMouse) {
            return false;
        }
        if (this.isMouseInside(mouseX, mouseY)) {
            this.clickAction.run();
            return true;
        }
        return false;
    }
}
