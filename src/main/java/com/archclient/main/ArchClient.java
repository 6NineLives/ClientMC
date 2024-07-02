package com.archclient.main;

import net.lax1dude.eaglercraft.v1_8.IOUtils;
import net.lax1dude.eaglercraft.v1_8.internal.KeyboardConstants;
import net.lax1dude.eaglercraft.v1_8.internal.vfs2.VFile2;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.config.ConfigManager;
import com.archclient.client.config.GlobalSettings;
import com.archclient.client.config.Profile;
import com.archclient.client.event.EventBus;
import com.archclient.client.event.type.GuiDrawEvent;
import com.archclient.client.event.type.KeyboardEvent;
import com.archclient.client.event.type.PluginMessageEvent;
import com.archclient.client.event.type.RenderPreviewEvent;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.module.ModuleManager;
import com.archclient.client.nethandler.NetHandler;
import com.archclient.client.ui.module.ACModulePlaceGui;
import com.archclient.client.ui.module.ACModulesGui;
import com.archclient.client.ui.overlay.Alert;
import com.archclient.client.ui.overlay.OverlayGui;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.util.cosmetic.Cosmetic;
import com.archclient.client.util.friend.FriendsManager;
import com.archclient.client.util.friend.Status;
import com.archclient.client.util.thread.ServerStatusThread;
import com.archclient.client.util.title.TitleManager;
import com.archclient.client.util.worldborder.WorldBorderManager;
import com.archclient.client.websocket.AssetsWebSocket;
import com.archclient.impl.ref.BridgedGL;
import com.archclient.impl.ref.DrawingUtils;
import com.archclient.impl.ref.InstanceCreator;
import com.archclient.main.identification.MinecraftVersion;

public class ArchClient
{
    public static final MinecraftVersion MINECRAFT_VERSION = MinecraftVersion.v1_8_9;
    public static final Logger LOGGER = LogManager.getLogger("ArchClient");
    private static ArchClient instance;
    public static ArchClient getInstance() {
        return instance == null ? new ArchClient() : instance;
    }

    public static byte[] processBytesAuth = "Decencies".getBytes(); // originally "Vote Trump 2020!" (jhalt's doing LMAO???)
    public List<Profile> profiles;
    public Profile activeProfile;
    public GlobalSettings globalSettings;
    public ModuleManager moduleManager;
    public ConfigManager configManager;
    public EventBus eventBus;

    public List<ResourceLocation> presetLocations;

    public NetHandler netHandler;
    public TitleManager titleManager;
    public WorldBorderManager borderManager;

    private List<Cosmetic> cosmetics = new ArrayList<>();
    private AssetsWebSocket websocket;

    private FriendsManager friendsManager;
    private Status status;

    private boolean consoleAllowed;

    private List<String> consoleLines;

    private boolean acceptingFriendRequests;

    private final Map<String, ResourceLocation> playerSkins = new HashMap<>();

    private boolean initialized = false;

    public List<Profile> getProfiles() {
        return this.profiles;
    }
    public Profile getActiveProfile() {
        return this.activeProfile;
    }
    public GlobalSettings getGlobalSettings() {
        return this.globalSettings;
    }
    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }
    public ConfigManager getConfigManager() {
        return this.configManager;
    }
    public EventBus getEventBus() {
        return this.eventBus;
    }
    public List<ResourceLocation> getPresetLocations() {
        return this.presetLocations;
    }
    public NetHandler getNetHandler() {
        return this.netHandler;
    }
    public TitleManager getTitleManager() {
        return this.titleManager;
    }
    public WorldBorderManager getBorderManager() {
        return this.borderManager;
    }
    public List<Cosmetic> getCosmetics() {
        return this.cosmetics;
    }
    public AssetsWebSocket getWebsocket() {
        return this.websocket;
    }
    public FriendsManager getFriendsManager() {
        return this.friendsManager;
    }
    public Status getStatus() {
        return this.status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setConsoleAllowed(boolean consoleAllowed) {
        this.consoleAllowed = consoleAllowed;
    }
    public List<String> getConsoleLines() {
        return this.consoleLines;
    }
    public boolean isAcceptingFriendRequests() {
        return this.acceptingFriendRequests;
    }
    public void setAcceptingFriendRequests(boolean acceptingFriendRequests) {
        this.acceptingFriendRequests = acceptingFriendRequests;
    }
    public Map<String, ResourceLocation> getPlayerSkins() {
        return this.playerSkins;
    }
    public boolean isInitialized() {
        return this.initialized;
    }

    public boolean isConsoleAllowed() {
        return true;
    }

    public ArchClient() {
        ArchClient.instance = this;
    }

    public void acInfo(String msg) {
        System.out.println("[AC] " + msg);
    }

    public void acInfo(String msg, Class<?> loadedClass) {
        this.acInfo(msg + " (" + loadedClass.getCanonicalName() + ")");
    }

    public void initialize() {
        if (!this.initialized) {
            Ref.setMinecraftVersion(MINECRAFT_VERSION);
            Ref.setGlBridge(new BridgedGL());
            Ref.setDrawingUtils(new DrawingUtils());
            Ref.setMinecraft(Minecraft.getMinecraft());
            //Ref.setI18n(I18n::format);
            //Ref.setBlockRegistry(Utils.iteratorToIterable(Utils.convertIterationType(Block.blockRegistry.iterator())));
            //Ref.setBossStatus(new BridgedBossStatus());
            //Ref.setRenderHelper(new BridgedRenderHelper());
            //Ref.setRenderManager(Ref.getMinecraft().bridge$getRenderManager());
            //Ref.setForgeEventBus(MinecraftForge.EVENT_BUS::register);
            Ref.setTessellator(Tessellator.getInstance());
            Ref.setInstanceCreator(new InstanceCreator());
            //Ref.setUtils(new RefUtils());

            Ref.getImplementations().setTextureUtil(new TextureUtil());

            this.presetLocations = new ArrayList<>();
            this.cosmetics = new ArrayList<>();
            this.profiles = new ArrayList<>();
            this.consoleLines = new ArrayList<>();
            this.acInfo("Starting ArchClient setup...");
            this.createDefaultConfigPresets();
            this.acInfo("Created default configuration presets.");
            this.globalSettings = new GlobalSettings();
            this.acInfo("Created Settings Manager", GlobalSettings.class);
            this.eventBus = new EventBus();
            this.acInfo("Created Event Bus", EventBus.class);
            this.moduleManager = new ModuleManager();
            this.acInfo("Created Module Manager", ModuleManager.class);
            this.netHandler = new NetHandler();
            this.acInfo("Created Network Handler", NetHandler.class);
            this.titleManager = new TitleManager();
            this.acInfo("Created Title Manager", TitleManager.class);
            this.cosmetics.add(new Cosmetic("Steve", "ArchClient Cape", 1.0f, true, "client/defaults/cb.png"));
            this.cosmetics.add(new Cosmetic("Steve", "ArchClient Black Cape", 1.0f, false, "client/defaults/cb_black.png"));
            this.status = Status.AWAY;
            this.loadProfiles();
            this.acInfo("Loaded " + this.profiles.size() + " custom profiles.");
            (this.configManager = new ConfigManager()).read();
            this.acInfo("Created Configuration Manager", ConfigManager.class);

            this.acInfo("Connecting to websocket server...");
            this.connectToAssetsServer();
            this.friendsManager = new FriendsManager();
            this.acInfo("Created Friends Manager", FriendsManager.class);
            OverlayGui.setInstance(new OverlayGui());
            this.acInfo("Created Overlay UI", OverlayGui.class);

            this.eventBus.addEvent(PluginMessageEvent.class, this.netHandler::onPluginMessage);
            this.eventBus.addEvent(KeyboardEvent.class, (e) -> {
                if (e.getKeyboardKey() == KeyboardConstants.KEY_H) {
                    Alert.displayMessage("Hello", "Hello, World\nNew Line");
                }
                if (e.getKeyboardKey() == KeyboardConstants.KEY_RSHIFT) {
                    if (Ref.getMinecraft().bridge$isIngame()) {
                        Ref.getMinecraft().bridge$displayGuiScreen(new ACModulesGui());
                    }
                }
                if (e.getKeyboardKey() == KeyboardConstants.KEY_F9) {
                    RenderUtil.minFps = 2147483647;
                    RenderUtil.maxFps = 0;
                }
                if (e.getKeyboardKey() == KeyboardConstants.KEY_F10) {
                    new ServerStatusThread().start();
                }
            });
            this.acInfo("Registered main events.");

            new ServerStatusThread().start();
            this.acInfo("Loaded version data.");
            this.borderManager = new WorldBorderManager();
            this.acInfo("Created World Border Manager", WorldBorderManager.class);

            this.initialized = true;
        }
    }

    private void createDefaultConfigPresets() {
        VFile2 file = ConfigManager.profilesDir;
        for (ResourceLocation location : presetLocations) {
            VFile2 file2 = new VFile2(file, location.getResourcePath().replaceAll("([a-zA-Z0-9/]+)/", ""));
            if (!file2.exists()) {
                try {
                    InputStream stream = Ref.getMinecraft().getResourceManager().getResource(location).getInputStream();
                    byte[] bytes = new byte[1024];
                    IOUtils.readFully(stream, bytes);
                    file2.setAllBytes(bytes);
                    stream.close();
                }
                catch (final Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void loadProfiles() {
        this.profiles.add(new Profile("default", false));
        final VFile2 dir = ConfigManager.profilesDir;
        final List<VFile2> files;
        if (dir.exists() && (files = dir.listFiles(false)) != null) {
            for (final VFile2 file : files) {
                if (file.getName().endsWith(".cfg")) {
                    this.profiles.add(new Profile(file.getName().replace(".cfg", ""), true));
                }
            }
        }
    }

    private String getNewProfileName(final String base) {
        final VFile2 dir = ConfigManager.profilesDir;
        if (new VFile2(dir, base + ".cfg").exists()) {
            return this.getNewProfileName(base + "1");
        }
        return base;
    }

    public void createNewProfile() {
        if (this.activeProfile == this.profiles.get(0)) {
            final Profile profile = new Profile(this.getNewProfileName("Profile 1"), true);
            this.activeProfile = profile;
            this.profiles.add(profile);
            this.configManager.write();
        }
    }

    public boolean isUsingStaffModules() {
        for (final AbstractModule acModule : this.moduleManager.staffModules) {
            if (acModule.isStaffEnabledModule()) {
                return true;
            }
        }
        return false;
    }

    public float getScaleFactor() {
        if (ArchClient.getInstance().getGlobalSettings().followMinecraftScale.<Boolean>value()) {
            return 1f;
        } else {
            return Ref.getInstanceCreator().createScaledResolution().getScaleFactor() / 2f;
        }
    }

    public void sendSound(final String sound) {
        this.sendSound(sound, 1.0f);
    }

    public void sendSound(final String sound, final float volume) {
        if (!this.globalSettings.muteArchClientSounds.<Boolean>value()) {
            //xxxRef.getMinecraft().getSoundHandler().getSoundManager().playSound(sound, volume);
        }
    }

    public ResourceLocation getHeadLocation(String displayName) {
        ResourceLocation playerSkin = this.playerSkins.getOrDefault(displayName, Ref.getInstanceCreator()
                .createResourceLocation("client/heads/" + displayName + ".png"));
        if (!this.playerSkins.containsKey(displayName)) {
            //ThreadDownloadImageDataBridge skinData = Ref.getInstanceCreator()XXX
            //        .createThreadDownloadImageData(null, "https://minotar.net/helm/" + displayName + "/32.png",
            //                playerSkin,
            //                Ref.getInstanceCreator().createResourceLocation("client/defaults/steve.png"),
            //                null);
            //Ref.getMinecraft().getTextureManager().loadTexture(playerSkin, skinData);
            this.playerSkins.put(displayName, playerSkin);
        }
        return playerSkin;
    }

    public String getPluginMessageChannel() {
        return "AC-Client";
    }

    public String getPluginBinaryChannel() {
        return "AC-Binary";
    }

    public void connectToAssetsServer() {
        final Map<String, String> hashMap = new HashMap<>();
        hashMap.put("username", Ref.getMinecraft().getSession().getProfile().getName());
        hashMap.put("playerId", Ref.getMinecraft().getSession().getProfile().getId().toString());
        hashMap.put("version", "");
        try {
            // TODO: Host websocket
            (this.websocket = new AssetsWebSocket(new URI("ws://localhost:80"), hashMap)).connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public AssetsWebSocket getAssetsWebSocket() {
        return websocket;
    }

    public String getStatusString() {
        String s;
        switch (this.getStatus()) {
            case AWAY: {
                s = "Away";
                break;
            }
            case BUSY: {
                s = "Busy";
                break;
            }
            case HIDDEN: {
                s = "Hidden";
                break;
            }
            default: {
                s = "Online";
                break;
            }
        }
        return s;
    }

    public void removeCosmeticsFromPlayer(final String playerId) {
        this.cosmetics.removeIf(cosmetic -> cosmetic.getPlayerId().equals(playerId));
    }

    public void renderGameOverlay() {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledResolution = new ScaledResolution(mc);

        if (!mc.gameSettings.showDebugInfo) {
            ArchClient.getInstance().getEventBus().callEvent(new GuiDrawEvent(scaledResolution));
        }

        if (mc.bridge$getCurrentScreen() instanceof ACModulesGui || mc.bridge$getCurrentScreen() instanceof ACModulePlaceGui) {
            ArchClient.getInstance().getEventBus().callEvent(new RenderPreviewEvent(scaledResolution));
        }

        if (!mc.gameSettings.hideGUI) {
            OverlayGui.getInstance().renderGameOverlay();
        }
    }

    public boolean isInDebugMode() {
        return true;
    }
}
