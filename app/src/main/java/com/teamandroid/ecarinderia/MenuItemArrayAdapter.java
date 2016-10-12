package com.teamandroid.ecarinderia;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class MenuItemArrayAdapter  extends ArrayAdapter<MenuItem> {

    Context context;
    int layoutResourceId;
    ArrayList<MenuItem> data = null;

    public MenuItemArrayAdapter(Context context, int layoutResourceId,  ArrayList<MenuItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MenuItemHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new MenuItemHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.itemName = (TextView)row.findViewById(R.id.itemName);
            holder.itemPrice = (TextView)row.findViewById(R.id.itemPrice);
            holder.itemQuantity = (TextView)row.findViewById(R.id.itemQuantity);

            row.setTag(holder);
        }
        else
        {
            holder = (MenuItemHolder)row.getTag();
        }

        MenuItem item = data.get(position);
        holder.itemName.setText(item.getName());
        holder.itemPrice.setText("PRICE: PhP " + Double.toString(item.getPrice()));
        holder.itemQuantity.setText("AVAILABLE ORDERS: " + Integer.toString(item.getQuantity()));
        Picasso.with(context).load(item.getImageUrl()).into(holder.imgIcon);


        return row;
    }

    static class MenuItemHolder
    {
        ImageView imgIcon;
        TextView itemName;
        TextView itemPrice;
        TextView itemQuantity;
    }

}

