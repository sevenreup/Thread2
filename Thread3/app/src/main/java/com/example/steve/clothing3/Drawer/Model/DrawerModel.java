package com.example.steve.clothing3.Drawer.Model;

import java.util.List;

public class DrawerModel
{
    public static final int TEXT_TYPE=1;
    public static final int EXPAND_TYPE=2;
    public static final int HEADER_TYPE=0;
    public static final int FOOTER_TYPE=3;

    public int type;
    public int data;
    public String text;
    public List<String> list;



    public DrawerModel(int type, String text, int data, List<String> list)
    {
        this.type=type;
        this.data=data;
        this.text=text;
        this.list=list;

    }
}
