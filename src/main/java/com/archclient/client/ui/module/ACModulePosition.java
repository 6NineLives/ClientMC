package com.archclient.client.ui.module;

import com.archclient.client.module.AbstractModule;

public class ACModulePosition {

    protected AbstractModule module;
    protected float x;
    protected float y;

    ACModulePosition(AbstractModule aCModule, float x, float y) {
        this.module = aCModule;
        this.x = x;
        this.y = y;
    }

}
