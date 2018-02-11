package com.example.steve.clothing3.Drawer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.steve.clothing3.Drawer.Helper.CollapseAnimation;
import com.example.steve.clothing3.Drawer.Helper.Helper;
import com.example.steve.clothing3.Drawer.Model.DrawerModel;
import com.example.steve.clothing3.FontManager.FontManager;
import com.example.steve.clothing3.R;
import com.example.steve.clothing3.activity.Home;
import com.example.steve.clothing3.activity.SettingsActivity;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;


/**
 * Created by SevenReup4ill on 1/17/2018.
 */

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private ArrayList<DrawerModel> dataSet;
    private Context mContext;
    private int total_types;
    private Activity activity;

    private static class TextTypeViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtType;
        CardView cardView;
        TextView drawer_icon;

        private   TextTypeViewHolder(View itemView)
        {
            super(itemView);
            this.cardView = (CardView) itemView.findViewById(R.id.drawer_card);
            this.txtType = (TextView) itemView.findViewById(R.id.drawer_menu);
            this.drawer_icon = (TextView) itemView.findViewById(R.id.drawer_icon);
        }
    }

    private static class ExpandableTypeHolder extends RecyclerView.ViewHolder
    {
        RelativeLayout expnd_butn, men_btn, kids_btn, women_btn;
        ExpandableLayout expnd_menu;
        TextView name, men, women, kids, men_icon, women_icon, kids_icon;
        TextView arrow;
        TextView drawer_icon;

        private   ExpandableTypeHolder(View itemView)
        {
            super(itemView);
            this.expnd_menu = (ExpandableLayout) itemView.findViewById(R.id.exp_expand);
            this.expnd_butn = (RelativeLayout) itemView.findViewById(R.id.exp_expand_button);
            this.name = (TextView) itemView.findViewById(R.id.cat);
            this.men = (TextView) itemView.findViewById(R.id.men);
            this.women = (TextView) itemView.findViewById(R.id.women);
            this.kids = (TextView) itemView.findViewById(R.id.kids);
            this.arrow = (TextView) itemView.findViewById(R.id.draw_arrow);
            this.drawer_icon = (TextView) itemView.findViewById(R.id.cat_img);
            this.men_btn = (RelativeLayout) itemView.findViewById(R.id.men_button);
            this.women_btn = (RelativeLayout) itemView.findViewById(R.id.women_button);
            this.kids_btn = (RelativeLayout) itemView.findViewById(R.id.kids_button);
            this.men_icon = (TextView) itemView.findViewById(R.id.men_img);
            this.women_icon = (TextView) itemView.findViewById(R.id.women_img);
            this.kids_icon = (TextView) itemView.findViewById(R.id.kids_img);
        }
    }
    private static class HeaderTypeHolder extends RecyclerView.ViewHolder
    {
        private HeaderTypeHolder(View itemView)
        {
            super(itemView);
        }
    }
    private static class FooterTypeHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView cat_img;
        CardView cardView;

        private FooterTypeHolder(View itemView)
        {
            super(itemView);
            this.cat_img = (TextView) itemView.findViewById(R.id.drawer_icon);
            this.name = (TextView) itemView.findViewById(R.id.drawer_name);
            this.cardView = (CardView) itemView.findViewById(R.id.drawer_footer);
        }
    }

    public DrawerAdapter (ArrayList<DrawerModel> data, Context context, Activity activity)
    {
        this.dataSet = data;
        this.activity = activity;
        this.mContext = context;
        total_types = dataSet.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case DrawerModel.HEADER_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_header,parent, false);
                return new HeaderTypeHolder(view);
            case DrawerModel.TEXT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_normal, parent, false);
                return new TextTypeViewHolder(view);
            case DrawerModel.EXPAND_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_drawer_list, parent, false);
                return new ExpandableTypeHolder(view);
            case DrawerModel.FOOTER_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_footer,parent,false);
                return new FooterTypeHolder(view);
        }
        return null;
    }
    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).type) {
            case 0:
                return DrawerModel.HEADER_TYPE;
            case 1:
                return DrawerModel.TEXT_TYPE;
            case 2:
                return DrawerModel.EXPAND_TYPE;
            case 3:
                return DrawerModel.FOOTER_TYPE;
            default:
                return -1;
        }
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition)
    {
        final DrawerModel object = dataSet.get(listPosition);
        if (object != null)
        {
            switch (object.type)
            {
                case DrawerModel.HEADER_TYPE:
                    break;
                case DrawerModel.TEXT_TYPE:
                    ((TextTypeViewHolder) holder).txtType.setText(object.text);
                    ((TextTypeViewHolder) holder).drawer_icon.setTypeface(FontManager.getTypeface(mContext, FontManager.DRIPICONS));
                    ((TextTypeViewHolder) holder).drawer_icon.setText(mContext.getResources().getString(object.data));
                    ((TextTypeViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new Helper(activity, object.text, mContext);
                        }
                    });
                    break;
                case DrawerModel.EXPAND_TYPE:
                    ((ExpandableTypeHolder) holder).drawer_icon.setTypeface(FontManager.getTypeface(mContext, FontManager.DRIPICONS));
                    ((ExpandableTypeHolder) holder).arrow.setTypeface(FontManager.getTypeface(mContext, FontManager.DRIPICONS));
                    ((ExpandableTypeHolder) holder).men_icon.setTypeface(FontManager.getTypeface(mContext, FontManager.DRIPICONS));
                    ((ExpandableTypeHolder) holder).women_icon.setTypeface(FontManager.getTypeface(mContext, FontManager.DRIPICONS));
                    ((ExpandableTypeHolder) holder).kids_icon.setTypeface(FontManager.getTypeface(mContext, FontManager.DRIPICONS));

                    ((ExpandableTypeHolder) holder).men_icon.setText(mContext.getResources().getString(R.string.d_cat_list));
                    ((ExpandableTypeHolder) holder).women_icon.setText(mContext.getResources().getString(R.string.d_cat_list));
                    ((ExpandableTypeHolder) holder).kids_icon.setText(mContext.getResources().getString(R.string.d_cat_list));

                    ((ExpandableTypeHolder) holder).name.setText(object.text);
                    ((ExpandableTypeHolder) holder).men.setText(mContext.getResources().getString(R.string.drawer_cat_men));
                    ((ExpandableTypeHolder) holder).women.setText(mContext.getResources().getString(R.string.drawer_cat_women));
                    ((ExpandableTypeHolder) holder).kids.setText(mContext.getResources().getString(R.string.drawer_cat_kids));
                    ((ExpandableTypeHolder) holder).drawer_icon.setText(mContext.getResources().getString(object.data));
                    ((ExpandableTypeHolder) holder).arrow.setText(mContext.getResources().getString(R.string.dri_chvron));
                    ((ExpandableTypeHolder) holder).expnd_menu.setOnExpansionUpdateListener(
                            new CollapseAnimation(((ExpandableTypeHolder) holder).expnd_menu,((ExpandableTypeHolder) holder).arrow));
                    ((ExpandableTypeHolder) holder).expnd_butn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (((ExpandableTypeHolder) holder).expnd_menu.isExpanded())
                            {
                                ((ExpandableTypeHolder) holder).expnd_menu.collapse();
                            } else
                            {
                                ((ExpandableTypeHolder) holder).expnd_menu.expand();
                            }
                        }
                    });
                    ((ExpandableTypeHolder) holder).kids_btn.setOnClickListener(new catClick());
                    ((ExpandableTypeHolder) holder).men_btn.setOnClickListener(new catClick());
                    ((ExpandableTypeHolder) holder).women_btn.setOnClickListener(new catClick());
                    break;
                case DrawerModel.FOOTER_TYPE:
                    ((FooterTypeHolder) holder).name.setText(object.text);
                    ((FooterTypeHolder) holder).cat_img.setTypeface(FontManager.getTypeface(mContext, FontManager.DRIPICONS));
                    ((FooterTypeHolder) holder).cat_img.setText(mContext.getResources().getString(object.data));
                    ((FooterTypeHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity, SettingsActivity.class);
                            activity.startActivity(intent);
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class catClick implements View.OnClickListener
    {

        public catClick()
        {
        }
        @Override
        public void onClick(View view)
        {
            if (view.getId() == R.id.men_button)
            {
                Home.selected = "Men";
                Intent intent = new Intent(activity, Home.class);
                activity.startActivity(intent);
            } else if(view.getId() == R.id.women_button)
            {
                Home.selected = "Women";
                Intent intent = new Intent(activity, Home.class);
                activity.startActivity(intent);
            } else if (view.getId() == R.id.kids_button)
            {
                Log.d("Kids", "Kids was clicked we dont have kids yet");
            }
        }
    }
}
