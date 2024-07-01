package com.archclient.client.ui.module;

import com.archclient.client.module.AbstractModule;

import net.minecraft.client.gui.ScaledResolution;

/**
 * Deals with anchoring.
 * uses {@link ACPositionEnum}
 */
public class ACAnchorHelper {

    public static ACGuiAnchor getAnchor(float x, float y, ScaledResolution scaledResolution) {
        int scaledWidth = (int) scaledResolution.getScaledWidth();
        int scaledHeight = (int) scaledResolution.getScaledHeight();
        if (x < (float) (scaledWidth / 3) && y < (float) (scaledHeight / 3)) {
            return ACGuiAnchor.LEFT_TOP;
        }
        if (x > (float) (scaledWidth / 3 * 2) && y < (float) (scaledHeight / 3)) {
            return ACGuiAnchor.RIGHT_TOP;
        }
        if (y < (float) (scaledHeight / 3)) {
            return ACGuiAnchor.MIDDLE_TOP;
        }
        if (x < (float) (scaledWidth / 3) && y < (float) (scaledHeight / 3 * 2)) {
            return ACGuiAnchor.LEFT_MIDDLE;
        }
        if (x > (float) (scaledWidth / 3 * 2) && y < (float) (scaledHeight / 3 * 2)) {
            return ACGuiAnchor.RIGHT_MIDDLE;
        }
        if (y < (float) (scaledHeight / 3 * 2)) {
            return ACGuiAnchor.MIDDLE_MIDDLE;
        }
        if (x < (float) (scaledWidth / 3)) {
            return ACGuiAnchor.LEFT_BOTTOM;
        }
        if (x < (float) (scaledWidth / 3 * 2)) {
            if (x > (float) (scaledWidth / 3 + scaledWidth / 6)) {
                return ACGuiAnchor.MIDDLE_BOTTOM_RIGHT;
            }
            return ACGuiAnchor.MIDDLE_BOTTOM_LEFT;
        }
        return ACGuiAnchor.RIGHT_BOTTOM;
    }

    public static float[] getPositions(float x, float y, ScaledResolution scaledResolution) {
        float scaledWidth = scaledResolution.getScaledWidth();
        float scaledHeight = scaledResolution.getScaledHeight();
        ACGuiAnchor aCGuiAnchor = ACAnchorHelper.getAnchor(x, y, scaledResolution);
        float returnX = 0.0f;
        float returnY = 0.0f;
        switch (aCGuiAnchor) {
            case LEFT_TOP: {
                returnX = 0.0f;
                returnY = 0.0f;
                break;
            }
            case RIGHT_TOP: {
                returnX = scaledWidth / (float) 3 * 2.0f;
                returnY = 0.0f;
                break;
            }
            case MIDDLE_TOP: {
                returnX = scaledWidth / (float) 3;
                returnY = 0.0f;
                break;
            }
            case LEFT_MIDDLE: {
                returnX = 0.0f;
                returnY = scaledHeight / (float) 3;
                break;
            }
            case RIGHT_MIDDLE: {
                returnX = scaledWidth / (float) 3 * 2.0f;
                returnY = scaledHeight / (float) 3;
                break;
            }
            case MIDDLE_MIDDLE: {
                returnX = scaledWidth / (float) 3;
                returnY = scaledHeight / (float) 3;
                break;
            }
            case LEFT_BOTTOM: {
                returnX = 0.0f;
                returnY = scaledHeight / (float) 3 * 2.0f;
                break;
            }
            case MIDDLE_BOTTOM_RIGHT: {
                returnX = scaledWidth / (float) 3 + scaledWidth / (float) 6;
                returnY = scaledHeight / (float) 3 * 2.0f;
                break;
            }
            case MIDDLE_BOTTOM_LEFT: {
                returnX = scaledWidth / (float) 3;
                returnY = scaledHeight / (float) 3 * 2.0f;
                break;
            }
            case RIGHT_BOTTOM: {
                returnX = scaledWidth / (float) 3 * 2.0f;
                returnY = scaledHeight / (float) 3 * 2.0f;
            }
        }
        return new float[]{returnX, returnY};
    }

    public static float[] getPositions(ACGuiAnchor anchor, ScaledResolution resolution, float x, float y, float scale) {
        float returnX = 0.0f;
        float returnY = 0.0f;
        x *= scale;
        y *= scale;
        switch (anchor) {
            case LEFT_TOP: {
                returnX = 2.0f;
                returnY = 2.0f;
                break;
            }
            case LEFT_MIDDLE: {
                returnX = 2.0f;
                returnY = (float) (resolution.getScaledHeight() / 2) - y / 2.0f;
                break;
            }
            case LEFT_BOTTOM: {
                returnY = (float) resolution.getScaledHeight() - y - 2.0f;
                returnX = 2.0f;
                break;
            }
            case MIDDLE_TOP: {
                returnX = (float) (resolution.getScaledWidth() / 2) - x / 2.0f;
                returnY = 2.0f;
                break;
            }
            case MIDDLE_MIDDLE: {
                returnX = (float) (resolution.getScaledWidth() / 2) - x / 2.0f;
                returnY = (float) (resolution.getScaledHeight() / 2) - y / 2.0f;
                break;
            }
            case MIDDLE_BOTTOM_LEFT: {
                returnX = (float) (resolution.getScaledWidth() / 2) - x;
                returnY = (float) resolution.getScaledHeight() - y - 2.0f;
                break;
            }
            case MIDDLE_BOTTOM_RIGHT: {
                returnX = (float) resolution.getScaledWidth() / 2;
                returnY = (float) resolution.getScaledHeight() - y - 2.0f;
                break;
            }
            case RIGHT_TOP: {
                returnX = (float) resolution.getScaledWidth() - x - 2.0f;
                returnY = 2.0f;
                break;
            }
            case RIGHT_MIDDLE: {
                returnX = (float) resolution.getScaledWidth() - x;
                returnY = (float) (resolution.getScaledHeight() / 2) - y / 2.0f;
                break;
            }
            case RIGHT_BOTTOM: {
                returnX = (float) resolution.getScaledWidth() - x;
                returnY = (float) resolution.getScaledHeight() - y;
            }
        }
        return new float[]{returnX, returnY};
    }

    public static float[] getPositions(AbstractModule module, float x, float y, ScaledResolution scaledResolution) {
        float scaledWidth = scaledResolution.getScaledWidth();
        float scaledHeight = scaledResolution.getScaledHeight();
        ACGuiAnchor aCGuiAnchor = ACAnchorHelper.getAnchor(x, y, scaledResolution);
        float moduleWidth = module.getWidth() * module.scale.<Float>value();
        float moduleHeight = module.getHeight() * module.scale.<Float>value();
        float returnX = 0.0f;
        float returnY = 0.0f;
        switch (aCGuiAnchor) {
            case LEFT_TOP: {
                returnX = moduleWidth / 2.0f;
                returnY = moduleHeight / 2.0f;
                break;
            }
            case RIGHT_TOP: {
                returnX = scaledWidth / (float) 3 - moduleWidth / 2.0f;
                returnY = moduleHeight / 2.0f;
                break;
            }
            case MIDDLE_TOP: {
                returnX = scaledWidth / (float) 6;
                returnY = moduleHeight / 2.0f;
                break;
            }
            case LEFT_MIDDLE: {
                returnX = moduleWidth / 2.0f;
                returnY = scaledHeight / (float) 6;
                break;
            }
            case RIGHT_MIDDLE: {
                returnX = scaledWidth / (float) 3 - moduleWidth / 2.0f;
                returnY = scaledHeight / (float) 6;
                break;
            }
            case MIDDLE_MIDDLE: {
                returnX = scaledWidth / (float) 6;
                returnY = scaledHeight / (float) 6;
                break;
            }
            case LEFT_BOTTOM:
            case MIDDLE_BOTTOM_RIGHT: {
                returnX = moduleWidth / 2.0f;
                returnY = scaledHeight / (float) 3 - moduleHeight / 2.0f;
                break;
            }
            case MIDDLE_BOTTOM_LEFT: {
                returnX = scaledWidth / (float) 6 - moduleWidth / 2.0f;
                returnY = scaledHeight / (float) 3 - moduleHeight / 2.0f;
                break;
            }
            case RIGHT_BOTTOM: {
                returnX = scaledWidth / (float) 3 - moduleWidth / 2.0f;
                returnY = scaledHeight / (float) 3 - moduleHeight / 2.0f;
            }
        }
        return new float[]{returnX, returnY};
    }

    public static float[] getPositions(ACGuiAnchor aCGuiAnchor) {
        float f = 0.0f;
        float f2 = 0.0f;
        switch (aCGuiAnchor) {
            case RIGHT_MIDDLE: {
                f = -2;
                break;
            }
            case LEFT_BOTTOM: {
                f = 2.0f;
                f2 = -34;
                break;
            }
            case MIDDLE_MIDDLE: {
                f2 = -50;
                f = 0.0f;
            }
        }
        return new float[]{f, f2};
    }

    public static ACPositionEnum getVerticalPositionEnum(ACGuiAnchor aCGuiAnchor) {
        switch (aCGuiAnchor) {
            case LEFT_TOP:
            case RIGHT_TOP:
            case MIDDLE_TOP: {
                return ACPositionEnum.TOP;
            }
            case LEFT_MIDDLE:
            case RIGHT_MIDDLE:
            case MIDDLE_MIDDLE: {
                return ACPositionEnum.CENTER;
            }
            case LEFT_BOTTOM:
            case MIDDLE_BOTTOM_RIGHT:
            case MIDDLE_BOTTOM_LEFT:
            case RIGHT_BOTTOM: {
                return ACPositionEnum.BOTTOM;
            }
        }
        return null;
    }

    public static ACPositionEnum getHorizontalPositionEnum(ACGuiAnchor aCGuiAnchor) {
        switch (aCGuiAnchor) {
            case LEFT_TOP:
            case LEFT_MIDDLE:
            case LEFT_BOTTOM:
            case MIDDLE_BOTTOM_RIGHT: {
                return ACPositionEnum.LEFT;
            }
            case MIDDLE_TOP:
            case MIDDLE_MIDDLE: {
                return ACPositionEnum.CENTER;
            }
            case RIGHT_TOP:
            case RIGHT_MIDDLE:
            case MIDDLE_BOTTOM_LEFT:
            case RIGHT_BOTTOM: {
                return ACPositionEnum.RIGHT;
            }
        }
        return null;
    }
}
