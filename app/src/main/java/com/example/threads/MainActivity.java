package com.example.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.threads.API.CepService;
import com.example.threads.API.DataService;
import com.example.threads.Model.Cep;
import com.example.threads.Model.Foto;
import com.example.threads.Model.Postagem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button botaoRecuperar;
    private TextView textoResultado;
    private Retrofit retrofit;
    private List<Foto> listaFotos = new ArrayList<>();
    private List<Postagem> listaPostagem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRecuperar = findViewById(R.id.btnRecuperar);
        textoResultado = findViewById(R.id.txtResultado);

        retrofit = new Retrofit.Builder()
                //.baseUrl("https://viacep.com.br/ws/")
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        botaoRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recuperarListaRetrofit();
                //recuperaCepRetrofit();
                salvarPostagem();

                /*MyTask task = new MyTask();
                String urlApi = "https://blockchain.info/ticker";
                String cep = "01310100";
                String urlCep = "https://viacep.com.br/ws/" + cep + "/json/";
                task.execute(urlCep);*/
            }
        });

    }

    private void salvarPostagem() {
        Postagem postagem = new Postagem(12, "titulo Postagem", "Corpo Postagem");

        DataService service = retrofit.create(DataService.class);
        Call<Postagem> call = service.salvarPostagem(postagem);

        call.enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                if (response.isSuccessful()) {
                    Postagem popstagemResposta = response.body();
                    textoResultado.setText(
                                    "Códgo: " + response.code() +
                                    "id: " + popstagemResposta.getId() +
                                    "Titulo: " + popstagemResposta.getTitle() +
                                    "Body: " + popstagemResposta.getBody()
                    );
                }
            }

            @Override
            public void onFailure(Call<Postagem> call, Throwable t) {

            }
        });
    }

    private void recuperarListaRetrofit() {
        DataService service = retrofit.create(DataService.class);
        //Call<List<Foto>> call = service.recuperarFotos();
        Call<List<Postagem>> call = service.recuperarPostagens();

        call.enqueue(new Callback<List<Postagem>>() {
            @Override
            public void onResponse(Call<List<Postagem>> call, Response<List<Postagem>> response) {
                if (response.isSuccessful()) {
                    //listaFotos = response.body();
                    listaPostagem = response.body();

                    for (int i = 0; i < listaPostagem.size(); i++) {
                        //Foto foto = listaPostagem.get(i);
                        Postagem postagem = listaPostagem.get(i);
                        Log.d("resultado", "resultado: " + postagem.getId() + " / " + postagem.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Postagem>> call, Throwable t) {

            }
        });
    }

    private void recuperaCepRetrofit() {
        CepService service = retrofit.create(CepService.class);
        Call<Cep> call = service.recuperarCep("01001000");

        call.enqueue(new Callback<Cep>() {
            @Override
            public void onResponse(Call<Cep> call, Response<Cep> response) {
                if (response.isSuccessful()) {
                    Cep cep = response.body();
                    textoResultado.setText(cep.getLogradouro() + " / " + cep.getBairro());
                }
            }

            @Override
            public void onFailure(Call<Cep> call, Throwable t) {

            }
        });
    }


    class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {

                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                // Recupera os dados em Bytes
                inputStream = conexao.getInputStream();

                //inputStreamReader lê os dados em Bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader(inputStream);

                //Objeto utilizado para leitura dos caracteres do InpuStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";

                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            String logradouro = null;
            String cep = null;
            String complemento = null;
            String bairro = null;
            String localidade = null;
            String uf = null;

           /* String objetoValor = null;
            String valorMoeda = null;
            String simbolo = null;*/

            try {

                JSONObject jsonObject = new JSONObject(resultado);
                logradouro = jsonObject.getString("logradouro");
                cep = jsonObject.getString("cep");
                complemento = jsonObject.getString("complemento");
                bairro = jsonObject.getString("bairro");
                localidade = jsonObject.getString("localidade");
                uf = jsonObject.getString("uf");

                /*JSONObject jsonObject = new JSONObject(resultado);
                objetoValor = jsonObject.getString("BRL");

                JSONObject jsonObjectReal = new JSONObject(objetoValor);
                valorMoeda = jsonObjectReal.getString("last");
                simbolo = jsonObjectReal.getString("symbol");*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

            textoResultado.setText(logradouro + " / " + cep + " / " + complemento + " / " + bairro + " / " + localidade + " / " + uf);
            //textoResultado.setText(valorMoeda+" /"+simbolo);
        }
    }
}