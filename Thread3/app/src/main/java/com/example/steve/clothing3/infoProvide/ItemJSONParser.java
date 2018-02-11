package com.example.steve.clothing3.infoProvide;

import android.util.Log;

import com.example.steve.clothing3.Model.Item;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SevenReup4ill on 1/21/2018.
 */

public class ItemJSONParser
{
    static List<Item> itemList;

    public static List<Item> parseJson(String content)
    {
        Log.d("fuck", content);
        JSONArray item_array = null;
        Item item = null;
        try{
            item_array = new JSONArray(content);
            itemList = new ArrayList<>();
            for (int i = 0; i < item_array.length(); i++) {

                JSONObject obj = item_array.getJSONObject(i);
                System.out.print(obj.getString("Item"));
                item = new Item(obj.getString("Item"), obj.getString("Size"),
                        obj.getString("Shop_ID"), obj.getInt("#"), obj.getInt("Total_Rating"), obj.getInt("Price"));
                itemList.add(item);
            }
            return itemList;
        } catch (JSONException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
