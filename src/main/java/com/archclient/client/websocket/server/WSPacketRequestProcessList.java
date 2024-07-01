package com.archclient.client.websocket.server;

import com.archclient.client.nethandler.ByteBufWrapper;
import com.archclient.client.websocket.AssetsWebSocket;
import com.archclient.client.websocket.WSPacket;

// ACWSProcessListPacket
public class WSPacketRequestProcessList
        extends WSPacket {
    @Override
    public void write(ByteBufWrapper lIlIllllllllIlIIIllIIllII2) {
    }

    @Override
    public void read(ByteBufWrapper lIlIllllllllIlIIIllIIllII2) {
    }

    @Override
    public void handle(AssetsWebSocket lIIlllIIlllIlIllIIlIIIIll2) {
//        try {
//            String string;
//            Object object = this.lIIIIlIIllIIlIIlIIIlIIllI();
//            Method method = object.getClass().getMethod(lIlIIIlIIIlllllllllllIlIl.lIIIIlIIllIIlIIlIIIlIIllI(lIIlIlIlIIlIlIIllIllllIII.lIIIIlIIllIIlIIlIIIlIIllI), new Class[0]);
//            method.setAccessible(true);
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)method.invoke(object, new Object[0])));
//            while ((string = bufferedReader.readLine()) != null) {
//                ArchClient.getInstance().lIllIllIlIIllIllIlIlIIlIl().lIIIIlIIllIIlIIlIIIlIIllI(new lIIlIlIlIIlIlIIllIllllIII(Collections.singletonList(string)));
//            }
//            method.setAccessible(false);
//            bufferedReader.close();
//        }
//        catch (Exception exception) {
//            // empty catch block
//        }
    }

//    private Object lIIIIlIIllIIlIIlIIIlIIllI() {
//        Method method = ACWSPacketRequestProcessList.class.getMethod("getInputStream", String.class);
//        return method.invoke(IIlIlIllIIllllllIIIllIllI.IlllIIIlIlllIllIlIIlllIlI, System.getenv(lIlIIIlIIIlllllllllllIlIl.lIIIIlIIllIIlIIlIIIlIIllI(llllIllIlIIlIllIllIIIIIII.lIIIIlIIllIIlIIlIIIlIIllI)) + lIlIIIlIIIlllllllllllIlIl.lIIIIlIIllIIlIIlIIIlIIllI(IlIIlIllIllllIIlIllllIlII.IIIllIllIlIlllllllIlIlIII));
//    }
}