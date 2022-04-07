package com.example.crop_crony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
{
    String UserName, UserRole;
    SessionManager SESSION;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        SESSION = new SessionManager(getApplicationContext());

        CropCronyDatabase DB = new CropCronyDatabase(LoginActivity.this);

        EditText ET = (EditText) findViewById(R.id.loginEditText1);
        EditText ET2 = (EditText) findViewById(R.id.loginEditText2);
        Button Btn = (Button) findViewById(R.id.loginButton);

        Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String Username = ET.getText().toString();
                String Password = ET2.getText().toString();
                if (Username.trim().length() > 0 && Password.trim().length() > 0)
                {
                    Cursor Result = DB.Login(Username, Password);
                    if(Result.getCount() == 0)
                    {
                        Toast.makeText(LoginActivity.this, "Wrong Username or Password!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        while (Result.moveToNext())
                        {
                            UserName = Result.getString(Result.getColumnIndex("Username"));
                            UserRole = Result.getString(Result.getColumnIndex("UserRole"));
                        }

                        SESSION.createLoginSession(UserName, UserRole);

                        if (UserRole.equals("Admin"))
                        {
                            startActivity(new Intent(LoginActivity.this, SellerMainActivity.class));
                            finish();
                        }
                        if (UserRole.equals("Buyer"))
                        {
                            startActivity(new Intent(LoginActivity.this, BuyerMainActivity.class));
                            finish();
                        }
                        if (UserRole.equals("Seller"))
                        {
                            startActivity(new Intent(LoginActivity.this, SellerMainActivity.class));
                            finish();
                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Please enter Username and Password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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