package com.example.steve.clothing3.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.steve.clothing3.Model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomeBoundService extends Service
{
    private IBinder myBinder = new Mybinder();

    public HomeBoundService()
    {}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;

    }

    public String getCurrentTime()
    {
        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US);
        return (dateformat.format(new Date()));
    }

    public String getSreing()
    {
        return "health";
    }
    public List<Item> dataReload(String url)
    {
        final List<Item> itemList = new ArrayList<>();
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray item_array;
                Item item;

                System.out.print(response);
                Log.d("response", response);

                try{
                    item_array = new JSONArray(response);
                    Log.d("tsg", "" + item_array.length());
                    for (int i = 0; i < item_array.length(); i++)
                    {
                        JSONObject object = item_array.getJSONObject(i);
                        item = new Item(object.getString("Item"), object.getString("Brands"), object.getString("Size"),
                                object.getInt("#"), object.getInt("Price"), object.getInt("Total_Rating"));
                        Log.d("item", item.getName());
                        System.out.print(item.getName());
                        System.out.print(i);
                        itemList.add(item);
                    }
                    Log.d("tsg", "" + itemList.size());
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("fuck this shit");
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
        return itemList;
    }

    public class Mybinder extends Binder
    {
        public HomeBoundService getService()
        {
            return HomeBoundService.this;
        }
    }
}
