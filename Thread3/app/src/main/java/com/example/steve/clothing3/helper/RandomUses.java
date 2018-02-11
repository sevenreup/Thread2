package com.example.steve.clothing3.helper;

import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by SevenReup4ill on 1/30/2018.
 */

public class RandomUses
{
    public static int dpToPx(int dp, Activity activity) {
        Resources r = activity.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
