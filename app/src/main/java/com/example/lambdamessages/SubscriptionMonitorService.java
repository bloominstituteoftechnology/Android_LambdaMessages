package com.example.lambdamessages;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.ArrayList;

public class SubscriptionMonitorService extends Service {
    private static final int CHECK_PERIOD = 15000;
    Long lastCheckTime;
    String subscription;
    Context context;

    public SubscriptionMonitorService() {
    }

    @Override
    public void onCreate() {
        lastCheckTime = System.currentTimeMillis()/1000;
        subscription = "";
        context = this;
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
                    MessageBoard newMessageBoard = MessageBoardDao.getAMessageBoard(subscription);
                    long lasttime = (long)newMessageBoard.getLastMessageTime();
                    if (lasttime > (lastCheckTime)) {
                        Log.i ("ServiceLog", "New result ready");
                        sendNotification();
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

    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("New Message Available")
                .setContentText("New Message in Your Subscribed Thread")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(123, builder.build());
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
