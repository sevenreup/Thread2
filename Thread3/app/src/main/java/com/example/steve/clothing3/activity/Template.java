package com.example.steve.clothing3.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.steve.clothing3.Decorators.GridItemDecorator;
import com.example.steve.clothing3.Drawer.Fragment.NavigationDrawerFragment;
import com.example.steve.clothing3.R;
import com.example.steve.clothing3.adapter.RecyclerViewAdapter;
import com.example.steve.clothing3.helper.RandomUses;

import java.util.ArrayList;

public class Template extends AppCompatActivity
{
    public static ArrayList<String> images = new ArrayList<>();
    public static ArrayList<String> shops = new ArrayList<String>();
    public static ArrayList<String> prev = new ArrayList<>();

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Home.selected);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.left_drawer);
        mNavigationDrawerFragment.setUp(
                R.id.left_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), this, toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.t_container);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new GridItemDecorator(2, RandomUses.dpToPx(10, this), true));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,Home.categories,R.layout.card_task);
        recyclerView.setAdapter(adapter);

        shops.add("Tuto Sport");
        shops.add("Top Man");
        shops.add("No overprice");
        shops.add("Vanzara Brothers");
        shops.add("White Rose");


    }

    @Override
    public void onBackPressed() {
        Home.categories.clear();
        for(String i : prev){
            Home.categories.add(i);
        }
        prev.clear();
        //content.cur_pg="";
        finish();
        super.onBackPressed();
    }

   /* @Override
    public boolean onSupportNavigateUp(){
        Home.categories.clear();
        for(String i : prev){
            Home.categories.add(i);
        }
        prev.clear();
        //content.cur_pg="";
        finish();
        return true;
    }
**/

}
