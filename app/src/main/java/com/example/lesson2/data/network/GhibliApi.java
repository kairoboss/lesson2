package com.example.lesson2.data.network;

import com.example.lesson2.data.models.Film;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GhibliApi {
    @GET("films/{filmId}")
    Call<Film> getFilm(
            @Path("filmId") String filmId
    );
    @GET("films")
    Call<List<Film>> getAllFilms();
}
