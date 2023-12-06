package com.apx.radiance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apx.radiance.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getName();
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fireStoreDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStoreDatabase = FirebaseFirestore.getInstance();

        userRegistration();

    }

    private void userRegistration(){

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

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                                User user = new User();
                                user.setEmail(currentUser.getEmail());
                                user.setUserType("user");

                                fireStoreDatabase.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                        currentUser.sendEmailVerification();
//                                        Toast.makeText(SignUpActivity.this, "Please verify your email", Toast.LENGTH_LONG).show();

                                        CoordinatorLayout coordinatorLayout = findViewById(R.id.signInCoordinatiorLayout);

                                        Snackbar.make(coordinatorLayout, "Please Verify Your Email", Snackbar.LENGTH_LONG)
                                                .setAction("Open Email",
                                                        new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                                                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                ).show();

//                                        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignUpActivity.this, "Error. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else {

                                Toast.makeText(SignUpActivity.this, "Registration fail", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }

            }
        });
    }
}