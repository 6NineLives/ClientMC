package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;

import java.io.IOException;

public class PacketNotification extends Packet {

    private String message;
    private long durationMs;
    private String level;

    public PacketNotification(String message, long durationMs, String level) {
        this.message = message;
        this.durationMs = durationMs;
        this.level = level;
    }

    public PacketNotification() {
    }

    public String getMessage() {
        return this.message;
    }

    public long getDurationMs() {
        return this.durationMs;
    }

    public String getLevel() {
        return this.level;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeString(this.message);
        buf.buf().writeLong(this.durationMs);
        buf.writeString(this.level);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.message = buf.readString();
        this.durationMs = buf.buf().readLong();
        this.level = buf.readString();
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleNotification(this);
    }

}
