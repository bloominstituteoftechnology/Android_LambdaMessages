package com.example.lambdamessages;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class SubscriptionMonitorService extends Service {
    private static final int CHECK_PERIOD = 15000;
    Long lastCheckTime;
    String subscription;

    public SubscriptionMonitorService() {
    }

    @Override
    public void onCreate() {
        lastCheckTime = System.currentTimeMillis()/1000;
        subscription = "";
        super.onCreate();
        Log.i("ServiceLog", "onCreate entered");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ServiceLog", "onStart entered");
        subscription = intent.getStringExtra("MESSAGE_BOARD_ID");

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (subscription != "") {
                    //ArrayList<MessageBoard> boardList = MessageBoardDao.getMessageBoards();

                    MessageBoard newMessageBoard = MessageBoardDao.getAMessageBoard(subscription);
                    long lasttime = (long)newMessageBoard.getLastMessageTime();
                    if (lasttime > (lastCheckTime)) {
                        Log.i ("ServiceLog", "New result ready");
                        lastCheckTime = (System.currentTimeMillis()/1000);
                    }

                    try {
                        Thread.sleep(CHECK_PERIOD);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
            }

        }).start();
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
