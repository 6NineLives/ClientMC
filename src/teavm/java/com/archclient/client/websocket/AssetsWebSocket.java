package com.archclient.client.websocket;

import com.archclient.bridge.ref.Ref;
import com.archclient.bridge.util.EnumChatFormattingBridge;
import com.archclient.main.ArchClient;
import com.archclient.util.Utils;
import com.archclient.client.config.Profile;
import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.ui.mainmenu.AbstractElement;
import com.archclient.client.ui.overlay.Alert;
import com.archclient.client.ui.overlay.OverlayGui;
import com.archclient.client.ui.overlay.element.MessagesElement;
import com.archclient.client.ui.overlay.friend.FriendRequest;
import com.archclient.client.ui.overlay.friend.FriendRequestElement;
import com.archclient.client.util.cosmetic.Cosmetic;
import com.archclient.client.util.friend.Friend;
import com.archclient.client.util.friend.Status;
import com.archclient.client.util.thread.WSReconnectThread;
import com.archclient.client.websocket.client.*;
import com.archclient.client.websocket.server.*;
import com.archclient.client.websocket.shared.*;
import net.lax1dude.eaglercraft.v1_8.internal.vfs2.VFile2;
import net.lax1dude.eaglercraft.v1_8.netty.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;

import net.lax1dude.eaglercraft.v1_8.internal.teavm.TeaVMUtils;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.MessageEvent;
import org.teavm.jso.websocket.WebSocket;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AssetsWebSocket {
    private final Minecraft minecraft = Ref.getMinecraft();
    private final List<String> playersCache = new ArrayList<>();
    protected final WebSocket sock;
    protected boolean open = true;

    public AssetsWebSocket(URI uRI, Map<String,String> map) {
        //xxxsuper(uRI, new Draft_6455(), map, 0);
        this.sock = WebSocket.create(uRI.toString());
        initHandlers();
    }

    @JSBody(params = { "obj" }, script = "return typeof obj === \"string\";")
    private static native boolean isString(JSObject obj);

    protected void initHandlers() {
        sock.setBinaryType("arraybuffer");
        TeaVMUtils.addEventListener(sock, "open", new EventListener<Event>() {
            @Override
            public void handleEvent(Event evt) {
                System.out.println("[AC] Connection established");
        //        if (Objects.equals(Ref.getMinecraft().bridge$getSession().bridge$getUsername(), Ref.getMinecraft().bridge$getSession().getPlayerID())) {
        //            this.close();
        //        }
            }
        });
        TeaVMUtils.addEventListener(sock, "close", new EventListener<Event>() {
            @Override
            public void handleEvent(Event evt) {
                open = false;
                System.out.println("Close");
                new WSReconnectThread().start();
                OverlayGui.getInstance().getFriendRequestsElement().getElements().clear();
                OverlayGui.getInstance().getFriendsListElement().getElements().clear();
                ArchClient.getInstance().getFriendsManager().getFriends().clear();
                ArchClient.getInstance().getFriendsManager().getFriendRequests().clear();
            }
        });
        TeaVMUtils.addEventListener(sock, "message", new EventListener<MessageEvent>() {
            @Override
            public void handleEvent(MessageEvent evt) {
                if(!isString(evt.getData())) {
                    //xxxthis.handleIncoming(new ByteBufWrapper(Unpooled.buffer(evt.getDataAsArray(), evt.getDataAsArray().length)));
                }
            }
        });
        TeaVMUtils.addEventListener(sock, "error", new EventListener<Event>() {
            @Override
            public void handleEvent(Event evt) {
                System.out.println("Error");
                sock.close();
                open = false;
            }
        });
    }

    public void connect() {
    }

    public boolean isOpen() {
        return open;
    }

    public void sendToServer(WSPacket packet) {
        if (!this.isOpen()) {
            return;
        }
        ByteBufWrapper buf = new ByteBufWrapper(Unpooled.buffer());
        buf.writeVarInt(WSPacket.REGISTRY.get(packet.getClass()));
        packet.write(buf);
        //xxxthis.send(buf.buf().array());
    }

    public void handleIncoming(ByteBufWrapper buf) {
        int n = buf.readVarInt();
        Class<? extends WSPacket> packetClass = WSPacket.REGISTRY.inverse().get(n);
        try {
            WSPacket packet = packetClass == null ? null : packetClass.newInstance();
            if (packet == null) {
                return;
            }
            packet.read(buf);
            packet.handle(this);

            Ref.getMinecraft().ingameGUI.getChatGUI().printChatMessage(Ref.getInstanceCreator().createChatComponentText("Handling incoming packet from websocket: " + packetClass.getCanonicalName()));
        }
        catch (Exception exception) {
            System.out.println("Error from: " + packetClass);
            exception.printStackTrace();
        }
    }

    public void handleConsoleOutput(WSPacketConsole packet) {
        ArchClient.getInstance().getConsoleLines().add(packet.getOutput());
        System.out.println(packet.getOutput());
    }

    public void handleFriendRemove(WSPacketClientFriendRemove packet) {
        String string = packet.getPlayerId();
        Friend friend = ArchClient.getInstance().getFriendsManager().getFriend(string);
        if (friend != null) {
            ArchClient.getInstance().getFriendsManager().getFriends().remove(string);
            OverlayGui.getInstance().handleFriend(friend, false);
        }
    }

    public void handleMessage(WSPacketMessage packet) {
        String playerId = packet.getPlayerId();
        String message = packet.getMessage();
        Friend friend = ArchClient.getInstance().getFriendsManager().getFriends().get(playerId);
        if (friend != null) {
            ArchClient.getInstance().getFriendsManager().addUnreadMessage(friend.getPlayerId(), message);
            if (ArchClient.getInstance().getStatus() != Status.BUSY) {
                ArchClient.getInstance().sendSound("message");
                Alert.displayMessage(EnumChatFormattingBridge.GREEN + friend.getName() + EnumChatFormattingBridge.RESET + " says:", message);
            }
            for (AbstractElement element : OverlayGui.getInstance().getElements()) {
                if (!(element instanceof MessagesElement) || ((MessagesElement)element).getFriend() != friend) continue;
                ArchClient.getInstance().getFriendsManager().readMessages(friend.getPlayerId());
            }
        }
    }

    public void sendUpdateServer(String string) {
        this.sendToServer(new WSPacketServerUpdate("", string));
    }

    public void handleServerUpdate(WSPacketServerUpdate packetServerUpdate) {
        String playerId = packetServerUpdate.getPlayerId();
        String server = packetServerUpdate.getServer();
        Friend friend = ArchClient.getInstance().getFriendsManager().getFriends().get(playerId);
        if (friend != null) {
            friend.setServer(server);
        }
    }

    public void handleBulkFriends(WSPacketBulkFriends packet) {
        ArchClient.getInstance().getFriendsManager().getFriendRequests().clear();
        /*xxx
        JsonArray bulkArray = packet.getBulkArray();
        for (JsonElement jsonElement : bulkArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String playerId = jsonObject.get("uuid").getAsString();
            String playerName = jsonObject.get("name").getAsString();
            FriendRequest friendRequest = new FriendRequest(playerName, playerId);
            ArchClient.getInstance().getFriendsManager().getFriendRequests().put(playerId, friendRequest);
            OverlayGui.getInstance().handleFriendRequest(friendRequest, true);
        }
        */
    }

    public void handleFriendRequest(WSPacket packet, boolean outgoing) {
        if (outgoing) {
            WSPacketFriendStatusUpdate friendStatus = (WSPacketFriendStatusUpdate)packet;
            FriendRequest friendRequest = new FriendRequest(friendStatus.getName(), friendStatus.getPlayerId());
            ArchClient.getInstance().getFriendsManager().getFriendRequests().put(friendStatus.getPlayerId(), friendRequest);
            OverlayGui.getInstance().handleFriendRequest(friendRequest, true);
            friendRequest.setFriend(friendStatus.isFriend());
            Alert.displayMessage("Friend Request", "Request has been sent.");
        } else {
            WSPacketFriendRequest friendRequestIncoming = (WSPacketFriendRequest) packet;
            String string = friendRequestIncoming.getPlayerId();
            String string2 = friendRequestIncoming.getName();
            FriendRequest friendRequest = new FriendRequest(string2, string);
            ArchClient.getInstance().getFriendsManager().getFriendRequests().put(string, friendRequest);
            OverlayGui.getInstance().handleFriendRequest(friendRequest, true);
            if (ArchClient.getInstance().getStatus() != Status.BUSY) {
                ArchClient.getInstance().sendSound("message");
                Alert.displayMessage("Friend Request", friendRequest.getUsername() + " wants to be your friend.");
            }
        }
    }

    public void handleFriendUpdate(WSPacketFriendUpdate packet) {
        String playerId = packet.getPlayerId();
        String name = packet.getName();
        boolean online = packet.isOnline();
        Friend friend = ArchClient.getInstance().getFriendsManager().getFriends().get(playerId);
        if (friend == null) {
            friend = Friend.builder()
                    .online(online)
                    .name(name)
                    .playerId(playerId)
                    .online(online)
                    .onlineStatus(Status.ONLINE).build();
            ArchClient.getInstance().getFriendsManager().getFriends().put(playerId, friend);
            OverlayGui.getInstance().handleFriend(friend, true);
        }
        if (packet.getOfflineSince() < 10L) {
            int n = (int)packet.getOfflineSince();
            Status aCStatusEnum = Status.ONLINE;
            for (Status aCStatusEnum2 : Status.values()) {
                if (aCStatusEnum2.ordinal() != n) continue;
                aCStatusEnum = aCStatusEnum2;
            }
            friend.setOnlineStatus(aCStatusEnum);
        }
        friend.setOnline(online);
        friend.setName(name);
        OverlayGui.getInstance().getFriendsListElement().updateSize();
        if (!online) {
            friend.setOfflineSince(packet.getOfflineSince());
        }
    }

    public void handleFriendsUpdate(WSPacketFriendsUpdate packet) {
        String name;
        String playerId;
        ArchClient.getInstance().getFriendsManager().getFriends().clear();
        Map<String, List<String>> onlineMap = packet.getOnlineMap();
        Map<String, List<String>> offlineMap = packet.getOfflineMap();
        ArchClient.getInstance().setConsoleAllowed(packet.isConsoleAllowed());
        ArchClient.getInstance().setAcceptingFriendRequests(packet.isAcceptingFriendRequests());
        for (Map.Entry<String, List<String>> online : onlineMap.entrySet()) {
            playerId = online.getKey();
            name = online.getValue().get(0);
            int statusOrdinal = Integer.parseInt(online.getValue().get(1));
            String server = online.getValue().get(2);
            Status aCStatusEnum = Status.ONLINE;
            for (Status aCStatusEnum2 : Status.values()) {
                if (aCStatusEnum2.ordinal() != statusOrdinal) continue;
                aCStatusEnum = aCStatusEnum2;
            }
            Friend object = Friend.builder()
                    .name(name)
                    .playerId(playerId)
                    .server(server)
                    .onlineStatus(aCStatusEnum)
                    .online(true)
                    .status("Online").build();
            ArchClient.getInstance().getFriendsManager().getFriends().put(playerId, object);
            OverlayGui.getInstance().handleFriend(object, true);
        }
        for (Map.Entry<String, List<String>> offline : offlineMap.entrySet()) {
            playerId = offline.getKey();
            name = offline.getValue().get(0);
            Friend friend = Friend.builder()
                    .name(name)
                    .playerId(playerId)
                    .server("")
                    .onlineStatus(Status.ONLINE)
                    .online(false)
                    .status("Online")
                    .offlineSince(Long.parseLong(offline.getValue().get(1))).build();
            ArchClient.getInstance().getFriendsManager().getFriends().put(playerId, friend);
            OverlayGui.getInstance().handleFriend(friend, true);
        }
    }

    public EntityPlayer theWorld$getPlayerFromUUID(String uuid) {
        for (int i = 0; i < Utils.convertListType(this.minecraft.theWorld.playerEntities).size(); ++i) {
            EntityPlayer entityplayer = this.minecraft.theWorld.playerEntities.get(i);

            if (uuid.replaceAll("-", "").equals(entityplayer.getUniqueID().toString().replaceAll("-", ""))) {
                return entityplayer;
            }
        }

        return null;
    }

    public void handleCosmetics(WSPacketCosmetics packet) {
        String string = packet.getPlayerId();
        ArchClient.getInstance().getCosmetics().removeIf(cosmetic -> cosmetic.getPlayerId().equals(string));
        ArchClient.getInstance().removeCosmeticsFromPlayer(string);
        for (Cosmetic cosmetic : packet.getCosmetics()) {
            try {
                ArchClient.getInstance().getCosmetics().add(cosmetic);
                EntityPlayer player = theWorld$getPlayerFromUUID(string);

//                if (!cosmetic.isEquipped() || !(player instanceof AbstractClientPlayer))
//                    continue;
                if (!(player instanceof AbstractClientPlayer)) {
                    ArchClient.getInstance().acInfo("Player " + string + " (str) is not instance of AbstractClientPlayer!");
                    Ref.getMinecraft().ingameGUI.getChatGUI().printChatMessage(Ref.getInstanceCreator().createChatComponentText("Player " + string + " (str) is not instance of AbstractClientPlayer!"));
                    continue;
                }

                if (!cosmetic.isEquipped()) {
                    ArchClient.getInstance().acInfo("System tried to apply cape to " + player.getDisplayName().getFormattedText() + "\u00a7r using " + cosmetic.getLocation() + " but failed as it is not equipped.");
                    Ref.getMinecraft().ingameGUI.getChatGUI().printChatMessage(Ref.getInstanceCreator().createChatComponentText("System tried to apply cape to " + player.getDisplayName().getFormattedText() + "\u00a7r using " + cosmetic.getLocation() + " but failed as it is not equipped."));
                    continue;
                }

                ArchClient.getInstance().acInfo("Player " + string + " passed checks!");
                Ref.getMinecraft().ingameGUI.getChatGUI().printChatMessage(Ref.getInstanceCreator().createChatComponentText("Player " + string + " passed checks!"));

                if (cosmetic.getType().equals("cape")) {
                    ((AbstractClientPlayer) player).getPlayerInfo().setLocationCape(cosmetic.getLocation());
                    Ref.getMinecraft().ingameGUI.getChatGUI().printChatMessage(Ref.getInstanceCreator().createChatComponentText("Set cape location for " + player.getDisplayName().getFormattedText() + "\u00a7r to " + cosmetic.getLocation()));
                } else {
                    ArchClient.getInstance().acInfo("Type is not cape!");
                    Ref.getMinecraft().ingameGUI.getChatGUI().printChatMessage(Ref.getInstanceCreator().createChatComponentText("Type is not cape!"));
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void handleFormattedConsoleOutput(WSPacketFormattedConsoleOutput packet) {
        String string = packet.getPrefix();
        String string2 = packet.getContent();
        ArchClient.getInstance().getConsoleLines().add(EnumChatFormattingBridge.DARK_GRAY + "[" + EnumChatFormattingBridge.RESET + packet.getPrefix() + EnumChatFormattingBridge.DARK_GRAY + "] " + EnumChatFormattingBridge.RESET + packet.getContent());
        Alert.displayMessage(string, string2);
    }

    // checks if cracked?? if it's returning before sending a response
    public void handleJoinServer(WSPacketJoinServer packet) {
        /*xxx
        SecretKey secretKey = CryptManagerBridge.createNewSharedKey();
        PublicKey publicKey = packet.getPublicKey();
        String string = new BigInteger(CryptManagerBridge.getServerIdHash("", publicKey, secretKey)).toString(16);
        try {
            this.createSessionService().joinServer(this.minecraft.bridge$getSession().bridge$func_148256_e(), this.minecraft.bridge$getSession().bridge$getToken(), string);
        }
        catch (AuthenticationUnavailableException authenticationUnavailableException) {
            Alert.displayMessage("Authentication Unavailable", authenticationUnavailableException.getMessage());
            return;
        }
        catch (InvalidCredentialsException invalidCredentialsException) {
            Alert.displayMessage("Invalid Credentials", invalidCredentialsException.getMessage());
            return;
        }
        catch (AuthenticationException authenticationException) {
            Alert.displayMessage("Authentication Error", authenticationException.getMessage());
            return;
        }
        catch (NullPointerException nullPointerException) {
            this.close();
        }
        try {
            ByteBufWrapper buf = new ByteBufWrapper(Unpooled.buffer());
            WSPacketClientJoinServerResponse response = new WSPacketClientJoinServerResponse(secretKey, publicKey, packet.getEncryptedPublicKey());
            response.write(buf);
            this.sendToServer(response);
            VFile2 file = new VFile2("config", "client", "profiles.txt");
            if (file.exists()) {
                this.sendToServer(new WSPacketClientProfilesExist());
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        */
    }

    public void send(String string) {
        if (!this.isOpen()) {
            return;
        }
        sock.send(string);
    }

    public void lIIIIlIIllIIlIIlIIIlIIllI(AbstractClientPlayer player) {
        if (player.getGameProfile() == null || this.minecraft.thePlayer == null) {
            return;
        }
        String string = player.getUniqueID().toString();
        if (!this.playersCache.contains(string) && !string.equals(this.minecraft.thePlayer.getUniqueID().toString())) {
            this.playersCache.add(string);
            this.sendToServer(new WSPacketClientPlayerJoin(string));
        }
    }

    public void sendClientCosmetics() {
        this.sendToServer(new WSPacketClientCosmetics(ArchClient.getInstance().getCosmetics()));
    }

    public void updateClientStatus() {
        this.sendToServer(new WSPacketFriendUpdate("", "", ArchClient.getInstance().getStatus().ordinal(), true));
    }

    public void handleFriendRequestUpdate(WSPacketClientFriendRequestUpdate packet) {
        if (!packet.isAdd()) {
            ArchClient.getInstance().getFriendsManager().getFriendRequests().remove(packet.lIIIIIIIIIlIllIIllIlIIlIl());
            FriendRequestElement requestElement = null;
            for (FriendRequestElement friendRequestElement : OverlayGui.getInstance().getFriendRequestsElement().getElements()) {
                if (!friendRequestElement.getFriendRequest().getPlayerId().equals(packet.lIIIIIIIIIlIllIIllIlIIlIl())) continue;
                requestElement = friendRequestElement;
            }
            if (requestElement != null) {
                OverlayGui.getInstance().getFriendRequestsElement().getFrientRequestElementList().add(requestElement);
                OverlayGui.getInstance().handleFriendRequest(requestElement.getFriendRequest(), false);
            }
        }
    }

    public void handleKeyRequest(WSPacketKeyRequest packet) {
        try {
            byte[] test = "a".getBytes();
            //xxxbyte[] data = AssetsWebSocket.getKeyResponse(packet.getPublicKey(), test);
            //this.sendToServer(new WSPacketClientKeyResponse(data));
        }
        catch (Exception exc) {
            // empty catch block
        }
    }

    public static byte[] getKeyResponse(byte[] publicKey, byte[] privateKey) throws NoSuchAlgorithmException {
        return new byte[1024];
        /*xxx
        PublicKey key = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, key);
        return cipher.doFinal(privateKey);
        */
    }

    public void handleForceCrash(WSPacketForceCrash packet) {
//        Ref.getMinecraft().forceCrash = true; // NOT TODO: FIX
    }

    public void lIIIIlIIllIIlIIlIIIlIIllI(Profile iIlIllllIIlIlIIIlllIIllIl) {
        try {
            VFile2 file = new VFile2("config", "client", "profiles.txt");
            try (OutputStream os = file.getOutputStream()) {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                bufferedWriter.write("################################");
                bufferedWriter.newLine();
                bufferedWriter.write("# MC_Client: PROFILES");
                bufferedWriter.newLine();
                bufferedWriter.write("################################");
                bufferedWriter.newLine();
                for (Profile ilIIlIIlIIlllIlIIIlIllIIl : ArchClient.getInstance().profiles) {
                    bufferedWriter.write(ilIIlIIlIIlllIlIIIlIllIIl.getName() + ":" + ilIIlIIlIIlllIlIIIlIllIIl.getIndex());
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
            catch (Exception exception) {}
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}
