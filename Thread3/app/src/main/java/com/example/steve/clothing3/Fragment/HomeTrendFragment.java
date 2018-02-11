package com.example.steve.clothing3.Fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.steve.clothing3.Model.HeaderModel;
import com.example.steve.clothing3.Model.Item;
import com.example.steve.clothing3.R;
import com.example.steve.clothing3.adapter.HomeAdapter;
import com.example.steve.clothing3.helper.Constants;
import com.example.steve.clothing3.services.HomeBoundService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeTrendFragment extends Fragment {

    HomeAdapter adapter, adapter1;
    List<HeaderModel> headerModelList;
    LinearLayoutManager layoutManager, layoutManager1;

    HomeBoundService homeService;
    boolean isBound = false;

    public HomeTrendFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onResume()
    {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_home_trend, container, false);
        Intent intent = new Intent(getActivity(), HomeBoundService.class);
        getActivity().bindService(intent, myConnection, Context.BIND_AUTO_CREATE);

       //getData();
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.trendrecycler);
        adapter = new HomeAdapter(dataReload("http://192.168.137.1/clothing/?rate"), Constants.TRENDING, null);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        /**getHeader();
        RecyclerView recyclerView1 = (RecyclerView) rootView.findViewById(R.id.headerrecycler);
        layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        adapter1 = new HomeAdapter(null, Constants.HEADER, headerModelList);
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.setAdapter(adapter1);*/
        return rootView;
    }

    private void getHeader()
    {
        headerModelList = new ArrayList<>();
        headerModelList.add(new HeaderModel(R.string.drawer_cat_kids, R.drawable.kids, getActivity()));
        headerModelList.add(new HeaderModel(R.string.drawer_cat_men, R.drawable.men, getActivity()));
        headerModelList.add(new HeaderModel(R.string.drawer_cat_women, R.drawable.women, getActivity()));
        headerModelList.add(new HeaderModel(R.string.drawer_shops, R.drawable.women, getActivity()));
    }

    private List<Item> dataReload(String url)
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
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
        return itemList;
    }

    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            HomeBoundService.Mybinder mybinder = (HomeBoundService.Mybinder) service;
            homeService = mybinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

}
