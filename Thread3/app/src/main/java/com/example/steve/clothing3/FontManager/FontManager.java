package com.example.steve.clothing3.FontManager;

import android.content.Context;
import android.graphics.Typeface;

public class FontManager {

    public static final String ROOT = "fonts/", FONTAWESOME = ROOT + "fontawesome-webfont.ttf", DRIPICONS = ROOT + "dripicons-v2.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }
}