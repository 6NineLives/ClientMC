package com.archclient.client.ui.mainmenu;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;

import net.minecraft.client.Minecraft;

public abstract class AbstractElement {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected final Minecraft mc = Ref.getMinecraft();
    protected final ArchClient client = ArchClient.getInstance();

    public boolean isMouseInside(float mouseX, float mouseY) {
        boolean minX = mouseX >= this.x;
        boolean maxX = mouseX <= this.x + this.width;
        boolean minY = mouseY >= this.y;
        boolean maxY = mouseY <= this.y + this.height;
        return minX && maxX && minY && maxY;
    }

    public boolean drawElement(float f, float f2, boolean bl) {
        this.handleElementDraw(f, f2, bl);
        return this.isMouseInside(f, f2);
    }

    public void setElementDimensions(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void handleElementUpdate() {
    }

    public void handleElementClose() {
    }

    public void handleElementKeyTyped(char c, int n) {
    }

    public void handleElementMouse() {
    }

    protected abstract void handleElementDraw(float mouseX, float mouseY, boolean enableMouse);

    public boolean handleElementMouseClicked(float mouseX, float mouseY, int mouseButton, boolean enableMouse) {
        return false;
    }

    protected Runnable clickAction = () -> {};
    public void setClickAction(Runnable clickAction) {
        this.clickAction = clickAction;
    }

    public boolean handleElementMouseRelease(float f, float f2, int n, boolean bl) {
        return false;
    }

    public boolean handleMouseClickedInternal(float f, float f2, int n) {
        return false;
    }

    public void setX(float f) {
        this.x = f;
    }

    public void setY(float f) {
        this.y = f;
    }

    public void setWidth(float f) {
        this.width = f;
    }

    public void setHeight(float f) {
        this.height = f;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

}
