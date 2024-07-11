package com.archclient.client.module.type.notifications;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.event.type.GuiDrawEvent;
import com.archclient.client.event.type.KeepAliveEvent;
import com.archclient.client.event.type.TickEvent;
import com.archclient.client.module.AbstractModule;

import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ACNotificationsModule extends AbstractModule
{
    public long time;
    private List<Notification> notifications;

    public ACNotificationsModule() {
        super("Notifications");
        this.time = System.currentTimeMillis();
        this.notifications = new ArrayList<>();
        this.addEvent(KeepAliveEvent.class, this::onKeepAlive);
        this.addEvent(TickEvent.class, this::onTick);
        this.addEvent(GuiDrawEvent.class, this::onDraw);
        this.setDefaultState(true);
    }

    private void onKeepAlive(final KeepAliveEvent time) {
        this.time = System.currentTimeMillis();
    }

    private void onTick(final TickEvent event) {
        final Iterator<Notification> iterator = this.notifications.iterator();
        while (iterator.hasNext()) {
            final Notification notification = iterator.next();
            notification.update();
            if (notification.startTime + notification.duration - System.currentTimeMillis() <= 0L) {
                int ilIlIIIlllIIIlIlllIlIllIl = notification.notificationBottom;
                for (final Notification illlIlIlllIlIlllIIlllIlIl2 : this.notifications) {
                    if (illlIlIlllIlIlllIIlllIlIl2.notificationBottom < notification.notificationBottom) {
                        illlIlIlllIlIlllIIlllIlIl2.framesAlive = 0;
                        illlIlIlllIlIlllIIlllIlIl2.notificationTop = ilIlIIIlllIIIlIlllIlIllIl;
                        ilIlIIIlllIIIlIlllIlIllIl = illlIlIlllIlIlllIIlllIlIl2.notificationBottom;
                    }
                }
                iterator.remove();
            }
        }
    }

    private void onDraw(final GuiDrawEvent event) {
        for (Notification notification : this.notifications) {
            notification.render((int) event.getResolution().getScaledWidth());
        }
    }

    public void queueNotification(final String type, String content, long duration) {
        final ScaledResolution scaledResolution = Ref.getInstanceCreator().createScaledResolution();
        duration = Math.max(2000L, duration);
        content = content.replaceAll("&([abcdefghijklmrABCDEFGHIJKLMNR0-9])|(&$)", "\u00a7$1");

        final String lowerCase = type.toLowerCase();
        ACNotificationType resolvedType;
        switch (lowerCase) {
            case "info":
                resolvedType = ACNotificationType.INFO;
                break;
            case "error":
                resolvedType = ACNotificationType.ERROR;
                break;
            default:
                resolvedType = ACNotificationType.DEFAULT;
                break;
        }

        final Notification notificationInstance = new Notification(this, scaledResolution, resolvedType, content, duration);
        int ilIlIIIlllIIIlIlllIlIllIl = notificationInstance.notificationTop - notificationInstance.notificationHeight - 2;
        for (int i = this.notifications.size() - 1; i >= 0; --i) {
            final Notification notification = this.notifications.get(i);
            notification.framesAlive = 0;
            notification.notificationTop = ilIlIIIlllIIIlIlllIlIllIl;
            ilIlIIIlllIIIlIlllIlIllIl -= 2 + notification.notificationHeight;
        }
        this.notifications.add(notificationInstance);
    }
}
