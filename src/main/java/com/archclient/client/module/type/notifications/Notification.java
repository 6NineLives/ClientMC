package com.archclient.client.module.type.notifications;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;
import com.archclient.main.utils.Utility;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

class Notification {
    public ACNotificationType type;
    public String content;
    public long duration;
    public long startTime = System.currentTimeMillis();
    public int notificationBottom;
    public int notificationTop;
    public int notificationHeight;
    public int framesAlive = 0;
    final ACNotificationsModule notificationsModule;

    Notification(ACNotificationsModule notificationsModule, ScaledResolution scaledResolution, ACNotificationType type, String content, long duration) {
        this.notificationsModule = notificationsModule;
        this.type = type;
        this.content = content;
        this.duration = duration;
        this.notificationHeight = type == ACNotificationType.DEFAULT ? 16 : 20;
        this.notificationTop = (int) (scaledResolution.getScaledHeight() - 14 - this.notificationHeight);
        this.notificationBottom = (int) (scaledResolution.getScaledHeight() + this.notificationHeight);
    }

    public void update() {
        if (this.notificationTop != -1) {
            ++this.framesAlive;
            float f = this.framesAlive * (this.framesAlive / 5f) / 7f;
            if (this.notificationBottom > this.notificationTop) {
                if ((float)this.notificationBottom - f < (float)this.notificationTop) {
                    this.notificationBottom = this.notificationTop;
                    this.notificationTop = -1;
                } else {
                    this.notificationBottom = (int)((float)this.notificationBottom - f);
                }
            } else if (this.notificationBottom < this.notificationTop) {
                if ((float)this.notificationBottom + f > (float)this.notificationTop) {
                    this.notificationBottom = this.notificationTop;
                    this.notificationTop = -1;
                } else {
                    this.notificationBottom = (int)((float)this.notificationBottom + f);
                }
            } else {
                this.notificationTop = -1;
            }
        }
    }

    public void render(int n) {
        FontRenderer font = FontRegistry.getPlayRegular16px();
        int n2 = this.notificationBottom;
        float contentWidth = font.getStringWidth(this.content);
        int n3 = (int)(this.type == ACNotificationType.DEFAULT ? contentWidth + (float)10 : contentWidth + (float)30);
        Ref.modified$drawRect(n - 5 - n3, n2, n - 5, n2 + this.notificationHeight, 0xFF000000);

        switch (this.type) {
            case ERROR:
                Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
                RenderUtil.drawIcon(Ref.getInstanceCreator().createResourceLocation("client/icons/error-64.png"), (float)6, (float)(n - 10 - n3 + 9), (float)(n2 + 4));
                Ref.modified$drawRect((float)(n - 10) - contentWidth - 4.5f, n2 + 4, (float)(n - 10) - contentWidth - 4f, n2 + this.notificationHeight - 4, 0xFFFFFFFF);
                break;
            case INFO:
                Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 0.65f);
                RenderUtil.drawIcon(Ref.getInstanceCreator().createResourceLocation("client/icons/info-64.png"), (float)6, (float)(n - 10 - n3 + 9), (float)(n2 + 4));
                Ref.modified$drawRect(n - 10f - contentWidth - 4.5f, n2 + 4, (float)(n - 10) - contentWidth - 4f, n2 + this.notificationHeight - 4, 0xFFFFFFFF);
                break;
        }

        long millisElapsed = Utility.clamp(this.duration - (this.startTime + this.duration - System.currentTimeMillis()), 0L, this.duration);
        float millisElapsedDisplay = contentWidth * ((float)millisElapsed / (float)this.duration * 100f / 100f);

        Ref.modified$drawRect(
                n - 10f - contentWidth,
                n2 + this.notificationHeight - 4.4f,
                n - 10f - contentWidth + contentWidth,
                n2 + this.notificationHeight - 4f,
                0x30666666
        );

        Ref.modified$drawRect(
                n - 10f - contentWidth,
                n2 + this.notificationHeight - 4.4f,
                n - 10f - contentWidth + millisElapsedDisplay,
                n2 + this.notificationHeight - 4f,
                0xFF00FF00
        );

        font.drawString(this.content, n - 10f - contentWidth, n2 + (this.type == ACNotificationType.DEFAULT ? 2f : 4f), -1);
    }
}
