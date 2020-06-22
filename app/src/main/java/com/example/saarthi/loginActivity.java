package com.example.saarthi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saarthi.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class loginActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText username, password;
    public String s1, s2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference Rootref;
                Rootref = FirebaseDatabase.getInstance().getReference();
                username = (EditText)findViewById(R.id.username);
                password= (EditText)findViewById(R.id.Password);

                s1=username.getText().toString();
                s2=password.getText().toString();

                Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Users U = (Users) dataSnapshot.child("salesman").child("s1").getValue(Users.class);
                        Users V = (Users) dataSnapshot.child("salesman").child("s2").getValue(Users.class);
                        Users W =(Users) dataSnapshot.child("salesman").child("s3").getValue(Users.class);
                        String check1 =U.userId;
                        String check2 = U.password;
                        String check3 =V.userId;
                        String check4= V.password;
                        String check5= W.userId;
                        String check6 =W.password;
                        // Log.i("Info", password);
                        if((s1.equals(check1)&&s2.equals(check2))||(s1.equals(check3)&&s2.equals(check4))||(s1.equals(check5)&&s2.equals(check6))){
                            Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(loginActivity.this, routeActivity.class);

                            String g= U.name;
                            if(s1.equals(check1)){
                                g=U.name;
                            }
                            if(s1.equals(check3)){
                                g=V.name;
                            }
                            if(s1.equals(check5)){
                                g=W.name;
                            }
                            Bundle extras= new Bundle();
                            extras.putString("ExtraDistributor", U.distributor );
                            extras.putString("ExtraName", g);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(loginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(loginActivity.this, "Password is incorrect2.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}

