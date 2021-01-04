package com.jeuxdevelopers.mvvm_tvshowsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jeuxdevelopers.mvvm_tvshowsapp.databinding.ItemEpisodeLayoutBinding;
import com.jeuxdevelopers.mvvm_tvshowsapp.models.EpisodeModel;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.MyEpisodeHolder> {
    Context context;
    List<EpisodeModel> list;

    public EpisodeAdapter(Context context, List<EpisodeModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyEpisodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyEpisodeHolder(ItemEpisodeLayoutBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyEpisodeHolder holder, int position) {
        EpisodeModel episodeModel = list.get(position);
        holder.itemEpisodeLayoutBinding.setAirDate(episodeModel.getAir_date());
        holder.itemEpisodeLayoutBinding.setEpisode(String.valueOf(episodeModel.getEpisode()));
        holder.itemEpisodeLayoutBinding.setEpisodeName(episodeModel.getName());
        holder.itemEpisodeLayoutBinding.setSeason(String.valueOf(episodeModel.getSeason()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<EpisodeModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyEpisodeHolder extends RecyclerView.ViewHolder {
        ItemEpisodeLayoutBinding itemEpisodeLayoutBinding;

        public MyEpisodeHolder(@NonNull ItemEpisodeLayoutBinding itemEpisodeLayoutBinding) {
            super(itemEpisodeLayoutBinding.getRoot());
            this.itemEpisodeLayoutBinding = itemEpisodeLayoutBinding;
        }
    }
}
