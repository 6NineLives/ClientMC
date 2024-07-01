package com.archclient.client.ui.overlay.element;

import com.archclient.client.ui.mainmenu.AbstractElement;
import com.archclient.client.util.javafx.geom.Vec2d;
import net.lax1dude.eaglercraft.v1_8.Mouse;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class DraggableElement extends AbstractElement {
    private final Vec2d position = new Vec2d();
    private final AtomicBoolean dragging = new AtomicBoolean();

    protected void drag(float mouseX, float mouseY) {
        if (this.dragging.get()) {
            if (!Mouse.isButtonDown(0)) {
                this.dragging.set(false);
                return;
            }
            double newX = (double)mouseX - this.position.x;
            double newY = (double)mouseY - this.position.y;
            this.setElementDimensions((float)newX, (float)newY, this.width, this.height);
        }
    }

    protected void updateDraggingPosition(float mouseX, float mouseY) {
        this.position.set(mouseX - this.x, mouseY - this.y);
        this.dragging.set(true);
    }

    protected void IllIIIIIIIlIlIllllIIllIII() {
        if (this.dragging.get()) {
            this.dragging.set(false);
        }
    }
}

