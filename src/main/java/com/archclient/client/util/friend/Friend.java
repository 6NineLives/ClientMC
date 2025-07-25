package com.archclient.client.util.friend;

import com.archclient.bridge.util.EnumChatFormattingBridge;
import com.archclient.client.ui.util.Color;

import java.util.Objects;

public class Friend {
    private final String playerId;
    private String name;
    private String status;
    private String server;
    private boolean online;
    private long offlineSince;
    private Status onlineStatus;

    public static int getStatusColor(Status aCStatusEnum) {
        if (aCStatusEnum == null) {
            return -13158601;
        }
        int n;
        switch (aCStatusEnum) {
            case AWAY: {
                n = new Color(-1722581).getRGB();
                break;
            }
            case BUSY: {
                n = new Color(-1758421).getRGB();
                break;
            }
            case HIDDEN: {
                n = new Color(-13158601).getRGB();
                break;
            }
            default: {
                n = -13369549;
                break;
            }
        }
        return n;
    }

    public String getStatusString() {
        String string;
        if (this.online) {
            if (this.server != null && !Objects.equals(this.server, "")) {
                string = "Playing" + EnumChatFormattingBridge.BOLD + " " + this.server;
            } else {
                switch (this.onlineStatus) {
                    case AWAY: {
                        string = "Away";
                        break;
                    }
                    case BUSY: {
                        string = "Busy";
                        break;
                    }
                    default: {
                        string = "Online";
                        break;
                    }
                }
            }
        } else {
            long l = System.currentTimeMillis() - this.offlineSince;
            long l2 = 1000L;
            long l3 = l2 * 60L;
            long l4 = l3 * 60L;
            long l5 = l4 * 24L;
            long l6 = l / l5;
            long l7 = (l %= l5) / l4;
            long l8 = l % l4 / l3;
            string = l6 > 0L ? "Offline for " + l6 + (l6 == 1L ? " day" : " days") : (l7 > 0L ? "Offline for " + l7 + (l7 == 1L ? " hour" : " hours") : "Offline for " + l8 + (l8 == 1L ? " minute" : " minutes"));
        }
        return string;
    }

    Friend(String string, String string2, String string3, String string4, boolean bl, long l, Status aCStatusEnum) {
        this.playerId = string;
        this.name = string2;
        this.status = string3;
        this.server = string4;
        this.online = bl;
        this.offlineSince = l;
        this.onlineStatus = aCStatusEnum;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPlayerId() {
        return this.playerId;
    }

    public String getName() {
        return this.name;
    }

    public String getStatus() {
        return this.status;
    }

    public String getServer() {
        return this.server;
    }

    public boolean isOnline() {
        return this.online;
    }

    public long getOfflineSince() {
        return this.offlineSince;
    }

    public Status getOnlineStatus() {
        return this.onlineStatus;
    }

    public void setName(String string) {
        this.name = string;
    }

    public void setStatus(String string) {
        this.status = string;
    }

    public void setServer(String string) {
        this.server = string;
    }

    public void setOnline(boolean bl) {
        this.online = bl;
    }

    public void setOfflineSince(long l) {
        this.offlineSince = l;
    }

    public void setOnlineStatus(Status aCStatusEnum) {
        this.onlineStatus = aCStatusEnum;
    }

    public static class Builder {
        private String playerId;
        private String name;
        private String status;
        private String server;
        private boolean online;
        private long offlineSince;
        private Status onlineStatus;

        Builder() {
        }

        public Builder playerId(String string) {
            this.playerId = string;
            return this;
        }

        public Builder name(String string) {
            this.name = string;
            return this;
        }

        public Builder status(String string) {
            this.status = string;
            return this;
        }

        public Builder server(String string) {
            this.server = string;
            return this;
        }

        public Builder online(boolean bl) {
            this.online = bl;
            return this;
        }

        public Builder offlineSince(long l) {
            this.offlineSince = l;
            return this;
        }

        public Builder onlineStatus(Status aCStatusEnum) {
            this.onlineStatus = aCStatusEnum;
            return this;
        }

        public Friend build() {
            return new Friend(this.playerId, this.name, this.status, this.server, this.online, this.offlineSince, this.onlineStatus);
        }

        public String toString() {
            return "Friend.FriendBuilder(playerId=" + this.playerId + ", name=" + this.name + ", status=" + this.status + ", server=" + this.server + ", online=" + this.online + ", offlineSince=" + this.offlineSince + ", onlineStatus=" + this.onlineStatus + ")";
        }
    }

}
