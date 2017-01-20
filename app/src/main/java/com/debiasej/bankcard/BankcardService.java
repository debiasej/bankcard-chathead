package com.debiasej.bankcard;

/**
 * Created by Mario de Biase on 19/1/17.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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

        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }
}
