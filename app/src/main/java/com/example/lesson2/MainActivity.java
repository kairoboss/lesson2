package com.example.lesson2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.Toolbar;

import com.example.lesson2.data.models.Film;
import com.example.lesson2.data.network.GhibliService;
import com.example.lesson2.ui.fragments.filmfragment.FilmsAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }


}