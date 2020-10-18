package com.zohotask.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

public class SplashScreen extends Activity {
    public final int SPLASH_DISPLAY_LENGTH = 2500;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  Intent intent = new Intent(SplashScreen.this, CountryListParentActivity.class);
                  startActivity(intent);
                  finish();
              }
          }, SPLASH_DISPLAY_LENGTH);
    }
}
