package com.archclient.common;

import com.archclient.bridge.ref.Ref;
import com.archclient.common.generic.Visitor;

import net.minecraft.client.settings.KeyBinding;

import java.util.ArrayList;
import java.util.List;

public class KeyMappings {
    private static final List<KeyBinding> MAPPINGS = new ArrayList<>();

    public static KeyBinding OPEN_MENU;
    public static KeyBinding OPEN_VOICE_MENU;
    public static KeyBinding PUSH_TO_TALK;
    public static KeyBinding DRAG_LOOK;
    public static KeyBinding HIDE_NAMES;

    public static void initialize() {
        MAPPINGS.add(OPEN_MENU = Ref.getInstanceCreator()
                .createKeyBinding("Open Menu", 54, "ArchClient Client"));
        MAPPINGS.add(OPEN_VOICE_MENU = Ref.getInstanceCreator()
                .createKeyBinding("Open Voice Menu", 25, "ArchClient Client"));
        MAPPINGS.add(PUSH_TO_TALK = Ref.getInstanceCreator()
                .createKeyBinding("Voice Chat", 47, "ArchClient Client"));
        MAPPINGS.add(DRAG_LOOK = Ref.getInstanceCreator()
                .createKeyBinding("Drag to look", 56, "ArchClient Client"));
        MAPPINGS.add(HIDE_NAMES = Ref.getInstanceCreator()
                .createKeyBinding("Hide name plates", 0, "ArchClient Client"));
    }

    public static void register(Visitor<KeyBinding> visitor) {
        for (KeyBinding mapping : MAPPINGS) {
            visitor.accept(mapping);
        }
    }
}
