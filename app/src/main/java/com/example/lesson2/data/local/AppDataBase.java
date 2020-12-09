package com.example.lesson2.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.lesson2.data.models.Film;

@Database(entities = {Film.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract FilmDao filmDao();
}
