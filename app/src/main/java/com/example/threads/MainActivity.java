package com.example.threads;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ipsec.ike.exceptions.IkeInternalException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnIniciar;
    private int numero;
    //private Handler handler = new Handler();
    private boolean pararExecucao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciar = findViewById(R.id.btnIniciar);
    }

    public void inicarThread(View v){
       /* MyThread thread = new MyThread();
        thread.start();*/

        pararExecucao = false;
        MyRunnable runnable = new MyRunnable();
        new Thread(runnable).start();
    }
    public void pararThread(View v){
        pararExecucao = true;
    }

    class MyRunnable implements Runnable{
        @Override
        public void run() {
            for(int i=0;i<15;i++){

                if(pararExecucao)
                    return;

                numero = i;
                Log.d("Thread","contador: "+i);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnIniciar.setText("Contador: "+numero);
                    }
                });

                /*
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        btnIniciar.setText("Contador: "+numero);
                    }
                });*/

                try{
                    Thread.sleep(1000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            for(int i=0;i<15;i++){
                Log.d("Thread","contador: "+i);
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}