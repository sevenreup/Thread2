package com.example.steve.clothing3.infoProvide;

import com.example.steve.clothing3.R;
import com.yalantis.contextmenu.lib.MenuObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SevenReup4ill on 12/24/2017.
 */

public class MenuItems
{
    private List<MenuObject> list = new ArrayList<>();
    public List<MenuObject> getCategories()
    {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.ic_more_vert_black_24dp);

        MenuObject send = new MenuObject("Share");
        send.setResource(R.drawable.ic_share_tool);

        list.add(close);
        list.add(send);
        return list;
    }
}
