package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;

import java.io.IOException;

public class PacketWorldBorder extends Packet {

    private String id;
    private String world;
    private boolean cancelsExit;
    private boolean canShrinkExpand;
    private int color;
    private double minX;
    private double minZ;
    private double maxX;
    private double maxZ;

    public PacketWorldBorder(String id, String world, boolean cancelsExit, boolean canShrinkExpand, int color, double minX, double minZ, double maxX, double maxZ) {
        this.id = id;
        this.world = world;
        this.cancelsExit = cancelsExit;
        this.canShrinkExpand = canShrinkExpand;
        this.color = color;
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
    }

    public PacketWorldBorder() {
    }

    public String getId() {
        return this.id;
    }
    public String getWorld() {
        return this.world;
    }
    public boolean isCancelsExit() {
        return this.cancelsExit;
    }
    public boolean isCanShrinkExpand() {
        return this.canShrinkExpand;
    }
    public int getColor() {
        return this.color;
    }
    public double getMinX() {
        return this.minX;
    }
    public double getMinZ() {
        return this.minZ;
    }
    public double getMaxX() {
        return this.maxX;
    }
    public double getMaxZ() {
        return this.maxZ;
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeOptional(this.id, buf::writeString);
        buf.writeString(this.world);
        buf.buf().writeBoolean(this.cancelsExit);
        buf.buf().writeBoolean(this.canShrinkExpand);
        buf.buf().writeInt(this.color);
        buf.buf().writeDouble(this.minX);
        buf.buf().writeDouble(this.minZ);
        buf.buf().writeDouble(this.maxX);
        buf.buf().writeDouble(this.maxZ);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.id = buf.readOptional(buf::readString);
        this.world = buf.readString();
        this.cancelsExit = buf.buf().readBoolean();
        this.canShrinkExpand = buf.buf().readBoolean();
        this.color = buf.buf().readInt();
        this.minX = buf.buf().readDouble();
        this.minZ = buf.buf().readDouble();
        this.maxX = buf.buf().readDouble();
        this.maxZ = buf.buf().readDouble();
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleWorldBorder(this);
    }

}
