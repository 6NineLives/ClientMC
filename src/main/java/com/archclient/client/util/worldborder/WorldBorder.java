package com.archclient.client.util.worldborder;

import com.archclient.client.ui.util.Color;
import com.archclient.client.util.javafx.geom.Vec2d;
import com.archclient.main.ArchClient;

import net.minecraft.entity.Entity;

public class WorldBorder {
    private String lIIIIIIIIIlIllIIllIlIIlIl;
    private String world;
    private Color IIIIllIlIIIllIlllIlllllIl;
    private Vec2d IIIIllIIllIIIIllIllIIIlIl;
    private Vec2d IlIlIIIlllIIIlIlllIlIllIl;
    private Vec2d IIIllIllIlIlllllllIlIlIII;
    private Vec2d IllIIIIIIIlIlIllllIIllIII;
    private Vec2d lIIIIllIIlIlIllIIIlIllIlI;
    private Vec2d IlllIllIlIIIIlIIlIIllIIIl;
    private boolean IlIlllIIIIllIllllIllIIlIl;
    private boolean llIIlllIIIIlllIllIlIlllIl;
    private int lIIlIlIllIIlIIIlIIIlllIII;
    private int IIIlllIIIllIllIlIIIIIIlII;
    final WorldBorderManager worldBorder;

    public WorldBorder(WorldBorderManager aCWorldBorder, String string, String string2, int n, double d, double d2, double d3, double d4, boolean bl, boolean bl2) {
        this.worldBorder = aCWorldBorder;
        this.lIIIIIIIIIlIllIIllIlIIlIl = string;
        this.world = string2;
        this.IIIIllIlIIIllIlllIlllllIl = new Color(n, true);
        this.IlIlllIIIIllIllllIllIIlIl = bl;
        this.llIIlllIIIIlllIllIlIlllIl = bl2;
        this.IIIllIllIlIlllllllIlIlIII = this.IIIIllIIllIIIIllIllIIIlIl = new Vec2d(d, d2);
        this.IllIIIIIIIlIlIllllIIllIII = this.IlIlIIIlllIIIlIlllIlIllIl = new Vec2d(d3, d4);
    }

    public boolean lIIIIlIIllIIlIIlIIIlIIllI() {
        return this.IlIlllIIIIllIllllIllIIlIl && this.IIIlllIIIllIllIlIIIIIIlII != 0 && this.lIIlIlIllIIlIIIlIIIlllIII < this.IIIlllIIIllIllIlIIIIIIlII;
    }

    public boolean lIIIIlIIllIIlIIlIIIlIIllI(double d, double d2) {
        return !this.llIIlllIIIIlllIllIlIlllIl || !this.world.equals(WorldBorderManager.lIIIIlIIllIIlIIlIIIlIIllI(this.worldBorder).getNetHandler().getWorld()) || d + 1.0 > this.IlIlIIIlllIIIlIlllIlIllIl() && d < this.IIIIllIlIIIllIlllIlllllIl() && d2 + 1.0 > this.IIIllIllIlIlllllllIlIlIII() && d2 < this.IIIIllIIllIIIIllIllIIIlIl();
    }

    public void ting() {
        if (this.lIIIIlIIllIIlIIlIIIlIIllI()) {
            double d = this.IIIIllIIllIIIIllIllIIIlIl.x - this.lIIIIllIIlIlIllIIIlIllIlI.x;
            double d2 = this.IlIlIIIlllIIIlIlllIlIllIl.x - this.IlllIllIlIIIIlIIlIIllIIIl.x;
            double d3 = this.IIIIllIIllIIIIllIllIIIlIl.y - this.lIIIIllIIlIlIllIIIlIllIlI.y;
            double d4 = this.IlIlIIIlllIIIlIlllIlIllIl.y - this.IlllIllIlIIIIlIIlIIllIIIl.y;
            double d5 = (float)this.lIIlIlIllIIlIIIlIIIlllIII / (float)this.IIIlllIIIllIllIlIIIIIIlII;
            double d6 = this.IIIIllIIllIIIIllIllIIIlIl.x - d * d5;
            double d7 = this.IlIlIIIlllIIIlIlllIlIllIl.x - d2 * d5;
            double d8 = this.IIIIllIIllIIIIllIllIIIlIl.y - d3 * d5;
            double d9 = this.IlIlIIIlllIIIlIlllIlIllIl.y - d4 * d5;
            this.IIIllIllIlIlllllllIlIlIII = new Vec2d(d6, d8);
            this.IllIIIIIIIlIlIllllIIllIII = new Vec2d(d7, d9);
            ++this.lIIlIlIllIIlIIIlIIIlllIII;
        } else if (this.IlIlIIIlllIIIlIlllIlIllIl != this.IllIIIIIIIlIlIllllIIllIII || this.IIIIllIIllIIIIllIllIIIlIl != this.IIIllIllIlIlllllllIlIlIII) {
            this.IIIIllIIllIIIIllIllIIIlIl = this.IIIllIllIlIlllllllIlIlIII;
            this.IlIlIIIlllIIIlIlllIlIllIl = this.IllIIIIIIIlIlIllllIIllIII;
            this.IllIIIIIIIlIlIllllIIllIII = this.IlllIllIlIIIIlIIlIIllIIIl;
            this.IIIllIllIlIlllllllIlIlIII = this.lIIIIllIIlIlIllIIIlIllIlI;
            this.IIIlllIIIllIllIlIIIIIIlII = 0;
            this.lIIlIlIllIIlIIIlIIIlllIII = 0;
        }
    }

    public boolean worldEqualsWorld() {
        return ArchClient.getInstance().getNetHandler().getWorld().equals(this.world);
    }

    public double IIIIllIlIIIllIlllIlllllIl() {
        return this.IllIIIIIIIlIlIllllIIllIII.x;
    }

    public double IIIIllIIllIIIIllIllIIIlIl() {
        return this.IllIIIIIIIlIlIllllIIllIII.y;
    }

    public double IlIlIIIlllIIIlIlllIlIllIl() {
        return this.IIIllIllIlIlllllllIlIlIII.x;
    }

    public double IIIllIllIlIlllllllIlIlIII() {
        return this.IIIllIllIlIlllllllIlIlIII.y;
    }

    public double lIIIIlIIllIIlIIlIIIlIIllI(Entity entity) {
        return this.lIIIIIIIIIlIllIIllIlIIlIl(entity.posX, entity.posZ);
    }

    public double lIIIIIIIIIlIllIIllIlIIlIl(double d, double d2) {
        double d3 = d2 - this.IIIllIllIlIlllllllIlIlIII();
        double d4 = this.IIIIllIIllIIIIllIllIIIlIl() - d2;
        double d5 = d - this.IlIlIIIlllIIIlIlllIlIllIl();
        double d6 = this.IIIIllIlIIIllIlllIlllllIl() - d;
        double d7 = Math.min(d5, d6);
        d7 = Math.min(d7, d3);
        return Math.min(d7, d4);
    }

    public double IlllIIIlIlllIllIlIIlllIlI(double d, double d2) {
        double d3 = d2 - this.IIIllIllIlIlllllllIlIlIII();
        double d4 = this.IIIIllIIllIIIIllIllIIIlIl() - d2;
        return Math.min(d3, d4);
    }

    public double IIIIllIlIIIllIlllIlllllIl(double d, double d2) {
        double d3 = d - this.IlIlIIIlllIIIlIlllIlIllIl();
        double d4 = this.IIIIllIlIIIllIlllIlllllIl() - d;
        return Math.min(d3, d4);
    }

    public String IllIIIIIIIlIlIllllIIllIII() {
        return this.lIIIIIIIIIlIllIIllIlIIlIl;
    }

    public String lIIIIllIIlIlIllIIIlIllIlI() {
        return this.world;
    }

    public Color IlllIllIlIIIIlIIlIIllIIIl() {
        return this.IIIIllIlIIIllIlllIlllllIl;
    }

    public Vec2d IlIlllIIIIllIllllIllIIlIl() {
        return this.IIIIllIIllIIIIllIllIIIlIl;
    }

    public Vec2d llIIlllIIIIlllIllIlIlllIl() {
        return this.IlIlIIIlllIIIlIlllIlIllIl;
    }

    public Vec2d lIIlIlIllIIlIIIlIIIlllIII() {
        return this.IIIllIllIlIlllllllIlIlIII;
    }

    public Vec2d IIIlllIIIllIllIlIIIIIIlII() {
        return this.IllIIIIIIIlIlIllllIIllIII;
    }

    public Vec2d llIlIIIlIIIIlIlllIlIIIIll() {
        return this.lIIIIllIIlIlIllIIIlIllIlI;
    }

    public Vec2d IIIlIIllllIIllllllIlIIIll() {
        return this.IlllIllIlIIIIlIIlIIllIIIl;
    }

    public boolean lllIIIIIlIllIlIIIllllllII() {
        return this.IlIlllIIIIllIllllIllIIlIl;
    }

    public boolean lIIIIIllllIIIIlIlIIIIlIlI() {
        return this.llIIlllIIIIlllIllIlIlllIl;
    }

    public int IIIIIIlIlIlIllllllIlllIlI() {
        return this.lIIlIlIllIIlIIIlIIIlllIII;
    }

    public int IllIllIIIlIIlllIIIllIllII() {
        return this.IIIlllIIIllIllIlIIIIIIlII;
    }

    public static String getPlayer(WorldBorder iIIlIllIIIlllIIlIIllIlIII) {
        return iIIlIllIIIlllIIlIIllIlIII.lIIIIIIIIIlIllIIllIlIIlIl;
    }

    public static Vec2d lIIIIlIIllIIlIIlIIIlIIllI(WorldBorder iIIlIllIIIlllIIlIIllIlIII, Vec2d vec2d) {
        iIIlIllIIIlllIIlIIllIlIII.lIIIIllIIlIlIllIIIlIllIlI = vec2d;
        return iIIlIllIIIlllIIlIIllIlIII.lIIIIllIIlIlIllIIIlIllIlI;
    }

    public static Vec2d lIIIIIIIIIlIllIIllIlIIlIl(WorldBorder iIIlIllIIIlllIIlIIllIlIII, Vec2d vec2d) {
        iIIlIllIIIlllIIlIIllIlIII.IlllIllIlIIIIlIIlIIllIIIl = vec2d;
        return iIIlIllIIIlllIIlIIllIlIII.IlllIllIlIIIIlIIlIIllIIIl;
    }

    public static int lIIIIlIIllIIlIIlIIIlIIllI(WorldBorder iIIlIllIIIlllIIlIIllIlIII, int n) {
        iIIlIllIIIlllIIlIIllIlIII.lIIlIlIllIIlIIIlIIIlllIII = n;
        return iIIlIllIIIlllIIlIIllIlIII.lIIlIlIllIIlIIIlIIIlllIII;
    }

    public static int lIIIIIIIIIlIllIIllIlIIlIl(WorldBorder iIIlIllIIIlllIIlIIllIlIII, int n) {
        iIIlIllIIIlllIIlIIllIlIII.IIIlllIIIllIllIlIIIIIIlII = n;
        return iIIlIllIIIlllIIlIIllIlIII.IIIlllIIIllIllIlIIIIIIlII;
    }

    public static boolean lIIIIIIIIIlIllIIllIlIIlIl(WorldBorder iIIlIllIIIlllIIlIIllIlIII) {
        return iIIlIllIIIlllIIlIIllIlIII.IlIlllIIIIllIllllIllIIlIl;
    }

    public static Color IlllIIIlIlllIllIlIIlllIlI(WorldBorder iIIlIllIIIlllIIlIIllIlIII) {
        return iIIlIllIIIlllIIlIIllIlIII.IIIIllIlIIIllIlllIlllllIl;
    }
}