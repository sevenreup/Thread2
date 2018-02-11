package com.example.steve.clothing3.network;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.steve.clothing3.R;
import com.example.steve.clothing3.helper.Constants;

/**
 * Created by SevenReup4ill on 2/3/2018.
 */

public class NetworkChecker
{
    private static String result;

    static private Fragment main, error;
    private AppCompatActivity activity;
    private static FragmentTransaction fragmentTransaction;

    public NetworkChecker(AppCompatActivity activity, Fragment main, Fragment error)
    {
        this.main = main;
        this.error = error;
        this.activity = activity;
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }
    static private void replaceFrag(Fragment fragment)
    {
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(R.id.home_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    static private void detachFrag(Fragment fragment)
    {
        fragmentTransaction.remove(fragment);
    }
    private String other()
    {

        other();

        final String url = "http://192.168.173.1/clothing/?check";
        final StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result = response;
                replaceFrag(main);
                Log.d(url, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError errorStac)
            {
                //result = Constants.NO_CONNECTION;
                Log.d(url, Constants.NO_CONNECTION);
                result = Constants.NO_CONNECTION;
                replaceFrag(error);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(request);

        if (!request.hasHadResponseDelivered())
        {
            result = Constants.NO_CONNECTION;
        }

        return result;
    }
    public void checkConnection()
    {
        final String url = "http://192.168.137.1/clothing/?check";
        new WebRequest(activity).setUrl(url).setMethod(Request.Method.GET).readFromURL().onListener
                (
                        new WebRequest.VolleyListener() {
                            @Override
                            public void onReceive(String data)
                            {
                                result = data;
                                checkNetwork(data);
                            }

                            @Override
                            public void onFail(VolleyError volleyError)
                            {
                                result = Constants.NO_CONNECTION;
                                checkNetwork(Constants.NO_CONNECTION);
                            }
                        }
                );
    }

    private void checkNetwork(String data)
    {
        if (data.equals(Constants.CONNECTION))
        {
            replaceFrag(main);
        } else
        {
            replaceFrag(error);
        }
    }

    public void refreshErrorPage(final SwipeRefreshLayout refreshLayout)
    {
        final String url = "http://192.168.137.1/clothing/?check";
        new WebRequest(activity).setUrl(url).setMethod(Request.Method.GET).readFromURL().onListener
                (
                        new WebRequest.VolleyListener() {
                            @Override
                            public void onReceive(String data)
                            {
                                refreshData(data, refreshLayout);
                            }

                            @Override
                            public void onFail(VolleyError volleyError)
                            {
                                refreshData(Constants.NO_CONNECTION, refreshLayout);
                            }
                        }
                );
    }

    public void refreshData(String data, SwipeRefreshLayout refreshLayout)
    {
        if (data.equals(Constants.CONNECTION))
        {
            detachFrag(error);
            activity.recreate();
            //replaceFrag(main);
        } else
        {
            Log.d("resfreshSwipe", "i have but failled " + data);
        }

        refreshLayout.setRefreshing(false);
    }
}
