package com.example.loginpage;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
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
    final static int REQ=100;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if(checkPermissions()){

        }else{
            requestPermissions();
        }
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
    private void requestPermissions(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            try{
                Intent i= new Intent();
                i.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package",this.getPackageName(),null);
                i.setData(uri);
                storageActivityResultLauncher.launch(i);
            }catch (Exception e){
                e.printStackTrace();
                Intent i= new Intent();
                i.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                storageActivityResultLauncher.launch(i);
            }
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},REQ);
        }
    }
    private ActivityResultLauncher<Intent> storageActivityResultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
                if(Environment.isExternalStorageManager()){

                }
            }
        }
    });
    public boolean checkPermissions(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        }else{
            int write= ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read= ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);

            return write== PackageManager.PERMISSION_GRANTED && read==PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQ){
            if(grantResults.length>0){
                boolean write=grantResults[0]== PackageManager.PERMISSION_GRANTED;
                boolean read=grantResults[0]== PackageManager.PERMISSION_GRANTED;
            }
        }
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