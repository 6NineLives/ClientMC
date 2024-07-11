package com.archclient.client.module.type;

import com.archclient.bridge.ref.Ref;
import com.archclient.bridge.util.EnumChatFormattingBridge;
import com.archclient.client.config.Setting;
import com.archclient.client.event.type.GuiDrawEvent;
import com.archclient.client.event.type.RenderPreviewEvent;
import com.archclient.client.module.AbstractModule;
import com.archclient.client.module.ModuleRule;
import com.archclient.client.ui.module.ACGuiAnchor;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;

import java.util.Collection;
import java.util.Iterator;

public class ScoreboardModule extends AbstractModule {
    public static ModuleRule rule = ModuleRule.SCOREBOARD;
    public Setting removeNumbers;

    public ScoreboardModule() {
        super("Scoreboard");
        this.setDefaultAnchor(ACGuiAnchor.RIGHT_MIDDLE);
        this.removeNumbers = new Setting(this, "Remove Scoreboard numbers").setValue(true);
        this.addEvent(GuiDrawEvent.class, this::renderReal);
        this.addEvent(RenderPreviewEvent.class, this::renderPreview);
        this.setDefaultState(true);
    }

    private void renderPreview(RenderPreviewEvent renderPreviewEvent) {
        if (!this.isRenderHud()) {
            return;
        }
        if (this.minecraft.theWorld.getScoreboard().func_96539_a(1) != null) {
            return;
        }
        Ref.getGlBridge().bridge$pushMatrix();
        this.scaleAndTranslate(renderPreviewEvent.getResolution());
        Ref.getGlBridge().bridge$translate(this.isRemoveNumbers() ? (float) -12 : 2.0f, this.height, 0.0f);
        Scoreboard scoreboard = Ref.getInstanceCreator().createScoreboard();
        ScoreObjective objective = Ref.getInstanceCreator().createScoreObjective(scoreboard, "ArchClient", "DUMMY");
        objective.setDisplayName(EnumChatFormattingBridge.RED + "" + EnumChatFormattingBridge.BOLD + "Arch" + EnumChatFormattingBridge.RESET + "" + EnumChatFormattingBridge.WHITE + "Client");
        scoreboard.func_96529_a("Steve", objective);
        scoreboard.func_96529_a("Alex", objective);
        this.drawObjective(objective, this.minecraft.fontRendererObj);
        Ref.getGlBridge().bridge$popMatrix();
    }

    private void renderReal(GuiDrawEvent guiDrawEvent) {
        if (!this.isRenderHud()) {
            return;
        }
        Ref.getGlBridge().bridge$pushMatrix();
        this.scaleAndTranslate(guiDrawEvent.getResolution());
        Ref.getGlBridge().bridge$translate(this.isRemoveNumbers() ? (float) -12 : 2.0f, this.height, 0.0f);
        ScoreObjective objective = this.minecraft.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
        if (objective != null) {
            this.drawObjective(objective, this.minecraft.fontRendererObj);
        }
        Ref.getGlBridge().bridge$popMatrix();
    }

    private void drawObjective(ScoreObjective objective, FontRenderer fontRenderer) {
        Scoreboard scoreboard = objective.getScoreboard();
        Collection<Score> collection = scoreboard.func_96534_i(objective);
        boolean removeNumbers = isRemoveNumbers();
        if (collection.size() <= 15) {
            int width = fontRenderer.getStringWidth(objective.getDisplayName());
            int numbersX = width + 16;
            for (Score score : collection) {
                String string = score.getFormattedName(scoreboard) + ": " + EnumChatFormattingBridge.RED + score.getScorePoints();
                width = Math.max(width, fontRenderer.getStringWidth(string));
            }
            int n8 = 0;
            Iterator<Score> iterator = collection.iterator();
            int n9 = 0;
            while (iterator.hasNext()) {
                Score score = iterator.next();
                String string = score.getFormattedName(scoreboard);
                String string2 = EnumChatFormattingBridge.RED + "" + score.getScorePoints();
                int lineY = -++n8 * 9;
                int lineX = width + 9;
                if (lineX < numbersX) {
                    lineX = numbersX;
                }
                Ref.modified$drawRect(-2 + (removeNumbers ? 14 : 0), lineY, lineX, lineY + 9, 0x50000000);
                n9 = lineX - (-2 + (removeNumbers ? 14 : 0));
                fontRenderer.drawString(string, (removeNumbers ? 16 : 0), lineY, 0x20FFFFFF);
                if (!removeNumbers) {
                    fontRenderer.drawString(string2, lineX - fontRenderer.getStringWidth(string2) - 2, lineY, 0x20FFFFFF);
                }
                if (n8 != collection.size()) continue;
                String string3 = objective.getDisplayName();
                Ref.modified$drawRect(-2 + (removeNumbers ? 14 : 0), lineY - 9 - 1, lineX, lineY - 1, 0x60000000);
                Ref.modified$drawRect(-2 + (removeNumbers ? 14 : 0), lineY - 1, lineX, lineY, 0x50000000);
                fontRenderer.drawString(string3, +width / 2 - fontRenderer.getStringWidth(string3) / 2 + (removeNumbers ? 14 : 0), lineY - 9, 0x20FFFFFF);
            }
            this.setDimensions(n9, collection.size() * 9 + 12);
        }
    }

    private boolean isRemoveNumbers() {
        return rule == ModuleRule.SCOREBOARD ? this.removeNumbers.<Boolean>value() : false;
    }
}
