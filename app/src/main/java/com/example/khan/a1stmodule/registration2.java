package com.example.khan.a1stmodule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class registration2 extends AppCompatActivity{
    Button submit;
    private static final String TAG = "AddToDatabase";
    private FirebaseAuth mAuth;
    private EditText password,password2,name,email;
    String phonenumber;
    private EditText username;
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);

         mAuth = FirebaseAuth.getInstance();
        phonenumber = getIntent().getExtras().getString("number");
                 initialfields();

    }

    public void initialfields()

    {
        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        password2 = (EditText)findViewById(R.id.password2);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        if(mAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(registration2.this,passenger_profile.class));
        }
    }


    void register() {

        final String Name = name.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Password1 = password.getText().toString().trim();
        String Password2 = password2.getText().toString().trim();
        Query usernamequery = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("phone_no").equalTo(phonenumber);
        usernamequery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    Toast.makeText(getApplicationContext(), "this phone no is already used", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "use new phone no", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                        reg(Name,Email,Password1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Name)) {
            Toast.makeText(this, "enter name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password1)) {
            Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (Password1 != Password2){
//            Toast.makeText(this, "enter same password", Toast.LENGTH_SHORT).show();
//            return;
//        }

    }
      public void reg(String Name,String Email,String Password1) {
            final String fname = Name;
            progressDialog.setMessage("creating new account plz wait.....");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(Email,Password1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            try {
                                //check if successful
                                if (task.isSuccessful()) {
                                    //User is successfully registered and logged in
                                    //start Profile Activity here
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    DatabaseReference currentuser_db = FirebaseDatabase.getInstance().getReference().child("users").child(user_id);
                                    Map newpost =new HashMap<>();
                                    newpost.put("Name",fname);
                                    newpost.put("phone_no",phonenumber);
                                    currentuser_db.setValue(newpost);
                                    progressDialog.dismiss();
                                    Toast.makeText(registration2.this, "registration successful",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), registration3.class));
                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(registration2.this, "Couldn't register, try again",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
        }

    }






//        public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.submit:
//
//
////                        username.setText(phonenumber);
////                        upassword = (EditText) findViewById(R.id.password);
////                        submit = (Button) findViewById(R.id.submit);
////                        upassword.setText(((EditText) findViewById(R.id.password)).getText());
////                        String p = upassword.toString();
////                        String n = phonenumber;
////                        RegisterUser(n,p);
//
//                }
//
//        }



 //       public void RegisterUser(String a,String b){


//            if (TextUtils.isEmpty(Password)){
//                Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            String username1 = a;
//            String password1 = b;
//            mAuth.createUserWithEmailAndPassword(username1, password1)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//                                //check if successful
//                                if (task.isSuccessful()) {
//                                    //User is successfully registered and logged in
//                                    //start Profile Activity here
//                                    Toast.makeText(registration2.this, "registration successful",
//                                            Toast.LENGTH_SHORT).show();
//                                    finish();
//                                    startActivity(new Intent(getApplicationContext(), registration3.class));
//                                } else {
//                                    Toast.makeText(registration2.this, "Couldn't register, try again",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                    });
//        }






