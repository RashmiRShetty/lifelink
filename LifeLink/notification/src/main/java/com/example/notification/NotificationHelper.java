package com.example.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public final class NotificationHelper {
	public static final String DEFAULT_CHANNEL_ID = "lifelink_default";

	private NotificationHelper() {}

	public static void ensureDefaultChannel(Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
			if (notificationManager != null) {
				NotificationChannel channel = new NotificationChannel(
						DEFAULT_CHANNEL_ID,
						context.getString(R.string.notification_channel_default_name),
						NotificationManager.IMPORTANCE_DEFAULT
				);
				channel.setDescription(context.getString(R.string.notification_channel_default_description));
				notificationManager.createNotificationChannel(channel);
			}
		}
	}

	public static Notification buildNotification(
			Context context,
			String title,
			String text,
			int smallIconResId,
			PendingIntent contentIntent
	) {
		ensureDefaultChannel(context);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID)
				.setSmallIcon(smallIconResId)
				.setContentTitle(title)
				.setContentText(text)
				.setAutoCancel(true)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT);
		if (contentIntent != null) {
			builder.setContentIntent(contentIntent);
		}
		return builder.build();
	}
}


