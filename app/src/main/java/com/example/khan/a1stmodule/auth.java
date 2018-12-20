package com.example.khan.a1stmodule;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class auth extends AppCompatActivity {


    EditText mphoneno,mcode;
    Button msendcode;
    TextView errormsg;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    FirebaseAuth mAuth;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    int btntype = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
//for design checking




        mphoneno = (EditText) findViewById(R.id.phoneno);
        mcode = (EditText) findViewById(R.id.code);
        msendcode = (Button) findViewById(R.id.sendcode);
        errormsg = (TextView) findViewById(R.id.error);

        mAuth = FirebaseAuth.getInstance();

        msendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btntype == 0){
                String phone = mphoneno.getText().toString();

             //   request that Firebase verify the user's phone number
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phone,
                        60,
                        TimeUnit.SECONDS,
                        auth.this,   //Activity (for callback binding)
                        mcallbacks);        //OnVerificationStateChangedCallbacks
                // pass Activity to the verifyPhoneNumber method
                // callbacks  auto-detached when the Activity stops

            }
            else {
                    msendcode.setEnabled(false);
                    String verificationcode = mcode.getText().toString();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,verificationcode);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
                mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        //invoked when an invalid request for verification is made
                        errormsg.setText("error was occured in verification");
                        errormsg.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {

                        //SMS verification code has been sent


                        mVerificationId = verificationId;
                        mResendToken = token;
                        //Save verification ID and resending token so we can use them later
                        btntype = 1;
                        msendcode.setText("verify code");

                        msendcode.setEnabled(true);

                        // ...
                    }
                };
                }

    //create a PhoneAuthCredential object, using the verification code and the verification ID

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            Intent mainintent = new Intent(auth.this,MainActivity.class);
                            startActivity(mainintent);
                            finish();

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                           // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            errormsg.setText("error was occured");
                            errormsg.setVisibility(View.VISIBLE);
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}
