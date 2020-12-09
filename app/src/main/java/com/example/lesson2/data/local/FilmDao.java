package com.example.lesson2.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lesson2.data.models.Film;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface FilmDao {
    @Insert
    void addFavourite(Film film);

    @Query("SELECT * FROM film" )
    List<Film> favouriteList();

    @Query("SELECT * FROM Film WHERE roomId = :roomId")
    Film getById(long roomId);

    @Delete
    void deleteFavorite(Film film);
}
