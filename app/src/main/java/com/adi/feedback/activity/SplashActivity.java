package com.adi.feedback.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.adi.feedback.R;
import com.adi.feedback.util.SessionManager;

/**
 * Created by root on 2/9/16.
 */
public class SplashActivity extends Activity {

    private Context mContext;
    private Handler handler;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager=new SessionManager(this);
        mContext = this;
        handler = new Handler();

        startHandler();
    }

    private void startHandler() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sessionManager.getBooleanData("isLoggedIn")) {
                    Intent i = new Intent(mContext, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(mContext, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 500);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if(handler != null){
            handler.removeCallbacksAndMessages(null);
        }
        finish();
    }

}
