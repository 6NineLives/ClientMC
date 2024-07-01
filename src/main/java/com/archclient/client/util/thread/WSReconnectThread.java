package com.archclient.client.util.thread;

import com.archclient.main.ArchClient;

public class WSReconnectThread extends Thread {
    private final long delay = 10000L;

    @Override
    public void run() {
        try {
            if (!interrupted()) {
                Thread.sleep(this.delay);
                System.out.println("[AC WS] Attempting reconnect.");
                ArchClient.getInstance().connectToAssetsServer();
            }
            if (ArchClient.getInstance().getWebsocket().isOpen()) {
                interrupt();
            }
        }
        catch (InterruptedException exception) {
            System.out.println("[AC WS] [ERR] " + exception.getMessage());
        }
    }

}
