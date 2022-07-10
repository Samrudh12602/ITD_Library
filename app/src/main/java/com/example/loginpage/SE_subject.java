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

import com.google.firebase.auth.FirebaseAuth;

public class SE_subject extends AppCompatActivity {
    private CardView cn,se,maths,dsa,ie;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_subject);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        auth=FirebaseAuth.getInstance();
        ie=findViewById(R.id.ie);
        se=findViewById(R.id.se);
        cn=findViewById(R.id.cn);
        dsa=findViewById(R.id.dsa);
        maths=findViewById(R.id.maths);

        View.OnClickListener listener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                switch (view.getId()){
                    case R.id.ie: i= new Intent(SE_subject.this,PdfActivity.class);i.putExtra("subject","IE");startActivity(i);break;
                    case R.id.maths: i= new Intent(SE_subject.this,PdfActivity.class);i.putExtra("subject","Maths");startActivity(i);break;
                    case R.id.cn: i= new Intent(SE_subject.this,PdfActivity.class);i.putExtra("subject","CN");startActivity(i);break;
                    case R.id.dsa: i= new Intent(SE_subject.this,PdfActivity.class);i.putExtra("subject","DSA");startActivity(i);break;
                    case R.id.se: i= new Intent(SE_subject.this,PdfActivity.class);i.putExtra("subject","SE");startActivity(i);break;
                }
            }
        };

        ie.setOnClickListener(listener);
        cn.setOnClickListener(listener);
        dsa.setOnClickListener(listener);
        se.setOnClickListener(listener);
        maths.setOnClickListener(listener);
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
            case R.id.about:break;
            case android.R.id.home:onBackPressed();return true;
            case R.id.logout:      new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            auth.signOut();startActivity(new Intent(SE_subject.this,MainActivity.class));finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();break;
        }
        return super.onOptionsItemSelected(item);
    }
}