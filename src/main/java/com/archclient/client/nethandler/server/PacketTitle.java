package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;

import java.io.IOException;

public class PacketTitle extends Packet {

    private String type;
    private String message;
    private float scale;
    private long displayTimeMs;
    private long fadeInTimeMs;
    private long fadeOutTimeMs;

    public PacketTitle(String type, String message, long displayTimeMs, long fadeInTimeMs, long fadeOutTimeMs) {
        this(type, message, 1.0f, displayTimeMs, fadeInTimeMs, fadeOutTimeMs);
    }

    public PacketTitle(String type, String message, float scale, long displayTimeMs, long fadeInTimeMs, long fadeOutTimeMs) {
        this.type = type;
        this.message = message;
        this.scale = scale;
        this.displayTimeMs = displayTimeMs;
        this.fadeInTimeMs = fadeInTimeMs;
        this.fadeOutTimeMs = fadeOutTimeMs;
    }

    public PacketTitle() {
    }

    public String getType() {
        return this.type;
    }
    public String getMessage() {
        return this.message;
    }

    public float getScale() {
        return this.scale;
    }

    public long getDisplayTimeMs() {
        return this.displayTimeMs;
    }

    public long getFadeInTimeMs() {
        return this.fadeInTimeMs;
    }

    public long getFadeOutTimeMs() {
        return this.fadeOutTimeMs;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeString(this.type);
        buf.writeString(this.message);
        buf.buf().writeFloat(this.scale);
        buf.buf().writeLong(this.displayTimeMs);
        buf.buf().writeLong(this.fadeInTimeMs);
        buf.buf().writeLong(this.fadeOutTimeMs);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.type = buf.readString();
        this.message = buf.readString();
        this.scale = buf.buf().readFloat();
        this.displayTimeMs = buf.buf().readLong();
        this.fadeInTimeMs = buf.buf().readLong();
        this.fadeOutTimeMs = buf.buf().readLong();
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleTitle(this);
    }

}
