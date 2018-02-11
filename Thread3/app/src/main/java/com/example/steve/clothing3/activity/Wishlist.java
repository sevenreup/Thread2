package com.example.steve.clothing3.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.steve.clothing3.R;
import com.example.steve.clothing3.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by Steve on 10/12/2017.
 */

public class Wishlist extends AppCompatActivity {

    private RecyclerViewAdapter adapter;
    static ArrayList<String> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle data = getIntent().getExtras();

        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        Context context = Wishlist.this;
        RecyclerView recycler = (RecyclerView) findViewById(R.id.t_container);
        adapter = new RecyclerViewAdapter(context, list,R.layout.wish_card);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                adapter.filter(query);
                return true;
            }


            @Override
            public boolean onQueryTextChange(String query){
                adapter.filter(query);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Home.selected.contains("cart")){
            getMenuInflater().inflate(R.menu.cart_menu, menu);
        }else{
            getMenuInflater().inflate(R.menu.wishlists, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.share) {
            Intent sendIntent = new  Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "send this to whatever app");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent,getResources().getText(R.string.drawer_close)));
            return true;
        }else if (id == R.id.payment){
            Intent intent = new Intent(Wishlist.this,Payment.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
