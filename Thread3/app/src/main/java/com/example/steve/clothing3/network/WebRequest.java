package com.example.steve.clothing3.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
/**
 * Created by Steve on 09/12/2017.
 */

public class WebRequest {
    private Context mContext;
    private String mUrl;
    private int mMethod;
    private VolleyListener mVolleyListener;

    public WebRequest(Context context){
        mContext = context;
    }
    public WebRequest setUrl(String url){
        mUrl = url;
        return this;
    }
    public WebRequest setMethod(int method){
        mMethod = method;
        return this;
    }
    public WebRequest readFromURL(){
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(mMethod, mUrl, new Response.Listener<String>(){
            @Override
            public void onResponse(String s){
                mVolleyListener.onReceive(s);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError){
                mVolleyListener.onFail(volleyError);
            }
        });
        requestQueue.add(stringRequest);
        return this;
    }
    public WebRequest onListener(VolleyListener volleyListener){
        mVolleyListener = volleyListener;
        return this;
    }
    public interface VolleyListener{
        void onReceive(String data);
        void onFail(VolleyError volleyError);
    }
}
