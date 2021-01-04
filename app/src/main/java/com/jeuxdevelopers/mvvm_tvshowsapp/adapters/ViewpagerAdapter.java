package com.jeuxdevelopers.mvvm_tvshowsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jeuxdevelopers.mvvm_tvshowsapp.databinding.ItemViewpagerBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerAdapter extends RecyclerView.Adapter<ViewpagerAdapter.MyHolder> {
    Context context;
    List<String> list = new ArrayList<>();

    public ViewpagerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemViewpagerBinding viewpagerBinding = ItemViewpagerBinding.inflate(layoutInflater, parent, false);

        return new MyHolder(viewpagerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String imageUrl = list.get(position);
        holder.viewpagerBinding.setImageUrl(imageUrl);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ItemViewpagerBinding viewpagerBinding;

        public MyHolder(@NonNull ItemViewpagerBinding viewpagerBinding) {
            super(viewpagerBinding.getRoot());
            this.viewpagerBinding = viewpagerBinding;
        }

        public void setImage(String imageurl) {
            viewpagerBinding.setImageUrl(imageurl);
        }
    }
}
