package com.example.lesson2.ui.fragments.filmfragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson2.R;
import com.example.lesson2.data.models.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.FilmViewHolder> {
    private Film film;
    private List<Film> filmList;
    private Context context;
    private FilmCallback callback;

    
    public FilmsAdapter(List<Film> list, FilmCallback callback){
        this.filmList = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        holder.bind(filmList.get(position));
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    public Film getItem(int pos){
        return filmList.get(pos);
    }

    class FilmViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.clicked(getAdapterPosition());
                }
            });
        }

        public void bind(Film film) {
            if (film == null){
                Log.e("yo", "It's OK");}
            title.setText(film.getTitle());
        }
    }
    public void addList(List<Film> list){
        filmList = new ArrayList<>();
        filmList.clear();
        filmList.addAll(list);
        notifyDataSetChanged();
    }
    public interface FilmCallback{
        void clicked(int pos);
    }
}
