package com.example.khan.a1stmodule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText password;
    private EditText email;
    RelativeLayout rellay1, rellay2;
    Button login_btn,sign_up,forgot_btn;
    ProgressDialog progressDialog;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initial();
//        mAuth.signOut();
        }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login_btn:
                login();
                break;
            case R.id.sign_up:
                RegisterUser();
                break;
            case R.id.forgot:
                forgot();
                break;
        }
    }



void initial(){

    rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
    rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
    handler.postDelayed(runnable, 1500); //2000 is the timeout for the splash

    email = (EditText)findViewById(R.id.email);
    password = (EditText) findViewById(R.id.password) ;

    login_btn =(Button)findViewById(R.id.login_btn);
    login_btn.setOnClickListener(this);

    sign_up = (Button) findViewById(R.id.sign_up);
    sign_up.setOnClickListener(this);

    forgot_btn = (Button) findViewById(R.id.forgot);
    forgot_btn.setOnClickListener(this);

    progressDialog = new ProgressDialog(this);
    mAuth = FirebaseAuth.getInstance();
    if(mAuth.getCurrentUser()!=null){
        finish();
        startActivity(new Intent(login.this,passenger_profile.class));
    }
}



public void login()
{
    String Email = email.getText().toString().trim();
    int elenth = email.getText().toString().length();
    String Password = password.getText().toString().trim();
    int plenth = password.getText().toString().length();

    if (TextUtils.isEmpty(Email)){
        Toast.makeText(this, "enter email", Toast.LENGTH_SHORT).show();
        return;
    }
    if (TextUtils.isEmpty(Password)){
        Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
        return;
    }
    progressDialog.setMessage("loading plz wait....");
    progressDialog.show();
    mAuth.signInWithEmailAndPassword(Email,Password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();

                    if(task.isSuccessful()){
                        finish();
                    startActivity(new Intent(login.this,passenger_profile.class));
                    }
                    else {
                        Toast.makeText(login.this, "incorrect email/password ", Toast.LENGTH_SHORT).show();
                        Toast.makeText(login.this, "enter valid account detail", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });

}
    public void RegisterUser() {

        startActivity(new Intent(login.this, registration.class));
        finish();
    }

   void forgot() {
        String forget = "forgoten";
        Intent intent = new Intent(login.this,registration.class);
        intent.putExtra("forgot",forget);
        startActivity(intent);
   }
}
