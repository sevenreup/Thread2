package com.example.steve.clothing3.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.steve.clothing3.Decorators.GridItemDecorator;
import com.example.steve.clothing3.Drawer.Fragment.NavigationDrawerFragment;
import com.example.steve.clothing3.R;
import com.example.steve.clothing3.adapter.H_Recycler;
import com.example.steve.clothing3.database.DatabaseHandler;
import com.example.steve.clothing3.helper.CollapsingToolBarHelper;
import com.example.steve.clothing3.helper.ContextMenuHelper;
import com.example.steve.clothing3.helper.RandomUses;
import com.example.steve.clothing3.helper.userList;
import com.example.steve.clothing3.infoProvide.MenuItems;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;


public class Categories extends AppCompatActivity
{
    private ContextMenuHelper menuHelper;
    private FragmentManager fragmentManager;
    MenuItems menuItemHelp = new MenuItems();
    private NavigationDrawerFragment mNavigationDrawerFragment;
    TextView pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        pageTitle =(TextView) findViewById(R.id.cat_title);
        pageTitle.setText(Home.selected + " Collection");

        new CollapsingToolBarHelper(this, Home.selected);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.left_drawer);
        mNavigationDrawerFragment.setUp(
                R.id.left_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), this, toolbar);

        menuHelper = new ContextMenuHelper(this);

        menuHelper.setMenu(menuItemHelp.getCategories());
        menuHelper.initMenuFragment();
        fragmentManager = getSupportFragmentManager();

        ProgressDialog dialog = new ProgressDialog(Categories.this);
        Context context = Categories.this;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_cont);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);

        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.addItemDecoration(new GridItemDecorator(2, RandomUses.dpToPx(10, this), true));
        H_Recycler adapter = new H_Recycler(context, Home.categories, R.layout.gridiconcard);
        recyclerView.setAdapter(adapter);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        userList list = new userList(getApplicationContext(), this);
        DatabaseHandler db = new DatabaseHandler(this);
        switch (item.getItemId()) {
            case R.id.share:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    menuHelper.getContextFrag().show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
            case R.id.cart:

                Intent intent = new Intent(this, Wishlist.class);
                startActivity(intent);
                Wishlist.list = db.getCart();
                break;
            case R.id.wishlist:
                Intent cartIntent = new Intent(this, Wishlist.class);
                startActivity(cartIntent);
                Wishlist.list = db.getWish("General");
        }
        list.select(item);
        return super.onOptionsItemSelected(item);
    }


    public boolean onSupportNavigateUp(){
        Home.categories.clear();
        for(String i : Template.prev){
            Home.categories.add(i);
        }
        //Template.prev.clear();
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_cont);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        H_Recycler adapter = new H_Recycler(this, Home.categories, R.layout.gridiconcard);
        recyclerView.setAdapter(adapter);
        //content.cur_pg = getSupportActionBar().getTitle().toString();
        ProgressDialog dialog = new ProgressDialog(Categories.this);
        Context context = Categories.this;
        Home.selected=getSupportActionBar().getTitle().toString();
        //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_cont);
        //new content(dialog,context,recyclerView).execute(new String[] {});
        adapter.notifyDataSetChanged();
        super.onResume();
    }
    @Override
    public void onBackPressed() {
        Home.categories.clear();
        for(String i : Template.prev){
            Home.categories.add(i);
        }
        //Template.prev.clear();
        //content.cur_pg="";
        super.onBackPressed();
    }

}
