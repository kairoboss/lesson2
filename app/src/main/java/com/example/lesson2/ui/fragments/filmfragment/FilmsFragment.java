package com.example.lesson2.ui.fragments.filmfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.lesson2.App;
import com.example.lesson2.R;
import com.example.lesson2.data.models.Film;
import com.example.lesson2.data.network.GhibliService;
import com.example.lesson2.databinding.FragmentFilmsBinding;
import com.example.lesson2.ui.fragments.detailfragment.DetailFragment;
import com.example.lesson2.ui.fragments.filmfragment.FilmsAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmsFragment extends Fragment implements FilmsAdapter.FilmCallback {
    private NavController navController;
    private static final String ARG_PARAM1 = "details";
    private FilmsAdapter filmsAdapter;
    private FragmentFilmsBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFilmsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);

    }

    private void initList(View view) {
        List<Film> list = new ArrayList<>();
        filmsAdapter = new FilmsAdapter(list, this);
        binding.filmsRecycler.setAdapter(filmsAdapter);


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
                Log.e("tag", t.getLocalizedMessage());
            }
        });

    }

    @Override
    public void onItemClick(int pos) {
        Bundle b = new Bundle();
        b.putLong("roomId",filmsAdapter.getItem(pos).getRoomId());
        b.putString("filmId", filmsAdapter.getItem(pos).getId());
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_filmsFragment_to_detailFragment, b);
    }



    @Override
    public void onFavoriteClick(int pos) {
        App.getDataBase().filmDao().addFavourite(filmsAdapter.getItem(pos));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favorites){
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_filmsFragment_to_favoriteFragment);
        }
        if (item.getItemId() == R.id.scan_qr_code){
            IntentIntegrator.forSupportFragment(this).initiateScan();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.film_menu, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.getContents() != null){
            GhibliService.getInstance().getFilm(result.getContents()).enqueue(new Callback<Film>() {
                @Override
                public void onResponse(Call<Film> call, Response<Film> response) {
                    if (response.body() != null){
                        Log.e("tag", "success");
                        Film film = response.body();
                        Bundle b = new Bundle();
                        b.putString("filmId", film.getId());
                        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.action_filmsFragment_to_detailFragment, b);
                    }
                }

                @Override
                public void onFailure(Call<Film> call, Throwable t) {
                    Log.e("tag", "lox");
                }
            });
        }else
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("tag", "not scanned");
    }
}