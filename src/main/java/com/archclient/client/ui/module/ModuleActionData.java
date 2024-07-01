package com.archclient.client.ui.module;

import com.archclient.client.module.AbstractModule;

import java.util.ArrayList;
import java.util.List;

class ModuleActionData {
    protected List<AbstractModule> modules;
    List<ACGuiAnchor> anchors;
    List<Float> xTranslations;
    List<Float> yTranslations;
    List<Float> scales;
    final ACModulesGui parent;

    ModuleActionData(ACModulesGui parent, List<ACModulePosition> list) {
        this.parent = parent;
        ArrayList<AbstractModule> modules = new ArrayList<>();
        ArrayList<ACGuiAnchor> anchors = new ArrayList<>();
        ArrayList<Float> xTranslations = new ArrayList<>();
        ArrayList<Float> yTranslations = new ArrayList<>();
        ArrayList<Float> scales = new ArrayList<>();
        for (ACModulePosition position : list) {
            if (position.module.getGuiAnchor() == null) continue;
            modules.add(position.module);
            anchors.add(position.module.getGuiAnchor());
            xTranslations.add(position.module.getXTranslation());
            yTranslations.add(position.module.getYTranslation());
            scales.add(position.module.scale.<Float>value());
        }
        this.modules = modules;
        this.anchors = anchors;
        this.xTranslations = xTranslations;
        this.yTranslations = yTranslations;
        this.scales = scales;
    }
}