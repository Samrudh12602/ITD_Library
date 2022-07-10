package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PdfActivity extends AppCompatActivity {
    private RecyclerView pdfrecycler;
    private DatabaseReference reference;
    private List<PdfData> list;
    String subject;
    private PdfAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        pdfrecycler=findViewById(R.id.pdfRecycler);
        subject=getIntent().getStringExtra("subject");
        reference= FirebaseDatabase.getInstance().getReference().child("pdf").child(subject);
        getData();
    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list= new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String url= snapshot.child("pdfUrl").getValue(String.class);
                    String name= snapshot.child("pdfTitle").getValue(String.class);
                    String category= snapshot.child("pdfCategory").getValue(String.class);
                    PdfData data= new PdfData(category,name,url);
                    list.add(data);
                    adapter= new PdfAdapter(PdfActivity.this,list);
                    pdfrecycler.setLayoutManager(new LinearLayoutManager(PdfActivity.this ));
                    pdfrecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}