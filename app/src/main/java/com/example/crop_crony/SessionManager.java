package com.example.crop_crony;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.HashMap;

public class SessionManager
{
    SharedPreferences Pref;
    SharedPreferences.Editor SP;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String SHARED_PREF = "CropCronyPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String USER_NAME = "UserName";
    public static final String USER_ROLE = "UserRole";

    public SessionManager(Context context)
    {
        this.context = context;
        Pref = context.getSharedPreferences(SHARED_PREF, PRIVATE_MODE);
        SP = Pref.edit();
    }

    public void createLoginSession(String userName, String userRole)
    {
        SP.putBoolean(IS_LOGIN, true);
        SP.putString(USER_NAME, userName);
        SP.putString(USER_ROLE, userRole);
        SP.commit();
    }

    public HashMap<String, String> getUserDetails()
    {
        HashMap<String, String> USER = new HashMap<String, String>();
        USER.put(USER_NAME, Pref.getString(USER_NAME, null));
        USER.put(USER_ROLE, Pref.getString(USER_ROLE, null));
        return USER;
    }

    public boolean isLoggedIn()
    {
        return Pref.getBoolean(IS_LOGIN, false);
    }

    public void checkLogin()
    {
        if (!this.isLoggedIn())
        {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public void logoutUser()
    {
        SP.clear();
        SP.commit();
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
