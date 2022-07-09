package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Button register;
    private EditText email,rollnumber,name;
    private TextInputEditText password;
    private static final String USER="user";
    private USer user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth=FirebaseAuth.getInstance();
        register= findViewById(R.id.regBtn);
        email=findViewById(R.id.etEmail);
        password= findViewById(R.id.etPassword);
        name= findViewById(R.id.etName);
        rollnumber= findViewById(R.id.etRollNumber);
        database= FirebaseDatabase.getInstance();
        mDatabase=database.getReference(USER);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt= email.getText().toString();
                String pass_txt= password.getText().toString();
                String name_txt= name.getText().toString();
                String roll_txt= name.getText().toString();
                user= new USer(email_txt,pass_txt,name_txt,roll_txt);
                if(TextUtils.isEmpty(email_txt)||TextUtils.isEmpty(pass_txt)){
                    Toast.makeText(RegisterActivity.this,"Credentials are Empty",Toast.LENGTH_LONG).show();
                }else if(pass_txt.length()<6){
                    Toast.makeText(RegisterActivity.this,"Password Must be at least 6 characters Long",Toast.LENGTH_LONG).show();
                }
                else{
                    registerUser(email_txt,pass_txt);
                }
            }
        });
    }
    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser fUser= auth.getCurrentUser();
                    fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(RegisterActivity.this,"Verification Email has been sent to your Email.",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                    Toast.makeText(RegisterActivity.this,"Registered User Successfully",Toast.LENGTH_LONG).show();
                    FirebaseUser user=auth.getCurrentUser();
                    Handler handler= new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateUI(user);
                            finish();
                        }
                    },1500);
                }else{
                    Toast.makeText(RegisterActivity.this,"Registering Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void updateUI(FirebaseUser currentUser) {
        String uid= currentUser.getUid();
        mDatabase.child(uid).setValue(user);
        if(currentUser!=null){
            Intent intent= new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{
        }
    }
}