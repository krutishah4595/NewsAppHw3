package com.example.assignment.hw2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


public class NotificationUtility {

    Context mContext;
    CharSequence mTitle;
    NotificationChannel channel;
    Notification notification;

    public NotificationUtility(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotification(String title, String message, Intent intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mTitle = "NewsStory Update notification";
            String mDesc = "News updating notification...";
            channel = new NotificationChannel("StoryUpdate", mTitle, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(mDesc);
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        PendingIntent mPendingIntent = PendingIntent.getService(
                mContext,
                0,
                intent,
                0
        );

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "StoryUpdate");

        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title).setWhen(0)
                .setAutoCancel(false)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_updatedata)
                .setContentText(message)
                .addAction(R.drawable.ic_cancel_, "Cancel News Update", mPendingIntent)
                .build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT;

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(309, notification);
    }

    public void removeNoti() {
        NotificationManagerCompat.from(mContext).cancel(309);
    }
}
