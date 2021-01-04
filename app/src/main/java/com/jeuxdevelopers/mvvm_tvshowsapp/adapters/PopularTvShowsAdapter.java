package com.jeuxdevelopers.mvvm_tvshowsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jeuxdevelopers.mvvm_tvshowsapp.databinding.ItemTvShowBinding;
import com.jeuxdevelopers.mvvm_tvshowsapp.models.TvShowModel;

import java.util.List;

public class PopularTvShowsAdapter extends RecyclerView.Adapter<PopularTvShowsAdapter.MyShowsHolder> {

    Context context;
    List<TvShowModel> list;
    OnTvShowClicked onTvShowClicked;
    boolean isWatchList;

    public PopularTvShowsAdapter(Context context, List<TvShowModel> list, OnTvShowClicked onTvShowClicked, boolean isWatchList) {
        this.context = context;
        this.list = list;
        this.onTvShowClicked = onTvShowClicked;
        this.isWatchList = isWatchList;
    }

    @NonNull
    @Override
    public MyShowsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyShowsHolder(ItemTvShowBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyShowsHolder holder, int position) {
        holder.itemTvShowBinding.setTvshow(list.get(position));
        holder.itemTvShowBinding.executePendingBindings();
        holder.itemTvShowBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTvShowClicked.onTvShowClickedListener(list.get(position));
            }
        });

        holder.itemTvShowBinding.setIsWatchList(isWatchList);
        if (isWatchList) {
            holder.itemTvShowBinding.itemTvShowDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTvShowClicked.onTvShowDelClickedListener(position, list.get(position));
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<TvShowModel> tv_shows) {
        this.list = tv_shows;
        notifyDataSetChanged();
    }

    public class MyShowsHolder extends RecyclerView.ViewHolder {
        ItemTvShowBinding itemTvShowBinding;

        public MyShowsHolder(@NonNull ItemTvShowBinding itemTvShowBinding) {
            super(itemTvShowBinding.getRoot());
            this.itemTvShowBinding = itemTvShowBinding;
        }
    }

    public interface OnTvShowClicked {

        public void onTvShowClickedListener(TvShowModel tvShowModel);

        public void onTvShowDelClickedListener(int position, TvShowModel tvShowModel);

    }
}
