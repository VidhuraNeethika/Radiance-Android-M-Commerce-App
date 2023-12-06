package com.apx.radiance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUpBtnTop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUp();
            }
        });

        userLogin();

    }

    public void goToSignUp() {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    private void userLogin(){

        EditText emailEditText = findViewById(R.id.emailFieldSignIn);
        EditText passwordEditText = findViewById(R.id.passwordFieldSignIn);

        findViewById(R.id.signInBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(email.isEmpty()){
                    emailEditText.setError("Please enter your email address");
                    emailEditText.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailEditText.setError("Please enter valid email address");
                    emailEditText.requestFocus();
                }else if(password.isEmpty()){
                    passwordEditText.setError("Please enter your password");
                    passwordEditText.requestFocus();
                }else{

                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                                if(currentUser.isEmailVerified()){
                                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                }else{
                                    Toast.makeText(SignInActivity.this, "Please verify your email", Toast.LENGTH_LONG).show();
                                }

                            }else{
                                Toast.makeText(SignInActivity.this, "Invalid details.Please try again.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignInActivity.this, "Invalid details.Please try again.", Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        });

    }

}