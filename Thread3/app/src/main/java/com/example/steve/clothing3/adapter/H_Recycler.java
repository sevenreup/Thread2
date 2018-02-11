package com.example.steve.clothing3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.steve.clothing3.R;
import com.example.steve.clothing3.activity.Categories;
import com.example.steve.clothing3.activity.Home;
import com.example.steve.clothing3.activity.Template;
import com.example.steve.clothing3.network.WebRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

/**
 * Created by Steve on 16/11/2017.
 */

public class H_Recycler extends RecyclerView.Adapter<H_Recycler.ViewHolder>{
    private List<String> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int layout;
    private Context context;

    public H_Recycler(Context context, List<String> data, int in){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.layout = in;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String item = mData.get(position);
        System.out.println(item);
        if(item.contains("{")){
            JSONObject val = null;
            try {
                val = new JSONObject(item);
                holder.sugg_name.setText(val.getString("Item"));
                String Uri = "http://192.168.173.1/clothing/images/"+val.getString("Item");
                Picasso.with(context).load(Uri).into(holder.sugg_img);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{
            holder.myTextView.setText(item);
        }

        final String imageUri = "http://192.168.173.1/clothing/images/"+item.toLowerCase()+".jpg";
        System.out.println("this : "+ Home.selected);
        if((Home.selected.contains("Brands")|| Home.selected.contains("Shoes")|| Home.selected.contains("Clothing") || Home.selected.contains("Categories"))){
            Glide.with(holder.context).load(imageUri).into(holder.imageView);
            System.out.println(imageUri);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView myTextView;
        public Context context;
        public ImageView imageView;
        public TextView sugg_name;
        public ImageView sugg_img;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            myTextView = (TextView) itemView.findViewById(R.id.title11);
            sugg_name = (TextView) itemView.findViewById(R.id.sug_name);
            sugg_img = (ImageView) itemView.findViewById(R.id.sug_img);
            itemView.setOnClickListener(this);
            if(!(Home.selected.contains("Men"))){
                imageView = (ImageView) itemView.findViewById(R.id.iconic);
            }
        }

        @Override
        public void onClick(View view){
            if(mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
            {
                Home.selected = mData.get(getLayoutPosition());
                System.out.println("layout : "+mData.get(getLayoutPosition()));
                new WebRequest(context)
                        .setUrl("http://192.168.173.1/clothing/content.php?catcont&cat="+mData.get(getLayoutPosition()))
                        .setMethod(Request.Method.GET)
                        .readFromURL()
                        .onListener(new WebRequest.VolleyListener(){
                            @Override
                            public void onReceive(String data){
                                if(!(data.contains(".jpg")|| data.contains(".jpeg"))){
                                    try {
                                        //Template.prev.clear();
                                        for(String i : Home.categories){
                                            Template.prev.add(i);
                                        }
                                        JSONArray array = new JSONArray(data);
                                        Home.categories.clear();
                                        for(int x=0;array.length()>x;x++){
                                            JSONObject dict = new JSONObject(array.getString(x));
                                            Home.categories.add(dict.getString("Item"));
                                        }
                                        Intent intent = new Intent(context,Categories.class);
                                        context.startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else if(data.contains(".jpg")|| data.contains(".jpeg")){
                                    System.out.println("well this seems to be working");
                                    try {
                                        Template.prev.clear();
                                        for(String i : Home.categories){
                                            Template.prev.add(i);
                                        }
                                        JSONArray array = new JSONArray(data);
                                        Home.categories.clear();
                                        for(int x=0;array.length()>x;x++){
                                            Home.categories.add(array.getString(x));
                                        }
                                        Intent intent = new Intent(context,Template.class);
                                        context.startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                            @Override
                            public void onFail(VolleyError volleyError){

                            }
                        });
            }
        }
    }

    private interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private void addPicture(ViewHolder holder, String url)
    {
        if (!TextUtils.isEmpty(url))
        {
            Glide.with(context).load(url).thumbnail(0.5f).into(holder.imageView);
        }
    }
}
