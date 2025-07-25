package com.archclient.client.ui.overlay;

import com.archclient.bridge.ref.Ref;
import com.archclient.bridge.wrapper.ACGuiScreen;
import com.archclient.main.ArchClient;
import com.archclient.client.ui.AbstractGui;
import com.archclient.client.ui.mainmenu.AbstractElement;
import com.archclient.client.ui.overlay.element.ElementListElement;
import com.archclient.client.ui.overlay.element.FlatButtonElement;
import com.archclient.client.ui.overlay.element.MessagesElement;
import com.archclient.client.ui.overlay.friend.*;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;
import com.archclient.client.util.friend.Friend;
import com.archclient.client.util.friend.Status;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.lax1dude.eaglercraft.v1_8.internal.KeyboardConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import static net.lax1dude.eaglercraft.v1_8.internal.PlatformOpenGL.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OverlayGui extends AbstractGui {
    private static OverlayGui instance;
    private final FriendsListElement friendsListElement;
    private final FriendRequestListElement friendRequestsElement;
    private final FlatButtonElement friendsButton;
    private final FlatButtonElement requestsButton;
    private ElementListElement<?> selectedElement;
    //xxxprivate final RadioElement radioElement;
    private long initGuiMillis;
    private final Queue<Alert> alertQueue = new LinkedList<>();
    private final List<Alert> alertList = new ArrayList<>();
    private ACGuiScreen context;

    public static OverlayGui getInstance() {
        return instance;
    }
    public static void setInstance(OverlayGui instance) {
        OverlayGui.instance = instance;
    }

    public FriendsListElement getFriendsListElement() {
        return this.friendsListElement;
    }

    public FriendRequestListElement getFriendRequestsElement() {
        return this.friendRequestsElement;
    }

    public OverlayGui() {
        List<FriendElement> arrayList = new ArrayList<>();
        ArchClient.getInstance()
                .getFriendsManager()
                .getFriends()
                .forEach((string, friend) -> arrayList.add(new FriendElement(friend)));
        this.setElementsAndUpdateSize(
                this.friendsListElement = new FriendsListElement(arrayList),
                this.friendRequestsElement = new FriendRequestListElement(new ArrayList<>()),
                this.requestsButton = new FlatButtonElement("REQUESTS"),
                this.friendsButton = new FlatButtonElement("FRIENDS")
                );//xxxthis.radioElement = new RadioElement());
        this.selectedElement = this.friendsListElement;
    }

    public void lIIIIlIIllIIlIIlIIIlIIllI(Friend friend) {
        try {
            MessagesElement messagesElement2 = null;

            for (AbstractElement element : this.elements) {
                if (element instanceof MessagesElement) {
                    messagesElement2 = (MessagesElement) element;
                }
            }

            if (messagesElement2 == null) {
                MessagesElement messagesElement = new MessagesElement(friend);
                this.elements.add(messagesElement);
                messagesElement.setElementDimensions(170f, 30f, 245f, 150f);
            } else {
                this.elements.add(this.elements.remove(this.elements.indexOf(messagesElement2)));
                messagesElement2.setFriend(friend);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.initGuiMillis = System.currentTimeMillis();
//        if (this.mc.previousGuiScreen != this) {
//            this.IllIllIIIlIIlllIIIllIllII(); // gui blur
//        }
        this.friendsButton.setElementDimensions(0f, 28f, 69.5f, 20f);
        this.requestsButton.setElementDimensions(70.5f, 28f, 69.5f, 20f);
        float f = 28f + this.friendsButton.getHeight() + 1f;
        this.friendsListElement.setElementDimensions(0f, f, 140f, this.getScaledHeight() - f);
        this.friendRequestsElement.setElementDimensions(0f, f, 140f, this.getScaledHeight() - f);
        //xxxthis.radioElement.setElementDimensions(this.getScaledWidth() - 210f, 20f, 190f, 28);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        this.handleMouse();
    }

    @Override
    public void drawMenu(float f, float f2) {
        _wglClear(0x100);
        Ref.getGlBridge().bridge$enableAlphaTest();
        drawDefaultBackground();
        //this.lIIIIIIIIIlIllIIllIlIIlIl(this.getScaledWidth(), this.getScaledHeight()); gui blur
        Ref.modified$drawRect(0.0f, 0.0f, 140, this.getScaledHeight(), -14671840);
        Ref.modified$drawRect(140, 0.0f, 141, this.getScaledHeight(), -15395563);
        Ref.modified$drawRect(0.0f, 0.0f, 140, 28, -15395563);
        Ref.modified$drawRect(6, 6, 22, 22, Friend.getStatusColor(ArchClient.getInstance().getStatus()));
        Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
        ResourceLocation headLocation = ArchClient.getInstance().getHeadLocation(this.mc.getSession().getProfile().getName());
        RenderUtil.drawIcon(headLocation, 7f, 7f, 7f);
        String username = this.mc.getSession().getProfile().getName();
        FontRegistry.getPlayRegular16px().drawString(username, 28, 6f, -1);
        FontRegistry.getPlayRegular16px().drawString(ArchClient.getInstance().getStatusString(), 28, (float)15, -5460820);
        boolean statusHovered = f > 6f && f < 94f && f2 > 6f && f2 < 22f;
        if (this.isMouseHovered(this.friendsButton, f, f2) && statusHovered && ArchClient.getInstance().getAssetsWebSocket().isOpen()) {
            Ref.modified$drawRect(22, 0.0f, 140, 28, -15395563);
            Ref.modified$drawRect(24, 6, 40, 22, Friend.getStatusColor(Status.ONLINE));
            Ref.modified$drawRect(42, 6, 58, 22, Friend.getStatusColor(Status.AWAY));
            Ref.modified$drawRect(60, 6, 76, 22, Friend.getStatusColor(Status.BUSY));
            Ref.modified$drawRect(78, 6, 94, 22, Friend.getStatusColor(Status.HIDDEN));
            boolean onlineHovered = f > 24f && f < 40f;
            boolean awayHovered = f > 42f && f < 58f;
            boolean busyHovered = f > 60f && f < 76f;
            boolean offlineHovered = f > 78f;
            Ref.getGlBridge().bridge$color(
                    onlineHovered ? 0.35f : 0.15f,
                    onlineHovered ? 0.35f : 0.15f,
                    onlineHovered ? 0.35f : 0.15f,
                    1f);
            RenderUtil.drawIcon(headLocation, 7f, 25f, 7f);
            Ref.getGlBridge().bridge$color(
                    awayHovered ? 0.35f : 0.15f,
                    awayHovered ? 0.35f : 0.15f,
                    awayHovered ? 0.35f : 0.15f,
                    1f);
            RenderUtil.drawIcon(headLocation, 7f, 43f, 7f);
            Ref.getGlBridge().bridge$color(
                    busyHovered ? 0.35f : 0.15f,
                    busyHovered ? 0.35f : 0.15f,
                    busyHovered ? 0.35f : 0.15f,
                    1f);
            RenderUtil.drawIcon(headLocation, 7f, 61f, 7f);
            Ref.getGlBridge().bridge$color(
                    offlineHovered ? 0.35f : 0.15f,
                    offlineHovered ? 0.35f : 0.15f,
                    offlineHovered ? 0.35f : 0.15f,
                    1f);
            RenderUtil.drawIcon(headLocation, 7f, 79f, 7f);
        }
        this.selectedElement.drawElement(f, f2, this.isMouseHovered(this.requestsButton, f, f2));
        Ref.modified$drawRect(69.5f, 28f, 70.5f, 28f + this.friendsButton.getHeight(),
                -14869219);
        Ref.modified$drawRect(0f, 28f + this.friendsButton.getHeight(), 140f,
                28f + this.friendsButton.getHeight() + 1f, -15395563);
        this.drawElements(f, f2, this.friendsListElement, this.friendRequestsElement);
        Ref.getGlBridge().bridge$disableAlphaTest();
    }

    @Override
    public void keyTyped(char c, int n) {
        if (n == 15 && Keyboard.isKeyDown(42) && System.currentTimeMillis() - this.initGuiMillis > 200L || n == 1) {
            this.mc.bridge$displayGuiScreen(this.context);
        }
        super.handleKeyTyped(c, n);
        if (n == KeyboardConstants.KEY_GRAVE && ArchClient.getInstance().isConsoleAllowed()) {
            boolean shouldOpen = true;
            for (AbstractElement element : this.elements) {
                if (element instanceof ConsoleElement) {
                    shouldOpen = false;
                    break;
                }
            }
            if (shouldOpen) {
                ConsoleElement consoleGui = new ConsoleElement();
                this.addElements(consoleGui);
                consoleGui.setElementDimensions(60f, 30f, 300f, 145f);
            }
        }
    }

    @Override
    protected void onMouseClicked(float f, float f2, int n) {
        this.selectedElement.handleElementMouseClicked(f, f2, n, this.isMouseHovered(this.requestsButton, f, f2));
        this.onMouseClicked(f, f2, n, this.friendsListElement, this.friendRequestsElement);
        boolean bl2 = this.isMouseHovered(this.friendsButton, f, f2);
        if (bl2 && this.friendsButton.isMouseInside(f, f2) && this.selectedElement != this.friendsListElement) {
            this.mc.getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref
                    .getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            this.selectedElement = this.friendsListElement;
        } else if (bl2 && this.requestsButton.isMouseInside(f, f2) && this.selectedElement != this.friendRequestsElement) {
            this.mc.getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref
                    .getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            this.selectedElement = this.friendRequestsElement;
        }
        boolean bl = f > 6f && f < 134f && f2 > 6f && f2 < 22f;
        if (bl2 && bl && ArchClient.getInstance().getAssetsWebSocket().isOpen()) {
            boolean onlineHovered = f > 24f && f < 40f;
            boolean awayHovered = f > 42f && f < 58f;
            boolean busyHovered = f > 60f && f < 76f;
            boolean offlineHovered = f > 78f && f < 94f;
            if (onlineHovered) {
                ArchClient.getInstance().setStatus(Status.ONLINE);
                this.mc.getSoundHandler().playSound(Ref.getInstanceCreator()
                        .createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"),
                                1.0f));
            } else if (awayHovered) {
                ArchClient.getInstance().setStatus(Status.AWAY);
                this.mc.getSoundHandler().playSound(Ref.getInstanceCreator()
                        .createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"),
                                1.0f));
            } else if (busyHovered) {
                ArchClient.getInstance().setStatus(Status.BUSY);
                this.mc.getSoundHandler().playSound(Ref.getInstanceCreator()
                        .createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"),
                                1.0f));
            } else if (offlineHovered) {
                ArchClient.getInstance().setStatus(Status.HIDDEN);
                this.mc.getSoundHandler().playSound(Ref.getInstanceCreator()
                        .createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"),
                                1.0f));
            }
            ArchClient.getInstance().getAssetsWebSocket().updateClientStatus();
        }
    }

    @Override
    public void onMouseMovedOrUp(float f, float f2, int n) {
        this.handlemouseMovedOrUp(f, f2, n);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.context = null;
        this.handleClose();
        //this.mc.entityRenderer.unloadSounds();
    }

    @Override
    public void updateScreen() {
        if (this.context != null) {
            this.context.updateScreen();
        }
        this.friendsButton.setText("FRIENDS (" + this.friendsListElement.getElements().size() + ")");
        this.requestsButton.setText("REQUESTS (" + this.friendRequestsElement.getElements().stream()
                .filter(element -> !element.getFriendRequest().isFriend()).count() + ")");
        this.updateElements();
    }

    public void pollNotifications() {
        this.alertList.removeIf(Alert::alertExpired);
        if (this.alertQueue.isEmpty()) {
            return;
        }
        boolean bl = true;
        for (Alert alert : this.alertList) {
            if (alert.hasFadeFinished()) {
                continue;
            }

            bl = false;
        }
        if (bl) {
            System.out.println("drawing");
            Alert alert = this.alertQueue.poll();
            if (alert != null) {
                alert.safeSetY(this.getScaledWidth() - Alert.getElementHeight());
                this.alertList.forEach(alert1 -> alert1.safeSetY(alert1.getTargetY() - Alert.getElementHeight()));
                this.alertList.add(alert);
            }
        }
    }

    public void renderGameOverlay() {
        this.alertList.forEach(Alert::drawAlert);
        /*xxx
        if (this.mc != null && this.mc.bridge$isIngame() && ArchClient.getInstance().getGlobalSettings().pinRadio
                .<Boolean>value() && DashUtil.isPlayerNotNull()) {
            this.radioElement.drawElement(0f, 0f, false);
        }
        */
    }

    @Override
    public void setWorldAndResolution(Minecraft minecraft, int displayWidth, int displayHeight) {
        if (this.context != null) {
            this.context.setWorldAndResolution(minecraft, displayWidth, displayHeight);
        }
        super.setWorldAndResolution(minecraft, displayWidth, displayHeight);
        float scaleFactor = this.getScaleFactor();
        this.alertList.forEach(alert -> this.renderAlert(alert, this.getScaledHeight() - scaleFactor));
        this.alertQueue.forEach(alert -> this.renderAlert(alert, this.getScaledHeight() - scaleFactor));
    }

    private void renderAlert(Alert alert, float f) {
        alert.setX(this.getScaledWidth() - (float) Alert.getElementWidth());
        alert.setY(alert.getY() + f);
        alert.setTargetY(alert.getTargetY() + f);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float delta) {
        if (this.context != null) {
            this.context.drawScreen(-1, -1, delta);
        }
        super.drawScreen(mouseX, mouseY, delta);
    }

    public void setSection(String string) {
        this.queueAlert("", string);
    }

    public void queueAlert(String title, String content) {
        int width = Alert.getElementWidth();
        content = FontRegistry.getPlayRegular16px().setWrapWords(content, width - 10);
        Alert alert = new Alert(title, content.split("\n"), this.getScaledWidth() - width, this.getScaledHeight());
        alert.setDisableTitle(title.equals(""));
        this.alertQueue.add(alert);
    }

    public void handleFriend(Friend friend, boolean add) {
        if (add) {
            this.friendsListElement.getElements().add(new FriendElement(friend));
        } else {
            this.friendsListElement.getElements().removeIf(element -> element.getFriend() == friend);
        }
        this.friendsListElement.updateSize();
    }

    public void handleFriendRequest(FriendRequest friendRequest, boolean add) {
        if (add) {
            this.friendRequestsElement.getElements().add(new FriendRequestElement(friendRequest));
        } else {
            this.friendRequestsElement.getElements().removeIf(element -> element.getFriendRequest() == friendRequest);
        }
        this.friendRequestsElement.resetSize();
    }

    public static OverlayGui createInstance(ACGuiScreen screen) {
        if (screen != instance) {
            getInstance().context = screen;
        }
        return getInstance();
    }
}
