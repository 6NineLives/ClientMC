package com.archclient.client.util.teammates;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.ui.util.Color;

import net.minecraft.util.Vec3;

public class Teammate {
    private String uuid;
    private boolean leader = false;
    private Vec3 vector3D;
    private long lastUpdated;
    private Color color;
    private long waitTime;
    public String getUuid() {
        return this.uuid;
    }
    public boolean isLeader() {
        return this.leader;
    }
    public Vec3 getVector3D() {
        return this.vector3D;
    }
    public long getLastUpdated() {
        return this.lastUpdated;
    }
    public Color getColor() {
        return this.color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public long getWaitTime() {
        return this.waitTime;
    }

    public Teammate(String string, boolean bl) {
        this.uuid = string;
        this.leader = bl;
        this.lastUpdated = System.currentTimeMillis();
    }

    public void reset(double x, double y, double z, long waitTime) {
        this.vector3D = Ref.getInstanceCreator().createVec3(x, y, z);
        this.lastUpdated = System.currentTimeMillis();
        this.waitTime = waitTime;
    }
}
 