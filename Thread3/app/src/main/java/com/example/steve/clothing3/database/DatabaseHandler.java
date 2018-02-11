package com.example.steve.clothing3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Steve on 12/12/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "clothing";
    private static final String TABLE_WISHLIST = "wishlist";
    private static final String TABLE_CART = "cart";
    private static final String TABLE_LOGIN = "logins";

    private static final String USERNAME = "Username";
    private static final String LOGIN_ID = "ID";
    private static final String ITEM = "Item";
    private static final String ITEM_NAME = "Item_Name";
    private static final String BRAND = "Brand";
    private static final String SIZE = "Size";
    private static final String COLOR = "Color";
    private static final String PRICE = "Price";
    private static final String LIST = "List";
    Context context;

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_WISHLIST_TABLE = "CREATE TABLE " +TABLE_WISHLIST+"("
                +ITEM+" VARCHAR PRIMARY KEY,"+ITEM_NAME+" VARCHAR,"+BRAND+" VARCHAR,"+SIZE+" TEXT,"
                +COLOR+" TEXT,"+PRICE+" INTEGER,"+LIST+" VARCHAR"+")";
        db.execSQL(CREATE_WISHLIST_TABLE);

        String CREATE_CART_TABLE = "CREATE TABLE " +TABLE_CART+"("
                +ITEM+" VARCHAR PRIMARY KEY,"+ITEM_NAME+" VARCHAR,"+BRAND+" VARCHAR,"+SIZE+" TEXT,"
                +COLOR+" TEXT,"+PRICE+" INTEGER"+")";
        db.execSQL(CREATE_CART_TABLE);

        String CREATE_LOGIN_TABLE = "CREATE TABLE "+TABLE_LOGIN+"("+LOGIN_ID+"INTEGER PRIMARY KEY,"+USERNAME+" VARCHAR )";
        db.execSQL(CREATE_LOGIN_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_WISHLIST);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_LOGIN);

        onCreate(db);
    }

    public void makeWish(String data){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            JSONObject object = new JSONObject(data);
            ContentValues values = new ContentValues();
            values.put(ITEM,object.getString("Item"));
            values.put(BRAND,object.getString("Brand"));
            values.put(SIZE,object.getString("Size"));
            //values.put(COLOR,object.getString("Color"));
            values.put(PRICE,object.getString("Price"));
            values.put(LIST,"General");

            db.insert(TABLE_WISHLIST,null,values);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addCart(String data){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(data+"this is wat has been printed");
        try {
            JSONObject object = new JSONObject(data);
            ContentValues values = new ContentValues();
            values.put(ITEM,object.getString("Item"));
            values.put(BRAND,object.getString("Brand"));
            values.put(SIZE,object.getString("Size"));
            //values.put(COLOR,object.getString("Color"));
            values.put(PRICE,object.getString("Price"));

            db.insert(TABLE_CART,null,values);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<String> getWish(String list_name){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_WISHLIST+" WHERE 1", null);
        if (cursor != null){
            for (int x=0;cursor.getCount()>x;x++){
                JSONObject output = new JSONObject();
                cursor.moveToPosition(x);
                try {
                    output.put("Item",cursor.getString(0));
                    output.put("Item_Name",cursor.getString(1));
                    output.put("Brand",cursor.getString(2));
                    output.put("Size",cursor.getString(3));
                    output.put("Color",cursor.getString(4));
                    output.put("Price",cursor.getString(5));
                    output.put("List",cursor.getString(6));
                    array.add(output.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }

        db.close();
        return array;
    }

    public ArrayList<String> getCart(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> array = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_CART+" WHERE 1", null);
        if (cursor != null){
            for (int x=0;cursor.getCount()>x;x++){
                JSONObject output = new JSONObject();
                cursor.moveToPosition(x);
                try {
                    output.put("Item",cursor.getString(0));
                    output.put("Item_Name",cursor.getString(1));
                    output.put("Brand",cursor.getString(2));
                    output.put("Size",cursor.getString(3));
                    output.put("Color",cursor.getString(4));
                    output.put("Price",cursor.getString(5));
                    array.add(output.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }

        db.close();
        return array;
    }


    public void loggedIn(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Username",username);

        db.insert(TABLE_LOGIN,null,values);
    }






    public void check(String category){
        /*final ProgressDialog load_dialog = new ProgressDialog(context);
        load_dialog.setMessage("Loading");
        load_dialog.setCancelable(false);
        load_dialog.show();
        final int[] count = {0};
        final SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(count[0]+"this is the count");
        if(category.contains("Tuto Sports")){
            category="sh001";
        }else if (category.contains("Vanzara")){
            category = "sh002";
        }else if (category.contains("Vanzara")){
            category = "sh002";
        }else if (category.contains("No Overprice")){
            category = "sh003";
        }else if (category.contains("White Rose")){
            category = "sh004";
        }else if (category.contains("Top Man")){
            category = "sh005";
        }
        final String finalCategory = category;
        new WebRequest(context)
                .setUrl("http://192.168.173.1/clothing/content.php?catcont&cat="+category)
                .setMethod(Request.Method.GET)
                .readFromURL()
                .onListener(new WebRequest.VolleyListener(){
                    @Override
                    public void onReceive(String data) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(data);
                            count[0] = new JSONObject(jsonArray.get(0).toString()).length();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ContentValues value = new ContentValues();
                        if(count[0] <= 3 && count[0] != 0){
                            System.out.println(data+"Create Table here");
                            String CREATE_TABLE = "CREATE TABLE "+ finalCategory +"(`#` INTEGER PRIMARY KEY, "+ITEM+" VARCHAR )";
                            db.execSQL(CREATE_TABLE);
                            for (int x=0;jsonArray.length()>x;x++){
                                try {
                                    JSONObject jsonObject = new JSONObject(jsonArray.getString(x));
                                    value.put("Item",jsonObject.getString("Item"));
                                    db.insert(finalCategory,null,value);
                                    System.out.println(jsonObject.getString("Item"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            replicate(finalCategory);
                            load_dialog.dismiss();
                        }else if (count[0]>3){
                            System.out.println("Start intent here");
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
                                load_dialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                load_dialog.dismiss();
                            }
                        }else if (count[0] == 0){
                            load_dialog.dismiss();
                            Dialog dialog = new Dialog(context);
                            dialog.setTitle("Check your internet connection");
                            dialog.setCancelable(true);
                            dialog.setContentView(R.layout.conn_dialog);
                            dialog.show();
                            System.out.println("check your internet connection");
                        }
                    }

                    @Override
                    public void onFail(VolleyError volleyError) {
                        System.out.println("Sumnz rong");
                    }
                });*/
    }




}
