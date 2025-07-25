package com.archclient.client.ui.overlay.friend;

import com.archclient.bridge.ref.Ref;
import com.archclient.bridge.util.EnumChatFormattingBridge;
import com.archclient.main.ArchClient;
import com.archclient.client.ui.mainmenu.element.ScrollableElement;
import com.archclient.client.ui.overlay.OverlayGui;
import com.archclient.client.ui.overlay.element.ElementListElement;
import com.archclient.client.ui.overlay.element.InputFieldElement;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class FriendsListElement extends ElementListElement<FriendElement> {
    private final InputFieldElement filterElement;
    private final ScrollableElement scrollableElement;
    private final List<FriendElement> friendElements = new ArrayList<>();

    public FriendsListElement(List<FriendElement> list) {
        super(list);
        this.filterElement = new InputFieldElement(FontRegistry.getPlayRegular16px(), "Filter", 0x2FFFFFFF, 0x6FFFFFFF);
        this.scrollableElement = new ScrollableElement(this);
    }

    public void updateSize() {
        this.setElementDimensions(this.x, this.y, this.width, this.height);
    }

    @Override
    public void setElementDimensions(float x, float y, float width, float height) {
        super.setElementDimensions(x, y, width, height);
        this.filterElement.setElementDimensions(0.0f, y, width, 13);
        this.scrollableElement.setElementDimensions(x + width - (float)4, y, (float)4, height);
        this.elements.sort((friendElement, friendElement2) -> {
            String string = EnumChatFormattingBridge.getTextWithoutFormattingCodes(friendElement.getFriend().getName());
            String string2 = EnumChatFormattingBridge.getTextWithoutFormattingCodes(friendElement2.getFriend().getName());
            if (friendElement.getFriend().getPlayerId().equals(friendElement2.getFriend().getPlayerId())) {
                return string.compareTo(string2);
            }
            return friendElement.getFriend().isOnline() ? 0 : 1;
        });
        int n = 22;
        int n2 = 0;
        for (FriendElement friendElement : this.elements) {
            if (!this.isFilterMatch(friendElement)) continue;
            friendElement.setElementDimensions(x, y + (float)14 + (float)(n2 * 22), width, 22);
            ++n2;
        }
        this.scrollableElement.setScrollAmount(14 + this.elements.size() * 22);
    }

    private boolean isFilterMatch(FriendElement friendElement) {
        return this.filterElement.getText().equals("") || EnumChatFormattingBridge.getTextWithoutFormattingCodes(friendElement.getFriend().getName()).toLowerCase().startsWith(this.filterElement.getText().toLowerCase());
    }

    /*
     * Iterators could be improved
     */
    @Override
    public void handleElementDraw(float mouseX, float mouseY, boolean enableMouse) {
        if (!this.friendElements.isEmpty()) {
            this.elements.removeAll(this.friendElements);
            OverlayGui.getInstance().getFriendsListElement().updateSize();
        }
        if (!ArchClient.getInstance().getAssetsWebSocket().isOpen()) {
            FontRegistry.getPlayRegular16px().drawCenteredString("Connection lost", this.x + this.width / 2.0f, this.y + (float)10, -1);
            FontRegistry.getPlayRegular16px().drawCenteredString("Please try again later.", this.x + this.width / 2.0f, this.y + (float)22, -1);
        } else {
            Ref.getGlBridge().bridge$pushMatrix();
            Ref.getGlBridge().bridge$enableScissoring();
            OverlayGui illlllIllIIIllIIIllIllIII = OverlayGui.getInstance();
            this.scrollableElement.drawScrollable(mouseX, mouseY, enableMouse);
            RenderUtil.scissorArea((int)this.x, (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), (float)((int)((float)illlllIllIIIllIIIllIllIII.getResolution().getScaleFactor() * illlllIllIIIllIIIllIllIII.getScaleFactor())), (int)illlllIllIIIllIIIllIllIII.getScaledHeight());
            ImmutableList<FriendElement> immutableList = ImmutableList.copyOf(this.elements);
            for (FriendElement friendElement : immutableList) {
                if (!this.isFilterMatch(friendElement)) continue;
                friendElement.drawElement(mouseX, mouseY - this.scrollableElement.getTranslateY(), enableMouse && !this.scrollableElement.isMouseInside(mouseX, mouseY));
            }
            if (immutableList.isEmpty()) {
                FontRegistry.getPlayRegular16px().drawCenteredString("No friends", this.x + this.width / 2.0f, this.y + (float)30, -1);
            }
            this.filterElement.drawElement(mouseX, mouseY - this.scrollableElement.getTranslateY(), enableMouse);
            Ref.getGlBridge().bridge$disableScissoring();
            Ref.getGlBridge().bridge$popMatrix();
            this.scrollableElement.handleElementDraw(mouseX, mouseY, enableMouse);
        }
    }

    @Override
    public void handleElementMouse() {
        this.scrollableElement.handleElementMouse();
    }

    @Override
    public void handleElementUpdate() {
        this.filterElement.handleElementUpdate();
        this.scrollableElement.handleElementUpdate();
    }

    @Override
    public void handleElementClose() {
        this.filterElement.handleElementClose();
        this.scrollableElement.handleElementClose();
    }

    @Override
    public void handleElementKeyTyped(char c, int n) {
        super.handleElementKeyTyped(c, n);
        this.filterElement.handleElementKeyTyped(c, n);
        this.scrollableElement.handleElementKeyTyped(c, n);
        this.setElementDimensions(this.x, this.y, this.width, this.height);
    }

    @Override
    public boolean handleElementMouseClicked(float mouseX, float mouseY, int mouseButton, boolean enableMouse) {
        this.filterElement.handleElementMouseClicked(mouseX, mouseY - this.scrollableElement.getTranslateY(), mouseButton, enableMouse);
        if (this.filterElement.lllIIIIIlIllIlIIIllllllII() && mouseButton == 1 && this.filterElement.getText().equals("")) {
            this.updateSize();
        }
        if (!enableMouse) {
            return false;
        }
        this.scrollableElement.handleElementMouseClicked(mouseX, mouseY, mouseButton, enableMouse);
        boolean bl2 = false;
        for (FriendElement frientElement : this.elements) {
            if (!this.isFilterMatch(frientElement)) continue;
            if (bl2) break;
            bl2 = frientElement.handleElementMouseClicked(mouseX, mouseY - this.scrollableElement.getTranslateY(), mouseButton, enableMouse && !this.scrollableElement.isMouseInside(mouseX, mouseY));
        }
        return bl2;
    }

    @Override
    public boolean handleElementMouseRelease(float f, float f2, int n, boolean bl) {
        this.filterElement.handleElementMouseRelease(f, f2 - this.scrollableElement.getTranslateY(), n, bl);
        this.scrollableElement.handleElementMouseRelease(f, f2, n, bl);
        if (!bl) {
            return false;
        }
        boolean bl2 = false;
        for (FriendElement friendElement : this.elements) {
            if (!this.isFilterMatch(friendElement)) continue;
            if (bl2) break;
            bl2 = friendElement.handleElementMouseRelease(f, f2, n, bl);
        }
        return bl2;
    }

    public List<FriendElement> getFriendElements() {
        return this.friendElements;
    }
}