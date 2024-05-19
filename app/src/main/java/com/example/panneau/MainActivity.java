package com.example.panneau;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView regtxt;
    Button btnLogin;
    EditText edtEmail,edtPassW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        regtxt = findViewById(R.id.regSignUPtxt);
        btnLogin = findViewById(R.id.btnlogin);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassW = findViewById(R.id.edtPassw);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String StrEmail = edtEmail.getText().toString().trim();
                String StrPassword = edtPassW.getText().toString().trim();
                if(StrEmail.isEmpty())
                {
                    edtEmail.setError("Invalide!");
                    edtEmail.requestFocus();
                    return;
                }
                if(StrPassword.isEmpty())
                {
                    edtPassW.setError("Invalide!");
                    edtPassW.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(StrEmail,StrPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(task.isSuccessful()){
                                FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();

                                if(user.isEmailVerified()){
                                }
                                startActivity(new Intent(MainActivity.this, Dashboard.class));
                            }else{
                                Toast.makeText(MainActivity.this,"failed to login",Toast.LENGTH_LONG).show();
                            }
                        }
                    };});
            }
        });
        regtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(i);
            }
        });
    }
}