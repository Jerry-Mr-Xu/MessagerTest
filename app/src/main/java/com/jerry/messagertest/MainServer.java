package com.jerry.messagertest;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 服务端
 * <p>
 * Created by xujierui on 2018/2/28.
 */

public class MainServer extends Service {

    private static String TAG = MainServer.class.getSimpleName();
    private final Messenger server = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    Log.i(TAG, "handleMessage: " + msg.getData().getString("msg"));
                    sendMessage2Client(msg.replyTo);
                    break;
            }
        }
    }

    private static void sendMessage2Client(Messenger client) {
        if (client == null) {
            return;
        }

        Message msg = Message.obtain(null, 100);
        Bundle data = new Bundle();
        data.putString("msg", "Hello this is server, I received the message.");
        msg.setData(data);
        try {
            client.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return server.getBinder();
    }
}
