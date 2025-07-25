package com.archclient.client.ui.overlay.element;

import com.archclient.bridge.ref.Ref;
import com.archclient.bridge.util.EnumChatFormattingBridge;
import com.archclient.main.ArchClient;
import com.archclient.client.ui.mainmenu.AbstractElement;
import com.archclient.client.ui.mainmenu.element.ScrollableElement;
import com.archclient.client.ui.overlay.OverlayGui;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;
import com.archclient.client.util.friend.Friend;
import com.archclient.client.websocket.shared.WSPacketMessage;

import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.minecraft.util.ResourceLocation;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MessagesElement extends DraggableElement {
    private Friend friend;
    private final InputFieldElement inputFieldElement;
    private final FlatButtonElement sendButton;
    private final ScrollableElement messageListScrollable;
    private final ScrollableElement recentsScrollable;
    private final FlatButtonElement aliasesButton;
    private final FlatButtonElement closeButton;
    private final int IIIlllIIIllIllIlIIIIIIlII = 22;

    public MessagesElement(Friend friend) {
        this.friend = friend;
        this.inputFieldElement = new InputFieldElement(FontRegistry.getPlayRegular16px(), "Message", 0x2FFFFFFF, 0x6FFFFFFF);
        this.inputFieldElement.trimToLength(256);
        this.sendButton = new FlatButtonElement("SEND");
        this.messageListScrollable = new ScrollableElement(this);
        this.recentsScrollable = new ScrollableElement(this);
        this.aliasesButton = new FlatButtonElement("Aliases");
        this.closeButton = new FlatButtonElement("X");
    }

    @Override
    public void setElementDimensions(float x, float y, float width, float height) {
        super.setElementDimensions(x, y, width, height);
        this.inputFieldElement.setElementDimensions(x + 26f, y + height - 15f, width - 62f, 13);
        this.sendButton.setElementDimensions(x + width - 37f, y + height - 15f, 35f, 13);
        this.messageListScrollable.setElementDimensions(x + width - 6f, y + 22f, 4f, height - 39f);
        this.recentsScrollable.setElementDimensions(x + 2.0f, y + 2.0f, 2.0f, height - 4f);
        this.aliasesButton.setElementDimensions(x + width - 54f, y + 2.0f, 40f, 16);
        this.closeButton.setElementDimensions(x + width - 12f, y + 2.0f, 10f, 16);
    }

    public static String lIIIIlIIllIIlIIlIIIlIIllI(byte[] arrby) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(ArchClient.processBytesAuth, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, secretKeySpec);
        return new String(cipher.doFinal(arrby));
    }

    @Override
    public void handleElementDraw(float mouseX, float mouseY, boolean enableMouse) {
        String[] restring;
        this.drag(mouseX, mouseY);
        Ref.modified$drawBoxWithOutLine(this.x, this.y, this.x + (float)23, this.y + this.height, 0.074324325f * 6.7272725f, -16777216, -14869219);
        Ref.modified$drawBoxWithOutLine(this.x + (float)23, this.y, this.x + this.width, this.y + this.height, 0.7132353f * 0.7010309f, -16777216, -15395563);
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.modified$drawRect(this.x + (float)25, this.y - 1.9285715f * 0.25925925f, this.x + this.width, this.y, -1357572843);
        Ref.modified$drawRect(this.x + (float)25, this.y + this.height, this.x + this.width, this.y + this.height + 0.25f * 2.0f, -1357572843);
        Ref.modified$drawRect(this.x + (float)27, this.y + (float)3, this.x + (float)43, this.y + (float)19, this.friend.isOnline() ? Friend.getStatusColor(this.friend.getOnlineStatus()) : -13158601);
        FontRegistry.getPlayRegular16px().drawString(this.friend.getName(), this.x + (float)52, this.y + 2.0f, -1);
        FontRegistry.getPlayRegular16px().drawString(this.friend.getStatusString(), this.x + (float)52, this.y + (float)11, -5460820);
        Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
        ResourceLocation location = ArchClient.getInstance().getHeadLocation(EnumChatFormattingBridge.getTextWithoutFormattingCodes(this.friend.getName()));
        RenderUtil.drawIcon(location, (float)7, this.x + (float)28, this.y + (float)4);
        Ref.modified$drawRect(this.x + (float) 27, this.y + (float) 22, this.x + this.width - 2.0f, this.y + this.height - (float) 17, -1356783327);
        this.recentsScrollable.drawScrollable(mouseX, mouseY, enableMouse);
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.getGlBridge().bridge$enableScissoring();
        OverlayGui overlayGui = OverlayGui.getInstance();
        RenderUtil.scissorArea(0, (int)(this.y + 2.0f), (int) overlayGui.getScaledWidth(), (int)(this.y + this.height - 2.0f), (float)((int)((float) overlayGui.getResolution().getScaleFactor() * overlayGui.getScaleFactor())), (int) overlayGui.getScaledHeight());
        int n = 18;
        int n2 = 0;
        for (Friend friend : this.client.getFriendsManager().getFriends().values()) {
            if (friend != this.friend && !this.client.getFriendsManager().getMessages().containsKey(friend.getPlayerId()) && !friend.isOnline()) continue;
            float f3 = this.y + (float)3 + (float)n2;
            boolean bl2 = mouseX > this.x && mouseX < this.x + (float)25 && mouseY > f3 - this.recentsScrollable.getTranslateY() && mouseY < f3 + (float)16 - this.recentsScrollable.getTranslateY() && mouseY > this.y && mouseY < this.y + this.height;
            Ref.modified$drawRect(this.x + (float)3, f3, this.x + (float)19, f3 + (float)16, friend.isOnline() ? Friend.getStatusColor(friend.getOnlineStatus()) : -13158601);
            Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, bl2 ? 1.0f : 0.6016854f * 1.4126984f);
            ResourceLocation location1 = ArchClient.getInstance().getHeadLocation(EnumChatFormattingBridge.getTextWithoutFormattingCodes(friend.getName()));
            RenderUtil.drawIcon(location1, (float)7, this.x + (float)4, this.y + (float)4 + (float)n2);
            if (bl2) {
                float f4 = FontRegistry.getPlayRegular16px().getStringWidth(EnumChatFormattingBridge.getTextWithoutFormattingCodes(friend.getName()));
                RenderUtil.drawRoundedRect(this.x - (float)10 - f4, f3 + 2.0f, this.x - 2.0f, f3 + (float)14, (double)6, -1895825408);
                FontRegistry.getPlayRegular16px().drawString(friend.getName(), this.x - (float)6 - f4, f3 + (float)4, -1);
                if (Mouse.isButtonDown(0) && this.friend != friend) {
                    this.mc.getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                    this.friend = friend;
                }
            }
            n2 += 18;
        }
        this.recentsScrollable.setScrollAmount(n2);
        Ref.getGlBridge().bridge$disableScissoring();
        Ref.getGlBridge().bridge$popMatrix();
        this.recentsScrollable.handleElementDraw(mouseX, mouseY, enableMouse);
        this.messageListScrollable.handleScrollableMouseClicked(mouseX, mouseY, enableMouse);
        try {
            if (ArchClient.getInstance().getFriendsManager().getMessages().containsKey(this.friend.getPlayerId())) {
                Ref.getGlBridge().bridge$pushMatrix();
                Ref.getGlBridge().bridge$enableScissoring();
                RenderUtil.scissorArea((int)(this.x + 2.0f), (int)(this.y + (float)22), (int)(this.x + this.width - 2.0f), (int)(this.y + this.height - (float)17), (float)((int)((float) overlayGui.getResolution().getScaleFactor() * overlayGui.getScaleFactor())), (int) overlayGui.getScaledHeight());
                List<String> messages = ArchClient.getInstance().getFriendsManager().getMessages().get(this.friend.getPlayerId());
                int n3 = 0;
                for (int messageIndex = messages.size() - 1; messageIndex >= 0; --messageIndex) {
                    String message = messages.get(messageIndex);
                    restring = FontRegistry.getPlayRegular16px().setWrapWords(message, this.width - (float)25).split("\n");
                    n3 += restring.length * 10;
                    int n4 = 0;
                    for (String string2 : restring) {
                        FontRegistry.getPlayRegular16px().drawString(string2, this.x + (float)31, this.y + this.height - (float)19 - (float)n3 + (float)(n4 * 10), -1);
                        ++n4;
                    }
                }
                this.messageListScrollable.setScrollAmount(n3 + 4);
                Ref.getGlBridge().bridge$disableScissoring();
                Ref.getGlBridge().bridge$popMatrix();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        this.messageListScrollable.scrollableOnMouseClick(mouseX, mouseY, enableMouse);
        Ref.getGlBridge().bridge$popMatrix();
        this.inputFieldElement.drawElement(mouseX, mouseY, enableMouse);
        this.sendButton.drawElement(mouseX, mouseY, enableMouse);
        this.aliasesButton.drawElement(mouseX, mouseY, enableMouse);
        this.closeButton.drawElement(mouseX, mouseY, enableMouse);
    }

    @Override
    public void handleElementUpdate() {
        this.inputFieldElement.handleElementUpdate();
        this.sendButton.handleElementUpdate();
        this.messageListScrollable.handleElementUpdate();
        this.aliasesButton.handleElementUpdate();
        this.closeButton.handleElementUpdate();
    }

    @Override
    public void handleElementClose() {
        this.inputFieldElement.handleElementClose();
        this.sendButton.handleElementClose();
        this.messageListScrollable.handleElementClose();
        this.aliasesButton.handleElementClose();
        this.closeButton.handleElementClose();
    }

    @Override
    public void handleElementKeyTyped(char c, int n) {
        if (this.inputFieldElement.lllIIIIIlIllIlIIIllllllII() && !this.inputFieldElement.getText().equals("") && n == 28) {
            this.sendMessage();
        }
        this.inputFieldElement.handleElementKeyTyped(c, n);
        this.sendButton.handleElementKeyTyped(c, n);
        this.messageListScrollable.handleElementKeyTyped(c, n);
        this.aliasesButton.handleElementKeyTyped(c, n);
        this.closeButton.handleElementKeyTyped(c, n);
    }

    @Override
    public boolean handleMouseClickedInternal(float f, float f2, int n) {
        if (!this.inputFieldElement.isMouseInside(f, f2) && this.inputFieldElement.lllIIIIIlIllIlIIIllllllII()) {
            this.inputFieldElement.lIIIIIIIIIlIllIIllIlIIlIl(false);
        }
        return false;
    }

    @Override
    public boolean handleElementMouseClicked(float mouseX, float mouseY, int mouseButton, boolean enableMouse) {
        this.inputFieldElement.handleElementMouseClicked(mouseX, mouseY, mouseButton, enableMouse);
        if (!enableMouse) {
            return false;
        }
        if (!this.inputFieldElement.getText().equals("") && this.sendButton.isMouseInside(mouseX, mouseY)) {
            this.sendMessage();
        }
        this.sendButton.handleElementMouseClicked(mouseX, mouseY, mouseButton, true);
        this.messageListScrollable.handleElementMouseClicked(mouseX, mouseY, mouseButton, true);
        this.aliasesButton.handleElementMouseClicked(mouseX, mouseY, mouseButton, true);
        if (!this.aliasesButton.isMouseInside(mouseX, mouseY) && this.isMouseInside(mouseX, mouseY) && mouseY < this.y + (float)22) {
            this.updateDraggingPosition(mouseX, mouseY);
        }
        if (this.closeButton.isMouseInside(mouseX, mouseY)) {
            this.mc.getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            OverlayGui.getInstance().removeElements(this);
            return true;
        }
        if (this.aliasesButton.isMouseInside(mouseX, mouseY)) {
            this.mc.getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            AbstractElement[] abstractElements = new AbstractElement[1];
            AliasesElement aliasesElement = new AliasesElement(this.friend);
            abstractElements[0] = aliasesElement;
            OverlayGui.getInstance().addElements(abstractElements);
            aliasesElement.setElementDimensions((float)60, (float)30, (float)140, 30);
            return true;
        }
        return false;
    }

    @Override
    public void handleElementMouse() {
        this.messageListScrollable.handleElementMouse();
        this.recentsScrollable.handleElementMouse();
    }

    private void sendMessage() {
        String message = this.inputFieldElement.getText();
        ArchClient.getInstance().getFriendsManager().addOutgoingMessage(this.friend.getPlayerId(), message);
        ArchClient.getInstance().getAssetsWebSocket().sendToServer(new WSPacketMessage(this.friend.getPlayerId(), message));
        this.inputFieldElement.setText("");
        this.mc.getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
    }

    @Override
    public boolean handleElementMouseRelease(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        this.inputFieldElement.handleElementMouseRelease(f, f2, n, true);
        this.sendButton.handleElementMouseRelease(f, f2, n, true);
        this.messageListScrollable.handleElementMouseRelease(f, f2, n, true);
        this.aliasesButton.handleElementMouseRelease(f, f2, n, true);
        this.closeButton.handleElementMouseRelease(f, f2, n, true);
        return false;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public Friend getFriend() {
        return this.friend;
    }

    public InputFieldElement getInputField() {
        return this.inputFieldElement;
    }
}
