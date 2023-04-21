package com.example.threads.API;

import com.example.threads.Model.Foto;
import com.example.threads.Model.Postagem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {

    @GET("/photos")
    Call<List<Foto>> recuperarFotos();

    @GET("/posts")
    Call<List<Postagem>> recuperarPostagens();

    @POST("/posts")
    Call<Postagem> salvarPostagem(@Body Postagem postagem);

    @FormUrlEncoded
    @POST("/posts")
    Call<Postagem> salvarPostagem(
            @Field("userId") Integer userId,
            @Field("title") String title,
            @Field("body") String body
    );

}
