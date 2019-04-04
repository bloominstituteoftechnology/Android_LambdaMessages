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
    boolean alreadySubbed;
    Context context;
    ArrayList<String> subList;

    public SubscriptionMonitorService() {
    }

    @Override
    public void onCreate() {
        lastCheckTime = System.currentTimeMillis() / 1000;
        subscription = "";
        context = this;
        subList = new ArrayList<>();
        super.onCreate();
        Log.i("ServiceLog", "onCreate entered");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ServiceLog", "onStart entered");
        subscription = intent.getStringExtra("MESSAGE_BOARD_ID");
        alreadySubbed = intent.getBooleanExtra("ALREADY_SUBBED", false);
        if (!alreadySubbed) {
            if (!subList.contains(subscription)) {
                subList.add(subscription); //adds subscription to sublist if it's not already in there.
            }
        }
        else { //removing subscription
            subList.remove(subscription);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (subscription != "") {
                    for (String name : subList) {
                        MessageBoard newMessageBoard = MessageBoardDao.getAMessageBoard(name);
                        long lasttime = (long) newMessageBoard.getLastMessageTime();
                        if (lasttime > (lastCheckTime)) {
                            Log.i("ServiceLog", "New result ready");
                            sendNotification(newMessageBoard.getTitle(), newMessageBoard.getLastMessageText(), newMessageBoard.getLastMessageSender());
                            lastCheckTime = (System.currentTimeMillis() / 1000);
                        }
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

    private void sendNotification(String title, String text, String sender) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("New Message in " + title)
                .setContentText(sender + ": " + text)
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
