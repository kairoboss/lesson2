package com.example.lesson2;

import android.app.Application;

import androidx.room.Room;

import com.example.lesson2.data.local.AppDataBase;

public class App extends Application {
    private static AppDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "database").allowMainThreadQueries().build();
    }

    public static AppDataBase getDataBase() {
        return dataBase;
    }
}
