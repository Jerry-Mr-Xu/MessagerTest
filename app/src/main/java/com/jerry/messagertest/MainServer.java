package com.jerry.messagertest;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 服务端
 * <p>
 * Created by xujierui on 2018/2/28.
 */

public class MainServer extends Service {

    private static String TAG = MainServer.class.getSimpleName();
    private final Messenger messenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    Log.i(TAG, "handleMessage: " + msg.getData().getString("msg"));
                    break;
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
