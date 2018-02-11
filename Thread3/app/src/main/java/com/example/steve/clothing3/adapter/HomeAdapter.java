package com.example.steve.clothing3.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.steve.clothing3.Model.HeaderModel;
import com.example.steve.clothing3.Model.Item;
import com.example.steve.clothing3.R;

import java.util.List;

import static com.example.steve.clothing3.helper.Constants.HEADER;

/**
 * Created by SevenReup4ill on 1/25/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ItemViewHolder>
{
    List<Item> itemList;
    List<HeaderModel> headerModelList;
    String TAG;

    public HomeAdapter(List<Item> list, String type, List<HeaderModel> headerModelList)
    {
        this.itemList = list;
        this.headerModelList = headerModelList;
        this.TAG = type;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView imageView;

        public ItemViewHolder(View itemView)
        {
            super(itemView);
            if (TAG.equals(HEADER))
            {
                name = (TextView) itemView.findViewById(R.id.headername);
                imageView = (ImageView) itemView.findViewById(R.id.headerimg);
            } else
            {
                name = (TextView) itemView.findViewById(R.id.trendname);
            }
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView;
        if (TAG.equals(HEADER))
        {
             itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_header_card, parent, false);
        } else
        {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_trending_card, parent, false);
        }

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position)
    {


        if (TAG.equals(HEADER))
        {
            HeaderModel header = headerModelList.get(position);
            holder.name.setText(header.getStringName());
            holder.imageView.setImageResource(header.getImageLocation());
        } else {
            Item item = itemList.get(position);
            holder.name.setText(item.getName());
        }


    }

    @Override
    public int getItemCount()
    {
        if (TAG.equals(HEADER))
        {
            return headerModelList.size();
        } else
        {
            return  itemList.size();
        }

    }
}
