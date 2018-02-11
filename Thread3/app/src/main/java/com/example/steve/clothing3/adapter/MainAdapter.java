package com.example.steve.clothing3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.steve.clothing3.R;
import com.example.steve.clothing3.activity.Categories;
import com.example.steve.clothing3.activity.Home;
import com.example.steve.clothing3.activity.Template;
import com.example.steve.clothing3.helper.RandomColor;
import com.example.steve.clothing3.network.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by SevenReup4ill on 2/7/2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.HomeTypeViewHolder> {
    static private List<String> dataSet;
    private Context context;


    public MainAdapter(Context context, List<String> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    @Override
    public HomeTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.homecard, parent, false);
        return new HomeTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeTypeViewHolder holder, final int listPosition) {
        String item = dataSet.get(listPosition);
        holder.home_text.setText(item);
        holder.profile_text.setText(item.substring(0, 1));
        holder.home_profile.setImageResource(R.drawable.bg_circle);
        holder.home_profile.setColorFilter(RandomColor.getRandomMaterialColor("400", context));
        holder.profile_text.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class HomeTypeViewHolder extends RecyclerView.ViewHolder {
        ImageView home_profile;
        TextView home_text, profile_text;
        Context context;

        private HomeTypeViewHolder(View itemView) {
            super(itemView);
            home_profile = (ImageView) itemView.findViewById(R.id.home_profile);
            home_text = (TextView) itemView.findViewById(R.id.title11);
            profile_text = (TextView) itemView.findViewById(R.id.icon_text);
            context = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data0 = dataSet.get(getLayoutPosition());
                    Home.selected = data0;

                    /** Temporary **/
                    if(data0.contains("anzara")){
                        data0="shoes";
                    }

                    new WebRequest(context)
                            .setUrl("http://192.168.173.1/clothing/content.php?catcont&cat=" + data0)
                            .setMethod(Request.Method.GET)
                            .readFromURL()
                            .onListener(new WebRequest.VolleyListener() {
                                @Override
                                public void onReceive(String data) {
                                    try {
                                        for (String i : Home.categories) {
                                            Template.prev.add(i);
                                        }
                                        JSONArray array = new JSONArray(data);
                                        Home.categories.clear();
                                        for (int x = 0; array.length() > x; x++) {
                                            JSONObject dict = new JSONObject(array.getString(x));
                                            Home.categories.add(dict.getString("Item"));
                                        }
                                        Intent intent = new Intent(context, Categories.class);
                                        context.startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFail(VolleyError volleyError) {

                                }
                            });
                }
            });
        }
    }


}
