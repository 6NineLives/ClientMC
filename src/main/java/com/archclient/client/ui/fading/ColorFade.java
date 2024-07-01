package com.archclient.client.ui.fading;

import lombok.Setter;

public class ColorFade extends ExponentialFade {
    @Setter
    private int startColor;
    @Setter
    private int endColor;
    private boolean switched;
    private int awtStartColor;
    private int awtEndColor;

    public ColorFade(long duration, int startColor, int endColor) {
        super(duration);
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public ColorFade(int startColor, int endColor) {
        this(175L, startColor, endColor);
    }

    public int get(boolean shouldSwitch) {
        int color = shouldSwitch ? this.endColor : this.startColor;
        if (shouldSwitch && !this.switched) {
            this.switched = true;
            this.awtStartColor = this.startColor;
            this.awtEndColor = this.endColor;
            this.reset();
        } else if (this.switched && !shouldSwitch) {
            this.switched = false;
            this.awtStartColor = this.endColor;
            this.awtEndColor = this.startColor;
            this.reset();
        }
        if (this.isFadeOngoing()) {
            float f = super.getCurrentValue();
            int red = (int)Math.abs(f * (float)((this.awtEndColor >> 16) & 0xFF) + (1.0f - f) * (float)((this.awtStartColor >> 16) & 0xFF));
            int green = (int)Math.abs(f * (float)((this.awtEndColor >> 8) & 0xFF) + (1.0f - f) * (float)((this.awtStartColor >> 8) & 0xFF));
            int blue = (int)Math.abs(f * (float)((this.awtEndColor >> 0) & 0xFF) + (1.0f - f) * (float)((this.awtStartColor >> 0) & 0xFF));
            int alpha = (int)Math.abs(f * (float)((this.awtEndColor >> 24) & 0xff) + (1.0f - f) * (float)((this.awtStartColor >> 24) & 0xff));
            color = ((alpha & 0xFF) << 24) |
                    ((red & 0xFF) << 16) |
                    ((green & 0xFF) << 8)  |
                    ((blue & 0xFF) << 0);
        }
        return color;
    }
}