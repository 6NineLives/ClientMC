package com.archclient.client.event;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.function.Consumer;

public class EventBus {

    private final ConcurrentHashMap<Class<? extends Event>, ArrayList<Consumer>> map;

    public EventBus() {
        this.map = new ConcurrentHashMap<>();
    }

    public <T extends Event> boolean addEvent(final Class<T> clazz, final Consumer<T> consumer) {
        return this.map.computeIfAbsent(clazz, p0 -> new ArrayList<>()).add(consumer);
    }

    public <T extends Event> boolean removeEvent(final Class<T> clazz, final Consumer<T> consumer) {
        final ArrayList<Consumer> list = this.map.get(clazz);
        return list != null && list.remove(consumer);
    }

    public void callEvent(final Event event) {
        try {
            for (Serializable s = event.getClass(); s != null && s != Event.class; s = ((Class<Event>) s).getSuperclass()) {
                final ArrayList<Consumer> list = this.map.get(s);
                if (list != null) {
                    list.forEach(consumer -> consumer.accept(event));
                }
            }
        } catch (Exception ex) {
            System.out.println("EventBus [" + event.getClass() + "]");
            ex.printStackTrace();
        }
    }

    public abstract static class Event {
    }
}
