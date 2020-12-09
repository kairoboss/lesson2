package com.example.lesson2.ui.fragments.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lesson2.App;
import com.example.lesson2.R;
import com.example.lesson2.data.models.Film;
import com.example.lesson2.databinding.FragmentFavoriteBinding;
import com.example.lesson2.ui.fragments.filmfragment.FilmsAdapter;

import java.util.List;


public class FavoriteFragment extends Fragment implements FilmsAdapter.FilmCallback {

    private FragmentFavoriteBinding binding;
    private FavoriteAdapter adapter;
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        List<Film> favList = App.getDataBase().filmDao().favouriteList();
        adapter = new FavoriteAdapter(favList, this);
        binding.favRecycler.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int pos) {
        Bundle b = new Bundle();
        b.putLong("roomId",adapter.getItem(pos).getRoomId());
        b.putString("filmId", adapter.getItem(pos).getId());
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_favoriteFragment_to_detailFragment ,b);
    }

    @Override
    public void onFavoriteClick(int pos) {
        App.getDataBase().filmDao().deleteFavorite(adapter.getItem(pos));
        adapter.removeItem(pos);
    }
}