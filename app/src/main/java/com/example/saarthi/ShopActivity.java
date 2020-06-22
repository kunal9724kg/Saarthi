package com.example.saarthi;

import com.example.saarthi.Model.Outlets;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayList<Outlets> outletsList =new ArrayList<>();

    Outlets shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Intent intent= getIntent();
        Bundle extras =intent.getExtras();
        final String Distributor = extras.getString("ExtraDistributor");
        final String Name =extras.getString("ExtraName");
        final String Routecode = extras.getString("ExtraRoutecode");
        final String Routename=extras.getString("ExtraRoutename");
        shop=new Outlets();
        listView =(ListView) findViewById(R.id.ShopList);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference();
        adapter=new ArrayAdapter<String>(this, R.layout.shop_info, R.id.ShopInfo, list);
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("outlets")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        System.out.println(dataSnapshot.getValue());
                        System.out.println(dataSnapshot.toString());
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            shop = (Outlets) ds.getValue(Outlets.class);
                            //System.out.println(route.getRouteId());
                            if(shop.getSalesmanname().equals(Name)&&shop.getRoutecode().equals(Routename))
                            {list.add(shop.getOutletname());
                            outletsList.add(shop);}
                        }
                        if(list.size()==0){
                            Toast.makeText(ShopActivity.this, "No Shops Exist", Toast.LENGTH_SHORT).show();
                        }
                        else{listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent =new Intent(ShopActivity.this, CartActivity.class);
                                Bundle extras = new Bundle();
                                extras.putString("ExtraDistributor", Distributor );
                                extras.putString("ExtraName", Name);
                                extras.putString("ExtraRoutecode",Routecode);
                                extras.putString("ExtraRoutename", Routename);
                                extras.putString("ExtraOutletname", outletsList.get(position).getOutletname());
                                intent.putExtras(extras);
                                startActivity(intent);

                            }
                        });}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}