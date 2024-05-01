package com.example.psychomeclick.helpers;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import com.example.psychomeclick.R;

import java.util.Random;

public class NotificationService extends Service {
    private static final String CHANNEL_ID = "NotificationChannel";
    private static final int NOTIFICATION_ID = 1001;
    private static final String NOTIFICATION_ACTION = "com.example.psychomeclick.NOTIFICATION_PUBLISH"; //for limiting broadcast



    private static Random random = new Random();
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private BroadcastReceiver bootReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        startNotificationScheduler();
       // registerBootReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBootReceiver();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startNotificationScheduler() {
        Intent intent = new Intent(this, NotificationPublisher.class);
        intent.setAction(NOTIFICATION_ACTION);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        // Generate a random delay between min and max interval (in milliseconds)
        long minInterval = 60 * 60 * 1000; // 1 hour minimum interval (adjust as needed)
        long maxInterval = 8 * 60 * 60 * 1000; // 8 hours maximum interval (adjust as needed)
        long randomDelay = (long) (random.nextDouble() * (maxInterval - minInterval)) + minInterval;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), randomDelay, pendingIntent);
    }

    private void registerBootReceiver() {
        bootReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
                    startNotificationScheduler();
                }
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
        registerReceiver(bootReceiver, filter);
    }

    private void unregisterBootReceiver() {
        if (bootReceiver != null) {
            unregisterReceiver(bootReceiver);
            bootReceiver = null;
        }
    }

    public static class NotificationPublisher extends BroadcastReceiver {
        private String[] notificationMessages = {
                "Notification 1",
                "Notification 2",
                "Notification 3"
                // Add more messages as needed
        };
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = notificationMessages[random.nextInt(notificationMessages.length)];
            showNotification(context, message);
        }

        private void showNotification(Context context, String message) {
            createNotificationChannel(context);

            Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Random Notification")
                    .setContentText(message);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }

        private void createNotificationChannel(Context context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "Notification Channel";
                String description = "Channel for notifications";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);

                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        }
    }


}
