package com.example.steve.clothing3.activity;

import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.steve.clothing3.Drawer.Fragment.NavigationDrawerFragment;
import com.example.steve.clothing3.Fragment.HomeTrendFragment;
import com.example.steve.clothing3.Fragment.NetworkError;
import com.example.steve.clothing3.R;
import com.example.steve.clothing3.helper.CollapsingToolBarHelper;
import com.example.steve.clothing3.network.NetworkChecker;

public class MainActivity extends AppCompatActivity
{

    private NavigationDrawerFragment mNavigationDrawerFragment;
    Fragment main, error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        new CollapsingToolBarHelper(this, getString(R.string.app_name));
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.left_drawer);
        mNavigationDrawerFragment.setUp(
                R.id.left_drawer,
                (DrawerLayout) findViewById(R.id.home), this, toolbar);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    void checkConnection()
    {
        main = new HomeTrendFragment();
        error = new NetworkError();
        NetworkChecker networkChecker = new NetworkChecker(this, main, error);
        networkChecker.checkConnection();
    }

    void swipeReload(SwipeRefreshLayout swipeRefreshLayout)
    {

    }

}

