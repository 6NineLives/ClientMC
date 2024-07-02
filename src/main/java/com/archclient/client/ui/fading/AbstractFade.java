package com.archclient.client.ui.fading;

public abstract class AbstractFade {
    protected long startTime;
    protected long lIIIIIIIIIlIllIIllIlIIlIl;
    protected long duration;
    protected boolean expireAtEndTime = true;
    protected float IIIIllIIllIIIIllIllIIIlIl;
    protected long timeElapsed;
    protected final float currentFadePercentage;
    private boolean shouldResetOnceCalled;
    private int lIIIIllIIlIlIllIIIlIllIlI = 1;
    private long inputtedTimeElapsed;
    private boolean currentlyInverted;
    public long getStartTime() {
        return this.startTime;
    }
    public long getDuration() {
        return this.duration;
    }
    public long getTimeElapsed() {
        return this.timeElapsed;
    }
    public boolean isCurrentlyInverted() {
        return this.currentlyInverted;
    }

    public AbstractFade(long duration, float f) {
        this.duration = duration;
        this.currentFadePercentage = f;
    }

    protected abstract float getValue();

    public void reset() {
        this.startTime = System.currentTimeMillis();
        this.expireAtEndTime = true;
        this.inputtedTimeElapsed = 0L;
    }

    public void lIIIIlIIllIIlIIlIIIlIIllI(float f) {
        this.startTime = System.currentTimeMillis();
        this.inputtedTimeElapsed = f == 0.0f ? 0L : (long)((float)this.duration * (1.0f - f));
        this.expireAtEndTime = true;
    }

    public void enableShouldResetOnceCalled() {
        this.shouldResetOnceCalled = true;
    }

    public boolean hasStartTime() {
        return this.startTime != 0L;
    }

    public boolean isExpired() {
        return this.getTimeUntilEnd() <= 0L && this.expireAtEndTime;
    }

    public void IlIlIIIlllIIIlIlllIlIllIl() {
        this.startTime = 0L;
        this.lIIIIllIIlIlIllIIIlIllIlI = 1;
    }

    public float lIIIIlIIllIIlIIlIIIlIIllI(boolean bl) {
        if (bl && !this.currentlyInverted) {
            this.currentlyInverted = true;
            this.lIIIIlIIllIIlIIlIIIlIIllI(this.getValueInternal());
        } else if (this.currentlyInverted && !bl) {
            this.currentlyInverted = false;
            this.lIIIIlIIllIIlIIlIIIlIIllI(this.getValueInternal());
        }
        if (this.startTime == 0L) {
            return 0.0f;
        }
        float f = this.getValueInternal();
        return this.currentlyInverted ? f : 1.0f - f;
    }

    public boolean isFadeOngoing() {
        return this.startTime != 0L && this.getTimeUntilEnd() > 0L;
    }

    private float getValueInternal() {
        if (this.startTime == 0L) {
            return 0.0f;
        }
        if (this.getTimeUntilEnd() <= 0L) {
            return 1.0f;
        }
        return this.getValue();
    }

    public float getCurrentValue() {
        if (this.startTime == 0L) {
            return 0.0f;
        }
        if (this.isExpired()) {
            if (this.shouldResetOnceCalled || this.lIIIIllIIlIlIllIIIlIllIlI < 1) {
                this.reset();
                ++this.lIIIIllIIlIlIllIIIlIllIlI;
            }
            return this.currentFadePercentage;
        }
        if (this.expireAtEndTime) {
            return this.getValue();
        }
        return this.IIIIllIIllIIIIllIllIIIlIl;
    }

    public void lIIIIllIIlIlIllIIIlIllIlI() {
        this.expireAtEndTime = false;
        this.IIIIllIIllIIIIllIllIIIlIl = this.getValue();
        this.timeElapsed = System.currentTimeMillis() - this.startTime;
    }

    public void IlllIllIlIIIIlIIlIIllIIIl() {
        this.startTime = System.currentTimeMillis() - this.timeElapsed;
        this.expireAtEndTime = true;
    }

    public long IlIlllIIIIllIllllIllIIlIl() {
        long l = this.expireAtEndTime ? this.getTimeUntilEnd() : System.currentTimeMillis() - this.timeElapsed + this.duration - System.currentTimeMillis();
        return Math.min(this.duration, Math.max(0L, l));
    }

    protected long getTimeUntilEnd() {
        return this.startTime + this.duration - this.inputtedTimeElapsed - System.currentTimeMillis();
    }
}
