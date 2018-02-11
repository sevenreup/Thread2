package com.example.steve.clothing3.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

/**
 * Created by SevenReup4ill on 2/7/2018.
 */

public class RandomColor
{
    public static int getRandomMaterialColor(String typeColor, Context context) {
        int returnColor = Color.GRAY;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }
}
