package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.stream.Stream;

public class PdfViewActivity extends AppCompatActivity {
    private String url;
    private PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        url=getIntent().getStringExtra("pdfUrl");
        pdfView=findViewById(R.id.pdfView);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new PdfDownload().execute(url);
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
            case R.id.about:
            case R.id.logout:
                break;
            case android.R.id.home:onBackPressed();return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class PdfDownload extends AsyncTask<String,Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inpurStream= null;
            try {
                URL url= new URL(strings[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();

                if(connection.getResponseCode()==200){
                    inpurStream=new BufferedInputStream(connection.getInputStream());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return inpurStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
           pdfView.fromStream(inputStream).load();
        }
    }
}