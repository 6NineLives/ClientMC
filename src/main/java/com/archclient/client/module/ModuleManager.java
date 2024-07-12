package com.archclient.client.module;

import com.archclient.client.module.staff.XRayModule;
import com.archclient.client.module.type.*;
import com.archclient.client.module.type.armourstatus.ArmourStatusModule;
import com.archclient.client.module.type.bossbar.BossBarModule;
import com.archclient.client.module.type.cooldowns.CooldownsModule;
import com.archclient.client.module.type.keystrokes.KeystrokesModule;
import com.archclient.client.module.type.notifications.ACNotificationsModule;
import com.archclient.client.module.type.togglesprint.ToggleSprintModule;
import com.archclient.client.util.voicechat.VoiceChat;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public List<AbstractModule> modules;
    public List<AbstractModule> staffModules;
    public AbstractModule llIIlllIIIIlllIllIlIlllIl;

    public ToggleSprintModule toggleSprint;
    public ACNotificationsModule notifications;
    public ArmourStatusModule armourStatus;
    public CooldownsModule cooldowns;
    public ScoreboardModule scoreboard;
    public XRayModule xray;
    public PotionStatusModule potionStatus;
    public BossBarModule bossBar;
    public DirectionHudModule directionHud;
    public KeystrokesModule keyStrokes;
    public FPSModule fpsModule;
    public CPSModule cpsModule;
    public CoordinatesModule coordinatesModule;
    public VoiceChat voiceChat;
    public TeammatesModule teammatesModule;

    public ModuleManager() {
        this.modules = new ArrayList<>();
        this.staffModules = new ArrayList<>();

        this.modules.add(this.coordinatesModule = new CoordinatesModule());
        this.modules.add(this.toggleSprint = new ToggleSprintModule());
        this.modules.add(this.potionStatus = new PotionStatusModule());
        this.modules.add(this.armourStatus = new ArmourStatusModule());
        this.modules.add(this.keyStrokes = new KeystrokesModule());
        this.modules.add(this.scoreboard = new ScoreboardModule());
        this.modules.add(this.cooldowns = new CooldownsModule());
        this.modules.add(this.notifications = new ACNotificationsModule());
        this.modules.add(this.directionHud = new DirectionHudModule());
        this.modules.add(this.bossBar = new BossBarModule());
        this.modules.add(this.cpsModule = new CPSModule());
        this.modules.add(this.fpsModule = new FPSModule());
        this.voiceChat = new VoiceChat();
        this.teammatesModule = new TeammatesModule();
        this.teammatesModule.lIIIIlIIllIIlIIlIIIlIIllI(true);

        this.staffModules.add(this.xray = new XRayModule());
        for (AbstractModule staffModule : this.staffModules) {
            staffModule.setStaffModuleEnabled(true);
        }
    }
}
