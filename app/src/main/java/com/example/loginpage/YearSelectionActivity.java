package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class YearSelectionActivity extends AppCompatActivity {
    CardView fe,se,te,be;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_selection);
        fe=findViewById(R.id.feCardView);
        se=findViewById(R.id.seCardView);
        te=findViewById(R.id.teCardView);
        be=findViewById(R.id.beCardView);
        auth=FirebaseAuth.getInstance();
        View.OnClickListener listener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.feCardView: Toast.makeText(getApplicationContext(),"Under development",Toast.LENGTH_LONG).show();break;
                    case R.id.seCardView: startActivity(new Intent(getApplicationContext(),SE_subject.class));break;
                    case R.id.teCardView: startActivity(new Intent(getApplicationContext(),TE_subjects.class));break;
                    case R.id.beCardView: startActivity(new Intent(getApplicationContext(),BE_subjects.class));break;
                }
            }
        };
        fe.setOnClickListener(listener);
        se.setOnClickListener(listener);
        te.setOnClickListener(listener);
        be.setOnClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:startActivity(new Intent(YearSelectionActivity.this,AboutUsActivity.class));break;
            case R.id.logout:      new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            auth.signOut();startActivity(new Intent(YearSelectionActivity.this,MainActivity.class));finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        YearSelectionActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}