package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button login,register;
    EditText mail,pass;
    String email,password;
    ProgressDialog pd;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        login = findViewById(R.id.login);
        register = findViewById(R.id.registerBtn);
        mail = findViewById(R.id.email);
        pass = findViewById(R.id.password);

        email = mail.getText().toString();
        password = pass.getText().toString();
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Empty Credentials", Toast.LENGTH_LONG).show();
                } else if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password Too short", Toast.LENGTH_LONG).show();
                } else {
//                    loginUser(email, password);
                }
                startActivity(new Intent(MainActivity.this, YearSelectionActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });
    }
    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                pd= new ProgressDialog(MainActivity.this);
                pd.setMessage("Logging In....");
                pd.setCancelable(false);
                pd.show();
                Handler handler= new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i=new Intent(MainActivity.this,YearSelectionActivity.class);
                        startActivity(i);
                        pd.dismiss();
                        Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                        FirebaseUser user= mAuth.getCurrentUser();
                        updateUI(user);
                        finish();
                    }
                },1500);
            }
        });
    }

    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Verification Email sent", Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null){
            Intent i = new Intent(MainActivity.this,YearSelectionActivity.class);
            startActivity(i);
            finish();
        }
    }
}