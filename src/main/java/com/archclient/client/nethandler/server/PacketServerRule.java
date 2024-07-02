package com.archclient.client.nethandler.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.nethandler.IACNetHandler;
import com.archclient.client.nethandler.Packet;
import com.archclient.client.nethandler.client.IACNetHandlerClient;
import com.archclient.client.nethandler.obj.ServerRule;

import java.io.IOException;

public class PacketServerRule extends Packet {

    private ServerRule rule;
    private int intValue;
    private float floatValue;
    private boolean enabled;
    private String stringValue;

    public ServerRule getRule() {
        return this.rule;
    }
    public int getIntValue() {
        return this.intValue;
    }
    public float getFloatValue() {
        return this.floatValue;
    }
    public boolean isEnabled() {
        return this.enabled;
    }
    public String getStringValue() {
        return this.stringValue;
    }

    public PacketServerRule() {
        this.stringValue = "";
    }

    public PacketServerRule(ServerRule rule, float value) {
        this(rule);
        this.floatValue = value;
    }

    public PacketServerRule(ServerRule rule, boolean value) {
        this(rule);
        this.enabled = value;
    }

    public PacketServerRule(ServerRule rule, int value) {
        this(rule);
        this.intValue = value;
    }

    public PacketServerRule(ServerRule rule, String value) {
        this(rule);
        this.stringValue = value;
    }

    private PacketServerRule(ServerRule rule) {
        this.rule = rule;
        this.stringValue = "";
    }

    @Override
    public void write(ByteBufWrapper buf) throws IOException {
        buf.writeString(this.rule.getRuleName());
        buf.buf().writeBoolean(this.enabled);
        buf.buf().writeInt(this.intValue);
        buf.buf().writeFloat(this.floatValue);
        buf.writeString(this.stringValue);
    }

    @Override
    public void read(ByteBufWrapper buf) throws IOException {
        this.rule = ServerRule.getRuleName(buf.readString());
        this.enabled = buf.buf().readBoolean();
        this.intValue = buf.buf().readInt();
        this.floatValue = buf.buf().readFloat();
        this.stringValue = buf.readString();
    }

    @Override
    public void process(IACNetHandler handler) {
        ((IACNetHandlerClient)handler).handleServerRule(this);
    }

}
