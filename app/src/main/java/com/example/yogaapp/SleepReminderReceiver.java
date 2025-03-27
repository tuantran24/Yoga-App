package com.example.yogaapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class SleepReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "sleep_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Tạo kênh thông báo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Nhắc giờ ngủ",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) manager.createNotificationChannel(channel);
        }

        Intent openIntent = new Intent(context, sleepsetting.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, openIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_sleep)
                .setContentTitle("🛌 Giờ đi ngủ rồi!")
                .setContentText("Thư giãn và nghỉ ngơi nhé 😴")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notifyMgr != null) {
            notifyMgr.notify(2024, builder.build());
        }
    }
}
