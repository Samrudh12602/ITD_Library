package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PdfActivity extends AppCompatActivity {
    private RecyclerView pdfrecycler;
    FirebaseAuth auth;
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
        switch (item.getItemId()){
            case R.id.about:startActivity(new Intent(PdfActivity.this,AboutUsActivity.class));break;
            case R.id.logout:auth.signOut();startActivity(new Intent(PdfActivity.this,MainActivity.class));finish();break;
        }
        return super.onOptionsItemSelected(item);
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