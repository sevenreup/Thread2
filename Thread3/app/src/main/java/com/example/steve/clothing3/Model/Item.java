package com.example.steve.clothing3.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SevenReup4ill on 1/21/2018.
 */

public class Item implements Parcelable
{
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    String name, brand, size, shop_id;
    int number, rated_count, price;

    public Item(String name,  String size, String shop_id, int number, int rated_count, int price) {
        this.name = name;
       // this.brand = brand;
        this.size = size;
        this.shop_id = shop_id;
        this.number = number;
        this.rated_count = rated_count;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getSize() {
        return size;
    }

    public String getShop_id() {
        return shop_id;
    }

    public int getNumber() {
        return number;
    }

    public int getRated_count() {
        return rated_count;
    }

    public int getPrice()
    {
        return price;
    }
}
