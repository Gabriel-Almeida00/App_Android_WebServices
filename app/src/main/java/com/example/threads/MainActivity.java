package com.example.threads;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ipsec.ike.exceptions.IkeInternalException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
    }

    public void IniciarTaskAsync(View v){

    }

    class MyAsyncTask extends AsyncTask<Integer,Integer,String >{
        @Override
        protected String doInBackground(Integer... integers) {
            return null;
        }
    }

}