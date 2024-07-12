package com.archclient.client.util.voicechat;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;
import com.archclient.client.event.type.GuiDrawEvent;
import com.archclient.client.event.type.TickEvent;
import com.archclient.client.ui.util.RenderUtil;
import com.archclient.client.ui.util.font.FontRegistry;
import com.archclient.common.KeyMappings;

import net.lax1dude.eaglercraft.v1_8.EaglercraftUUID;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VoiceChat {
    Minecraft minecraft = Ref.getMinecraft();
    ArchClient archclient = ArchClient.getInstance();
    private final ResourceLocation microphoneIcon = Ref.getInstanceCreator().createResourceLocation("client/icons/microphone-64.png");
    private final Map<VoiceUser, Long> userLastSpoken = new HashMap<>();
    private boolean isTalking;
    public boolean checkMicVolume;

    public VoiceChat() {
        ArchClient.getInstance().getEventBus().addEvent(GuiDrawEvent.class, this::onRender);
        ArchClient.getInstance().getEventBus().addEvent(TickEvent.class, this::onTick);
    }

    public void addUserToSpoken(EaglercraftUUID uuid) {
        VoiceUser voiceUser = this.archclient.getNetHandler().getVoiceUser(uuid);
        if (voiceUser != null && !voiceUser.getUsername().equals(Ref.getMinecraft().getSession().getProfile().getName())) {
            this.userLastSpoken.put(voiceUser, System.currentTimeMillis() + 250L);
        }
    }

    public void onRender(GuiDrawEvent event) {
        if (this.archclient.getNetHandler().voiceChatEnabled && this.archclient.getNetHandler().getVoiceChannels() != null && (!this.userLastSpoken.isEmpty() || this.isTalking)) {
            float f = 20;
            float f2 = (float)event.getResolution().getScaledWidth_double() - (float)120;
            float[] arrf = new float[]{10};

            if (this.isTalking) {
                this.renderHeadAndName(this.minecraft.thePlayer.getDisplayName().toString(), f2, arrf[0], true);
                arrf[0] = arrf[0] + f;
            }

            this.userLastSpoken.forEach((voiceUser, l) -> {
                this.renderHeadAndName(voiceUser.getUsername(), f2, arrf[0], false);
                arrf[0] = arrf[0] + f;
            });
        }
    }

    private void renderHeadAndName(String string, float f, float f2, boolean isSelf) {
        if (isSelf) {
            RenderUtil.drawCorneredGradientRectWithOutline(f, f2, f + (float)110, f2 + (float)18, -11493284, -10176146, -11164318);
        } else {
            RenderUtil.drawCorneredGradientRectWithOutline(f, f2, f + (float)110, f2 + (float)18, -1356454362, -1355664846, -1356191190);
        }
        Ref.getGlBridge().bridge$color(1.0f, 1.0f, 1.0f, 1.0f);
        ResourceLocation location = ArchClient.getInstance().getHeadLocation(string);
        RenderUtil.drawIcon(location, (float)7, f + 2.0f, f2 + 2.0f);
        FontRegistry.getPlayRegular16px().drawString(string, f + (float)22, f2 + (float)4, -1);
    }

    public void onTick(TickEvent aCTickEvent) {
        if (!this.userLastSpoken.isEmpty()) {
            ArrayList<VoiceUser> arrayList = new ArrayList<>();
            for (Map.Entry<VoiceUser, Long> entry : this.userLastSpoken.entrySet()) {
                if (System.currentTimeMillis() - entry.getValue() < 0L) continue;
                arrayList.add(entry.getKey());
            }
            arrayList.forEach(voiceUser -> this.userLastSpoken.remove(voiceUser));
        }
        if (!this.archclient.getNetHandler().voiceChatEnabled && this.archclient.getNetHandler().getVoiceChannels() != null) {
            return;
        }
        if (this.isTalking && !this.minecraft.inGameHasFocus) {
            this.isTalking = false;
            ArchClient.getInstance().getVoiceChatManager().setTalking(false);
            ArchClient.getInstance().sendSound("voice_up");
        }
        if (!this.isTalking && this.minecraft.inGameHasFocus && KeyMappings.PUSH_TO_TALK.isKeyDown()) {
            this.isTalking = true;
            if (checkMicVolume && ArchClient.getInstance().getGlobalSettings().microphoneVolume.<Integer>value() < 10) {
                ArchClient.getInstance().getModuleManager().notifications.queueNotification("info", "Your microphone is muted.", 3000L);
            } else {
                checkMicVolume = false;
                ArchClient.getInstance().getVoiceChatManager().setTalking(true);
                ArchClient.getInstance().sendSound("voice_down");
            }
        } else if (this.isTalking && this.minecraft.inGameHasFocus && !KeyMappings.PUSH_TO_TALK.isKeyDown()) {
            this.isTalking = false;
            ArchClient.getInstance().getVoiceChatManager().setTalking(false);
            ArchClient.getInstance().sendSound("voice_up");
        }
    }
}
