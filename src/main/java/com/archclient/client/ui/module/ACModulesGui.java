package com.archclient.client.ui.module;

import com.archclient.bridge.ref.Ref;
import com.archclient.bridge.util.EnumChatFormattingBridge;
import com.archclient.bridge.wrapper.ACGuiScreen;
import com.archclient.main.ArchClient;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.ui.element.AbstractModulesGuiElement;
import com.archclient.client.ui.element.AbstractScrollableElement;
import com.archclient.client.ui.element.module.ModuleListElement;
import com.archclient.client.ui.element.module.ModulePreviewContainer;
import com.archclient.client.ui.element.module.ModulesGuiButtonElement;
import com.archclient.client.ui.element.profile.ProfilesListElement;
import com.archclient.client.ui.util.Rectangle;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.lax1dude.eaglercraft.v1_8.internal.KeyboardConstants;
import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ACModulesGui extends ACGuiScreen {

    public static ACModulesGui instance;
    private final ResourceLocation cogIcon = Ref.getInstanceCreator().createResourceLocation("client/icons/cog-64.png");
    private final ResourceLocation deleteIcon = Ref.getInstanceCreator().createResourceLocation("client/icons/delete-64.png");
    private final List<ACModulePosition> positions = new ArrayList<>();
    private final List<AbstractScrollableElement> elementList = new ArrayList<>();
    private final List<ModulesGuiButtonElement> buttons = new ArrayList<>();
    private List<AbstractModule> modules;
    private ModulesGuiButtonElement showGuidesButton;
    public ModulesGuiButtonElement helpButton;
    public AbstractScrollableElement settingsElement;
    public AbstractScrollableElement profilesElement;
    protected AbstractScrollableElement modulesElement;
    protected AbstractScrollableElement staffModulesElement;
    protected AbstractScrollableElement lIIIIllIIlIlIllIIIlIllIlI = null;
    public AbstractScrollableElement currentScrollableElement = null;
    private static AbstractModule draggingModule;
    private boolean IlIlIIIlllllIIIlIlIlIllII = false;
    private float IIlIIllIIIllllIIlllIllIIl;
    private float lllIlIIllllIIIIlIllIlIIII;
    private List<ModuleActionData> undoList;
    private List<ModuleActionData> redo;
    private int someMouseX;
    private int someMouseY;
    private boolean IlIIIIllIIIIIlllIIlIIlllI = false;
    private ModuleDataHolder dataHolder;
    public static boolean IlIlllIIIIllIllllIllIIlIl;
    private int arrowKeyMoves;
    private int IIllIlIllIlIllIIlIllIlIII = 0;

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        //this.mc.entityRenderer.deactivateShader();
    }

    @Override
    public void initGui() {
        //this.IllIllIIIlIIlllIIIllIllII(); shader shid
        Keyboard.enableRepeatEvents(true);
        this.modules = new ArrayList<>();
        this.modules.addAll(ArchClient.getInstance().moduleManager.modules);
        this.undoList = new ArrayList<>();
        this.redo = new ArrayList<>();
        this.someMouseX = -1;
        this.someMouseY = -1;
        this.arrowKeyMoves = 0;
        instance = this;
        draggingModule = null;
        this.lIIIIllIIlIlIllIIIlIllIlI = null;
        this.currentScrollableElement = null;
        this.dataHolder = null;
        IlIlllIIIIllIllllIllIIlIl = false;
        float f = 1.0f / ArchClient.getInstance().getScaleFactor();

        if (this.mc == null) {
            this.mc = Ref.getMinecraft();
        }

        ScaledResolution resolution = Ref.getInstanceCreator().createScaledResolution();

        this.width = (int) resolution.getScaledWidth();
        this.height = (int) resolution.getScaledHeight();

        int n = (int)((float)this.width / f);
        int n2 = (int)((float)this.height / f);
        this.elementList.clear();
        this.buttons.clear();
        List<AbstractModule> modules = ArchClient.getInstance().moduleManager.modules;
        List<AbstractModule> staffModules = ArchClient.getInstance().moduleManager.staffModules;
        this.modulesElement = new ModulePreviewContainer(f, n / 2 - 565, n2 / 2 + 14, 370, n2 / 2 - 35);
        this.elementList.add(this.modulesElement);
        this.staffModulesElement = new ModuleListElement(staffModules, f, n / 2 + 195, n2 / 2 + 14, 370, n2 / 2 - 35);
        this.elementList.add(this.staffModulesElement);
        this.settingsElement = new ModuleListElement(modules, f, n / 2 + 195, n2 / 2 + 14, 370, n2 / 2 - 35);
        this.elementList.add(this.settingsElement);
        this.profilesElement = new ProfilesListElement(f, n / 2 - 565, n2 / 2 + 14, 370, n2 / 2 - 35);
        this.elementList.add(this.profilesElement);
        this.showGuidesButton = new ModulesGuiButtonElement(null, "eye-64.png", 4, n2 - 32, 28, 28, -12418828, f);
        this.helpButton = new ModulesGuiButtonElement(null, "?", 36, n2 - 32, 28, 28, -12418828, f);
        if (ArchClient.getInstance().isUsingStaffModules()) {
            this.buttons.add(new ModulesGuiButtonElement(this.staffModulesElement, "Staff Mods", n / 2 - 50, n2 / 2 - 44, 100, 20, -9442858, f));
        }
        this.buttons.add(new ModulesGuiButtonElement(this.modulesElement, "Mods", n / 2 - 50, n2 / 2 - 19, 100, 28, -13916106, f));
        this.buttons.add(new ModulesGuiButtonElement(this.settingsElement, "cog-64.png", n / 2 + 54, n2 / 2 - 19, 28, 28, -12418828, f));
        this.buttons.add(new ModulesGuiButtonElement(this.profilesElement, "profiles-64.png", n / 2 - 82, n2 / 2 - 19, 28, 28, -12418828, f));
        IlIlllIIIIllIllllIllIIlIl = false;
        this.lIIIIllIIlIlIllIIIlIllIlI = null;
        this.IIllIlIllIlIllIIlIllIlIII = 5;
    }

    @Override
    public void updateScreen() {
        float f = 1.0f / ArchClient.getInstance().getScaleFactor();
        int n = (int)((float)this.width / f);
        this.lIIIIlIIllIIlIIlIIIlIIllI(n);
        if (!this.positions.isEmpty()) {
            boolean leftKey = Keyboard.isKeyDown(KeyboardConstants.KEY_LEFT);
            boolean rightDown = Keyboard.isKeyDown(KeyboardConstants.KEY_RIGHT);
            boolean upDown = Keyboard.isKeyDown(KeyboardConstants.KEY_UP);
            boolean downDown = Keyboard.isKeyDown(KeyboardConstants.KEY_DOWN);
            if (leftKey || rightDown || upDown || downDown) {
                ++this.arrowKeyMoves;
                if (this.arrowKeyMoves > 10) {
                    for (ACModulePosition position : this.positions) {
                        AbstractModule module = position.module;
                        if (module == null) continue;
                        if (leftKey) {
                            module.setTranslations((int)module.getXTranslation() - 1, (int)module.getYTranslation());
                            continue;
                        }
                        if (rightDown) {
                            module.setTranslations((int)module.getXTranslation() + 1, (int)module.getYTranslation());
                            continue;
                        }
                        if (upDown) {
                            module.setTranslations((int)module.getXTranslation(), (int)module.getYTranslation() - 1);
                            continue;
                        }
                        module.setTranslations((int)module.getXTranslation(), (int)module.getYTranslation() + 1);
                    }
                }
            }
        }
        float f2 = IIllIlIllIlIllIIlIllIlIII + getSmoothFloat((float) IIllIlIllIlIllIIlIllIlIII);
        this.IIllIlIllIlIllIIlIllIlIII = (float)this.IIllIlIllIlIllIIlIllIlIII + f2 >= (float)255 ? 255 : (int)((float)this.IIllIlIllIlIllIIlIllIlIII + f2);
    }

    private float getIntersectionFloat(Rectangle rectangle, Rectangle rectangle2) {
        float f = Math.max(Math.abs(rectangle.x - rectangle2.x) - rectangle2.width / 2, 0);
        float f2 = Math.max(Math.abs(rectangle.y - rectangle2.y) - rectangle2.height / 2, 0);
        return f * f + f2 * f2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float delta) {
        float f2;
        float f3;
        Rectangle object;
        super.drawScreen(mouseX, mouseY, delta);
        ///this.lIIlllIIlIlllllllllIIIIIl(); blur shader
        ScaledResolution scaledResolution = Ref.getInstanceCreator().createScaledResolution();
        float scale = 1.0f / ArchClient.getInstance().getScaleFactor();
        if (draggingModule != null) {
            if (!Mouse.isButtonDown(1)) {
                RenderUtil.drawRoundedRect(2, 0.0, 2.916666637692187 * 0.8571428656578064, this.height, 0.0, -15599126);
                RenderUtil.drawRoundedRect((float)this.width - 5.0f * 0.5f, 0.0, this.width - 2, this.height, 0.0, -15599126);
                RenderUtil.drawRoundedRect(0.0, 2, this.width, 1.1547619104385376 * 2.164948442965692, 0.0, -15599126);
                RenderUtil.drawRoundedRect(0.0, (float)this.height - 1.3529412f * 2.5869565f, this.width, this.height - 3, 0.0, -15599126);
            }
            this.modules.sort((aCModule, aCModule2) -> {
                if (aCModule == draggingModule || aCModule2 == draggingModule || aCModule.getGuiAnchor() == null || aCModule2.getGuiAnchor() == null) {
                    return 0;
                }
                float[] modulePoints = aCModule.getScaledPoints(scaledResolution, true);
                float[] modulePoints2 = aCModule2.getScaledPoints(scaledResolution, true);
                float[] selectedPoints = draggingModule.getScaledPoints(scaledResolution, true);
                Rectangle rectangle = new Rectangle((int)(modulePoints[0] * aCModule.scale.<Float>value()), (int)(modulePoints[1] * aCModule.scale.<Float>value()), (int)(aCModule.width * aCModule.scale.<Float>value()), (int)(aCModule.height * aCModule.scale.<Float>value()));
                Rectangle rectangle2 = new Rectangle((int)(modulePoints2[0] * aCModule2.scale.<Float>value()), (int)(modulePoints2[1] * aCModule2.scale.<Float>value()), (int)(aCModule2.width * aCModule2.scale.<Float>value()), (int)(aCModule2.height * aCModule2.scale.<Float>value()));
                Rectangle rectangle3 = new Rectangle((int)(selectedPoints[0] * draggingModule.scale.<Float>value()), (int)(selectedPoints[1] * ACModulesGui.draggingModule.scale.<Float>value()), (int)(ACModulesGui.draggingModule.width * ACModulesGui.draggingModule.scale.<Float>value()), (int)(ACModulesGui.draggingModule.height * ACModulesGui.draggingModule.scale.<Float>value()));
                try {
                    if (this.getIntersectionFloat(rectangle, rectangle3) > this.getIntersectionFloat(rectangle2, rectangle3)) {
                        return -1;
                    }
                    return 1;
                }
                catch (Exception exception) {
                    return 0;
                }
            });
            ACModulePosition ACModulePosition = this.getModulePosition(draggingModule);
            if (ACModulePosition != null) {
                this.positions.remove(ACModulePosition);
                this.positions.add(ACModulePosition);
            }
            for (ACModulePosition position : this.positions) {
                this.lIIIIlIIllIIlIIlIIIlIIllI(position, mouseX, mouseY, scaledResolution);
                if (!ArchClient.getInstance().globalSettings.snapModules.<Boolean>value()
                        || !this.IlIlIIIlllllIIIlIlIlIllII
                        || Mouse.isButtonDown(1)
                        || position.module != draggingModule) {
                    continue;
                }
                for (AbstractModule aCModule3 : this.modules) {
                    if (this.getModulePosition(aCModule3) != null || aCModule3.getGuiAnchor() == null || !aCModule3.isEnabled()) continue;
                    float f5 = 18;
                    if (aCModule3.width < f5) {
                        aCModule3.width = (int)f5;
                    }
                    if (aCModule3.height < (float)18) {
                        aCModule3.height = 18;
                    }
                    if (position.module.width < f5) {
                        position.module.width = (int)f5;
                    }
                    if (position.module.height < (float)18) {
                        position.module.height = 18;
                    }
                    boolean bl = true;
                    boolean bl2 = true;
                    float[] arrf = aCModule3.getScaledPoints(scaledResolution, true);
                    float[] scaledPoints = position.module.getScaledPoints(scaledResolution, true);
                    float f6 = arrf[0] * aCModule3.scale.<Float>value() - scaledPoints[0] * position.module.scale.<Float>value();
                    float f7 = (arrf[0] + aCModule3.width) * aCModule3.scale.<Float>value() - (scaledPoints[0] + position.module.width) * position.module.scale.<Float>value();
                    float f8 = (arrf[0] + aCModule3.width) * aCModule3.scale.<Float>value() - scaledPoints[0] * position.module.scale.<Float>value();
                    float f9 = arrf[0] * aCModule3.scale.<Float>value() - (scaledPoints[0] + position.module.width) * position.module.scale.<Float>value();
                    float f10 = arrf[1] * aCModule3.scale.<Float>value() - scaledPoints[1] * position.module.scale.<Float>value();
                    f3 = (arrf[1] + aCModule3.height) * aCModule3.scale.<Float>value() - (scaledPoints[1] + position.module.height) * position.module.scale.<Float>value();
                    f2 = (arrf[1] + aCModule3.height) * aCModule3.scale.<Float>value() - scaledPoints[1] * position.module.scale.<Float>value();
                    float f11 = arrf[1] * aCModule3.scale.<Float>value() - (scaledPoints[1] + position.module.height) * position.module.scale.<Float>value();
                    int n3 = 2;
                    if (f6 >= (float)(-n3) && f6 <= (float)n3) {
                        bl = false;
                        this.snapHorizontally(f6);
                    }
                    if (f7 >= (float)(-n3) && f7 <= (float)n3 && bl) {
                        bl = false;
                        this.snapHorizontally(f7);
                    }
                    if (f9 >= (float)(-n3) && f9 <= (float)n3 && bl) {
                        bl = false;
                        this.snapHorizontally(f9);
                    }
                    if (f8 >= (float)(-n3) && f8 <= (float)n3 && bl) {
                        this.snapHorizontally(f8);
                    }
                    if (f10 >= (float)(-n3) && f10 <= (float)n3) {
                        bl2 = false;
                        this.snapVertically(f10);
                    }
                    if (f3 >= (float)(-n3) && f3 <= (float)n3 && bl2) {
                        bl2 = false;
                        this.snapVertically(f3);
                    }
                    if (f11 >= (float)(-n3) && f11 <= (float)n3 && bl2) {
                        bl2 = false;
                        this.snapVertically(f11);
                    }
                    if (!(f2 >= (float)(-n3)) || !(f2 <= (float)n3) || !bl2) continue;
                    this.snapVertically(f2);
                }
            }
        } else if (this.dataHolder != null) {
            float f12 = 1.0f;
            switch (this.dataHolder.unknown) {
                case RIGHT_BOTTOM: {
                    int n4 = mouseY - this.dataHolder.mouseY + (mouseX - this.dataHolder.mouseX);
                    f12 = this.dataHolder.scale - (float)n4 / (float)115;
                    break;
                }
                case LEFT_TOP: {
                    int n4 = mouseY - this.dataHolder.mouseY + (mouseX - this.dataHolder.mouseX);
                    f12 = this.dataHolder.scale + (float)n4 / (float)115;
                    break;
                }
                case RIGHT_TOP: {
                    int n4 = mouseX - this.dataHolder.mouseX - (mouseY - this.dataHolder.mouseY);
                    f12 = this.dataHolder.scale - (float)n4 / (float)115;
                    break;
                }
                case LEFT_BOTTOM: {
                    int n4 = mouseX - this.dataHolder.mouseX - (mouseY - this.dataHolder.mouseY);
                    f12 = this.dataHolder.scale + (float)n4 / (float)115;
                }
            }
            if (f12 >= 1.0421053f * 0.47979796f && f12 <= 1.8962264f * 0.7910448f) {
                this.dataHolder.module.scale.setValue((float) ((double) Math.round((double) f12 * (double) 100) / (double) 100));
            }
        }
        this.lIIIIlIIllIIlIIlIIIlIIllI(scaledResolution);
        boolean bl = true;
        for (AbstractModule module : this.modules) {
            boolean bl3 = this.lIIIIlIIllIIlIIlIIIlIIllI(scale, module, scaledResolution, mouseX, mouseY, bl);
            if (bl3) continue;
            bl = false;
        }
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.getGlBridge().bridge$scale(scale, scale, scale);
        int n5 = (int)((float)this.width / scale);
        int n6 = (int)((float)this.height / scale);
        this.showGuidesButton.handleDrawElement(mouseX, mouseY, delta);
        this.helpButton.handleDrawElement(mouseX, mouseY, delta);
        float f13 = (float)(this.IIllIlIllIlIllIIlIllIlIII * 8) / (float)255;
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, f13);
        int n7 = 0xFFFFFF;
        if (f13 / (float)4 > 0.0f && f13 / (float)4 < 1.0f) {
            n7 = (((int)((f13 / (float)4)*255+0.5) & 0xFF) << 24) |
                    (((int)((1.0f)*255+0.5) & 0xFF) << 16) |
                    (((int)((1.0f)*255+0.5) & 0xFF) << 8)  |
                    (((int)((1.0f)*255+0.5) & 0xFF) << 0);
        }
        Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, f13);
        if (f13 > 1.0f) {
            Ref.getGlBridge().bridge$translate(-((float)(this.IIllIlIllIlIllIIlIllIlIII * 2) - 32f) / 12f - 1f, 0f, 0f);
        }
        RenderUtil.renderIcon(Ref.getInstanceCreator().createResourceLocation("client/logo_white.png"), (float)(n5 / 2 - 14), (float)(n6 / 2 - 47 - (ArchClient.getInstance().isUsingStaffModules() ? 22 : 0)), (float)28, 15);
        if (f13 > 2.0f) {
            FontRegistry.getPlayBold18px().drawString("| ARCH", n5 / 2 + 18, (float)(n6 / 2 - 42 - (ArchClient.getInstance().isUsingStaffModules() ? 22 : 0)), n7);
            FontRegistry.getPlayRegular18px().drawString("CLIENT", n5 / 2 + 53, (float)(n6 / 2 - 42 - (ArchClient.getInstance().isUsingStaffModules() ? 22 : 0)), n7);
        }
        Ref.getGlBridge().bridge$popMatrix();
        for (ModulesGuiButtonElement llllIIIIIlIlIlIlIllIIIIII2 : this.buttons) {
            llllIIIIIlIlIlIlIllIIIIII2.handleDrawElement(mouseX, mouseY, delta);
        }
        if (draggingModule == null) {
            Ref.getGlBridge().bridge$pushMatrix();
            Ref.getGlBridge().bridge$enableScissoring();
            RenderUtil.scissorArea(n5 / 2 - 185, n6 / 2 + 15, n5 / 2 + 185, n6 - 20, (float)scaledResolution.getScaleFactor() * scale, n6);
            for (AbstractScrollableElement lllIllIllIlIllIlIIllllIIl2 : this.elementList) {
                if (lllIllIllIlIllIlIIllllIIl2 != this.lIIIIllIIlIlIllIIIlIllIlI && lllIllIllIlIllIlIIllllIIl2 != this.currentScrollableElement) continue;
                lllIllIllIlIllIlIIllllIIl2.handleDrawElement(mouseX, mouseY, delta);
            }
            Ref.getGlBridge().bridge$disableScissoring();
            Ref.getGlBridge().bridge$popMatrix();
        }
        Ref.getGlBridge().bridge$popMatrix();
        if (this.someMouseX != -1) {
            if (Mouse.isButtonDown(0)) {
                if (this.someMouseX != mouseX && this.someMouseY != mouseY) {
                    Ref.modified$drawRect(mouseX, this.someMouseY, (float)mouseX + 1.1538461f * 0.43333334f, mouseY, -1358888961);
                    Ref.modified$drawRect((float)this.someMouseX - 0.4329897f * 1.1547619f, mouseY, (float)mouseX + 18.2f * 0.027472526f, (float)mouseY + 0.121212125f * 4.125f, -1358888961);
                    Ref.modified$drawRect((float)this.someMouseX - 0.8666667f * 0.5769231f, this.someMouseY, this.someMouseX, mouseY, -1358888961);
                    Ref.modified$drawRect((float)this.someMouseX - 0.557971f * 0.8961039f, (float)this.someMouseY - 0.3611111f * 1.3846154f, (float)mouseX + 1.2692307f * 0.3939394f, this.someMouseY, -1358888961);
                    Ref.modified$drawRect(this.someMouseX, this.someMouseY, mouseX, mouseY, 0x1F00FFFF);
                }
            } else {
                this.positions.clear();
                for (AbstractModule aCModule4 : this.modules) {
                    int n10;
                    int n11;
                    if (aCModule4.getGuiAnchor() == null || !aCModule4.isEnabled())
                        continue;
                    float[] arrf = aCModule4.getScaledPoints(scaledResolution, true);
                    float f14 = scale / aCModule4.scale.<Float>value();
                    object = new Rectangle((int)(arrf[0] * aCModule4.scale.<Float>value() - 2.0f), (int)(arrf[1] * aCModule4.scale.<Float>value() - 2.0f), (int)(aCModule4.width * aCModule4.scale.<Float>value() + 4f), (int)(aCModule4.height * aCModule4.scale.<Float>value() + 4f));
                    if (!object.intersects(new Rectangle(n11 = Math.min(this.someMouseX, mouseX), n10 = Math.min(this.someMouseY, mouseY), Math.max(this.someMouseX, mouseX) - n11, Math.max(this.someMouseY, mouseY) - n10))) continue;
                    f3 = (float)mouseX - aCModule4.getXTranslation();
                    f2 = (float)mouseY - aCModule4.getYTranslation();
                    this.positions.add(new ACModulePosition(aCModule4, f3, f2));
                }
                this.someMouseX = -1;
                this.someMouseY = -1;
            }
        }
        if (this.helpButton.isMouseInside(mouseX, mouseY) && (this.lIIIIllIIlIlIllIIIlIllIlI == null || !this.lIIIIllIIlIlIllIIIlIllIlI.isMouseInside(mouseX, mouseY))) {
            this.drawHelpMenu(scale);
        }
    }

    private void drawHelpMenu(float f) {
        Ref.getGlBridge().bridge$pushMatrix();
        Ref.getGlBridge().bridge$translate(4, (float)this.height - (float)185 * f, 0.0f);
        Ref.getGlBridge().bridge$scale(f, f, f);
        Ref.modified$drawRect(0.0f, 0.0f, 240, 140, -1895825408);
        FontRegistry.getUbuntuMedium16px().drawString("Shortcuts & Movement", 4, 2.0f, -1);
        Ref.modified$drawRect(4, 12, 234, 2.5815217f * 4.8421054f, 0x4FFFFFFF);
        int n = 16;
        this.renderRoundButton("Mouse1", 6, n);
        FontRegistry.getPlayRegular14px().drawString("| " + EnumChatFormattingBridge.LIGHT_PURPLE + "HOLD" + EnumChatFormattingBridge.LIGHT_PURPLE + " Add mods to selected region", 80, (float)n+3, -1);
        this.renderRoundButton("Mouse1", 6, n += 12);
        FontRegistry.getPlayRegular14px().drawString("| " + EnumChatFormattingBridge.LIGHT_PURPLE + "HOLD" + EnumChatFormattingBridge.LIGHT_PURPLE + " Select & drag mods", 80, (float)n+3, -1);
        this.renderRoundButton("Mouse2", 6, n += 12);
        FontRegistry.getPlayRegular14px().drawString("| " + EnumChatFormattingBridge.LIGHT_PURPLE + "CLICK" + EnumChatFormattingBridge.LIGHT_PURPLE + " Reset mod to closest position", 80, (float)n+3, -1);
        this.renderRoundButton("Mouse2", 6, n += 12);
        FontRegistry.getPlayRegular14px().drawString("| " + EnumChatFormattingBridge.LIGHT_PURPLE + "HOLD" + EnumChatFormattingBridge.LIGHT_PURPLE + " Don't lock mods while dragging", 80, (float)n+3, -1);
        this.renderRoundButton("CTRL", 6, n += 12);
        FontRegistry.getPlayRegular14px().drawString("+", 30, (float)n+3, -1);
        this.renderRoundButton("Mouse1", 36, n);
        FontRegistry.getPlayRegular14px().drawString("| Toggle (multiple) mod selection", 80, (float)n+3, -1);
        this.renderRoundButton("CTRL", 6, n += 12);
        FontRegistry.getPlayRegular14px().drawString("+", 30, (float)n+3, -1);
        this.renderRoundButton("Z", 36, n);
        FontRegistry.getPlayRegular14px().drawString("| Undo mod movements", 80, (float)n+3, -1);
        this.renderRoundButton("CTRL", 6, n += 12);
        FontRegistry.getPlayRegular14px().drawString("+", 30, (float)n+3, -1);
        this.renderRoundButton("Y", 36, n);
        FontRegistry.getPlayRegular14px().drawString("| Redo mod movements", 80, (float)n+3, -1);
        n = 112;
        this.renderRoundButton("Up", 31, n);
        this.renderRoundButton("Left", 6, n += 12);
        this.renderRoundButton("Down", 26, n);
        this.renderRoundButton("Right", 51, n);
        FontRegistry.getPlayRegular14px().drawString("| Move selected mod with precision", 80, (float)n, -1);
        Ref.getGlBridge().bridge$popMatrix();
    }

    private void renderRoundButton(String string, int x, int y) {
        EaglerFontRenderer font = FontRegistry.getPlayRegular14px();
        float width = font.getStringWidth(string);
        RenderUtil.drawRoundedRect(x, y, (float)x + width + (float)4, y + 10, (double)2, -1073741825);
        font.drawString(string, x + 2, (float)y, -16777216);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        ScaledResolution scaledResolution = Ref.getInstanceCreator().createScaledResolution();
        if (this.lIIIIllIIlIlIllIIIlIllIlI != null && this.lIIIIllIIlIlIllIIIlIllIlI.isMouseInside(mouseX, mouseY)) {
            this.lIIIIllIIlIlIllIIIlIllIlI.handleMouseClick(mouseX, mouseY, mouseButton);
        } else {
            AbstractModule iterator;
            if (!(draggingModule != null && this.IlIlIIIlllllIIIlIlIlIllII || (iterator = this.lIIIIlIIllIIlIIlIIIlIIllI(scaledResolution, mouseX, mouseY)) == null)) {
                boolean bl;
                float[] arrf = iterator.getScaledPoints(scaledResolution, true);
                boolean bl2 = !iterator.getSettingsList().isEmpty() && (float)mouseX >= arrf[0] * iterator.scale.<Float>value() && (float)mouseX <= (arrf[0] + (float)10) * iterator.scale.<Float>value() && (float)mouseY >= (arrf[1] + iterator.height - 10f) * iterator.scale.<Float>value() && (float)mouseY <= (arrf[1] + iterator.height + 2.0f) * iterator.scale.<Float>value();
                boolean bl3 = bl = (float)mouseX > (arrf[0] + iterator.width - 10f) * iterator.scale.<Float>value() && (float)mouseX < (arrf[0] + iterator.width + 2.0f) * iterator.scale.<Float>value() && (float)mouseY > (arrf[1] + iterator.height - 10f) * iterator.scale.<Float>value() && (float)mouseY < (arrf[1] + iterator.height + 2.0f) * iterator.scale.<Float>value();
                if (bl2) {
                    Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                    ((ModuleListElement)this.settingsElement).llIlIIIlIIIIlIlllIlIIIIll = false;
                    ((ModuleListElement)this.settingsElement).module = iterator;
                    this.currentScrollableElement = this.settingsElement;
                } else if (bl) {
                    Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                    iterator.setState(false);
                }
                return;
            }
            for (AbstractModule object : this.modules) {
                ACGuiAnchor aCGuiAnchor;
                SomeRandomAssEnum dELETE_ME_D;
                if (object.getGuiAnchor() == null || !object.isEnabled()) continue;
                float[] scaledPoints = object.getScaledPoints(scaledResolution, true);
                boolean bl4 = (float)mouseX > scaledPoints[0] * object.scale.<Float>value()
                                && (float)mouseX < (scaledPoints[0] + object.width) * object.scale.<Float>value()
                                && (float)mouseY > scaledPoints[1] * object.scale.<Float>value()
                                && (float)mouseY < (scaledPoints[1] + object.height) * object.scale.<Float>value();
                boolean bl5 = this.dataHolder != null && this.dataHolder.module == object && this.dataHolder.unknown == SomeRandomAssEnum.LEFT_BOTTOM || !bl4 && (float)mouseX >= (scaledPoints[0] + object.width - 5f) * object.scale.<Float>value() && (float)mouseX <= (scaledPoints[0] + object.width + 5f) * object.scale.<Float>value() && (float)mouseY >= (scaledPoints[1] - 5f) * object.scale.<Float>value() && (float)mouseY <= (scaledPoints[1] + 5f) * object.scale.<Float>value();
                boolean bl6 = this.dataHolder != null && this.dataHolder.module == object && this.dataHolder.unknown == SomeRandomAssEnum.RIGHT_TOP || !bl4 && (float)mouseX >= (scaledPoints[0] - 5f) * object.scale.<Float>value() && (float)mouseX <= (scaledPoints[0] + (float)5) * object.scale.<Float>value() && (float)mouseY >= (scaledPoints[1] + object.height - 5f) * object.scale.<Float>value() && (float)mouseY <= (scaledPoints[1] + object.height + 5f) * object.scale.<Float>value();
                boolean bl7 = this.dataHolder != null && this.dataHolder.module == object && this.dataHolder.unknown == SomeRandomAssEnum.RIGHT_BOTTOM || !bl4 && (float)mouseX >= (scaledPoints[0] - 5f) * object.scale.<Float>value() && (float)mouseX <= (scaledPoints[0] + (float)5) * object.scale.<Float>value() && (float)mouseY >= (scaledPoints[1] - 5f) * object.scale.<Float>value() && (float)mouseY <= (scaledPoints[1] + 5f) * object.scale.<Float>value();
                boolean bl = this.dataHolder != null && this.dataHolder.module == object && this.dataHolder.unknown == SomeRandomAssEnum.LEFT_TOP || !bl4 && (float)mouseX >= (scaledPoints[0] + object.width - 5f) * object.scale.<Float>value() && (float)mouseX <= (scaledPoints[0] + object.width + 5f) * object.scale.<Float>value() && (float)mouseY >= (scaledPoints[1] + object.height - 5f) * object.scale.<Float>value() && (float)mouseY <= (scaledPoints[1] + object.height + 5f) * object.scale.<Float>value();
                if (this.someMouseX != -1 || !bl5 && !bl6 && !bl7 && !bl) continue;
                if (bl5) {
                    dELETE_ME_D = SomeRandomAssEnum.LEFT_BOTTOM;
                    aCGuiAnchor = ACGuiAnchor.LEFT_BOTTOM;
                } else if (bl6) {
                    dELETE_ME_D = SomeRandomAssEnum.RIGHT_TOP;
                    aCGuiAnchor = ACGuiAnchor.RIGHT_TOP;
                } else if (bl7) {
                    dELETE_ME_D = SomeRandomAssEnum.RIGHT_BOTTOM;
                    aCGuiAnchor = ACGuiAnchor.RIGHT_BOTTOM;
                } else {
                    dELETE_ME_D = SomeRandomAssEnum.LEFT_TOP;
                    aCGuiAnchor = ACGuiAnchor.LEFT_TOP;
                }
                if (this.lIIIIIIIIIlIllIIllIlIIlIl(scaledResolution, mouseX, mouseY)) continue;
                if (mouseButton == 0) {
                    this.undoList.add(new ModuleActionData(this, this.positions));
                    this.dataHolder = new ModuleDataHolder(this, object, dELETE_ME_D, mouseX, mouseY);
                    this.lIIIIlIIllIIlIIlIIIlIIllI(object, aCGuiAnchor, scaledResolution);
                } else if (mouseButton == 1) {
                    ACGuiAnchor aCGuiAnchor2 = object.getGuiAnchor();
                    this.lIIIIlIIllIIlIIlIIIlIIllI(object, aCGuiAnchor, scaledResolution);
                    object.scale.setValue(1.0f);
                    this.lIIIIlIIllIIlIIlIIIlIIllI(object, aCGuiAnchor2, scaledResolution);
                }
                return;
            }
            if (draggingModule == null) {
                if (this.showGuidesButton.isMouseInside(mouseX, mouseY)) {
                    Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                    this.IlIIIIllIIIIIlllIIlIIlllI = !this.IlIIIIllIIIIIlllIIlIIlllI;
                }
                this.IlllIIIlIlllIllIlIIlllIlI(mouseX, mouseY, mouseButton);
                this.lIIIIlIIllIIlIIlIIIlIIllI(scaledResolution, mouseX, mouseY, mouseButton);
            }
            for (Object object : this.buttons) {
                if (!((AbstractModulesGuiElement)object).isMouseInside(mouseX, mouseY)) continue;
                return;
            }
            boolean bl = this.lIIIIIIIIIlIllIIllIlIIlIl(scaledResolution, mouseX, mouseY);
            if (bl) {
                return;
            }
            if (!this.positions.isEmpty()) {
                this.positions.clear();
                Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            }
            this.someMouseX = mouseX;
            this.someMouseY = mouseY;
        }
        if (!this.positions.isEmpty()) {
            this.arrowKeyMoves = 0;
        }
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int n = Mouse.getEventDWheel();
        if (this.lIIIIllIIlIlIllIIIlIllIlI != null) {
            //this.lIIIIllIIlIlIllIIIlIllIlI.onScroll(n);
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(AbstractModule aCModule, ACGuiAnchor aCGuiAnchor, ScaledResolution scaledResolution) {
        if (aCGuiAnchor != aCModule.getGuiAnchor()) {
            float[] scaledPointsWithTranslations = aCModule.getScaledPoints(scaledResolution, true);
            aCModule.setAnchor(aCGuiAnchor);
            float[] scaledPointsWithoutTranslations = aCModule.getScaledPoints(scaledResolution, false);
            aCModule.setTranslations(
                    scaledPointsWithTranslations[0] * aCModule.scale.<Float>value() - scaledPointsWithoutTranslations[0] * aCModule.scale.<Float>value(),
                    scaledPointsWithTranslations[1] * aCModule.scale.<Float>value() - scaledPointsWithoutTranslations[1] * aCModule.scale.<Float>value()
            );
        }
    }

    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int button) {
        ScaledResolution scaledResolution = Ref.getInstanceCreator().createScaledResolution();
        if (this.dataHolder != null && button == 0) {
            this.lIIIIlIIllIIlIIlIIIlIIllI(this.dataHolder.module, this.dataHolder.anchor, scaledResolution);
            this.dataHolder = null;
        }
        if (draggingModule != null && button == 0) {
            if (this.IlIlIIIlllllIIIlIlIlIllII) {
                for (ACModulePosition ACModulePosition : this.positions) {
                    ACGuiAnchor aCGuiAnchor = ACAnchorHelper.getAnchor(mouseX, mouseY, scaledResolution);
                    if (aCGuiAnchor == ACGuiAnchor.MIDDLE_MIDDLE || aCGuiAnchor == ACModulePosition.module.getGuiAnchor() || !this.IlIlIIIlllllIIIlIlIlIllII) continue;
                    this.lIIIIlIIllIIlIIlIIIlIIllI(ACModulePosition.module, aCGuiAnchor, scaledResolution);
                    ACModulePosition.x = (float)mouseX - ACModulePosition.module.getXTranslation();
                    ACModulePosition.y = (float)mouseY - ACModulePosition.module.getYTranslation();
                }
                if (this.getModulePosition(draggingModule) == null) {
                    float x = (float)mouseX - draggingModule.getXTranslation();
                    float y = (float)mouseY - draggingModule.getYTranslation();
                    this.positions.add(new ACModulePosition(draggingModule, x, y));
                }
                Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            }
            System.out.println(draggingModule.getName());
            draggingModule = null;
        }
    }

    @Override
    public void keyTyped(char c, int n) {
        if (n == 1) {
            ArchClient.getInstance().configManager.write();

            this.mc.bridge$goIngame();

            if (this.mc.bridge$isIngame()) {
                this.mc.setIngameFocus();
            }
        }
//        super.keyTyped(c, n);
        if (n == KeyboardConstants.KEY_Z && wrapped$isCtrlKeyDown()) {
            if (!this.undoList.isEmpty()) {
                int n2 = this.undoList.size() - 1;
                ModuleActionData moduleActionData = this.undoList.get(this.undoList.size() - 1);
                for (int i = 0; i < moduleActionData.modules.size(); ++i) {
                    AbstractModule aCModule = moduleActionData.modules.get(i);
                    float f = moduleActionData.xTranslations.get(i);
                    float f2 = moduleActionData.yTranslations.get(i);
                    ACGuiAnchor aCGuiAnchor = moduleActionData.anchors.get(i);
                    Float f3 = moduleActionData.scales.get(i);
                    aCModule.setAnchor(aCGuiAnchor);
                    aCModule.setTranslations(f, f2);
                    aCModule.scale.setValue(f3);
                }
                if (this.redo.size() > 50) {
                    this.redo.remove(0);
                }
                this.redo.add(moduleActionData);
                this.undoList.remove(n2);
            }
        } else if (n == KeyboardConstants.KEY_Y && wrapped$isCtrlKeyDown()) {
            if (!this.redo.isEmpty()) {
                int n3 = this.redo.size() - 1;
                ModuleActionData moduleActionData = this.redo.get(this.redo.size() - 1);
                for (int i = 0; i < moduleActionData.modules.size(); ++i) {
                    AbstractModule module = moduleActionData.modules.get(i);
                    float xTranslation = moduleActionData.xTranslations.get(i);
                    float yTranslation = moduleActionData.yTranslations.get(i);
                    ACGuiAnchor anchor = moduleActionData.anchors.get(i);
                    float scale = moduleActionData.scales.get(i);
                    module.setAnchor(anchor);
                    module.setTranslations(xTranslation, yTranslation);
                    module.scale.setValue(scale);
                }
                if (this.redo.size() > 50) {
                    this.redo.remove(0);
                }
                this.undoList.add(moduleActionData);
                this.redo.remove(n3);
            }
        } else {
            this.arrowKeyMoves = 0;
            for (ACModulePosition ACModulePosition : this.positions) {
                AbstractModule aCModule = ACModulePosition.module;
                if (aCModule == null) continue;
                switch (n) {
                    case 203: {
                        aCModule.setTranslations(aCModule.getXTranslation() - 1.0f, aCModule.getYTranslation());
                        break;
                    }
                    case 205: {
                        aCModule.setTranslations(aCModule.getXTranslation() + 1.0f, aCModule.getYTranslation());
                        break;
                    }
                    case 200: {
                        aCModule.setTranslations(aCModule.getXTranslation(), aCModule.getYTranslation() - 1.0f);
                        break;
                    }
                    case 208: {
                        aCModule.setTranslations(aCModule.getXTranslation(), aCModule.getYTranslation() + 1.0f);
                    }
                }
            }
        }
    }

    private void snapHorizontally(float f) {
        for (ACModulePosition ACModulePosition : this.positions) {
            ACModulePosition.module.setTranslations(ACModulePosition.module.getXTranslation() + f, ACModulePosition.module.getYTranslation());
        }
    }

    private void snapVertically(float f) {
        for (ACModulePosition ACModulePosition : this.positions) {
            ACModulePosition.module.setTranslations(ACModulePosition.module.getXTranslation(), ACModulePosition.module.getYTranslation() + f);
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(ScaledResolution scaledResolution, int n, int n2, int n3) {
        for (AbstractModule aCModule : this.modules) {
            boolean bl;
            float[] arrf;
            if (aCModule.getGuiAnchor() == null || !aCModule.isEnabled())
                continue;
            float f = aCModule.width;
            float f2 = aCModule.height;
            float f3 = 18f;
            if (f < f3) {
                aCModule.width = f3;
            }
            if (f2 < 18f) {
                aCModule.height = 18f;
            }
            if (!((float) n > (arrf = aCModule.getScaledPoints(scaledResolution, true))[0] * aCModule.scale.<Float>value() && (float) n < (arrf[0] + aCModule.width) * aCModule.scale.<Float>value() && (float) n2 > arrf[1] * aCModule.scale.<Float>value() && (float) n2 < (arrf[1] + aCModule.height) * aCModule.scale.<Float>value()))
                continue;
            boolean bl3 = !aCModule.getSettingsList().isEmpty() && (float) n >= arrf[0] * aCModule.scale.<Float>value() && (float) n <= (arrf[0] + (float) 10) * aCModule.scale.<Float>value() && (float) n2 >= (arrf[1] + aCModule.height - (float) 10) * aCModule.scale.<Float>value() && (float) n2 <= (arrf[1] + aCModule.height + 2.0f) * aCModule.scale.<Float>value();
            boolean bl4 = bl = (float) n > (arrf[0] + aCModule.width - 10f) * aCModule.scale.<Float>value() && (float) n < (arrf[0] + aCModule.width + 2.0f) * aCModule.scale.<Float>value() && (float) n2 > (arrf[1] + aCModule.height - (float) 10) * aCModule.scale.<Float>value() && (float) n2 < (arrf[1] + aCModule.height + 2.0f) * aCModule.scale.<Float>value();
            if (n3 == 0 && !bl3 && !bl) {
                boolean bl5 = true;
                if (this.getModulePosition(aCModule) != null) {
                    this.removePositionForModule(aCModule);
                    bl5 = false;
                }
                float f4 = (float) n - aCModule.getXTranslation() * aCModule.scale.<Float>value();
                float f5 = (float) n2 - aCModule.getYTranslation() * aCModule.scale.<Float>value();
                this.IIlIIllIIIllllIIlllIllIIl = n;
                this.lllIlIIllllIIIIlIllIlIIII = n2;
                this.IlIlIIIlllllIIIlIlIlIllII = false;
                draggingModule = aCModule;
                if (this.getModulePosition(aCModule) == null) {
                    if (!wrapped$isCtrlKeyDown() && bl5) {
                        this.positions.clear();
                    }
                    if (bl5 || !wrapped$isCtrlKeyDown()) {
                        this.positions.add(new ACModulePosition(aCModule, f4, f5));
                    }
                }
                this.IlllIIIlIlllIllIlIIlllIlI(scaledResolution, n, n2);
            }
            if (!(n3 != 0 || this.lIIIIllIIlIlIllIIIlIllIlI != null && this.lIIIIllIIlIlIllIIIlIllIlI.isMouseInside(n, n2))) {
                if (bl3) {
                    Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                    ((ModuleListElement) this.settingsElement).llIlIIIlIIIIlIlllIlIIIIll = false;
                    ((ModuleListElement) this.settingsElement).module = aCModule;
                    this.currentScrollableElement = this.settingsElement;
                } else if (bl) {
                    Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                    aCModule.setState(false);
                }
            } else if (n3 == 1) {
                Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                float[] arrf2 = ACAnchorHelper.getPositions(aCModule.getGuiAnchor());
                aCModule.setTranslations(arrf2[0], arrf2[1]);
            }
            break;
        }
    }

    private void IlllIIIlIlllIllIlIIlllIlI(int n, int n2, int n3) {
        for (ModulesGuiButtonElement llllIIIIIlIlIlIlIllIIIIII2 : this.buttons) {
            if (n3 != 0 || !llllIIIIIlIlIlIlIllIIIIII2.isMouseInside(n, n2) || IlIlllIIIIllIllllIllIIlIl) continue;
            if (llllIIIIIlIlIlIlIllIIIIII2.parentElement != null && this.lIIIIllIIlIlIllIIIlIllIlI != llllIIIIIlIlIlIlIllIIIIII2.parentElement && this.currentScrollableElement == null) {
                Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
                this.currentScrollableElement = llllIIIIIlIlIlIlIllIIIIII2.parentElement;
                continue;
            }
            if (llllIIIIIlIlIlIlIllIIIIII2.parentElement == null || this.currentScrollableElement != null) continue;
            Ref.getMinecraft().getSoundHandler().playSound(Ref.getInstanceCreator().createSoundFromPSR(Ref.getInstanceCreator().createResourceLocation("gui.button.press"), 1.0f));
            IlIlllIIIIllIllllIllIIlIl = true;
        }
    }

    private AbstractModule lIIIIlIIllIIlIIlIIIlIIllI(ScaledResolution scaledResolution, int n, int n2) {
        for (AbstractModule aCModule : this.modules) {
            if (aCModule.getGuiAnchor() == null) continue;
            float[] arrf = aCModule.getScaledPoints(scaledResolution, true);
            boolean bl = (float) n > (arrf[0] + aCModule.width - 10f) * aCModule.scale.<Float>value() && (float) n < (arrf[0] + aCModule.width + 2f) * aCModule.scale.<Float>value() && (float) n2 > (arrf[1] + aCModule.height - 10f) * aCModule.scale.<Float>value() && (float) n2 < (arrf[1] + aCModule.height + 2f) * aCModule.scale.<Float>value();
            boolean bl2 = !aCModule.getSettingsList().isEmpty() && (float)n >= arrf[0] * aCModule.scale.<Float>value() && (float)n <= (arrf[0] + 10f) * aCModule.scale.<Float>value() && (float)n2 >= (arrf[1] + aCModule.height - 10f) * aCModule.scale.<Float>value() && (float)n2 <= (arrf[1] + aCModule.height + 2f) * aCModule.scale.<Float>value();
            if (!bl && !bl2) continue;
            return aCModule;
        }
        return null;
    }

    private boolean lIIIIIIIIIlIllIIllIlIIlIl(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        boolean bl = false;
        for (AbstractModule aCModule : this.modules) {
            if (aCModule.getGuiAnchor() == null) continue;
            float[] arrf = aCModule.getScaledPoints(scaledResolution, true);
            boolean bl2 = (float)mouseX > arrf[0] * aCModule.scale.<Float>value() && (float)mouseX < (arrf[0] + aCModule.width) * aCModule.scale.<Float>value() && (float)mouseY > arrf[1] * aCModule.scale.<Float>value() && (float)mouseY < (arrf[1] + aCModule.height) * aCModule.scale.<Float>value();
            bl = bl || bl2;
        }
        return bl;
    }

    private boolean lIIIIlIIllIIlIIlIIIlIIllI(float f, AbstractModule aCModule, ScaledResolution scaledResolution, int n, int n2, boolean bl) {
        int n3;
        int n4;
        int n5;
        int n6;
        float[] object;
        boolean bl2;
        if (aCModule.getGuiAnchor() == null || !aCModule.isEnabled() || !aCModule.isEditable && !aCModule.isRenderHud()) {
            return true;
        }
        boolean bl3 = false;
        float f2 = 18;
        if (aCModule.width < f2) {
            aCModule.width = (int)f2;
        }
        if (aCModule.height < (float)18) {
            aCModule.height = 18;
        }
        Ref.getGlBridge().bridge$pushMatrix();
        float[] arrf = aCModule.getScaledPoints(scaledResolution, true);
        aCModule.scaleAndTranslate(scaledResolution);
        bl2 = this.someMouseX != -1;
        if (bl2) {
            Rectangle rectangle1 = new Rectangle((int)(arrf[0] * aCModule.scale.<Float>value() - 2.0f), (int)(arrf[1] * aCModule.scale.<Float>value() - 2f), (int)(aCModule.width * aCModule.scale.<Float>value() + 4f), (int)(aCModule.height * aCModule.scale.<Float>value() + 4f));
            n6 = Math.min(this.someMouseX, n);
            n5 = Math.min(this.someMouseY, n2);
            n4 = Math.max(this.someMouseX, n) - n6;
            n3 = Math.max(this.someMouseY, n2) - n5;
            Rectangle rectangle = new Rectangle(n6, n5, n4, n3);
            bl2 = rectangle1.intersects(rectangle);
        }
        n6 = (float) n > (object = aCModule.getScaledPoints(scaledResolution, true))[0] * aCModule.scale.<Float>value() && (float) n < (object[0] + aCModule.width) * aCModule.scale.<Float>value() && (float) n2 > object[1] * aCModule.scale.<Float>value() && (float) n2 < (object[1] + aCModule.height) * aCModule.scale.<Float>value() ? 1 : 0;
        if (!this.IlIIIIllIIIIIlllIIlIIlllI) {
            if (this.getModulePosition(aCModule) != null || bl2) {
                Ref.modified$drawRectWithOutline(0f, 0f, aCModule.width, aCModule.height, 2.064516f * 0.2421875f, -1627324417, 0x1AFFFFFF);
            } else {
                Ref.modified$drawRectWithOutline(0f, 0f, aCModule.width, aCModule.height, 1.2179487f * 0.41052634f, 0x6FFFFFFF, 0x1AFFFFFF);
            }
        }
        if (!this.IlIIIIllIIIIIlllIIlIIlllI && n6 != 0) {
            n5 = !aCModule.getSettingsList().isEmpty() && (float)n >= (object[0] + 2f) * aCModule.scale.<Float>value() && (float)n <= (object[0] + 10f) * aCModule.scale.<Float>value() && (float)n2 >= (object[1] + aCModule.height - 8f) * aCModule.scale.<Float>value() && (float)n2 <= (object[1] + aCModule.height - 2.0f) * aCModule.scale.<Float>value() ? 1 : 0;
            n4 = (float)n > (object[0] + aCModule.width - 10f) * aCModule.scale.<Float>value() && (float)n < (object[0] + aCModule.width - 2f) * aCModule.scale.<Float>value() && (float)n2 > (object[1] + aCModule.height - 8f) * aCModule.scale.<Float>value() && (float)n2 < (object[1] + aCModule.height - 2.0f) * aCModule.scale.<Float>value() ? 1 : 0;
            if (!aCModule.getSettingsList().isEmpty()) {
                Ref.getGlBridge().bridge$color(1f, 1f, 1f, n5 != 0 ? 1f : 0.20895523f * 2.8714287f);
                RenderUtil.drawIcon(this.cogIcon, 3f, 2f, aCModule.height - 2.162162f * 3.4687502f);
            }
            Ref.getGlBridge().bridge$color(.8f, .2f, .2f, n4 != 0 ? 1f : .6f);
            RenderUtil.drawIcon(this.deleteIcon, 3f, aCModule.width - 8f, aCModule.height - 0.2972973f * 25.227272f);
        }
        Ref.getGlBridge().bridge$pushMatrix();
        float f3 = f / aCModule.scale.<Float>value();
        Ref.getGlBridge().bridge$scale(f3, f3, f3);
        if (bl) {
            n4 = this.dataHolder != null && this.dataHolder.module == aCModule && this.dataHolder.unknown == SomeRandomAssEnum.LEFT_BOTTOM || n6 == 0 && (float)n >= (object[0] + aCModule.width - 5f) * aCModule.scale.<Float>value() && (float)n <= (object[0] + aCModule.width + 5f) * aCModule.scale.<Float>value() && (float)n2 >= (object[1] - 5f) * aCModule.scale.<Float>value() && (float)n2 <= (object[1] + 5f) * aCModule.scale.<Float>value() ? 1 : 0;
            n3 = this.dataHolder != null && this.dataHolder.module == aCModule && this.dataHolder.unknown == SomeRandomAssEnum.RIGHT_TOP || n6 == 0 && (float)n >= (object[0] - 5f) * aCModule.scale.<Float>value() && (float)n <= (object[0] + 5f) * aCModule.scale.<Float>value() && (float)n2 >= (object[1] + aCModule.height - 5f) * aCModule.scale.<Float>value() && (float)n2 <= (object[1] + aCModule.height + 5f) * aCModule.scale.<Float>value() ? 1 : 0;
            boolean bl5 = this.dataHolder != null && this.dataHolder.module == aCModule && this.dataHolder.unknown == SomeRandomAssEnum.RIGHT_BOTTOM || n6 == 0 && (float)n >= (object[0] - 5f) * aCModule.scale.<Float>value() && (float)n <= (object[0] + 5f) * aCModule.scale.<Float>value() && (float)n2 >= (object[1] - 5f) * aCModule.scale.<Float>value() && (float)n2 <= (object[1] + 5f) * aCModule.scale.<Float>value();
            boolean bl6 = this.dataHolder != null && this.dataHolder.module == aCModule && this.dataHolder.unknown == SomeRandomAssEnum.LEFT_TOP || n6 == 0 && (float)n >= (object[0] + aCModule.width - 5f) * aCModule.scale.<Float>value() && (float)n <= (object[0] + aCModule.width + 5f) * aCModule.scale.<Float>value() && (float)n2 >= (object[1] + aCModule.height - 5f) * aCModule.scale.<Float>value() && (float)n2 <= (object[1] + aCModule.height + 5f) * aCModule.scale.<Float>value();
            Ref.getGlBridge().bridge$pushMatrix();
            float f4 = 4;
            if (this.someMouseX == -1 && bl5) {
                Ref.getGlBridge().bridge$translate(0f, 0f, 0f);
                Ref.modified$drawRect(-f4 / 2.0f, -f4 / 2f, f4 / 2f, f4 / 2f, -16711936);
            }
            if (this.someMouseX == -1 && n4 != 0) {
                Ref.getGlBridge().bridge$translate(aCModule.width / f3, 0f, 0f);
                Ref.modified$drawRect(-f4 / 2.0f, -f4 / 2f, f4 / 2f, f4 / 2f, -16711936);
            }
            if (this.someMouseX == -1 && bl6) {
                Ref.getGlBridge().bridge$translate(aCModule.width / f3, aCModule.height / f3, 0f);
                Ref.modified$drawRect(-f4 / 2.0f, -f4 / 2f, f4 / 2f, f4 / 2f, -16711936);
            }
            if (this.someMouseX == -1 && n3 != 0) {
                Ref.getGlBridge().bridge$translate(0f, aCModule.height / f3, 0f);
                Ref.modified$drawRect(-f4 / 2f, -f4 / 2f, f4 / 2f, f4 / 2f, -16711936);
            }
            Ref.getGlBridge().bridge$popMatrix();
            bl3 = this.someMouseX == -1 && (bl5 || n4 != 0 || n3 != 0 || bl6);
        }
        n4 = arrf[1] - FontRegistry.getUbuntuMedium16px().getHeight() - (float)6 < 0.0f ? 1 : 0;
        float f5 = n4 != 0 ? aCModule.height * aCModule.scale.<Float>value() / f : (-FontRegistry.getUbuntuMedium16px().getHeight() - 4f);
        switch (aCModule.getPosition()) {
            case LEFT: {
                float f6 = 0.0f;
                FontRegistry.getUbuntuMedium16px().drawString(aCModule.getName(), f6, f5, -1);
                break;
            }
            case CENTER: {
                float f7 = aCModule.width * aCModule.scale.<Float>value() / f / 2.0f;
                FontRegistry.getUbuntuMedium16px().drawString(aCModule.getName(), f7, f5, -1);
                break;
            }
            case RIGHT: {
                float f8 = aCModule.width * aCModule.scale.<Float>value() / f - (float) FontRegistry.getUbuntuMedium16px().getStringWidth(aCModule.getName());
                FontRegistry.getUbuntuMedium16px().drawString(aCModule.getName(), f8, f5, -1);
            }
        }
        Ref.getGlBridge().bridge$popMatrix();
        Ref.getGlBridge().bridge$popMatrix();
        return !bl3;
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(ScaledResolution scaledResolution) {
        if (!Mouse.isButtonDown(1) && draggingModule != null) {
            for (ACModulePosition ACModulePosition : this.positions) {
                if (ACModulePosition.module != draggingModule || !ArchClient.getInstance().globalSettings.snapModules.<Boolean>value()) continue;
                Object var5_5 = null;
                for (AbstractModule aCModule : this.modules) {
                    if (this.getModulePosition(aCModule) != null
                            || aCModule.getGuiAnchor() == null
                            || !aCModule.isEnabled()
                            || !aCModule.isEditable
                            && !aCModule.isRenderHud()) {
                        continue;
                    }

                    float minSize = 18f;
                    aCModule.width = Math.max(aCModule.width, minSize);
                    aCModule.height = Math.max(aCModule.height, minSize);
                    ACModulePosition.module.width = Math.max(ACModulePosition.module.width, minSize);
                    ACModulePosition.module.height = Math.max(ACModulePosition.module.height, minSize);

                    float[] arrf = aCModule.getScaledPoints(scaledResolution, true);
                    float[] arrf2 = ACModulePosition.module.getScaledPoints(scaledResolution, true);
                    boolean bl = false;
                    float f2 = arrf[0] * aCModule.scale.<Float>value() - arrf2[0] * ACModulePosition.module.scale.<Float>value();
                    float f3 = (arrf[0] + aCModule.width) * aCModule.scale.<Float>value() - (arrf2[0] + ACModulePosition.module.width) * ACModulePosition.module.scale.<Float>value();
                    float f4 = (arrf[0] + aCModule.width) * aCModule.scale.<Float>value() - arrf2[0] * ACModulePosition.module.scale.<Float>value();
                    float f5 = arrf[0] * aCModule.scale.<Float>value() - (arrf2[0] + ACModulePosition.module.width) * ACModulePosition.module.scale.<Float>value();
                    float f6 = arrf[1] * aCModule.scale.<Float>value() - arrf2[1] * ACModulePosition.module.scale.<Float>value();
                    float f7 = (arrf[1] + aCModule.height) * aCModule.scale.<Float>value() - (arrf2[1] + ACModulePosition.module.height) * ACModulePosition.module.scale.<Float>value();
                    float f8 = (arrf[1] + aCModule.height) * aCModule.scale.<Float>value() - arrf2[1] * ACModulePosition.module.scale.<Float>value();
                    float f9 = arrf[1] * aCModule.scale.<Float>value() - (arrf2[1] + ACModulePosition.module.height) * ACModulePosition.module.scale.<Float>value();
                    int n = 2;
                    if (f2 >= (float)(-n) && f2 <= (float)n) {
                        bl = true;
                        RenderUtil.drawRoundedRect(arrf[0] * aCModule.scale.<Float>value() - .5f, 0, arrf[0] * aCModule.scale.<Float>value(), this.height, 0, -3596854);
                    }
                    if (f3 >= (float)(-n) && f3 <= (float)n) {
                        bl = true;
                        RenderUtil.drawRoundedRect((arrf[0] + aCModule.width) * aCModule.scale.<Float>value(), 0, (arrf[0] + aCModule.width) * aCModule.scale.<Float>value() + .5f, this.height, 0, -3596854);
                    }
                    if (f5 >= (float)(-n) && f5 <= (float)n) {
                        bl = true;
                        RenderUtil.drawRoundedRect(arrf[0] * aCModule.scale.<Float>value(), 0, arrf[0] * aCModule.scale.<Float>value() + .5f, this.height, 0, -3596854);
                    }
                    if (f4 >= (float)(-n) && f4 <= (float)n) {
                        bl = true;
                        RenderUtil.drawRoundedRect((arrf[0] + aCModule.width) * aCModule.scale.<Float>value(), 0, (arrf[0] + aCModule.width) * aCModule.scale.<Float>value() + .5f, this.height, 0, -3596854);
                    }
                    if (f6 >= (float)(-n) && f6 <= (float)n) {
                        bl = true;
                        RenderUtil.drawRoundedRect(0, arrf[1] * aCModule.scale.<Float>value(), this.width, arrf[1] * aCModule.scale.<Float>value() + .5f, 0, -3596854);
                    }
                    if (f7 >= (float)(-n) && f7 <= (float)n) {
                        bl = true;
                        RenderUtil.drawRoundedRect(0, (arrf[1] + aCModule.height) * aCModule.scale.<Float>value(), this.width, (arrf[1] + aCModule.height) * aCModule.scale.<Float>value() + .5f, 0, -3596854);
                    }
                    if (f9 >= (float)(-n) && f9 <= (float)n) {
                        bl = true;
                        RenderUtil.drawRoundedRect(0, arrf[1] * aCModule.scale.<Float>value(), this.width, arrf[1] * aCModule.scale.<Float>value() + .5f, 0, -3596854);
                    }
                    if (f8 >= (float)(-n) && f8 <= (float)n) {
                        bl = true;
                        RenderUtil.drawRoundedRect(0, (arrf[1] + aCModule.height) * aCModule.scale.<Float>value() - .5f, this.width, (arrf[1] + aCModule.height) * aCModule.scale.<Float>value(), 0, -3596854);
                    }
                    if (!bl) continue;
                    Ref.getGlBridge().bridge$pushMatrix();
                    aCModule.scaleAndTranslate(scaledResolution);
                    Ref.modified$drawRectWithOutline(0f, 0f, aCModule.width, aCModule.height, .5f, 0, 449387978);
                    Ref.getGlBridge().bridge$popMatrix();
                }
            }
        }
    }

    private float lIIIIlIIllIIlIIlIIIlIIllI(AbstractModule aCModule, float f, float[] arrf, int n) {
        float f2 = f;
        float padding = 2.0f;
        if (f2 + arrf[0] * aCModule.scale.<Float>value() < padding) {
            f2 = -arrf[0] * aCModule.scale.<Float>value() + padding;
        } else if (f2 + arrf[0] * aCModule.scale.<Float>value() + (float)n > (float)this.width - padding) {
            f2 = (float)this.width - arrf[0] * aCModule.scale.<Float>value() - (float)n - padding;
        }
        return f2;
    }

    private float lIIIIIIIIIlIllIIllIlIIlIl(AbstractModule aCModule, float f, float[] arrf, int n) {
        float f2 = f;
        float padding = 2.0f;
        if (f2 + arrf[1] * aCModule.scale.<Float>value() < padding) {
            f2 = -arrf[1] * aCModule.scale.<Float>value() + padding;
        } else if (f2 + arrf[1] * aCModule.scale.<Float>value() + (float)n > (float)this.height - padding) {
            f2 = (float)this.height - arrf[1] * aCModule.scale.<Float>value() - (float)n - padding;
        }
        return f2;
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(ACModulePosition ACModulePosition, int n, int n2, ScaledResolution scaledResolution) {
        if (ACModulePosition.module.getGuiAnchor() == null || !ACModulePosition.module.isEnabled() || !ACModulePosition.module.isEditable && !ACModulePosition.module.isRenderHud()) {
            return;
        }
        float f = (float)n - ACModulePosition.x;
        float f2 = (float)n2 - ACModulePosition.y;
        if (!(this.IlIlIIIlllllIIIlIlIlIllII || ACModulePosition.module != draggingModule || (float)n == this.IIlIIllIIIllllIIlllIllIIl && (float)n2 == this.lllIlIIllllIIIIlIllIlIIII)) {
            if (this.undoList.size() > 50) {
                this.undoList.remove(0);
            }
            this.undoList.add(new ModuleActionData(this, this.positions));
            ArchClient.getInstance().createNewProfile();
            this.IlIlIIIlllllIIIlIlIlIllII = true;
        }
        float[] arrf = ACModulePosition.module.getScaledPoints(scaledResolution, false);
        if (!Mouse.isButtonDown(1) && this.IlIlIIIlllllIIIlIlIlIllII && ACModulePosition.module == draggingModule) {
            float f3 = f;
            float f4 = f2;
            f = this.lIIIIlIIllIIlIIlIIIlIIllI(ACModulePosition.module, f, arrf, (int)(ACModulePosition.module.width * ACModulePosition.module.scale.<Float>value()));
            f2 = this.lIIIIIIIIIlIllIIllIlIIlIl(ACModulePosition.module, f2, arrf, (int)(ACModulePosition.module.height * ACModulePosition.module.scale.<Float>value()));
            float f5 = f3 - f;
            float f6 = f4 - f2;
            for (ACModulePosition dragCache2 : this.positions) {
                if (dragCache2 == ACModulePosition) continue;
                arrf = dragCache2.module.getScaledPoints(scaledResolution, false);
                float f7 = this.lIIIIlIIllIIlIIlIIIlIIllI(dragCache2.module, dragCache2.module.getXTranslation() - f5, arrf, (int)(dragCache2.module.width * dragCache2.module.scale.<Float>value()));
                float f8 = this.lIIIIIIIIIlIllIIllIlIIlIl(dragCache2.module, dragCache2.module.getYTranslation() - f6, arrf, (int)(dragCache2.module.height * dragCache2.module.scale.<Float>value()));
                dragCache2.module.setTranslations(f7, f8);
            }
        }
        if (this.IlIlIIIlllllIIIlIlIlIllII) {
            ACModulePosition.module.setTranslations(f, f2);
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(int n) {
        if (IlIlllIIIIllIllllIllIIlIl) {
            if (this.lIIIIllIIlIlIllIIIlIllIlI != null) {
                this.lIIIIlIIllIIlIIlIIIlIIllI(this.lIIIIllIIlIlIllIIIlIllIlI, true, n);
            }
        } else if (this.currentScrollableElement != null) {
            if (this.lIIIIllIIlIlIllIIIlIllIlI != null) {
                this.lIIIIlIIllIIlIIlIIIlIIllI(this.lIIIIllIIlIlIllIIIlIllIlI, true, n);
            }
            this.lIIIIlIIllIIlIIlIIIlIIllI(this.currentScrollableElement, false, n);
        }
    }

    private void lIIIIlIIllIIlIIlIIIlIIllI(AbstractScrollableElement lllIllIllIlIllIlIIllllIIl2, boolean bl, int n) {
        if (bl) {
            lllIllIllIlIllIlIIllllIIl2.x = lllIllIllIlIllIlIIllllIIl2.IlIlllIIIIllIllllIllIIlIl;
            IlIlllIIIIllIllllIllIIlIl = false;
            this.lIIIIllIIlIlIllIIIlIllIlI = null;
        } else {
            lllIllIllIlIllIlIIllllIIl2.x = n / 2 - 185;
            this.currentScrollableElement = null;
            this.lIIIIllIIlIlIllIIIlIllIlI = lllIllIllIlIllIlIIllllIIl2;
        }
    }

    public static float getSmoothFloat(float f) {
        float f2 = f / (float)(Minecraft.getDebugFPS() + 1);
        return Math.max(f2, 1.0f);
    }

    private ACModulePosition getModulePosition(AbstractModule aCModule) {
        for (ACModulePosition ACModulePosition : this.positions) {
            if (aCModule != ACModulePosition.module) continue;
            return ACModulePosition;
        }
        return null;
    }

    private void IlllIIIlIlllIllIlIIlllIlI(ScaledResolution scaledResolution, int n, int n2) {
        for (ACModulePosition ACModulePosition : this.positions) {
            if (ACModulePosition.module == null || ACModulePosition.module.getGuiAnchor() == null) continue;
            ACModulePosition.x = (float)n - ACModulePosition.module.getXTranslation();
            ACModulePosition.y = (float)n2 - ACModulePosition.module.getYTranslation();
        }
    }

    private void removePositionForModule(AbstractModule aCModule) {
        this.positions.removeIf(ACModulePosition -> ACModulePosition.module == aCModule);
    }

    static {
        IlIlllIIIIllIllllIllIIlIl = false;
    }
}
