package com.archclient.client.config;

/**
 * Represents a profile to be stored to the disk.
 * @author Decencies
 */
public class Profile {

    private int index = 0;
    private final boolean editable;
    private String name;

    public Profile(String name, boolean editable) {
        this.name = name;
        this.editable = editable;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
