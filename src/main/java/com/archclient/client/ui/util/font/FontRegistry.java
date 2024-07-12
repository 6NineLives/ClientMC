package com.archclient.client.ui.util.font;

import com.archclient.bridge.ref.Ref;
import com.archclient.main.ArchClient;
import com.archclient.main.utils.Utility;

import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerFontRenderer;
import net.minecraft.util.ResourceLocation;

public class FontRegistry {
    private static int fixFontSize(int size, float scale) {
        if (scale - ((int) scale) == 0f) { // if scale == 1 (mc:2), == 2 (mc:4), == 3 (mc:6), and so on
            return size * (int) scale;
        }

        float scaledSize = size * scale;
        if (scaledSize - ((int) scaledSize) == 0) {
            if (scaledSize % 2 == 0) {
                return (int) scaledSize;
            }
        }

        int castedSize = (int) scaledSize - 1;
        float sizeRemaining = (size - 1) - castedSize;

        if (sizeRemaining >= 0.5f) {
            castedSize += 1;
        }

        if (castedSize % 2 != 0) {
            castedSize += 1;
        }

        return castedSize;
    }

    private static EaglerFontRenderer createNewFont(ResourceLocation font, int size, float scale) {
        int castedSize = fixFontSize(size, scale);
        ArchClient.LOGGER.info(Utility.fmt("{} {} -> {}", font.getResourcePath(), size, castedSize));
        EaglerFontRenderer instance = new EaglerFontRenderer(Ref.getMinecraft().gameSettings,
                font, Ref.getMinecraft().getTextureManager(), false, ArchClient.getInstance().globalSettings.followMinecraftScale.<Boolean>value() ? castedSize : size * scale);
        Ref.getMinecraft().getResourceManager().registerReloadListener(instance);
        return instance;
    }

    private static boolean validFont(EaglerFontRenderer font, int ogSize) {
        if (ArchClient.getInstance().globalSettings.followMinecraftScale.<Boolean>value()) {
            float scale = (Ref.getInstanceCreator().createScaledResolution().getScaleFactor() / 2f);
            return fixFontSize(ogSize, scale) == font.size;
        } else {
            return ogSize == font.size;
        }
    }

    private static EaglerFontRenderer getFont(String fontName, int ogSize) {
        float scale = (Ref.getInstanceCreator().createScaledResolution().getScaleFactor() / 2f);
        if (!ArchClient.getInstance().globalSettings.followMinecraftScale.<Boolean>value()) {
            scale = 1f;
        }

        switch (fontName) {
            case "Play-Regular":
                switch (ogSize) {
                    case 12:
                        if (!validFont(playRegular12px, ogSize)) {
                            playRegular12px = createNewFont(playRegular, ogSize, scale);
                        }
                        return playRegular12px;
                    case 14:
                        if (!validFont(playRegular14px, ogSize)) {
                            playRegular14px = createNewFont(playRegular, ogSize, scale);
                        }
                        return playRegular14px;
                    case 16:
                        if (!validFont(playRegular16px, ogSize)) {
                            playRegular16px = createNewFont(playRegular, ogSize, scale);
                        }
                        return playRegular16px;
                    case 18:
                        if (!validFont(playRegular18px, ogSize)) {
                            playRegular18px = createNewFont(playRegular, ogSize, scale);
                        }
                        return playRegular18px;
                    case 22:
                        if (!validFont(playRegular22px, ogSize)) {
                            playRegular22px = createNewFont(playRegular, ogSize, scale);
                        }
                        return playRegular22px;
                }
            break;
            case "Play-Bold":
                switch (ogSize) {
                    case 18:
                        if (!validFont(playBold18px, ogSize)) {
                            playBold18px = createNewFont(playBold, ogSize, scale);
                        }
                        return playBold18px;
                    case 22:
                        if (!validFont(playBold22px, ogSize)) {
                            playBold22px = createNewFont(playBold, ogSize, scale);
                        }
                        return playBold22px;
                }
            break;
            case "Roboto-Regular":
                switch (ogSize) {
                    case 13:
                        if (!validFont(robotoRegular13px, ogSize)) {
                            robotoRegular13px = createNewFont(robotoRegular, ogSize, scale);
                        }
                        return robotoRegular13px;
                    case 24:
                        if (!validFont(robotoRegular24px, ogSize)) {
                            robotoRegular24px = createNewFont(robotoRegular, ogSize, scale);
                        }
                        return robotoRegular24px;
                }
            break;
            case "Roboto-Bold":
                switch (ogSize) {
                    case 14:
                        if (!validFont(robotoBold14px, ogSize)) {
                            robotoBold14px = createNewFont(robotoBold, ogSize, scale);
                        }
                        return robotoBold14px;
                }
            break;
            case "Ubuntu-M":
                switch (ogSize) {
                    case 16:
                        if (!validFont(ubuntuMedium16px, ogSize)) {
                            ubuntuMedium16px = createNewFont(ubuntuMedium, ogSize, scale);
                        }
                        return ubuntuMedium16px;
                }
            break;
        }

        // This should never happen BUT in the off chance it does
        return new EaglerFontRenderer(Ref.getMinecraft().gameSettings,
                Ref.getInstanceCreator().createResourceLocation("null"), Ref.getMinecraft().getTextureManager(), false);
    }

    // Textures
    private static final ResourceLocation playRegular = Ref.getInstanceCreator().createResourceLocation("client/font/play/regular.png");
    private static final ResourceLocation playBold = Ref.getInstanceCreator().createResourceLocation("client/font/play/bold.png");
    private static final ResourceLocation robotoRegular = Ref.getInstanceCreator().createResourceLocation("client/font/roboto/regular.png");
    private static final ResourceLocation robotoBold = Ref.getInstanceCreator().createResourceLocation("client/font/roboto/bold.png");
    private static final ResourceLocation ubuntuMedium = Ref.getInstanceCreator().createResourceLocation("client/font/ubuntu/medium.png");

    // Font objects
    private static EaglerFontRenderer playRegular12px = createNewFont(playRegular, 12, 1f);
    private static EaglerFontRenderer playRegular14px = createNewFont(playRegular, 14, 1f);
    private static EaglerFontRenderer playRegular16px = createNewFont(playRegular, 16, 1f);
    private static EaglerFontRenderer playRegular18px = createNewFont(playRegular, 18, 1f);
    private static EaglerFontRenderer playRegular22px = createNewFont(playRegular, 22, 1f);
    private static EaglerFontRenderer playBold18px = createNewFont(playBold, 18, 1f);
    private static EaglerFontRenderer playBold22px = createNewFont(playBold, 22, 1f);
    private static EaglerFontRenderer robotoRegular13px = createNewFont(robotoRegular, 13, 1f);
    private static EaglerFontRenderer robotoRegular24px = createNewFont(robotoRegular, 24, 1f);
    private static EaglerFontRenderer robotoBold14px = createNewFont(robotoBold, 14, 1f);
    private static EaglerFontRenderer ubuntuMedium16px = createNewFont(ubuntuMedium, 16, 1f);

    // Getters
    public static EaglerFontRenderer getPlayRegular12px() {
        return getFont("Play-Regular", 12);
    }

    public static EaglerFontRenderer getPlayRegular14px() {
        return getFont("Play-Regular", 14);
    }

    public static EaglerFontRenderer getPlayRegular16px() {
        return getFont("Play-Regular", 16);
    }

    public static EaglerFontRenderer getPlayRegular18px() {
        return getFont("Play-Regular", 18);
    }

    public static EaglerFontRenderer getPlayRegular22px() {
        return getFont("Play-Regular", 22);
    }

    public static EaglerFontRenderer getPlayBold18px() {
        return getFont("Play-Bold", 18);
    }

    public static EaglerFontRenderer getPlayBold22px() {
        return getFont("Play-Bold", 22);
    }

    public static EaglerFontRenderer getRobotoRegular13px() {
        return getFont("Roboto-Regular", 13);
    }

    public static EaglerFontRenderer getRobotoRegular24px() {
        return getFont("Roboto-Regular", 24);
    }

    public static EaglerFontRenderer getRobotoBold14px() {
        return getFont("Roboto-Bold", 14);
    }

    public static EaglerFontRenderer getUbuntuMedium16px() {
        return getFont("Ubuntu-M", 16);
    }
}
