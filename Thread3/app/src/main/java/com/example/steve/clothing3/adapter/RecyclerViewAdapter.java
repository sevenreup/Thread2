package com.example.steve.clothing3.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.steve.clothing3.R;
import com.example.steve.clothing3.activity.Details;
import com.example.steve.clothing3.activity.Home;
import com.example.steve.clothing3.network.WebRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Steve on 07/11/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    //private List<String> mData = Collections.emptyList();
    private List<String> mImage = Collections.emptyList();
    ArrayList<String> mCopy = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    public static Resources pic;
    private int layout;
    private JSONObject dict = null;
    Context context1;
    private LinearLayout v;

    public RecyclerViewAdapter(Context context ,List<String> data2,int layout){
        this.mInflater = LayoutInflater.from(context);
        //this.mData = data;
        this.mImage = data2;
        this.layout = layout;
        context1 = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //View view = mInflater.inflate(R.layout.item_row1,parent,false);
        //ViewHolder viewHolder = new ViewHolder(view);
        //return viewHolder;
        v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            dict = new JSONObject(mImage.get(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String imageUri ="";
        try{
            imageUri = "http://192.168.173.1/clothing/images/"+dict.getString("Item");
        }catch (JSONException e) {
            e.printStackTrace();
        }

        try{
            holder.descr.setText("MKW "+dict.getString("Price"));
            holder.shopName.setText(dict.getString("Shop_ID"));
        }catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalImageUri = imageUri;
        Picasso.with(holder.context).load(imageUri).networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.image, new Callback(){
                    @Override
                    public void onSuccess(){

                    }

                    @Override
                    public void onError(){
                        Picasso.with(holder.context).load(finalImageUri).into(holder.image);
                    }
                });

        if(Home.selected.contains("get_wishes")){
            holder.remove.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    try {
                        final String to_remove = mImage.get(position);
                        JSONObject vals = new JSONObject(to_remove);
                        System.out.println(vals.getString("Item"));
                        new WebRequest(holder.context)
                                .setUrl("http://192.168.173.1/clothing/content.php?delete_wish&user_id=q23&item_id="
                                        +vals.getString("Item"))
                                .setMethod(Request.Method.GET)
                                .readFromURL()
                                .onListener(new WebRequest.VolleyListener(){
                                    @Override
                                    public void onReceive(String data){
                                        mImage.remove(to_remove);
                                        notifyDataSetChanged();
                                        System.out.println(data);
                                    }

                                    @Override
                                    public void onFail(VolleyError volleyError){

                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.custom.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    final String item = mImage.get(position);
                    JSONObject vals = null;
                    try {
                        vals = new JSONObject(item);
                        showPopup(view,vals.getString("Item"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    }



    @Override
    public int getItemCount() {
        return mImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView shopName;
        public ImageView image;
        public TextView descr;
        public Context context;
        public Button remove;
        public Button custom;
        public RelativeLayout viewBackground;
        public LinearLayout viewForeground;

        public ViewHolder(View itemView) {
            super(itemView);
            shopName = (TextView) itemView.findViewById(R.id.shop_name);
            image = (ImageView) itemView.findViewById(R.id.image1);
            descr = (TextView) itemView.findViewById(R.id.descr);
            remove = (Button) itemView.findViewById(R.id.remove);
            custom = (Button) itemView.findViewById(R.id.customList);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            if(mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            {
                Intent intent = new Intent(context,Details.class);
                context.startActivity(intent);
                JSONObject values = null;
                try {
                    values = new JSONObject(mImage.get(getLayoutPosition()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Details.data = mImage.get(getLayoutPosition());
                    Details.image_name = values.getString("Item");
                    Details.brand = values.getString("Brand");
                    //Details.size = values.getString("Size");
                    Details.price = values.getString("Price");
                    if(Integer.parseInt(values.getString("Rated_Count"))>0){
                        Details.rating = Integer.parseInt(values.getString("Total_Rating"))
                                /Integer.parseInt(values.getString("Rated_Count"));
                    }else {
                        Details.rating=0;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(Details.image_name);
            }
        }
    }

    public void filter(String text){
        new WebRequest(context1)
                .setUrl("http://192.168.173.1//clothing/content.php?get_wishes&user_id=q23")
                .setMethod(Request.Method.GET)
                .readFromURL()
                .onListener(new WebRequest.VolleyListener(){
                    @Override
                    public void onReceive(String data){
                        try {
                            JSONArray jsonArray = new JSONArray(data);
                            mCopy.clear();
                            for (int x=0;jsonArray.length()>x;x++){
                                mCopy.add(jsonArray.getString(x));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFail(VolleyError volleyError){
                        System.out.println("Damn shit failed");
                    }
                });

        mImage.clear();
        if(text.isEmpty()){
            System.out.println("2");
            mImage.addAll(mCopy);
        }else{
            System.out.println("3");
            for (String i : mCopy){
                System.out.println(i);
            }
            text = text.toLowerCase();
            for(String i : mCopy){
                System.out.println("atleast we came here");
                try {
                    JSONObject vals = new JSONObject(i);
                    if(vals.getString("Brand").toLowerCase().contains(text)){
                        System.out.println(i);
                        mImage.add(i);
                    }
                } catch (JSONException e) {
                    System.out.println("darn");
                    e.printStackTrace();
                }
            }
        }
        notifyDataSetChanged();
    }

    public String getItem(int id){
        return mImage.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener){
        this.mClickListener = itemClickListener;
    }

    public void showPopup(View v, final String item){
        PopupMenu popup = new PopupMenu(context1,v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.wishlists,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();

                /*if (id == R.id.create){
                    Dialog cust_dialog = new Dialog(context1);
                    cust_dialog.setContentView(R.layout.wish_dialog);
                    final EditText editText = (EditText) cust_dialog.findViewById(R.id.cust_name);
                    Button create = (Button) cust_dialog.findViewById(R.id.create_cust);
                    create.setOnClickListener(new View.OnClickListener(){
                        public void onClick(View view){
                            String name = editText.getText().toString();
                            System.out.println(name+"yas");
                            new WebRequest(context1).setUrl("http://192.168.173.1/clothing/content.php?custom&user_id=q23&item_id="+item+"&title="+name)
                                    .setMethod(Request.Method.GET)
                                    .readFromURL()
                                    .onListener(new WebRequest.VolleyListener(){
                                         @Override
                                        public void onReceive(String data){

                                        }
                                        @Override
                                        public void onFail(VolleyError volleyError){
                                            System.out.println("Pssh failed");
                                        }
                                    });
                        }
                    });
                    cust_dialog.show();
                }*/

                return false;
            }
        });
        popup.show();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
