package com.example.threads.API;

import com.example.threads.Model.Cep;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CepService {

    @GET("01001000/json/")
    Call<Cep> recuperarCep();
}
