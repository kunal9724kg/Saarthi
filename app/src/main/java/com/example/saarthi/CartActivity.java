package com.example.saarthi;

import com.example.saarthi.Model.Product;
import com.example.saarthi.R;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.saarthi.MyListAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public class CartActivity extends AppCompatActivity {
    ListView list;

    List<Product> productList = new ArrayList<>();
    Product product;

    Button finalSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent intent= getIntent();
        Bundle extras =intent.getExtras();
        final String Distributor = extras.getString("ExtraDistributor");
        final String Name =extras.getString("ExtraName");
        final String Routecode = extras.getString("ExtraRoutecode");
        final String Outletname=extras.getString("ExtraOutletname");
        final String Routename=extras.getString("ExtraRoutename");

        final MyListAdapter adapter=new MyListAdapter(this, productList);
        list=(ListView)findViewById(R.id.list);
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                            product = (Product) ds.getValue(Product.class);
                            System.out.println(product);
                            productList.add(product);
                        }
                        adapter.updateData(productList);
                        list.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });







        finalSubmit = (Button) findViewById(R.id.FinalSubmit);

        finalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object > order = new HashMap<>();
                order.put("salesman", Name);
                order.put("route", Routecode);
                order.put("Outlet", Outletname);
                order.put("distributor", Distributor);
                order.put("items", adapter.getHashMap());
                order.put("timestamp", System.currentTimeMillis());
                System.out.println(order);
                FirebaseDatabase
                        .getInstance()
                        .getReference()
                        .child("orders")
                        .child(UUID.randomUUID().toString().toLowerCase(Locale.US))
                        .setValue(order)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println(e.getMessage());
                                System.out.println(e.getCause());
                                System.out.println(e.getStackTrace());
                                System.out.println(e.getClass());
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CartActivity.this, "Order Succesful", Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(CartActivity.this, ShopActivity.class);
                                Bundle extras = new Bundle();
                                extras.putString("ExtraDistributor", Distributor );
                                extras.putString("ExtraName", Name);
                                extras.putString("ExtraRoutecode",Routecode);
                                extras.putString("ExtraRoutename", Routename);
                                intent.putExtras(extras);
                                startActivity(intent);
                            }
                        });
                System.out.println(adapter.getHashMap().toString());
            }
        });


    }
}