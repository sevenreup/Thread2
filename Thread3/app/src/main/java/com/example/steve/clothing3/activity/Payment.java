package com.example.steve.clothing3.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.steve.clothing3.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Payment extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button airtel = (Button) findViewById(R.id.airtel);
        Button mpamba = (Button) findViewById(R.id.Tnm);
        airtel.setOnClickListener(this);
        mpamba.setOnClickListener(this);

        LinearLayout listLayout = (LinearLayout) findViewById(R.id.list);


        for (int x=0;x<Wishlist.list.size();x++){
            try {
                JSONObject object = new JSONObject(Wishlist.list.get(x));

                LinearLayout linearLayout2 = new LinearLayout(this);
                linearLayout2.setOrientation(LinearLayout.HORIZONTAL);

                TextView textView = new TextView(this);
                textView.setText(object.getString("Item")+"\t\t\t");

                TextView price = new TextView(this);
                price.setText(object.getString("Price"));

                linearLayout2.addView(textView);
                linearLayout2.addView(price);

                listLayout.addView(linearLayout2);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }









        int total=0;
        double handlig_cost=0;

        for (String i: Wishlist.list){
            try {
                JSONObject object = new JSONObject(i);
                total+=object.getInt("Price");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        handlig_cost = (total*0.06);
        total+=handlig_cost;

        TextView totalview = (TextView) findViewById(R.id.total);
        totalview.setText("K"+String.valueOf(total));
        TextView handlingview = (TextView) findViewById(R.id.handling);
        handlingview.setText("K"+String.valueOf(handlig_cost));

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.airtel){
            String ussdCode = "*"+"211"+ Uri.encode("#");
            startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:"+ussdCode)));
        }else if(id == R.id.Tnm){
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.login_dialog);
            dialog.show();
            dialog.setCancelable(true);
            Button signUp1 = (Button) dialog.findViewById(R.id.signUp1);
            signUp1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.setContentView(R.layout.signup_dialog);
                }
            });
            //String ussdCode = "*"+"211"+ Uri.encode("#");
            //startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:"+ussdCode)));
        }
    }
}
