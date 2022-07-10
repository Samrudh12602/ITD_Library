package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class TE_subjects extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_te_subjects);
        auth=FirebaseAuth.getInstance();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                break;
            case R.id.syllabus:Intent i= new Intent(TE_subjects.this,PdfActivity.class);i.putExtra("subject","Syllabus");startActivity(i);break;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.logout:      new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            auth.signOut();startActivity(new Intent(TE_subjects.this,MainActivity.class));finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();break;
        }
        return super.onOptionsItemSelected(item);
    }
}