package com.archclient.main.utils;

import com.archclient.main.ArchClient;

public class Callbacks {
    public interface ReturnCallback<T> {
        T run();
    }

    public interface ConnectionCallback {
        void run();
    }

    public static <T> void trySetValue(T value, String valueVisualName, ReturnCallback<T> setCallback) {
        try {
            value = setCallback.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (value == null) {
            ArchClient.LOGGER.error("Failed to set value of " + valueVisualName + ".");
        }
    }
}
