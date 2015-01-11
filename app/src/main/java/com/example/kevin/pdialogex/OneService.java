package com.example.kevin.pdialogex;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class OneService extends Service {
    public OneService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PdThread thread = new PdThread();
        thread.start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    class PdThread extends Thread {
        public void run() {
            while (true) {
                try {
                    Intent inte = new Intent(getApplicationContext(),DialogActivity.class);
                    inte.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|
                    Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(inte);
                    Log.i("YJ","쓰레드는 도는가!");
                    Thread.sleep(30000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
