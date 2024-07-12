package com.archclient.client.nethandler;

import com.archclient.bridge.ref.Ref;
import com.archclient.bridge.util.EnumChatFormattingBridge;
import com.archclient.main.ArchClient;
import com.archclient.client.event.type.PluginMessageEvent;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.module.staff.StaffModule;
import com.archclient.client.module.type.cooldowns.CooldownsModule;
import com.archclient.client.nethandler.client.IACNetHandlerClient;
import com.archclient.client.nethandler.client.PacketVoiceChannelSwitch;
import com.archclient.client.nethandler.server.*;
import com.archclient.client.nethandler.shared.PacketAddWaypoint;
import com.archclient.client.nethandler.shared.PacketRemoveWaypoint;
import com.archclient.client.ui.util.Color;
import com.archclient.client.util.hologram.Hologram;
import com.archclient.client.util.teammates.Teammate;
import com.archclient.client.util.title.Title;
import com.archclient.client.util.voicechat.VoiceChannel;
import com.archclient.client.util.voicechat.VoiceUser;
import com.archclient.common.KeyMappings;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.lax1dude.eaglercraft.v1_8.netty.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.List;
import java.util.*;

public class NetHandler implements IACNetHandler, IACNetHandlerClient {

    private List<VoiceChannel> voiceChannels;
    private VoiceChannel voiceChannel;
    public List<VoiceChannel> getVoiceChannels() {
        return this.voiceChannels;
    }
    public VoiceChannel getVoiceChannel() {
        return this.voiceChannel;
    }
    private List<EaglercraftUUID> uuidList;
    private List<EaglercraftUUID> anotherUuidList;
    private String world = "";
    private boolean serverHandlesWaypoints = false;
    public boolean voiceChatEnabled = true;
    private boolean competitiveGamemode = false;
    private boolean isArchClientChannel = false;

    private Map<EaglercraftUUID, List<String>> nametagsMap = new HashMap<>();
    public List<EaglercraftUUID> getUuidList() {
        return this.uuidList;
    }
    public List<EaglercraftUUID> getAnotherUuidList() {
        return this.anotherUuidList;
    }
    public String getWorld() {
        return this.world;
    }
    public Map<EaglercraftUUID, List<String>> getNametagsMap() {
        return this.nametagsMap;
    }

    public NetHandler() {
        this.voiceChannels = new ArrayList<>();
        this.anotherUuidList = new ArrayList<>();
        this.uuidList = new ArrayList<>();
    }

    private void initialize() {
        this.voiceChannels = null;
        this.anotherUuidList.clear();
        this.competitiveGamemode = false;
        this.voiceChatEnabled = true;
        this.serverHandlesWaypoints = false;
        this.world = "";
        this.nametagsMap = new HashMap<>();
        for (AbstractModule aCModule : ArchClient.getInstance().getModuleManager().staffModules) {
            ((StaffModule)aCModule).disableStaffModule();
        }
        ArchClient.getInstance().getBorderManager().lIIIIlIIllIIlIIlIIIlIIllI();
        Hologram.getHolograms().clear();
        ArchClient.getInstance().getModuleManager().teammatesModule.getTeammates().clear();
        this.lIIIIlIIllIIlIIlIIIlIIllI();
    }
    public void lIIIIlIIllIIlIIlIIIlIIllI() {
        ArchClient.getInstance().getTitleManager().getTitles().clear();
    }

    public void onPluginMessage(PluginMessageEvent pluginMessageEvent) {
        try {
            if (pluginMessageEvent.getChannel().equals("REGISTER")) {
                String string = new String(pluginMessageEvent.getPayload(), Charsets.UTF_8);
                this.isArchClientChannel = string.contains(ArchClient.getInstance().getPluginMessageChannel());
                this.serverHandlesWaypoints = string.contains(ArchClient.getInstance().getPluginBinaryChannel());
                PacketBuffer buf = Ref.getInstanceCreator().createPacketBuffer(Unpooled.buffer());
                buf.writeBytes(ArchClient.getInstance().getPluginMessageChannel().getBytes(Charsets.UTF_8));
                if (Ref.getMinecraft().getNetHandler() != null && this.isArchClientChannel) {
                    Ref.getMinecraft().getNetHandler().addToSendQueue(Ref.getInstanceCreator().createC17PacketCustomPayload("REGISTER", buf));
                }
                this.initialize();
            } else if (pluginMessageEvent.getChannel().equals(ArchClient.getInstance().getPluginMessageChannel())) {
                Packet packet = Packet.handle(this, pluginMessageEvent.getPayload());
                if (ArchClient.getInstance().getGlobalSettings().isDebug) {
                    ChatComponentText chatComponentText = Ref.getInstanceCreator().createChatComponentText( "[AC] ");
                    ChatComponentText chatComponentText2 = Ref.getInstanceCreator().createChatComponentText("Received: " + packet.getClass().getSimpleName());
                    //xxxchatComponentText2.getChatStyle().bridge$setChatHoverEvent(Ref.getInstanceCreator().createHoverEvent("SHOW_TEXT", Ref.getInstanceCreator().createChatComponentText(new Gson().toJson(packet))));
                    chatComponentText.appendSibling(chatComponentText2);
                    Ref.getMinecraft().ingameGUI.getChatGUI().printChatMessage(chatComponentText);
                }
            }
        }
        catch (AssertionError | Exception throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void handleAddWaypoint(PacketAddWaypoint packetAddWaypoint) {
//        int x = packetAddWaypoint.getX();
//        int y = packetAddWaypoint.getY();
//        int z = packetAddWaypoint.getZ();
//        lIlIIIIIIlIllIlIllIlIlIlI lIlIIIIIIlIllIlIllIlIlIlI2 = this.lIIIIlIIllIIlIIlIIIlIIllI.IIIIllIIllIIIIllIllIIIlIl().IIIIllIlIIIllIlllIlllllIl.lIIIIlIIllIIlIIlIIIlIIllI();
//        if (lIlIIIIIIlIllIlIllIlIlIlI2.IIIllIllIlIlllllllIlIlIII().lIIIIlIIllIIlIIlIIIlIIllI().stream().anyMatch(lIIllIllIlIllIIIlIlllllIl2 -> lIIllIllIlIllIIIlIlllllIl2.lIIIIlIIllIIlIIlIIIlIIllI.equals(lIllllIIIlllIIIllIIIIllll2.lIIIIIIIIIlIllIIllIlIIlIl()) && lIIllIllIlIllIIIlIlllllIl2.IIIIllIIllIIIIllIllIIIlIl.equals(lIllllIIIlllIIIllIIIIllll2.IlllIIIlIlllIllIlIIlllIlI()))) {
//            return;
//        }
//        Color color = new Color(packetAddWaypoint.getColor());
//        float f = (float)color.getRed() / (float)255;
//        float f2 = (float)color.getGreen() / (float)255;
//        float f3 = (float)color.getBlue() / (float)255;
//        TreeSet<Integer> treeSet = new TreeSet<>();
//        treeSet.add(-1);
//        treeSet.add(0);
//        treeSet.add(1);
//        System.out.println("Received waypoint (" + packetAddWaypoint.getName() + ")[x" + x + ",y" + y + ",z" + z + "][r" + f + ",g" + f2 + ",b" + f3 + "]");
//        Waypoint lIIllIllIlIllIIIlIlllllIl3 = new Waypoint(packetAddWaypoint.getName(), x, z, y, true, f, f2, f3, "", lIlIIIIIIlIllIlIllIlIlIlI2.IIIllIllIlIlllllllIlIlIII().IlllIIIlIlllIllIlIIlllIlI(), treeSet, true, true);
//        lIIllIllIlIllIIIlIlllllIl3.lIIlIlIllIIlIIIlIIIlllIII = lIllllIIIlllIIIllIIIIllll2.IllIIIIIIIlIlIllllIIllIII();
//        lIIllIllIlIllIIIlIlllllIl3.IIIIllIIllIIIIllIllIIIlIl = lIllllIIIlllIIIllIIIIllll2.IlllIIIlIlllIllIlIIlllIlI();
//        lIIllIllIlIllIIIlIlllllIl3.IlIlIIIlllIIIlIlllIlIllIl = true;
//        lIlIIIIIIlIllIlIllIlIlIlI2.IIIllIllIlIlllllllIlIlIII().lIIIIIIIIIlIllIIllIlIIlIl(lIIllIllIlIllIIIlIlllllIl3);
    }

    @Override
    public void handleRemoveWaypoint(PacketRemoveWaypoint packetRemoveWaypoint) { }

    @Override
    public void handleCooldown(PacketCooldown packet) {
        CooldownsModule.lIIIIlIIllIIlIIlIIIlIIllI(packet.getMessage(), packet.getDurationMs(), packet.getIconId());
    }

    @Override
    public void handleNotification(PacketNotification aCPacketNotification) {
        ArchClient.getInstance().getModuleManager().notifications.queueNotification(aCPacketNotification.getLevel(), aCPacketNotification.getLevel(), aCPacketNotification.getDurationMs());
    }

    @Override
    public void handleStaffModState(PacketStaffModState packet) {
        for (AbstractModule aCModule : ArchClient.getInstance().getModuleManager().staffModules) {
            if (!aCModule.getName().equals(packet.getMod().replaceAll("_", "").toLowerCase())) continue;
            aCModule.setState(packet.isState());
        }
    }

    @Override
    public void handleNametagsUpdate(PacketUpdateNametags packet) {
//        if (packet.getPlayersMap() != null) {
//            IIIlllIllIIllIllIlIIIllII.lIIIIlIIllIIlIIlIIIlIIllI(new HashMap());
//            for (Map.Entry<UUID, List<String>> entry : packet.getPlayersMap().entrySet()) {
//                IIIlllIllIIllIllIlIIIllII.lIllIllIlIIllIllIlIlIIlIl().put(entry.getKey().toString(), entry.getValue());
//            }
//        } else {
//            IIIlllIllIIllIllIlIIIllII.lIIIIlIIllIIlIIlIIIlIIllI(null);
//        }
    }

    @Override
    public void handleTeammates(PacketTeammates packet) {
        System.out.println("[AC] Received Teammates: " + packet.toString());
        System.out.println(" - [AC] Players: " + packet.getPlayers().toString());
        System.out.println(" - [AC] Last MS: " + packet.getLastMs());
        System.out.println(" - [AC] Leader: " + packet.getLeader());
        Map<EaglercraftUUID, Map<String, Double>> map = packet.getPlayers();
        EaglercraftUUID uUID = packet.getLeader();
        long l = packet.getLastMs();
        if (!ArchClient.getInstance().getGlobalSettings().enableTeamView.<Boolean>value() || map == null || map.isEmpty() || map.size() == 1 && map.containsKey(Ref.getMinecraft().thePlayer.getUniqueID())) {
            ArchClient.getInstance().getModuleManager().teammatesModule.getTeammates().clear();
            System.out.println("[AC Teammates] Cleared Map..");
            return;
        }
        int n = 0;
        for (Map.Entry<EaglercraftUUID, Map<String, Double>> entry : map.entrySet()) {
            System.out.println("[AC Teammates] Entry: " + entry.toString());
            Teammate teammate = ArchClient.getInstance().getModuleManager().teammatesModule.getTeammate((entry.getKey()).toString());
            if (teammate == null) {
                teammate = new Teammate((entry.getKey()).toString(), uUID != null && uUID.equals(entry.getKey()));
                ArchClient.getInstance().getModuleManager().teammatesModule.getTeammates().add(teammate);
                System.out.println("[AC Teammates] New Teammate Added: " + entry);
                Random random = new Random();
                if (n < ArchClient.getInstance().getModuleManager().teammatesModule.lIIIIIIIIIlIllIIllIlIIlIl().length) {
                    teammate.setColor(new Color(ArchClient.getInstance().getModuleManager().teammatesModule.lIIIIIIIIIlIllIIllIlIIlIl()[n]));
                } else {
                    float f = random.nextFloat();
                    float f2 = random.nextFloat();
                    float f3 = random.nextFloat() / 2.0f;
                    teammate.setColor(new Color(f, f2, f3));
                }
            }
            try {
                double d = entry.getValue().get("x");
                double d2 = entry.getValue().get("y") + (double)2;
                double d3 = entry.getValue().get("z");
                teammate.reset(d, d2, d3, l);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            ++n;
        }
        ArchClient.getInstance().getModuleManager().teammatesModule.getTeammates().removeIf(teammate -> !map.containsKey(EaglercraftUUID.fromString(teammate.getUuid())));
    }

    @Override
    public void handleOverrideNametags(PacketOverrideNametags packet) {
        if (packet.getTags() == null) {
            this.nametagsMap.remove(packet.getPlayer());
        } else {
            Collections.reverse(packet.getTags());
            this.nametagsMap.put(packet.getPlayer(), packet.getTags());
        }
    }

    @Override
    public void handleAddHologram(PacketAddHologram var1) {
        Hologram iIIlIllIIllllIIIllllIllll = new Hologram(var1.getUuid(), var1.getX(), var1.getY(), var1.getZ());
        Hologram.getHolograms().add(iIIlIllIIllllIIIllllIllll);
        iIIlIllIIllllIIIllllIllll.setTexts(var1.getLines().toArray(new String[0]));
    }

    @Override
    public void handleUpdateHologram(PacketUpdateHologram var1) {
        Hologram.getHolograms().stream().filter(hologram -> hologram.getUuid().equals(var1.getUuid())).forEach(hologram -> hologram.setTexts(var1.getLines().toArray(new String[0])));
    }

    @Override
    public void handleRemoveHologram(PacketRemoveHologram var1) {
        Hologram.getHolograms().removeIf(hologram -> hologram.getUuid().equals(var1.getUuid()));
    }

    @Override
    public void handleTitle(PacketTitle packet) {
        Title.TitleType titleEnum = Title.TitleType.TITLE;
        if (packet.getType().toLowerCase().equals("subtitle")) {
            titleEnum = Title.TitleType.SUBTITLE;
        }
        ArchClient.getInstance().getTitleManager().getTitles().add(new Title(packet.getMessage(), titleEnum, packet.getScale(), packet.getDisplayTimeMs(), packet.getFadeInTimeMs(), packet.getFadeOutTimeMs()));
    }

    @Override
    public void handleServerRule(PacketServerRule packetServerRule) {
        switch (packetServerRule.getRule()) {
            case SERVER_HANDLES_WAYPOINTS: {
                this.serverHandlesWaypoints = packetServerRule.isEnabled();
                break;
            }
            case VOICE_ENABLED: {
                System.out.println("[AC] Voice is: " + (packetServerRule.isEnabled() ? "enabled" : "disabled"));
                this.voiceChatEnabled = packetServerRule.isEnabled();
                break;
            }
            case COMPETITIVE_GAMEMODE: {
                this.competitiveGamemode = packetServerRule.isEnabled();
            }
        }
    }

    @Override
    public void handleVoice(PacketVoice packet) {
        if (packet.getUuid().equals(Ref.getMinecraft().thePlayer.getUniqueID()))
            return;
        //Message.f(packet.getUuid().toString(), packet.getData());
        ArchClient.getInstance().getVoiceChatManager().handleIncoming(packet);
        ArchClient.getInstance().getModuleManager().voiceChat.addUserToSpoken(packet.getUuid());

    }

    @Override
    // this is not recieved????
    public void handleVoiceChannels(PacketVoiceChannel packet) {
        System.out.println("[AC Voice] Voice Channel Received: " + packet.getName());
        System.out.println(" - [AC Voice] Channel has " + packet.getPlayers().size() + " members");

        if (this.doesVoiceChannelExist(packet.getUuid())) {
            System.out.println("[AC Voice] the player is already in this channel. (" + packet.getName() + ")");
            return;
        }
        if (this.voiceChannels == null) {
            this.voiceChannels = new ArrayList<>();
        }

        VoiceChannel voiceChannel = new VoiceChannel(packet.getUuid(), packet.getName());
        this.voiceChannels.add(voiceChannel);

        List<VoiceUser> voiceUserList = new ArrayList<>();

        for (Map.Entry<EaglercraftUUID, String> entry : packet.getPlayers().entrySet()) {
            VoiceUser voiceUser = voiceChannel.getOrCreateVoiceUser(entry.getKey(), entry.getValue());
            if (voiceUser == null) continue;
            System.out.println("[AC] Added member [" + entry.getValue() + "]");
            voiceUserList.add(voiceUser);
        }

        this.addUsers(voiceUserList);
        for (Map.Entry<EaglercraftUUID, String> entry : packet.getPlayers().entrySet()) {
            System.out.println("[AC] Added listener [" + entry.getValue() + "]");
            voiceChannel.addToListening(entry.getKey());
        }
    }

    @Override
    public void handleVoiceChannelUpdate(PacketVoiceChannelUpdate packet) {
        System.out.println("[AC Voice] Channel Update: " + packet.getName() + " (" + packet.getStatus() + ")");
        System.out.println(packet.toString());
        if (this.voiceChannels == null) {
            System.err.println("Gay coon is null");
            return;
        }

        VoiceChannel voiceChannel = this.getVoiceChannel(packet.getChannelUuid());
        if (voiceChannel == null) {
            System.out.println("[AC Voice] VoiceChannel null: " + packet.getChannelUuid().toString());
            return;
        }

        switch (packet.getStatus()) {
            case 0: {
                // Adding player
                VoiceUser voiceUser = voiceChannel.getOrCreateVoiceUser(packet.getUuid(), packet.getName());
                System.out.println("[AC Voice] Packet status 0");
                if (voiceUser == null) break;
                this.addUsers(ImmutableList.of(voiceUser));
                break;
            }
            case 1: {
                // Removing player
                voiceChannel.removeUser(packet.getUuid());
                System.out.println("[AC Voice] Packet status 1");
                break;
            }
            case 2: {
                System.out.println("[AC Voice] Joined " + voiceChannel.getChannelName() + " channel.");
                System.out.println("[AC Voice] " + packet.getUuid() + " - " + Ref.getMinecraft().getSession()
                        .getProfile().getId());

                if (packet.getName().equals(Ref.getMinecraft().getSession().getProfile().getId().toString())) {
                    this.voiceChannel = voiceChannel;
                    for (VoiceChannel voiceChannel2 : this.voiceChannels) {
                        voiceChannel2.removeListener(packet.getUuid());
                    }
                    ChatComponentText chatComponentText = Ref.getInstanceCreator()
                            .createChatComponentText(EnumChatFormattingBridge.AQUA + "Joined "
                                    + voiceChannel.getChannelName() + " channel. Press '"
                                    + Keyboard.getKeyName(KeyMappings.PUSH_TO_TALK.getKeyCode()) + "' to talk!"
                                    + EnumChatFormattingBridge.RESET);
                    Ref.getMinecraft().ingameGUI.getChatGUI()
                            .printChatMessage(chatComponentText);
                } else if (this.voiceChannel == voiceChannel) {
                    ChatComponentText chatComponentText = Ref.getInstanceCreator()
                            .createChatComponentText(EnumChatFormattingBridge.AQUA + packet.getName()
                                    + EnumChatFormattingBridge.AQUA + " joined "
                                    + voiceChannel.getChannelName() + " channel. Press '"
                                    + Keyboard.getKeyName(KeyMappings.OPEN_VOICE_MENU.getKeyCode()) + "'!"
                                    + EnumChatFormattingBridge.RESET);
                    Ref.getMinecraft().ingameGUI.getChatGUI()
                            .printChatMessage(chatComponentText);
                }
                voiceChannel.addToListening(packet.getUuid());
                break;
            }
            case 3: {
                // remove listening
                if (this.voiceChannel == voiceChannel && !packet.getUuid().toString().equals(Ref.getMinecraft()
                        .getSession().getProfile().getId().toString())) {
                    ChatComponentText chatComponentText = Ref.getInstanceCreator()
                            .createChatComponentText(EnumChatFormattingBridge.AQUA + packet.getName()
                                    + EnumChatFormattingBridge.AQUA + " left "
                                    + voiceChannel.getChannelName() + " channel. Press '"
                                    + Keyboard.getKeyName(KeyMappings.OPEN_VOICE_MENU.getKeyCode()) + "'!"
                                    + EnumChatFormattingBridge.RESET);
                    Ref.getMinecraft().ingameGUI.getChatGUI()
                            .printChatMessage(chatComponentText);
                }
                voiceChannel.removeListener(packet.getUuid());
            }
        }
    }

    @Override
    public void handleDeleteVoiceChannel(PacketDeleteVoiceChannel acPacketDeleteVoiceChannel) {
        System.out.println("[AC] Deleted channel: " + acPacketDeleteVoiceChannel.getUuid().toString());
        if (this.voiceChannels != null) {
            this.voiceChannels.removeIf(voiceChannel -> voiceChannel.getUuid().equals(acPacketDeleteVoiceChannel.getUuid()));
        }
        if (this.voiceChannel != null && this.voiceChannel.getUuid().equals(acPacketDeleteVoiceChannel.getUuid())) {
            this.voiceChannel = null;
        }
    }

    @Override
    public void handleUpdateWorld(PacketUpdateWorld var1) {
        System.out.println("[AC] World Update: " + var1.getWorld());
        this.world = var1.getWorld();
    }

    @Override
    public void handleServerUpdate(PacketServerUpdate packet) {
        System.out.println("[AC] Retrieved " + packet.getServer());
//        ACClient.getInstance().lIIIIlIIllIIlIIlIIIlIIllI(packet.getServer());
    }

    @Override
    public void handleWorldBorder(PacketWorldBorder packet) {
        ArchClient.getInstance().getBorderManager().lIIIIlIIllIIlIIlIIIlIIllI(packet.getId(), packet.getWorld(), packet.getColor(), packet.getMinX(), packet.getMinZ(), packet.getMaxX(), packet.getMaxZ(), packet.isCanShrinkExpand(), packet.isCancelsExit());
    }

    @Override
    public void handleWorldBorderUpdate(PacketWorldBorderUpdate packet) {
        ArchClient.getInstance().getBorderManager().lIIIIlIIllIIlIIlIIIlIIllI(packet.getId(), packet.getMinX(), packet.getMinZ(), packet.getMaxX(), packet.getMaxZ(), packet.getDurationTicks());
    }

    @Override
    public void handleWorldBorderRemove(PacketWorldBorderRemove packet) {
        ArchClient.getInstance().getBorderManager().lIIIIlIIllIIlIIlIIIlIIllI(packet.getId());
    }

    // Util Methods

    private boolean doesVoiceChannelExist(EaglercraftUUID uUID) {
        return this.getVoiceChannel(uUID) != null;
    }

    public VoiceUser getVoiceUser(EaglercraftUUID uuid) {
        if (this.voiceChannels == null || this.voiceChannel == null) {
            return null;
        }
        for (VoiceUser voiceUser : this.voiceChannel.getVoiceUsers()) {
            if (!voiceUser.getUUID().equals(uuid)) continue;
            return voiceUser;
        }
        return null;
    }

    private VoiceChannel getVoiceChannel(EaglercraftUUID uuid) {
        if (this.voiceChannels == null) {
            System.err.println("[AC Voice] Voice channels is null");
            return null;
        }
        for (VoiceChannel voiceChannel : this.voiceChannels) {
            if (!voiceChannel.getUuid().equals(uuid)) continue;
            return voiceChannel;
        }
        return null;
    }

    private void addUsers(List<VoiceUser> userList) {
        for (VoiceUser voiceUser : userList) {
            if (voiceUser == null || !this.uuidList.contains(voiceUser.getUUID()) || this.anotherUuidList.contains(voiceUser.getUUID())) continue;
            this.anotherUuidList.add(voiceUser.getUUID());
            this.sendPacketToQueue(new PacketVoiceChannelSwitch(voiceUser.getUUID()));
        }
    }

    public void sendPacketToQueue(Packet packet) {
        Object object;
        if (packet != null && ArchClient.getInstance().getGlobalSettings().isDebug) {
            object = Ref.getInstanceCreator().createChatComponentText(EnumChatFormattingBridge.RED + "[C" + EnumChatFormattingBridge.WHITE + "B" + EnumChatFormattingBridge.RED + "] " + EnumChatFormattingBridge.RESET);
            ChatComponentText chatComponentText = Ref.getInstanceCreator().createChatComponentText(EnumChatFormattingBridge.GRAY + "Sent: " + EnumChatFormattingBridge.WHITE + packet.getClass().getSimpleName());
            //xxxchatComponentText.getChatStyle().bridge$setChatHoverEvent(Ref.getInstanceCreator().createHoverEvent("SHOW_TEXT", Ref.getInstanceCreator().createChatComponentText(new Gson().toJson(packet))));
            ((ChatComponentStyle)object).appendSibling(chatComponentText);
            Ref.getMinecraft().ingameGUI.getChatGUI().printChatMessage((IChatComponent)object);
        }

        C17PacketCustomPayload payload = Ref.getInstanceCreator().createC17PacketCustomPayload(ArchClient.getInstance().getPluginMessageChannel(), Packet.getPacketData(packet));
        Ref.getMinecraft().thePlayer.sendQueue.addToSendQueue(payload);
    }
}
