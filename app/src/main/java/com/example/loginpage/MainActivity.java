package com.example.loginpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class
MainActivity extends AppCompatActivity {
    Button login,register;
    EditText mail,pass;
    TextInputLayout passwordLayout;
    String email,password;
    ProgressDialog pd;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        passwordLayout= findViewById(R.id.passwordLayout);
        login = findViewById(R.id.login);
        register = findViewById(R.id.registerBtn);
        mail = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(pass.getText().toString())){
                    passwordLayout.setHint("password");
                }else{
                    passwordLayout.setHint(null);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });
    }
    public void validateUser(){
        email = mail.getText().toString();
        password = pass.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Empty Email", Toast.LENGTH_LONG).show();
            mail.requestFocus();
        } else if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Password Empty ", Toast.LENGTH_LONG).show();
            pass.requestFocus();
        } else if(password.length()<6){

        } else {
            loginUser(email, password);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this,YearSelectionActivity.class));
            finish();
        }
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("Logging In....");
                pd.setCancelable(false);
                pd.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, YearSelectionActivity.class);
                        startActivity(i);
                        pd.dismiss();
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        finish();
                    }
                }, 1500);
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