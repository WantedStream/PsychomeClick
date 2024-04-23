package com.example.psychomeclick.helpers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.psychomeclick.R;

import java.sql.SQLOutput;

import io.grpc.internal.JsonUtil;

public class NotificationService extends Service {

    private static final String TAG = "NotificationService";
    private static final String CHANNEL_ID = "notification_channel";
    private static final int NOTIFICATION_ID = 101;
    public static final String[] messages = {"dont forget to learn english!", "a question a day gives one wrong answer away!", "talk to psychoBot!", "reminder-your grade depends on your studies"}; // Replace with your messages

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        scheduleRandomNotification();
        return START_NOT_STICKY;
    }

    private void scheduleRandomNotification() {
        long minDelay = 60 * 1000; // Minimum delay (1 minute)
        long maxDelay = 60 * 60 * 1000; // Maximum delay (1 hour)

        // Generate random delay within the specified range
        long delay = (long) (Math.random() * (maxDelay - minDelay)) + minDelay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), delay, pendingIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public static class NotificationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            int randomIndex = (int) (Math.random() * messages.length);
            String message = messages[randomIndex];
            createNotification(message,context);
        }

        private static void createNotification(String message,Context serviceContext) {
            NotificationManager notificationManager = (NotificationManager) serviceContext.getSystemService(NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            Notification.Builder builder = new Notification.Builder(serviceContext, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher) // Replace with your icon resource
                    .setContentTitle("hi!")
                    .setContentText(message)
                    .setAutoCancel(true);

            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }
}