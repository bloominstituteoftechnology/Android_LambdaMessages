package com.example.lambdamessages;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SubscriptionMonitorService extends Service {
    public SubscriptionMonitorService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("ServiceLog", "onCreate entered");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ServiceLog", "onStart entered");
        String boardId = intent.getStringExtra("MESSAGE_BOARD_ID");

        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("ServiceLog", "onDestroy entered");

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i("ServiceLog", "onBind entered");

        return null;
    }
}
