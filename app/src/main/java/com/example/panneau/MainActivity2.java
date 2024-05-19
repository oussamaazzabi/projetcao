package com.example.panneau;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText regEmail, regPass, regName, regPhone, regvalpass;
    Button btnSignUP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        regEmail = findViewById(R.id.edtRegEmail);
        regPass = findViewById(R.id.edtRegPassw);
        btnSignUP = findViewById(R.id.btnRegister);
        regName = findViewById(R.id.edtRegusern);
        regPhone = findViewById(R.id.edtRegphone);
        regvalpass = findViewById(R.id.edtvalPassw);

        btnSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = regEmail.getText().toString().trim();
                String username = regName.getText().toString().trim();
                String phone = regPhone.getText().toString().trim();
                String validatepass = regvalpass.getText().toString().trim();
                String password = regPass.getText().toString().trim();

                if (username.isEmpty()) {
                    regName.setError("Username is required");
                    regName.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    regPhone.setError("Phone number is required");
                    regPhone.requestFocus();
                    return;
                }

                if (validatepass.isEmpty()) {
                    regvalpass.setError("Password is required");
                    regvalpass.requestFocus();
                    return;
                }

                if (!validatepass.equals(password)) {
                    regvalpass.setError("Passwords don't match");
                    regvalpass.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    regEmail.setError("Email is required");
                    regEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    regEmail.setError("Please provide a valid email");
                    regEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    regPass.setError("Password is required");
                    regPass.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    regPass.setError("Password must be at least 6 characters long");
                    regPass.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(email, password, username, phone);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(MainActivity2.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                                        // Redirect to another activity or perform any desired action after successful registration
                                                    } else {
                                                        Toast.makeText(MainActivity2.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(MainActivity2.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}
