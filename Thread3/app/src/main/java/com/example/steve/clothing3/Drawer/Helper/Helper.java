package com.example.steve.clothing3.Drawer.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.steve.clothing3.R;
import com.example.steve.clothing3.activity.Home;
import com.example.steve.clothing3.activity.MainActivity;
import com.example.steve.clothing3.activity.SettingsActivity;


public class Helper
{
    Activity activity;
    String name;
    Context context;

    public Helper(Activity activity, String name, Context context)
    {
        this.activity = activity;
        this.name = name;
        this.context = context;
        intentHelper();
    }

    private void intentHelper()
    {

        if (name.equals(context.getResources().getString(R.string.drawer_home)))
        {
            if(activity.getClass() != MainActivity.class)
            {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
            }
        } else if (name.equals(context.getResources().getString(R.string.drawer_shops)))
        {
            Home.selected="Shops";
            Intent intent = new Intent(activity, Home.class);
            activity.startActivity(intent);
        }else if (name.equals(context.getResources().getString(R.string.drawer_designers)))
        {
            Home.selected="nyalisa";
            Intent intent = new Intent(activity,Home.class);
            activity.startActivity(intent);
        }
    }

}
