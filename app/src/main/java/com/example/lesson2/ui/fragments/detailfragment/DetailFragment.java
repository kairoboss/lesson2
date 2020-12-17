package com.example.lesson2.ui.fragments.detailfragment;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lesson2.App;
import com.example.lesson2.R;
import com.example.lesson2.data.models.Film;
import com.example.lesson2.data.network.GhibliService;
import com.example.lesson2.databinding.FragmentDetailBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;
    private long roomId;
    private String id;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("filmId");
            roomId = getArguments().getLong("roomId");
        }else
            Log.e("tag", "argsNull");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (haveNetwork()){
            setDetail(id);
        }
        else if (!haveNetwork()){
            setDetailsLocal(roomId);
        }
    }

    private void setDetailsLocal(long id) {
        Film film = App.getDataBase().filmDao().getById(id);
        binding.tvTitle.setText("Title: "+film.getTitle());
        binding.tvDescription.setText("Description: "+film.getDescription());
        binding.tvDirector.setText("Director: "+film.getDirector());
        binding.tvProducer.setText("Producer: "+film.getProducer());
        binding.releaseDate.setText("Release date: "+film.getReleaseDate());
        binding.rtScore.setText("Rating score: "+film.getRtScore());
    }

    private void setDetail(String id) {
        GhibliService.getInstance().getFilm(id).enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Film film = response.body();
                    Log.e("tag", "yaai");
                    binding.tvTitle.setText("title: " + film.getTitle());
                    binding.tvDescription.setText("description: " + film.getDescription());
                    binding.tvDirector.setText("director: " + film.getDirector());
                      binding.tvProducer.setText("producer: " + film.getProducer());
                    binding.releaseDate.setText("release date: " + film.getReleaseDate());
                    binding.rtScore.setText("rtScore: " + film.getRtScore());
                }
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Log.e("tag", "NO");
            }
        });
    }


    private boolean haveNetwork(){
        boolean have_WIFI= false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI"))if (info.isConnected())have_WIFI=true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE DATA"))if (info.isConnected())have_MobileData=true;
        }
        return have_WIFI||have_MobileData;
    }
}