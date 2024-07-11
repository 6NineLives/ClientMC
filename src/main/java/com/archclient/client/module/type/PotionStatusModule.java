package com.archclient.client.module.type;

import com.archclient.bridge.ref.Ref;
import com.archclient.client.config.Setting;
import com.archclient.client.event.type.GuiDrawEvent;
import com.archclient.client.event.type.RenderPreviewEvent;
import com.archclient.client.event.type.TickEvent;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.ui.module.ACGuiAnchor;
import com.archclient.client.ui.module.ACPositionEnum;
import com.archclient.client.ui.util.RenderUtil;

import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;

public class PotionStatusModule extends AbstractModule {
    private final Setting showWhileTying;
    private final Setting showEffectName;
    private final Setting colorOptionsLabel;
    private final Setting nameColor;
    private final Setting durationColor;
    private final Setting blink;
    private final Setting blinkDuration;
    private final ResourceLocation location = Ref.getInstanceCreator().createResourceLocation("textures/gui/container/inventory.png");
    private int ticks = 0;

    public PotionStatusModule() {
        super("Potion Effects");
        this.setDefaultState(false);
        this.setDefaultAnchor(ACGuiAnchor.LEFT_MIDDLE);

        new Setting(this, "label").setValue("General Options");
        {
            this.showWhileTying = new Setting(this, "Show While Typing").setValue(true);
            this.showEffectName = new Setting(this, "Effect Name").setValue(true);
            //this.showInInventory = new Setting(this, "Show Potion info in inventory").setValue(false);
            // commented out due to there being two of the same option.
        }
        new Setting(this, "label").setValue("Blink Options");
        {
            this.blink = new Setting(this, "Blink").setValue(true);
            this.blinkDuration = new Setting(this, "Blink Duration").setValue(10).setMinMax(2, 20);
            this.colorOptionsLabel = new Setting(this, "label").setValue("Color Options");
            this.nameColor = new Setting(this, "Name Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
            this.durationColor = new Setting(this, "Duration Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        this.setPreviewIcon(Ref.getInstanceCreator().createResourceLocation("client/icons/mods/speed_icon.png"), 28, 28);

        this.addEvent(TickEvent.class, this::onTick);
        this.addEvent(RenderPreviewEvent.class, this::renderPreview);
        this.addEvent(GuiDrawEvent.class, this::renderReal);
    }

    private void onTick(TickEvent aCTickEvent) {
        ++this.ticks;
    }

    private void renderReal(GuiDrawEvent guiDrawEvent) {
        Ref.getGlBridge().bridge$pushMatrix();
        if (this.showWhileTying.<Boolean>value() || !this.minecraft.ingameGUI.getChatGUI().getChatOpen()) {
            Ref.getGlBridge().bridge$pushMatrix();
            this.scaleAndTranslate(guiDrawEvent.getResolution());
            ACPositionEnum position = this.getPosition();
            Collection<PotionEffect> collection = this.minecraft.thePlayer.getActivePotionEffects();
            if (collection.isEmpty()) {
                Ref.getGlBridge().bridge$popMatrix();
                Ref.getGlBridge().bridge$popMatrix();
                return;
            }
            int n = 0;
            int n2 = 0;
            int n3 = 22;
            for (PotionEffect potionEffect : collection) {
                Potion potion;
                String string;
                boolean shouldBlink = this.shouldBlink(potionEffect.getDuration());
                int n4 = 0;
                if (this.showEffectName.<Boolean>value()) {
                    string = I18n.format(potionEffect.getEffectName()) + this.getLevelName(potionEffect.getAmplifier());
                    n4 = this.minecraft.fontRendererObj.getStringWidth(string) + 20;
                    if (position == ACPositionEnum.RIGHT) {
                        this.minecraft.fontRendererObj.drawStringWithShadow(string + "\u00a7r", (int) width - n4, n, this.nameColor.getColorValue());
                    } else if (position == ACPositionEnum.LEFT) {
                        this.minecraft.fontRendererObj.drawStringWithShadow(string + "\u00a7r", 20, n, this.nameColor.getColorValue());
                    } else if (position == ACPositionEnum.CENTER) {
                        this.minecraft.fontRendererObj.drawStringWithShadow(string + "\u00a7r", (int) width / 2 - (n4 / 2) + 20, n, this.nameColor.getColorValue());
                    }
                    if (n4 > n2) {
                        n2 = n4;
                    }
                }
                string = Potion.getDurationString((PotionEffect) ((PotionEffect) potionEffect));
                int n5 = this.minecraft.fontRendererObj.getStringWidth(string) + 20;
                if (shouldBlink) {
                    if (position == ACPositionEnum.RIGHT) {
                        this.minecraft.fontRendererObj.drawStringWithShadow(string + "\u00a7r", (int) width - n5, n + (this.showEffectName.<Boolean>value() ? 10 : 5), this.durationColor.getColorValue());
                    } else if (position == ACPositionEnum.LEFT) {
                        this.minecraft.fontRendererObj.drawStringWithShadow(string + "\u00a7r", 20, n + (this.showEffectName.<Boolean>value() ? 10 : 5), this.durationColor.getColorValue());
                    } else if (position == ACPositionEnum.CENTER) {
                        this.minecraft.fontRendererObj.drawStringWithShadow(string + "\u00a7r", (int) width / 2 - (n5 / 2) + 20, n + (this.showEffectName.<Boolean>value() ? 10 : 5), this.durationColor.getColorValue());
                    }
                }
                if ((potion = (Potion) Potion.potionTypes[potionEffect.getPotionID()]).hasStatusIcon()) {
                    Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
                    this.minecraft.getTextureManager().bindTexture(this.location);
                    int n6 = potion.getStatusIconIndex();
                    if (position == ACPositionEnum.RIGHT) {
                        RenderUtil.drawTexturedModalRect(width - (float) 20, (float) n, (float) (n6 % 8 * 18), (float) (198 + n6 / 8 * 18), 18, 18);
                    } else if (position == ACPositionEnum.LEFT) {
                        RenderUtil.drawTexturedModalRect(0.0f, (float) n, (float) (n6 % 8 * 18), (float) (198 + n6 / 8 * 18), 18, 18);
                    } else if (position == ACPositionEnum.CENTER) {
                        RenderUtil.drawTexturedModalRect(width / 2.0f - (float) (n4 / 2), (float) n, (float) (n6 % 8 * 18), (float) (198 + n6 / 8 * 18), 18, 18);
                    }
                }
                if (n5 > n2) {
                    n2 = n5;
                }
                n += n3;
            }
            this.setDimensions(n2, n);
            Ref.getGlBridge().bridge$popMatrix();
        }
        Ref.getGlBridge().bridge$popMatrix();
    }

    private void renderPreview(RenderPreviewEvent renderPreviewEvent) {
        if (!this.isRenderHud()) {
            return;
        }
        Ref.getGlBridge().bridge$pushMatrix();
        Collection<PotionEffect> collection = this.minecraft.thePlayer.getActivePotionEffects();
        if (collection.isEmpty()) {
            Ref.getGlBridge().bridge$pushMatrix();
            this.scaleAndTranslate(renderPreviewEvent.getResolution());
            HashMap<Integer, PotionEffect> hashMap = new HashMap<>();
            PotionEffect fireResistance = Ref.getInstanceCreator().createPotionEffect("FIRE_RESISTANCE", 1200, 3);
            PotionEffect speed = Ref.getInstanceCreator().createPotionEffect("MOVE_SPEED", 30, 3);
            hashMap.put(fireResistance.getPotionID(), fireResistance);
            hashMap.put(speed.getPotionID(), speed);
            collection = hashMap.values();
            ACPositionEnum position = this.getPosition();
            int n = 0;
            int n2 = 0;
            int n3 = 22;
            for (PotionEffect potionEffect : collection) {
                Potion potion;
                String string;
                boolean shouldBlink = this.shouldBlink(potionEffect.getDuration());
                int n4 = 0;
                if (this.showEffectName.<Boolean>value()) {
                    string = I18n.format(potionEffect.getEffectName()) + this.getLevelName(potionEffect.getAmplifier());
                    n4 = this.minecraft.fontRendererObj.getStringWidth(string) + 20;
                    if (position == ACPositionEnum.RIGHT) {
                        this.minecraft.fontRendererObj.drawStringWithShadow(string + "\u00a7r", (int) width - n4, n, this.nameColor.getColorValue());
                    } else if (position == ACPositionEnum.LEFT) {
                        this.minecraft.fontRendererObj.drawStringWithShadow(string + "\u00a7r", 20, n, this.nameColor.getColorValue());
                    } else if (position == ACPositionEnum.CENTER) {
                        this.minecraft.fontRendererObj.drawStringWithShadow(string + "\u00a7r", (int) width / 2 - (n4 / 2) + 20, n, this.nameColor.getColorValue());
                    }
                    if (n4 > n2) {
                        n2 = n4;
                    }
                }
                string = Potion.getDurationString((PotionEffect) ((PotionEffect) potionEffect));
                int n5 = this.minecraft.fontRendererObj.getStringWidth(string) + 20;
                if (shouldBlink) {
                    if (position == ACPositionEnum.RIGHT) {
                        this.minecraft.fontRendererObj.drawStringWithShadow(string + "\u00a7r", (int) width - n5, n + (this.showEffectName.<Boolean>value() ? 10 : 5), this.durationColor.getColorValue());
                    } else if (position == ACPositionEnum.LEFT) {
                        this.minecraft.fontRendererObj.drawStringWithShadow(string + "\u00a7r", 20, n + (this.showEffectName.<Boolean>value() ? 10 : 5), this.durationColor.getColorValue());
                    } else if (position == ACPositionEnum.CENTER) {
                        this.minecraft.fontRendererObj.drawStringWithShadow(string + "\u00a7r", (int) width / 2 - (n5 / 2) + 20, n + (this.showEffectName.<Boolean>value() ? 10 : 5), this.durationColor.getColorValue());
                    }
                }
                if ((potion = (Potion) Potion.potionTypes[potionEffect.getPotionID()]).hasStatusIcon()) {
                    Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
                    this.minecraft.getTextureManager().bindTexture(this.location);
                    int n6 = potion.getStatusIconIndex();
                    if (position == ACPositionEnum.RIGHT) {
                        RenderUtil.drawTexturedModalRect(width - (float) 20, (float) n, (float) (n6 % 8 * 18), (float) (198 + n6 / 8 * 18), 18, 18);
                    } else if (position == ACPositionEnum.LEFT) {
                        RenderUtil.drawTexturedModalRect(0.0f, (float) n, (float) (n6 % 8 * 18), (float) (198 + n6 / 8 * 18), 18, 18);
                    } else if (position == ACPositionEnum.CENTER) {
                        RenderUtil.drawTexturedModalRect(width / 2.0f - (float) (n4 / 2), (float) n, (float) (n6 % 8 * 18), (float) (198 + n6 / 8 * 18), 18, 18);
                    }
                }
                if (n5 > n2) {
                    n2 = n5;
                }
                n += n3;
            }
            this.setDimensions(n2, n);
            Ref.getGlBridge().bridge$popMatrix();
        }
        Ref.getGlBridge().bridge$popMatrix();
    }

    private boolean shouldBlink(float f) {
        if (this.blink.<Boolean>value() && f <= (float) (this.blinkDuration.<Integer>value() * 22)) {
            if (this.ticks > 20) {
                this.ticks = 0;
            }
            return this.ticks <= 10;
        }
        return true;
    }

    private String getLevelName(int level) {
        switch (level) {
            case 1: {
                return " II";
            }
            case 2: {
                return " III";
            }
            case 3: {
                return " IV";
            }
            case 4: {
                return " V";
            }
            case 5: {
                return " VI";
            }
            case 6: {
                return " VII";
            }
            case 7: {
                return " VIII";
            }
            case 8: {
                return " IX";
            }
            case 9: {
                return " X";
            }
        }
        if (level > 9) {
            return " " + level + 1;
        }
        return "";
    }

}
