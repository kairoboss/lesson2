package com.example.lesson2.ui.fragments.filmfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.lesson2.R;
import com.example.lesson2.data.models.Film;
import com.example.lesson2.data.network.GhibliService;
import com.example.lesson2.ui.fragments.detailfragment.DetailFragment;
import com.example.lesson2.ui.fragments.filmfragment.FilmsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmsFragment extends Fragment implements FilmsAdapter.FilmCallback {
    private NavController navController;
    private static final String ARG_PARAM1 = "details";
    private FilmsAdapter filmsAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public static FilmsFragment newInstance(Film film) {
        FilmsFragment fragment = new FilmsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, film.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_films, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    private void initList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.films_recycler);
        List<Film> list = new ArrayList<>();
        filmsAdapter = new FilmsAdapter(list, this);
        recyclerView.setAdapter(filmsAdapter);


        GhibliService.getInstance().getAllFilms().enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list.addAll(response.body());
                    filmsAdapter.notifyDataSetChanged();
                    Log.e("tag", "Bingo");
                }
            }

            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {
                Log.e("tag", "Failed");
            }
        });

    }

    @Override
    public void clicked(int pos) {
        Bundle b = new Bundle();
        b.putString("filmId", filmsAdapter.getItem(pos).getId());
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_filmsFragment_to_detailFragment, b);
    }
}