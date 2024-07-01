package com.archclient.main.utils;

import com.archclient.main.identification.MinecraftVersion;

public class Utility {
    public static String fmt(String format, Object... args) {
        for (int x = 0; x < args.length; x++) {
            String str = args[x].toString();
            format = format.replaceFirst("\\{}", str);
            format = format.replaceAll("\\{" + x + "}", str);
        }
        return format;
    }

    public static long clamp(long value, long min, long max) {
        return value < min ? min : Math.min(value, max);
    }

    public static String getFullTitle(MinecraftVersion minecraftVersion) {
        return fmt("ArchClient {}",
                minecraftVersion
        );
    }
}
