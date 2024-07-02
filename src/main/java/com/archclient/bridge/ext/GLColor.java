package com.archclient.bridge.ext;

public class GLColor {
    private float red;
    private float green;
    private float blue;
    private float alpha;

    public float getRed() {
        return this.red;
    }

    public float getGreen() {
        return this.green;
    }

    public float getBlue() {
        return this.blue;
    }

    public float getAlpha() {
        return this.alpha;
    }

    public GLColor setValues(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        return this;
    }

    public static GLColor glsmCurrentColor = new GLColor().setValues(1f, 1f, 1f, 1f);
}
