package com.example.kevin.pdialogex;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;


public class DialogActivity extends Activity {
    private KeyguardManager km = null;
    private KeyguardManager.KeyguardLock keylock = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        PushWakeLock.acquireCpuWakeLock(this);
        km=(KeyguardManager) this.getSystemService(Activity.KEYGUARD_SERVICE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
//        this.setFinishOnTouchOutside(false);
        if(km!=null){
            keylock = km.newKeyguardLock("test");
            keylock.disableKeyguard();
        }
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button closeButton = (Button) findViewById(R.id.btn_close);
        closeButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.i("yj","닫기누름");
                if(keylock!=null){
                    keylock.reenableKeyguard();
                }
                finish();
            }
        });
        Button okayButton=(Button) findViewById(R.id.btn_gogo);
        okayButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.i("yj","보기 누름");
                if(keylock!=null){
                    keylock.reenableKeyguard();
                }
                finish();
            }
        });



//        PushWakeLock.releaseCpuLock();
//        setScreenOffTimer();
//        setDialogFinishTimer();
        Log.i("YJ", "DIalog Activity 실행(onCreate)");
    }
//
//    @Override
//    protected void onRestart() {
//        if(keylock!=null){
//            keylock.reenableKeyguard();
//        }
//        Log.i("yj","여기서 넌 죽게 되어있지");
//        finish();
//        super.onRestart();
//
////        PushWakeLock.releaseCpuLock();
//    }

//boolean mFocusDuringOnPause;
//    @Override
//    protected void onPause() {
////        Log.i("yj","스탑전");
//        super.onPause();
////        finish();
//       mFocusDuringOnPause = hasWindowFocus();
////        Log.i("yj","스탑후");
//    }

    @Override
    protected void onStop() {
        super.onStop();
        if(hasWindowFocus()){ Log.i("yj","여기 걸리면 완성!");
        finish();
        }
        else{}
    }


    //

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.i("YJ","Dialog OnResume");
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        PushWakeLock.releaseCpuLock();
//        Log.i("yj","WKL끝남!");
//    }

    private void setDialogFinishTimer() {
        int finishTime = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,1000);
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                Log.i("yj","다이어로그 사라짐");
                finish();
                if(keylock!=null){
                    keylock.reenableKeyguard();
                }
            }
        };

        Timer timer2 = new Timer();
        timer2.schedule(task2, finishTime);
    }

    private void setScreenOffTimer() {
        int defTimeOut = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,1000);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                finish();
                PushWakeLock.releaseCpuLock();
                Log.i("yj","wake락 헤제");
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, defTimeOut);

    }


}
