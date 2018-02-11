package com.example.steve.clothing3.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.steve.clothing3.R;
import com.example.steve.clothing3.network.NetworkChecker;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetworkError extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    public SwipeRefreshLayout refreshLayout;
    NetworkChecker networkChecker;
    Fragment main;

    public NetworkError() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_network_error, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.errorreload);
        refreshLayout.setOnRefreshListener(this);

        main = new HomeTrendFragment();

        networkChecker = new NetworkChecker((AppCompatActivity) getActivity(), main, this);
        return view;
    }

    @Override
    public void onRefresh()
    {
        getNet();
    }

    private void getNet()
    {

        refreshLayout.setRefreshing(true);
        networkChecker.refreshErrorPage(refreshLayout);
    }

}
