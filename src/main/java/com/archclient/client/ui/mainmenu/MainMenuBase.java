package com.archclient.client.ui.mainmenu;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.ui.AbstractGui;
import com.archclient.client.ui.fading.ColorFade;
import com.archclient.client.ui.mainmenu.cosmetics.GuiCosmetics;
import com.archclient.client.ui.mainmenu.element.IconButtonElement;
import com.archclient.client.ui.mainmenu.element.TextButtonElement;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;
import com.archclient.main.ArchClient;

import net.lax1dude.eaglercraft.v1_8.internal.vfs2.VFile2;
import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.*;

public class MainMenuBase extends AbstractGui {
    protected static int panoramaTimer = 4100;
    private final ResourceLocation logo = Ref.getInstanceCreator().createResourceLocation("client/logo_42.png");
    private final IconButtonElement exitButton;
    private final IconButtonElement languageButton;
    private final IconButtonElement modMenuButton;
    private final IconButtonElement replayViewerButton;
    private final AccountList accountList;
    private final TextButtonElement optionsButton;
    private final TextButtonElement cosmeticsButton;
    private final ColorFade acTextShadowFade;
    protected final ResourceLocation[] panoramaImages = new ResourceLocation[] {
            Ref.getInstanceCreator().createResourceLocation("client/panorama/0.png"),
            Ref.getInstanceCreator().createResourceLocation("client/panorama/1.png"),
            Ref.getInstanceCreator().createResourceLocation("client/panorama/2.png"),
            Ref.getInstanceCreator().createResourceLocation("client/panorama/3.png"),
            Ref.getInstanceCreator().createResourceLocation("client/panorama/4.png"),
            Ref.getInstanceCreator().createResourceLocation("client/panorama/5.png")
    };
    protected ResourceLocation panoramaBackgroundLocation;
    private final VFile2 launcherAccounts;
    private final List<Account> accountsList;
    private float accountButtonWidth;

    public boolean renderExtraButtons = true;

    private final List<TextButtonElement> topBarButtons = new ArrayList<>();
    private final List<IconButtonElement> bottomButtons = new ArrayList<>();

    public MainMenuBase() {
        this.launcherAccounts = new VFile2("launcher_accounts.json");
        this.accountsList = new ArrayList<>();

        this.topBarButtons.add(this.optionsButton = new TextButtonElement("OPTIONS"));
        this.topBarButtons.add(this.cosmeticsButton = new TextButtonElement("COSMETICS"));

        this.bottomButtons.add(this.languageButton = new IconButtonElement(6,
                Ref.getInstanceCreator().createResourceLocation("client/icons/globe-24.png")));

        this.replayViewerButton = new IconButtonElement(8,
                Ref.getInstanceCreator().createResourceLocation("client/external/replaymod-logo.png"));

        this.modMenuButton = new IconButtonElement(8,
                Ref.getInstanceCreator().createResourceLocation("client/external/modmenu-logo.png"));

        /*xxx
        if (ReplayModCompatibility.isReplayModPresent()) {
            this.bottomButtons.add(this.replayViewerButton);
        }

        if (ModMenuCompatibility.isModMenuPresent()) {
            this.bottomButtons.add(this.modMenuButton);
        }
        */

        this.acTextShadowFade = new ColorFade(0xF000000, -16777216);
        this.exitButton = new IconButtonElement(Ref.getInstanceCreator().createResourceLocation("client/icons/delete-64.png"));
        this.accountButtonWidth = FontRegistry.getRobotoRegular13px().getStringWidth(Ref.getMinecraft().getSession().getProfile().getName());
        this.accountList = new AccountList(this, Ref.getMinecraft().getSession().getProfile().getName(), ArchClient.getInstance().getHeadLocation(Ref.getMinecraft().getSession().getProfile().getName()));
        //this.loadAccounts();
    }

    /*
     * Could not resolve type clashes
     */
    private void loadAccounts() {
        // TODO: rewrite this
        /*XXX
        Minecraft minecraft = Ref.getMinecraft();
        if (launcherAccounts.exists()) {
            try (final BufferedReader reader = new BufferedReader(new FileReader(new File(Ref.getMinecraft().bridge$getMcDataDir(), "launcher_accounts.json")))) {
                final JsonObject object = new JsonParser().parse(reader).getAsJsonObject();
                final Set<Map.Entry<String, JsonElement>> accounts = object.getAsJsonObject("accounts").entrySet();
                for (Map.Entry<String, JsonElement> accountElement : accounts) {
                    JsonObject account = accountElement.getValue().getAsJsonObject();
                    String accessToken = account.get("accessToken").getAsString();
                    JsonObject minecraftProfileObject = account.getAsJsonObject("minecraftProfile");
                    String uuid = minecraftProfileObject.get("id").getAsString();
                    String displayName = minecraftProfileObject.get("name").getAsString();
                    String clientToken = object.get("mojangClientToken").getAsString();
                    String userName = account.get("username").toString();
                    if (userName != null){
                        Account finalAccount = new Account(userName, clientToken,accessToken, displayName, uuid);
                        accountsList.add(finalAccount);
                        System.out.println("[AC] added account " + finalAccount.getUsername() + ".");
                        float f = FontRegistry.getRobotoRegular13px().getStringWidth(finalAccount.getDisplayName());
                        if (f > this.accountButtonWidth) {
                            this.accountButtonWidth = f;
                        }
                        if (minecraft.bridge$getSession() == null || !finalAccount.getUsername().equalsIgnoreCase(minecraft.bridge$getSession().bridge$getUsername()))
                            continue;
                        this.accountList.setDisplayName(finalAccount.getDisplayName());
                        this.accountList.setHeadLocation(ArchClient.getInstance().getHeadLocation(finalAccount.getDisplayName()));
                        this.updateAccountButtonSize();
                    } else {
                        System.err.println("[AC] userName is null.");
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        */
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        if (this.accountList != null) {
            this.accountList.handleElementMouse();
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++panoramaTimer;
    }

    @Override
    public void initGui() {
        super.initGui();
        DynamicTexture texture = Ref.getInstanceCreator().createDynamicTexture(256, 256);
        this.panoramaBackgroundLocation = this.mc.getTextureManager().getDynamicTextureLocation("background", texture);
        this.allowUserInput = true;
        if (this.renderExtraButtons) {
            float topBarButtonX = 124f;
            for (TextButtonElement elem : this.topBarButtons) {
                topBarButtonX += elem.setElementDimensions(topBarButtonX, 6f);
            }

            float totalBottomBarWidth = (26f * this.bottomButtons.size()) + (4 * (this.bottomButtons.size() - 1));
            float bottomBarButtonX = this.getScaledWidth() / 2f - totalBottomBarWidth / 2f;

            for (IconButtonElement elem : this.bottomButtons) {
                elem.setElementDimensions(bottomBarButtonX, this.getScaledHeight() - 17f, 26f, 18f);
                bottomBarButtonX += 30f;
            }

            this.exitButton.setElementDimensions(this.getScaledWidth() - 30f, 7f, 23f, 17f);
            this.updateAccountButtonSize();
        }
    }

    public void updateAccountButtonSize() {
        this.accountList.setElementDimensions(this.getScaledWidth() - 35f - this.accountList.getMaxWidthFor(this.accountButtonWidth), 7f, this.accountList.getMaxWidthFor(this.accountButtonWidth), 17);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float delta) {
        Ref.getGlBridge().bridge$disableAlphaTest();
        Ref.getDrawingUtils().renderSkybox(this.panoramaImages, delta, this.width, this.height, panoramaTimer, this.panoramaBackgroundLocation);
        Ref.getGlBridge().bridge$enableAlphaTest();
        super.drawScreen(mouseX, mouseY, delta);
    }

    @Override
    public void drawMenu(float mouseX, float mouseY) {
        ArchClient ac = ArchClient.getInstance();
        EaglerFontRenderer font = FontRegistry.getRobotoRegular24px();

        if (ac.globalSettings.mainMenuLightGradient.<Boolean>value()) {
            Ref.modified$drawGradientRect(0f, 0f, this.getScaledWidth(), this.getScaledHeight(),
                    0x5FFFFFFF, 0x2FFFFFFF);
        }

        if (ac.globalSettings.mainMenuTopGradient.<Boolean>value()) {
            Ref.modified$drawGradientRect(0f, 0f, this.getScaledWidth(), 160, -553648128,
                    0);
        }

        // ArchClient text in top corner
        boolean isOverArchClientText =
                mouseX > 36 && mouseX < this.optionsButton.getX() &&
                mouseY > 8 && mouseY < 30;

        int archClientTextColor = this.acTextShadowFade.get(isOverArchClientText);

        font.drawString("ArchClient", 37, 9, archClientTextColor);
        font.drawString("ArchClient", 36, 8, -1);

        // ArchClient icon in top corner
        Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.drawIcon(this.logo, 10, 8, 6);

        // Render text at bottom
        font = FontRegistry.getPlayRegular18px();

        int textColor = ((143 & 0xFF) << 24) |
                ((255 & 0xFF) << 16) |
                ((255 & 0xFF) << 8)  |
                ((255 & 0xFF) << 0);

        String version = "ArchClient " + Ref.getMinecraftVersion();
        String copyright = "Copyright Mojang AB. Do not distribute!";

        font.drawStringWithShadow(version, 5f, this.getScaledHeight() - 14f, textColor);
        font.drawRightAlignedStringWithShadow(copyright,
                this.getScaledWidth() - 5f, this.getScaledHeight() - 14f, textColor);

        // Render buttons
        if (this.renderExtraButtons) {
            this.exitButton.drawElement(mouseX, mouseY, true);
            if (!(mc.bridge$getCurrentScreen() instanceof GuiCosmetics)) {
                for (IconButtonElement elem : this.bottomButtons) {
                    elem.drawElement(mouseX, mouseY, true);
                }
            }

            this.accountList.drawElement(mouseX, mouseY, true);

            for (TextButtonElement elem : this.topBarButtons) {
                elem.drawElement(mouseX, mouseY, true);
            }
        }
    }

    @Override
    public void onMouseMovedOrUp(float mouseX, float mouseY, int mouseButton) {

    }

    @Override
    public void onMouseClicked(float mouseX, float mouseY, int button) {
        if (this.renderExtraButtons) {
            this.exitButton.handleElementMouseClicked(mouseX, mouseY, button, true);
            this.accountList.handleElementMouseClicked(mouseX, mouseY, button, true);
            if (this.exitButton.isMouseInside(mouseX, mouseY)) {
                this.mc.getSoundHandler().playSound(Ref.getInstanceCreator()
                        .createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"),
                                1.0f));
                this.mc.shutdown();
            } else if (this.optionsButton.isMouseInside(mouseX, mouseY)) {
                this.mc.getSoundHandler().playSound(Ref.getInstanceCreator()
                        .createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"),
                                1.0f));
                this.mc.bridge$displayInternalGuiScreen(Minecraft.InternalScreen.OPTIONS, new MainMenu());
            } else if (this.languageButton.isMouseInside(mouseX, mouseY)) {
                this.mc.getSoundHandler().playSound(Ref.getInstanceCreator()
                        .createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"),
                                1.0f));
                this.mc.bridge$displayInternalGuiScreen(Minecraft.InternalScreen.LANGUAGE, new MainMenu());
            } else if (this.cosmeticsButton.isMouseInside(mouseX, mouseY)) {
                this.mc.getSoundHandler().playSound(Ref.getInstanceCreator()
                        .createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"),
                                1.0f));
                this.mc.bridge$displayGuiScreen(new GuiCosmetics());
            /*xxx
            } else if (this.replayViewerButton.isMouseInside(mouseX, mouseY)) {
                if (ReplayModCompatibility.isReplayModPresent()) {
                    this.mc.getSoundHandler().playSound(Ref.getInstanceCreator()
                            .createSoundFromPSR(Ref.getInstanceCreator()
                                    .createResourceLocation("gui.button.press"), 1.0f));
                    ReplayModCompatibility.openReplayViewer();
                }
            } else if (this.modMenuButton.isMouseInside(mouseX, mouseY)) {
                if (ModMenuCompatibility.isModMenuPresent()) {
                    this.mc.getSoundHandler().playSound(Ref.getInstanceCreator()
                            .createSoundFromPSR(Ref.getInstanceCreator()
                                    .createResourceLocation("gui.button.press"), 1.0f));
                    ModMenuCompatibility.openModListScreen();
                }
            */
            } else {
                boolean bl = mouseX < this.optionsButton.getX() && mouseY < (float) 30;
                if (bl && !(this.mc.bridge$getCurrentScreen() instanceof MainMenu)) {
                    this.mc.getSoundHandler().playSound(Ref.getInstanceCreator()
                            .createSoundFromPSR(Ref.getInstanceCreator()
                                    .createResourceLocation("gui.button.press"), 1.0f));
                    this.mc.bridge$displayGuiScreen(new MainMenu());
                }
            }
        }
    }

    public void login(String string) {
        /*XXX
        block21:
        {
            try {
                SessionBridge session;
                Account selectedAccount = null;
                for (Account account : this.accountsList) {
                    if (!account.getDisplayName().equals(string)) continue;
                    selectedAccount = account;
                }
                if (selectedAccount == null) break block21;
                if (selectedAccount.getUUID().equalsIgnoreCase(Ref.getMinecraft().bridge$getSession().bridge$getPlayerID())) {
                    return;
                }
                Ref.getMinecraft().bridge$getSoundHandler().bridge$playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                for (SessionBridge object2 : ArchClient.getInstance().sessions) {
                    if (!object2.bridge$func_148256_e().getId().toString().replaceAll("-", "").equalsIgnoreCase(selectedAccount.getUUID().replaceAll("-", "")))
                        continue;
//                    Ref.getMinecraft().setSession(object2);
                    this.accountList.setDisplayName(selectedAccount.getDisplayName());
                    this.accountList.setHeadLocation(selectedAccount.getHeadLocation());
                    this.updateAccountButtonSize();
                    return;
                }
                YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, selectedAccount.getClientToken());
                YggdrasilUserAuthentication object2 = (YggdrasilUserAuthentication) yggdrasilAuthenticationService.createUserAuthentication(Agent.MINECRAFT);
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("uuid", selectedAccount.getUUID());
                hashMap.put("displayName", selectedAccount.getDisplayName());
                hashMap.put("username", selectedAccount.getUsername());
                hashMap.put("accessToken", selectedAccount.getAccessToken());
                object2.loadFromStorage(hashMap);
                try {
                    object2.logIn();
                    session = Ref.getInstanceCreator().createSession(object2.getSelectedProfile().getName(), object2.getSelectedProfile().getId().toString(), object2.getAuthenticatedToken(), "mojang");
                } catch (AuthenticationException authenticationException) {
                    authenticationException.printStackTrace();
                    return;
                }
                System.out.println("Updated accessToken and logged user in.");
                this.accountList.setDisplayName(selectedAccount.getDisplayName());
                this.accountList.setHeadLocation(selectedAccount.getHeadLocation());
                this.updateAccountButtonSize();
                ArchClient.getInstance().sessions.add(session);
//                Ref.getMinecraft().setSession(session);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        */
    }

    public List<Account> getAccounts() {
        return this.accountsList;
    }
}

