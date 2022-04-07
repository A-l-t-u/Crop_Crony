package com.example.crop_crony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.HashMap;

public class SplashScreenActivity extends AppCompatActivity
{
    SessionManager SESSION;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        SESSION = new SessionManager(getApplicationContext());
        SESSION.isLoggedIn();
        HashMap<String, String> USER = SESSION.getUserDetails();
        String UserRole = USER.get(SessionManager.USER_ROLE);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (SESSION.isLoggedIn() == false)
                {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
                else
                {
                    if (UserRole.equals("Admin"))
                    {
                        startActivity(new Intent(SplashScreenActivity.this, AdminMainActivity.class));
                    }
                    if (UserRole.equals("Buyer"))
                    {
                        startActivity(new Intent(SplashScreenActivity.this, BuyerMainActivity.class));
                    }
                    if (UserRole.equals("Seller"))
                    {
                        startActivity(new Intent(SplashScreenActivity.this, SellerMainActivity.class));
                    }
                }
            }
        }, 1000);
    }

    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}