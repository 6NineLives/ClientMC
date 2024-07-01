package com.archclient.client.module;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public List<AbstractModule> modules;
    public List<AbstractModule> staffModules;
    public AbstractModule llIIlllIIIIlllIllIlIlllIl;

    public ModuleManager() {
        this.modules = new ArrayList<>();
        this.staffModules = new ArrayList<>();
        for (AbstractModule staffModule : this.staffModules) {
            staffModule.setStaffModuleEnabled(true);
        }
    }
}
