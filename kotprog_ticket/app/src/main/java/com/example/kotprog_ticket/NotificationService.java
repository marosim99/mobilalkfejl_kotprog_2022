package com.example.kotprog_ticket;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationService {
    private NotificationManager mManager;
    private Context mContext;

    public NotificationService(Context mContext) {
        this.mContext = mContext;
        this.mManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        CreateChannel();
    }

    private void CreateChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;

        NotificationChannel channel = new NotificationChannel
                ("main_channel", "MainChannel", NotificationManager.IMPORTANCE_HIGH);

        channel.enableVibration(true);

        mManager.createNotificationChannel(channel);
    }

    public void SendNotification(String message, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "main_channel")
                .setContentTitle("Ne hagyj itt! :(")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_baseline_shopping_basket_24)
                .setContentIntent(pendingIntent);

        mManager.notify(0, builder.build());
    }
}
