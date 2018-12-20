package com.example.khan.a1stmodule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class registration extends AppCompatActivity implements View.OnClickListener{
   // Dialog myDialog;

    private FirebaseAuth mAuth;
    String phonenumber,forgeteduser;
    EditText phone_no,code;
    Button verify,sendcode;
    String mVerificationId;

//    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Intent intent = getIntent();
        forgeteduser = intent.getStringExtra("forgot");


        findViewById(R.id.lay2).setVisibility(LinearLayout.INVISIBLE);
        initFields();
        mAuth = FirebaseAuth.getInstance();
        initFireBaseCallbacks();
    }



    void initFields() {
        phone_no = (EditText)findViewById(R.id.phoneno);
        code = (EditText)findViewById(R.id.code);

        sendcode = findViewById(R.id.sendcode);
        verify = findViewById(R.id.verify);

        verify.setOnClickListener(this);
        sendcode.setOnClickListener(this);
    }




    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    void initFireBaseCallbacks() {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                if(forgeteduser=="forgoten") {
                    startActivity(new Intent(registration.this, passenger_profile.class));
                    //Toast.makeText(MainActivity.this, "Verification Complete", Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivity(new Intent(registration.this,registration2.class));
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(registration.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                Toast.makeText(registration.this, "Try Again", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(registration.this,registration.class));

            }

            @Override
            public void onCodeSent(String verificationId,PhoneAuthProvider.ForceResendingToken token) {
                //findViewById(R.id.pbar).setVisibility(View.INVISIBLE);
                findViewById(R.id.lay2).setVisibility(LinearLayout.VISIBLE);
                findViewById(R.id.pbar).setVisibility(LinearLayout.INVISIBLE);

                Toast.makeText(registration.this, "Code Sent", Toast.LENGTH_SHORT).show();
                mVerificationId = verificationId;
//                findViewById(R.id.verify).setVisibility(View.VISIBLE);
//                findViewById(R.id.code).setVisibility(View.VISIBLE);


            }
        };
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sendcode:
                sendcode();
                break;
            case R.id.verify:
               verify();
                break;


        }
    }
    void sendcode() {

            findViewById(R.id.pbar).setVisibility(View.VISIBLE);
            findViewById(R.id.lay1).setVisibility(LinearLayout.INVISIBLE);

            int lenth =  phone_no.getText().toString().length();
            if(lenth !=13 )
            {
                Toast.makeText(registration.this, "plz enter a valid phone no", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(registration.this,registration.class));
            }
        else {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phone_no.getText().toString(),        // Phone number to verify
                        1,                 // Timeout duration
                        TimeUnit.MINUTES,   // Unit of timeout
                        this,               // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallbacks
            }
        }

    void verify()
    {
        findViewById(R.id.pbar).setVisibility(View.VISIBLE);
        int lenth = code.getText().toString().length();
        if(lenth ==6 ) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code.getText().toString());
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                findViewById(R.id.pbar).setVisibility(View.INVISIBLE);
                                Toast.makeText(registration.this, "phone NO verified", Toast.LENGTH_SHORT).show();

                                Intent registration2 = new Intent(registration.this, registration2.class);
                                registration2.putExtra("number", phone_no.getText().toString());

                                startActivity(registration2);
                                finish();

                            } else {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(registration.this, "Verification Failed re send code", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(registration.this,registration.class));

                                }l
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(registration.this, "Enter complete code", Toast.LENGTH_SHORT).show();
            return;
            //startActivity(new Intent(registration.this,registration.class));
        }
    }


//        myDialog = new Dialog(this);
//
//    }
//
//    public void ShowPopup(View v) {
//        TextView txtclose;
//        Button btnFollow;
//        myDialog.setContentView(R.layout.custompopup);
//        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
//        txtclose.setText("X");
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
