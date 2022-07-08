package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class YearSelectionActivity extends AppCompatActivity {
    CardView fe,se,te,be;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_selection);
        fe=findViewById(R.id.feCardView);
        se=findViewById(R.id.seCardView);
        te=findViewById(R.id.teCardView);
        be=findViewById(R.id.beCardView);
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
}