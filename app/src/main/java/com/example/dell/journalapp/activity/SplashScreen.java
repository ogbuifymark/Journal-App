package com.example.dell.journalapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.dell.journalapp.R;
import com.example.dell.journalapp.Utils.Utility;

public class SplashScreen extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent mainIntent =null;
//                if (Utility.chechIfUserIsLoggedIn(getApplicationContext())) {
//                    mainIntent = new Intent(SplashScreen.this, MainActivity.class);
//                }else{
                    mainIntent = new Intent(SplashScreen.this, LoginActivity.class);


                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


}
