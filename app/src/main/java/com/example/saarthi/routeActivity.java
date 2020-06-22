package com.example.saarthi;

import com.example.saarthi.Model.Routes;
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

import java.util.ArrayList;

public class routeActivity extends AppCompatActivity {
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayList<Routes> RoutesList =new ArrayList<>();

    Routes route;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        Intent intent= getIntent();
        Bundle extras =intent.getExtras();
        final String Distributor = extras.getString("ExtraDistributor");
        final String Name =extras.getString("ExtraName");
        System.out.println(Distributor);
        System.out.println(Name);
        route=new Routes();
        listView =(ListView) findViewById(R.id.RouteList);
        database=FirebaseDatabase.getInstance();
        ref=database.getReference();
        adapter=new ArrayAdapter<String>(this, R.layout.route_info, R.id.RouteInfo, list);
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("routes")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        System.out.println(dataSnapshot.getValue());
                        System.out.println(dataSnapshot.toString());
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            route = (Routes) ds.getValue(Routes.class);
                            System.out.println(route.getRoutename());
                            RoutesList.add(route);
                            list.add(route.getRoutename());
                        }
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent =new Intent(routeActivity.this, ShopActivity.class);
                                Bundle extras = new Bundle();
                                extras.putString("ExtraDistributor", Distributor );
                                extras.putString("ExtraName", Name);
                                extras.putString("ExtraRoutename",RoutesList.get(position).getRoutecode());
                                extras.putString("ExtraRoutecode", RoutesList.get(position).getRoutename());
                                intent.putExtras(extras);
                                startActivity(intent);

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



    }
}