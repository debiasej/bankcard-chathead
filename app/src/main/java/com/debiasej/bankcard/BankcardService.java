package com.debiasej.bankcard;

/**
 * Created by Mario de Biase on 19/1/17.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.view.WindowManager;


public class BankcardService extends Service {

    private WindowManager windowManager;
    private BankcardServicePresenter presenter;

    @Override
    public void onCreate() {

        // Get references to all the views and add them to root view as needed.
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        BankcardServiceManager serviceManager = new BankcardServiceManagerImpl(windowManager);
        presenter = new BankcardServicePresenter(serviceManager);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getBooleanExtra("stop_service", false)){
            // If it's a call from the notification tray, stop the service.
            stopSelf();
        }else{
            // Make the service run in foreground so that the system does not shut it down.
            NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            Intent notificationIntent = new Intent(this, BankcardService.class);
            notificationIntent.putExtra("stop_service", true);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, notificationIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    this);
            Notification notification = builder.setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("Bankcard Chathead launched")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentTitle("Bankcard Chathead")
                    .setContentText("Tap to remove the Bankcard Chathead").build();
            mNM.notify(86, notification);
        }

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
