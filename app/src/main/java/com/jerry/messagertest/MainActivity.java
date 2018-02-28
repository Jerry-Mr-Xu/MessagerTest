package com.jerry.messagertest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 客户端
 */
public class MainActivity extends AppCompatActivity {
    private Messenger messenger = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSendMessage = (Button) findViewById(R.id.btn_send_message);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messenger != null) {
                    Message newMessage = Message.obtain();
                    newMessage.what = 100;

                    Bundle data = new Bundle();
                    data.putString("msg", "Hello this is client");
                    newMessage.setData(data);
                    try {
                        messenger.send(newMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Intent intent = new Intent(this, MainServer.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
        }
    };

    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }
}
