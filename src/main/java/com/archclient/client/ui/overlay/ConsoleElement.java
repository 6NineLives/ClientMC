package com.archclient.client.ui.overlay;

import com.archclient.bridge.ref.Ref;
import com.archclient.bridge.util.EnumChatFormattingBridge;
import com.archclient.main.ArchClient;
import com.archclient.client.ui.mainmenu.element.ScrollableElement;
import com.archclient.client.ui.overlay.element.DraggableElement;
import com.archclient.client.ui.overlay.element.FlatButtonElement;
import com.archclient.client.ui.overlay.element.InputFieldElement;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;
import com.archclient.client.websocket.shared.WSPacketConsole;

import java.util.List;

public class ConsoleElement extends DraggableElement {
    private final InputFieldElement textInputElement;
    private final FlatButtonElement sentButton;
    private final ScrollableElement scrollableElement;
    private final FlatButtonElement closeButton;

    public ConsoleElement() {
        this.textInputElement = new InputFieldElement(FontRegistry.getPlayRegular16px(), "", 0x2FFFFFFF, 0x6FFFFFFF);
        this.textInputElement.trimToLength(256);
        this.sentButton = new FlatButtonElement("SEND");
        this.scrollableElement = new ScrollableElement(this);
        this.closeButton = new FlatButtonElement("X");
    }

    @Override
    public void setElementDimensions(float x, float y, float width, float height) {
        super.setElementDimensions(x, y, width, height);
        this.textInputElement.setElementDimensions(x + 2.0f, y + height - (float)15, width - (float)40, 13);
        this.sentButton.setElementDimensions(x + width - (float)37, y + height - (float)15, (float)35, 13);
        this.scrollableElement.setElementDimensions(x + width - (float)6, y + (float)12 + (float)3, (float)4, height - (float)32);
        this.closeButton.setElementDimensions(x + width - (float)12, y + 2.0f, (float)10, 10);
    }

    @Override
    public void handleElementDraw(float mouseX, float mouseY, boolean enableMouse) {
        this.drag(mouseX, mouseY);
        Ref.modified$drawBoxWithOutLine(this.x, this.y, this.x + this.width, this.y + this.height, 0.2972973f * 1.6818181f, -16777216, -15395563);
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.modified$drawRect(this.x, this.y - 0.25f * 2.0f, this.x + this.width, this.y, -1357572843);
        Ref.modified$drawRect(this.x, this.y + this.height, this.x + this.height, this.y + this.height + 0.8961039f * 0.557971f, -1357572843);
        FontRegistry.getPlayRegular16px().drawString("Console", this.x + (float)4, this.y + (float)3, -1);
        Ref.getGlBridge().bridge$color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Ref.modified$drawRect(this.x + 2.0f, this.y + (float)12 + (float)3, this.x + this.width - 2.0f, this.y + this.height - (float)17, -1356783327);
        this.scrollableElement.handleScrollableMouseClicked(mouseX, mouseY, enableMouse);
        try {
            if (ArchClient.getInstance().isConsoleAllowed()) {
                Ref.getGlBridge().bridge$pushMatrix();
                Ref.getGlBridge().bridge$enableScissoring();
                OverlayGui overlayGui = OverlayGui.getInstance();
                RenderUtil.scissorArea((int)(this.x + 2.0f), (int)(this.y + (float)12 + (float)3), (int)(this.x + this.width - 2.0f), (int)(this.y + this.height - (float)17), (float)((int)((float) overlayGui.getResolution().getScaleFactor() * overlayGui.getScaleFactor())), (int) overlayGui.getScaledHeight());
                List<String> list = ArchClient.getInstance().getConsoleLines();
                int n = 0;
                for (int i = list.size() - 1; i >= 0; --i) {
                    String string = list.get(i);
                    String[] arrstring = FontRegistry.getPlayRegular16px().setWrapWords(string, this.width - (float)10).split("\n");
                    n += arrstring.length * 10;
                    int n2 = 0;
                    for (String string2 : arrstring) {
                        FontRegistry.getPlayRegular16px().drawString(string2, this.x + (float)6, this.y + this.height - (float)19 - (float)n + (float)(n2 * 10), -1);
                        ++n2;
                    }
                }
                this.scrollableElement.setScrollAmount(n + 4);
                Ref.getGlBridge().bridge$disableScissoring();
                Ref.getGlBridge().bridge$popMatrix();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        this.scrollableElement.scrollableOnMouseClick(mouseX, mouseY, enableMouse);
        Ref.getGlBridge().bridge$popMatrix();
        this.textInputElement.drawElement(mouseX, mouseY, enableMouse);
        this.sentButton.drawElement(mouseX, mouseY, enableMouse);
        this.closeButton.drawElement(mouseX, mouseY, enableMouse);
    }

    @Override
    public void handleElementUpdate() {
        this.textInputElement.handleElementUpdate();
        this.sentButton.handleElementUpdate();
        this.scrollableElement.handleElementUpdate();
        this.closeButton.handleElementUpdate();
    }

    @Override
    public void handleElementClose() {
        this.textInputElement.handleElementClose();
        this.sentButton.handleElementClose();
        this.scrollableElement.handleElementClose();
        this.closeButton.handleElementClose();
    }

    @Override
    public void handleElementKeyTyped(char c, int n) {
        if (this.textInputElement.lllIIIIIlIllIlIIIllllllII() && !this.textInputElement.getText().equals("") && n == 28) {
            this.sendCommandToServer();
        }
        this.textInputElement.handleElementKeyTyped(c, n);
        this.sentButton.handleElementKeyTyped(c, n);
        this.scrollableElement.handleElementKeyTyped(c, n);
        this.closeButton.handleElementKeyTyped(c, n);
    }

    @Override
    public boolean handleMouseClickedInternal(float f, float f2, int n) {
        if (!this.textInputElement.isMouseInside(f, f2) && this.textInputElement.lllIIIIIlIllIlIIIllllllII()) {
            this.textInputElement.lIIIIIIIIIlIllIIllIlIIlIl(false);
        }
        return false;
    }

    @Override
    public boolean handleElementMouseClicked(float mouseX, float mouseY, int mouseButton, boolean enableMouse) {
        this.textInputElement.handleElementMouseClicked(mouseX, mouseY, mouseButton, enableMouse);
        this.scrollableElement.handleElementMouseClicked(mouseX, mouseY, mouseButton, enableMouse);
        if (!enableMouse) {
            return false;
        }
        if (!this.textInputElement.getText().equals("") && this.sentButton.isMouseInside(mouseX, mouseY)) {
            this.sendCommandToServer();
        }
        this.sentButton.handleElementMouseClicked(mouseX, mouseY, mouseButton, true);
        if (this.isMouseInside(mouseX, mouseY) && mouseY < this.y + (float)12) {
            this.updateDraggingPosition(mouseX, mouseY);
        }
        if (this.closeButton.isMouseInside(mouseX, mouseY)) {
            this.mc.getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            OverlayGui.getInstance().removeElements(this);
            return true;
        }
        return false;
    }

    @Override
    public void handleElementMouse() {
        this.scrollableElement.handleElementMouse();
    }

    private void sendCommandToServer() {
        String string = this.textInputElement.getText();
        if (string.equals("clear") || string.equals("cls")) {
            ArchClient.getInstance().getConsoleLines().clear();
        } else {
            ArchClient.getInstance().getConsoleLines().add(EnumChatFormattingBridge.GRAY + "> " + string);
            ArchClient.getInstance().getAssetsWebSocket().sendToServer(new WSPacketConsole(string));
        }
        this.textInputElement.setText("");
        this.mc.getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
    }

    @Override
    public boolean handleElementMouseRelease(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        this.textInputElement.handleElementMouseRelease(f, f2, n, true);
        this.sentButton.handleElementMouseRelease(f, f2, n, true);
        this.scrollableElement.handleElementMouseRelease(f, f2, n, true);
        this.closeButton.handleElementMouseRelease(f, f2, n, true);
        return false;
    }

    public InputFieldElement getTextInputElement() {
        return this.textInputElement;
    }
}
