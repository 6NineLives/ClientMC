package com.archclient.client.module.staff;

import com.archclient.client.config.Setting;
import com.archclient.client.module.AbstractModule;

public abstract class StaffModule extends AbstractModule {
    private final Setting keybind = new Setting(this, "Keybind").setValue(0);

    public Setting getKeybindSetting() {
        return this.keybind;
    }

    public StaffModule(String string) {
        super(string);
    }

    public void disableStaffModule() {
        if (this.isEnabled()) {
            this.setState(false);
        }
        this.setStaffModuleEnabled(false);
    }
}
