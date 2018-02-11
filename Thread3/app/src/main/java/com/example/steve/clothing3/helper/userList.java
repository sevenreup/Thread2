package com.example.steve.clothing3.helper;

import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;

import com.example.steve.clothing3.R;
import com.example.steve.clothing3.activity.Home;
import com.example.steve.clothing3.database.DatabaseHandler;

import java.util.ArrayList;

/**
 * Created by SevenReup4ill on 12/19/2017.
 */

public class userList {

    public static ArrayList<String> array = new ArrayList<String>();
    Context context;
    Activity activity;
    String word;

    public userList(Context context, Activity activity)
    {
        this.context = context;
        this.activity = activity;
    }
    public void select(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.wishlist){
            Home.selected = "get_wishes";
            word = "get_wishes";
            get_entries("");
            System.out.println("well this seems to be working too");
        }else if(id == R.id.cart){
            Home.selected = "get_cart";
            word = "get_cart";
            get_entries(word);
        }
    }
    public void get_entries(String word)
    {
        System.out.println(word);
        DatabaseHandler db = new DatabaseHandler(context);
        db.getWish("General");
    }
}
