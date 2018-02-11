package com.example.steve.clothing3.Drawer.Fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.steve.clothing3.Drawer.Adapter.DrawerAdapter;
import com.example.steve.clothing3.Drawer.Model.DrawerModel;
import com.example.steve.clothing3.R;

import java.util.ArrayList;
import java.util.List;


public class NavigationDrawerFragment extends Fragment {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";


    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;

    private RecyclerView recyclerView;

    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    ArrayList<DrawerModel> list;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override

    public void onActivityCreated (Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        // Indicate that this fragment would like to influence the set of actions in the action bar.

        setHasOptionsMenu(true);

    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.drawer_drawer, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_slidermenu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        setData();
        recyclerView.setAdapter(new DrawerAdapter(list, getContext(), getActivity()));
        return rootView;

    }

    public void setData()
    {
        list = new ArrayList<>();
        List<String> mem = new ArrayList<>();
        List<String> dude = new ArrayList<>();
        dude.add("Men");
        dude.add("Women");

        list.add(new DrawerModel(0, "header", 0, mem));
        list.add(new DrawerModel(1, getContext().getResources().getString(R.string.drawer_home), R.string.d_home, mem));
        list.add(new DrawerModel(2, getContext().getResources().getString(R.string.drawer_category), R.string.d_cat, dude));
        list.add(new DrawerModel(1, getContext().getResources().getString(R.string.drawer_shops), R.string.d_shop, mem));
        list.add(new DrawerModel(1, getContext().getResources().getString(R.string.drawer_designers), R.string.d_shop, mem));
        list.add(new DrawerModel(3, getContext().getResources().getString(R.string.action_settings),R.string.d_setting, mem));
    }
    public boolean isDrawerOpen() {

        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);

    }



        /**
         192
         * Users of this fragment must call this method to set up the navigation drawer interactions.
         193
         *
         194
         * @param fragmentId   The android:id of this fragment in its activity's layout.
        195
         * @param drawerLayout The DrawerLayout containing this fragment's UI.
        196
         */

    public void setUp(int fragmentId, DrawerLayout drawerLayout, AppCompatActivity activity, Toolbar toolbar) {

        mFragmentContainerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions

        // between the navigation drawer and the action bar app icon.

        mDrawerToggle = new ActionBarDrawerToggle(

                getActivity(),/* host Activity */

                mDrawerLayout,                    /* DrawerLayout object */

                R.string.drawer_open,  /* "open drawer" description for accessibility */

                R.string.drawer_close  /* "close drawer" description for accessibility */

        ) {
            @Override

            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);


                if (!isAdded()) {

                    return;

                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()

            }

            @Override

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!isAdded()) {

                    return;

                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()

            }
        };

        // Defer code dependent on restoration of previous instance state.

        mDrawerLayout.post(new Runnable() {
            @Override

            public void run() {

                mDrawerToggle.syncState();

            }

        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override

    public void onAttach(Activity activity) {

        super.onAttach(activity);

        try {


        } catch (ClassCastException e) {

            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");

        }

    }

    @Override

    public void onDetach() {

        super.onDetach();


    }



    @Override

    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);

    }



    @Override

    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        // Forward the new configuration the drawer toggle component.

        mDrawerToggle.onConfigurationChanged(newConfig);

    }



    @Override

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // If the drawer is open, show the global app actions in the action bar. See also

        // showGlobalContextActionBar, which controls the top-left area of the action bar.

        if (mDrawerLayout != null && isDrawerOpen()) {

        }

        super.onCreateOptionsMenu(menu, inflater);

    }



    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {

            return true;

        }

        return super.onOptionsItemSelected(item);

    }



        /**
         320
         * Per the navigation drawer design guidelines, updates the action bar to show the global app
         321
         * 'context', rather than just what's in the current screen.
         322
         */




    private ActionBar getActionBar() {

        return ((AppCompatActivity) getActivity()).getSupportActionBar();

    }


}
