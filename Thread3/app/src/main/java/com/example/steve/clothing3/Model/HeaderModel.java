package com.example.steve.clothing3.Model;

import android.app.Activity;

public class HeaderModel
{
    private int stringName;
    private int imageLocation;
    Activity activity;

    public HeaderModel(int stringName, int imageLocation, Activity activity) {
        this.stringName = stringName;
        this.imageLocation = imageLocation;
        this.activity = activity;
    }

    public String getStringName() {
        return activity.getString(stringName);
    }

    public int getImageLocation() {
        return imageLocation;
    }
}
