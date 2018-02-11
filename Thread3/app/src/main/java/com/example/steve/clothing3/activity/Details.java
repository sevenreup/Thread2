package com.example.steve.clothing3.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.steve.clothing3.Drawer.Helper.CollapseAnimation;
import com.example.steve.clothing3.FontManager.FontManager;
import com.example.steve.clothing3.R;
import com.example.steve.clothing3.database.DatabaseHandler;
import com.example.steve.clothing3.helper.CollapsingToolBarHelper;
import com.example.steve.clothing3.network.WebRequest;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

public class Details extends AppCompatActivity {
    public static String image_name = "";
    public static String description = "";
    public static String brand = "";
    public static String price = "";
    public static float rating = 0;
    public static String data = "";
    Spinner size = null;
    Spinner color = null;
    ExpandableLayout collapse1, collapse2;
    TextView arrow1, arrow2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        new CollapsingToolBarHelper(this, brand);
        final CollapsingToolbarLayout collapse = (CollapsingToolbarLayout) findViewById(R.id.home_collapsing_toolbar);
        collapse.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimary_lt));
        collapse.setContentScrimColor(getResources().getColor(R.color.colorPrimary_lt));
        collapse.setTitle(brand);


        arrow1 = (TextView) findViewById(R.id.arrow_rot);
        arrow2 = (TextView) findViewById(R.id.arrow_rot1);

        arrow1.setTypeface(FontManager.getTypeface(this, FontManager.DRIPICONS));
        arrow1.setText(getResources().getString(R.string.dri_chvron));
        arrow2.setTypeface(FontManager.getTypeface(this, FontManager.DRIPICONS));
        arrow2.setText(getResources().getString(R.string.dri_chvron));

        collapse1 = (ExpandableLayout) findViewById(R.id.cchapa);
        collapse2 = (ExpandableLayout) findViewById(R.id.cchapa1);

        collapse1.setOnExpansionUpdateListener( new CollapseAnimation(collapse1, arrow1));
        collapse2.setOnExpansionUpdateListener( new CollapseAnimation(collapse2, arrow2));

        final DatabaseHandler db = new DatabaseHandler(this);

        final ImageView imageView = (ImageView) findViewById(R.id.d_image);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating);
        ratingBar.setRating(rating);
        final String imageUri = "http://192.168.173.1/clothing/images/"+image_name;
        Picasso.with(this).load(imageUri).into(imageView);

        //RecyclerView suggestions = (RecyclerView) findViewById(R.id.suggestions);
        //suggestions.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //H_Recycler adapter = new H_Recycler(this, Home.categories,R.layout.sugg_card);
        //suggestions.setAdapter(adapter);

        size = (Spinner) findViewById(R.id.spinner);
        color = (Spinner) findViewById(R.id.spinner1);

        final RecyclerView recycler = null;
        Button cart = (Button) findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                db.addCart(data);
            }
        });

        ImageView wish = (ImageView) findViewById(R.id.wish);
        wish.setOnClickListener(new View.OnClickListener(){
           public void onClick(View view){
               System.out.println(Home.selected);
               Home.selected = "make_wish";
               db.makeWish(data);
           }
        });

        TextView descr = (TextView) findViewById(R.id.description);
        descr.setText(description);

        TextView brand_tv = (TextView) findViewById(R.id.brand);
        brand_tv.setText(brand);

        TextView price_tv = (TextView) findViewById(R.id.price);
        price_tv.setText("MK "+price);
    }

    public void capchClick(View view)
    {
        if(collapse1.isExpanded())
        {
            collapse1.collapse();
        } else
        {
            collapse1.expand();
        }
    }
    public void capchClick1(View view)
    {
        if(collapse2.isExpanded())
        {
            collapse2.collapse();
        } else
        {
            collapse2.expand();
        }
    }
    /*public void add_entry(String word){
        Context context = Details.this;
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Making wish");
        dialog.setCancelable(true);
        dialog.show();
        new WebRequest(context)
                .setUrl("http://192.168.137.1/clothing/content.php?"+word+"&user_id=q23&item_id="
                        +image_name+"&color="+color.getSelectedItem()
                        +"&size="+size.getSelectedItem())
                .setMethod(Request.Method.GET)
                .readFromURL()
                .onListener(new WebRequest.VolleyListener(){
                    @Override
                    public void onReceive(String data){
                        dialog.dismiss();
                    }
                    @Override
                    public void onFail(VolleyError volleyError){

                    }
                });
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.rate) {
            final Dialog r_dialog = new Dialog(Details.this);
            r_dialog.setTitle("Rate");
            r_dialog.setContentView(R.layout.dialog);
            Button ok = (Button) r_dialog.findViewById(R.id.ok);
            final RatingBar r_bar = (RatingBar) r_dialog.findViewById(R.id.rating);
            ok.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    new WebRequest(Details.this).setUrl("http://192.168.137.1/clothing/content.php?rate&rating="+r_bar.getRating()+"&item_id=heels1.jpg")
                            .setMethod(Request.Method.GET)
                            .readFromURL()
                            .onListener(new WebRequest.VolleyListener(){
                                @Override
                                public void onReceive(String data) {

                                }

                                @Override
                                public void onFail(VolleyError volleyError) {

                                }
                            });
                    r_dialog.dismiss();
                }
            });
            r_dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
