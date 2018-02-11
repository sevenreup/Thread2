package com.example.steve.clothing3.helper;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.steve.clothing3.R;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SevenReup4ill on 12/24/2017.
 */

public class ContextMenuHelper implements OnMenuItemClickListener, OnMenuItemLongClickListener
{
    AppCompatActivity activity;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private List<MenuObject> list;

    public ContextMenuHelper(AppCompatActivity activity)
    {
        this.activity = activity;

    }

    public void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) activity.getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(list);
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }
    public void setMenu(List<MenuObject> item)
    {
        list = new ArrayList<>();
        list = item;
    }

    public ContextMenuDialogFragment getContextFrag()
    {
        return mMenuDialogFragment;
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(activity, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(activity, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }
}
