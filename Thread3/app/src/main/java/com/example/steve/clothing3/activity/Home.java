package com.example.steve.clothing3.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.steve.clothing3.Drawer.Fragment.NavigationDrawerFragment;
import com.example.steve.clothing3.R;
import com.example.steve.clothing3.adapter.H_Recycler;
import com.example.steve.clothing3.adapter.MainAdapter;
import com.example.steve.clothing3.helper.CollapsingToolBarHelper;
import com.example.steve.clothing3.helper.DividerItemDecoration;
import com.example.steve.clothing3.network.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Home extends AppCompatActivity {

    public static ArrayList<String> categories = new ArrayList<>();
    public static String selected = "";
    static ArrayList<String> array = new ArrayList<String>();
    ArrayList<String> cats = new ArrayList<String>();
    TextView pageTitle;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        pageTitle = (TextView) findViewById(R.id.cat_title);
        pageTitle.setText(selected + " Collection");

        new CollapsingToolBarHelper(this, selected);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.left_drawer);
        mNavigationDrawerFragment.setUp(
                R.id.left_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), this, toolbar);

        final ProgressDialog dialog = new ProgressDialog(Home.this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.show();
        final Context context = Home.this;
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_cont);

        new WebRequest(Home.this).setUrl("http://192.168.173.1/clothing/content.php?catcont&cat=" + Home.selected)
                .setMethod(Request.Method.GET)
                .readFromURL()
                .onListener(new WebRequest.VolleyListener() {
                    @Override
                    public void onReceive(String data) {
                        JSONArray array = null;
                        try {
                            //for(String i : Home.categories){

                            //}
                            cats.clear();
                            array = new JSONArray(data);
                            for (int x = 0; array.length() > x; x++) {
                                JSONObject dict = new JSONObject(array.getString(x));
                                cats.add(dict.getString("Item"));
                                Template.prev.add(dict.getString("Item"));
                                System.out.println(dict.getString("Item") + "nurlly");
                            }
                            //H_Recycler adapter = new H_Recycler(context,cats,R.layout.homecard);
                            MainAdapter adapter = new MainAdapter(context, cats);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                            recyclerView.setAdapter(adapter);
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFail(VolleyError volleyError) {

                    }
                });


    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        //ProgressDialog dialog = new ProgressDialog(Home.this);
        //Context context = Home.this;
        selected = getSupportActionBar().getTitle().toString();
        //System.out.println(selected);
        //Home.categories.clear();
        //for(String i : Template.prev){
        //    Home.categories.add(i);
        //    System.out.println(i+"check here");
        //}
        //Template.prev.clear();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_cont);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        H_Recycler adapter = new H_Recycler(this, Home.categories, R.layout.gridiconcard);
        //recyclerView.setAdapter(adapter);
        super.onResume();
    }


    public void get_entries(String word) {
        new WebRequest(Home.this)
                .setUrl("http://192.168.137.1//clothing/content.php?" + word + "&user_id=q23")
                .setMethod(Request.Method.GET)
                .readFromURL()
                .onListener(new WebRequest.VolleyListener() {
                    @Override
                    public void onReceive(String data) {
                        try {
                            JSONArray jsonArray = new JSONArray(data);
                            array.clear();
                            for (int x = 0; jsonArray.length() > x; x++) {
                                //JSONObject dict = new JSONObject(jsonArray.getString(x));
                                array.add(jsonArray.getString(x));
                            }
                            Intent intent = new Intent(Home.this, Wishlist.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(VolleyError volleyError) {
                        System.out.println("Damn shit failed");
                    }
                });
    }
}
