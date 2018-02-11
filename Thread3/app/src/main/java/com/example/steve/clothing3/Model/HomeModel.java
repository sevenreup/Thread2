package com.example.steve.clothing3.Model;

import java.util.List;

/**
 * Created by SevenReup4ill on 1/25/2018.
 */

public class HomeModel
{
    public static final int TOP_TYPE=1;
    public static final int TRENDING_TYPE=2;

    public int type;
    public List<String> list;



    public HomeModel(int type, List<String> list)
    {
        this.type=type;
        this.list=list;

    }
}
