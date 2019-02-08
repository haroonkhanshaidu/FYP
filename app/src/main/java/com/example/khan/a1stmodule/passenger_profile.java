package com.example.khan.a1stmodule;


import android.app.ActionBar;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import 	android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class passenger_profile extends AppCompatActivity{
    FirebaseAuth mAuth;
    FirebaseUser muser;
    Button actionBar;
    DrawerLayout mdrawerlayout;
    ActionBarDrawerToggle mToggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_profile);
        initial();
       // showbar();


        actionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    void initial() {
        mdrawerlayout = findViewById(R.id.drawer);
//       mAuth = FirebaseAuth.getInstance();
//       muser = FirebaseAuth.getInstance().getCurrentUser();
//       final String uid = muser.getUid();
//        DatabaseReference dbref;
//        dbref = FirebaseDatabase.getInstance().getReference();
//
//        dbref.child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                String Name= dataSnapshot.child(uid).child("Name").getValue(String.class);
//                String phone= dataSnapshot.child(uid).child("phone_no").getValue(String.class);
//                TextView name,phoneno;
//               name= findViewById(R.id.usernamefrmdb);
//               phoneno= findViewById(R.id.phonefrmdb);
//               name.setText(Name);
//               phoneno.setText(phone);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(passenger_profile.this, "no /week internet connection reload plz",
//                        Toast.LENGTH_SHORT).show();
//                return;
//            }
//        });

    }







    void showbar() {

        mToggle = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.open, R.string.close);
        mdrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);


    }



}
