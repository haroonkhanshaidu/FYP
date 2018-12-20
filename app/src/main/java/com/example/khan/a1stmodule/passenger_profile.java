package com.example.khan.a1stmodule;


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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class passenger_profile extends AppCompatActivity{
    FirebaseAuth mAuth;
    FirebaseUser muser;
    Button logoutbtn;
    DrawerLayout mdrawerlayout;
    ActionBarDrawerToggle mToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_profile);
        initial();
        showbar();

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
//FirebaseUser user =mAuth.getCurrentUser();
//String photourl = user.getPhotoUrl().toString();
//String displayname =user.getDisplayName();
//TextView n= findViewById(R.id.a);
//ImageView ph=findViewById(R.id.pf);
//        Glide.with(this).load(user.getPhotoUrl().toString()).into(ph);
//        n.setText(user.getDisplayName());
    }

    void initial() {
        logoutbtn = findViewById(R.id.logoutbt);
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





    void logout() {
       mAuth.signOut();

       // Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(passenger_profile.this, login.class));
        finish();
    }







//    public void ShowPopupout(View v) {
//        TextView txtclose;
//        //Button btnFollow;
//     myDialog.setContentView(R.layout.confrmlogout);
//        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
//        sure = (Button) findViewById(R.id.surelogout);
//        //txtclose.setText("cancel");
//
//        sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logout();
//            }
//        });
//
//        txtclose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDialog.dismiss();
//            }
//        });
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.show();
//    }
}
