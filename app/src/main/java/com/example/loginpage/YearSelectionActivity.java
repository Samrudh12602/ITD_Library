package com.example.loginpage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class YearSelectionActivity extends AppCompatActivity {
    Spinner yr,sem;
    Button proceed;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_selection);
        yr=findViewById(R.id.year);
        sem=findViewById(R.id.semester);
        proceed=findViewById(R.id.proceedButton);
        getSupportActionBar().hide();
        ArrayAdapter<CharSequence > ad= ArrayAdapter.createFromResource(this,R.array.year,R.layout.support_simple_spinner_dropdown_item);

        yr.setAdapter(ad);
        yr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<CharSequence > adapter =null;
                if(i==1){
                    adapter= ArrayAdapter.createFromResource(getApplicationContext(),R.array.FE,R.layout.support_simple_spinner_dropdown_item);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                }else if(i==2){
                    adapter= ArrayAdapter.createFromResource(getApplicationContext(),R.array.SE,R.layout.support_simple_spinner_dropdown_item);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                }else if(i==3){
                    adapter= ArrayAdapter.createFromResource(getApplicationContext(),R.array.TE,R.layout.support_simple_spinner_dropdown_item);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                }else if(i==4){
                    adapter= ArrayAdapter.createFromResource(getApplicationContext(),R.array.BE,R.layout.support_simple_spinner_dropdown_item);
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                }
                else if(i==0){
                    Toast.makeText(getApplicationContext(),"Please select Year First",Toast.LENGTH_LONG).show();
                }
                sem.setAdapter(adapter);
                sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(i==0){
                            Toast.makeText(getApplicationContext(),"Please select Sem First",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sem.isSelected()&&yr.isSelected()){
                    Toast.makeText(getApplicationContext(),"All good",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}