package com.example.steve.clothing3.Drawer.Helper;

import android.view.View;

import net.cachapa.expandablelayout.ExpandableLayout;

/**
 * Created by SevenReup4ill on 1/17/2018.
 */

public class CollapseAnimation implements ExpandableLayout.OnExpansionUpdateListener, View.OnClickListener
{
    private ExpandableLayout layout;

    private View res;

    public  CollapseAnimation(ExpandableLayout layout, View res)
    {
        this.layout = layout;
        layout.setOnExpansionUpdateListener(this);
        this.res = res;

    }
    @Override
    public void onExpansionUpdate(float expansionFraction, int state)
    {
        res.setRotation(expansionFraction * 90);
    }
    @Override
    public void onClick(View view)
    {

    }
}
