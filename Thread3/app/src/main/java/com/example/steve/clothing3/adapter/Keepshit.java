/**package com.example.steve.clothing3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.steve.clothing3.Interfaces.MainAdapterListerner;
import com.example.steve.clothing3.R;
import com.example.steve.clothing3.activity.Categories;
import com.example.steve.clothing3.activity.Home;
import com.example.steve.clothing3.activity.Template;
import com.example.steve.clothing3.helper.Constants;
import com.example.steve.clothing3.network.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;



public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MainAdapterListerner
{
    static private List<String> dataSet;
    private Context context;
    private int type;

    public MainAdapter(Context context, List<String> dataSet, int type)
    {
        this.context = context;
        this.dataSet = dataSet;
        this.type = type;
    }

    private static class HomeTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView  home_profile;
        TextView home_text;

        private HomeTypeViewHolder(View itemView)
        {
            super(itemView);
            home_profile = (ImageView) itemView.findViewById(R.id.home_profile);
            home_text = (TextView) itemView.findViewById(R.id.title11);
        }

        @Override
        public void onClick(final View view)
        {

        }
    }
    private static class CategoryTypeViewHolder extends RecyclerView.ViewHolder
    {
        ImageView cardImage;
        private CategoryTypeViewHolder(View itemView)
        {
            super(itemView);
            cardImage = (ImageView) itemView.findViewById(R.id.iconic);
        }
    }
    private static class DetailsTypeViewHolder extends RecyclerView.ViewHolder
    {
        ImageView sugg_img;
        TextView sugg_name;

        public DetailsTypeViewHolder(View itemView)
        {
            super(itemView);
            sugg_img = (ImageView) itemView.findViewById(R.id.sug_img);
            sugg_name = (TextView) itemView.findViewById(R.id.sug_name);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        switch (viewType)
        {
            case Constants.HOMEVIEWTYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homecard, parent, false);
                return new HomeTypeViewHolder(view);
            case Constants.CATEGORIESIEWTYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridiconcard, parent, false);
                return new CategoryTypeViewHolder(view);
            case Constants.DETAILSVIEWTYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sugg_card, parent, false);
                return new DetailsTypeViewHolder(view);
        }
        return null;
    }
    @Override
    public int getItemViewType(int position)
    {
        switch (type)
        {
            case 0:
                return Constants.HOMEVIEWTYPE;
            case 1:
                return Constants.CATEGORIESIEWTYPE;
            case 2:
                return Constants.DETAILSVIEWTYPE;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition)
    {
        String item = dataSet.get(listPosition);
        switch (type)
        {
            case Constants.HOMEVIEWTYPE:
                ((HomeTypeViewHolder) holder).home_text.setText(item);
                break;
            case Constants.CATEGORIESIEWTYPE:
                final String imageUri = "http://192.168.173.1/clothing/images/"+item.toLowerCase()+".jpg";
                Glide.with(context).load(imageUri).into(((CategoryTypeViewHolder) holder).cardImage);
                System.out.println(imageUri);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onCardSelected(int position)
    {
        if (type == Constants.HOMEVIEWTYPE)
        {
            String data = dataSet.get(position);
            Home.selected = data;
            new WebRequest(context)
                    .setUrl("http://192.168.173.1/clothing/content.php?catcont&cat="+data)
                    .setMethod(Request.Method.GET)
                    .readFromURL()
                    .onListener(new WebRequest.VolleyListener(){
                        @Override
                        public void onReceive(String data){
                            try {
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
                        }
                        @Override
                        public void onFail(VolleyError volleyError){

                        }
                    });

        }
    }
}
**/