package com.example.lesson2.ui.fragments.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson2.R;
import com.example.lesson2.data.models.Film;
import com.example.lesson2.ui.fragments.filmfragment.FilmsAdapter;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Film> favList;
    private FilmsAdapter.FilmCallback callback;

    public FavoriteAdapter(List<Film> favList, FilmsAdapter.FilmCallback callback) {
        this.favList = favList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav, parent, false);
        return new FavoriteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.bind(favList.get(position));
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    public Film getItem(int pos){
        return favList.get(pos);
    }

    public void removeItem(int pos) {
        favList.remove(pos);
        notifyItemRemoved(pos);
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder{
        private TextView favTitle;
        private Button unFavBtn;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            unFavBtn = itemView.findViewById(R.id.unfav_btn);
            favTitle = itemView.findViewById(R.id.fav_title);
            itemView.setOnClickListener(v -> callback.onItemClick(getAdapterPosition()));
            unFavBtn.setOnClickListener(v -> callback.onFavoriteClick(getAdapterPosition()));

        }
        public void bind(Film film){
            favTitle.setText(film.getTitle());
        }
    }
}
