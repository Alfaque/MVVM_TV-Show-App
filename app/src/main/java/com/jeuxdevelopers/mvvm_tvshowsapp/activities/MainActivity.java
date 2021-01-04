package com.jeuxdevelopers.mvvm_tvshowsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.jeuxdevelopers.mvvm_tvshowsapp.R;
import com.jeuxdevelopers.mvvm_tvshowsapp.adapters.PopularTvShowsAdapter;
import com.jeuxdevelopers.mvvm_tvshowsapp.databinding.ActivityMainBinding;
import com.jeuxdevelopers.mvvm_tvshowsapp.models.TvShowModel;
import com.jeuxdevelopers.mvvm_tvshowsapp.response.TvShowResponse;
import com.jeuxdevelopers.mvvm_tvshowsapp.utilities.Constants;
import com.jeuxdevelopers.mvvm_tvshowsapp.viewmodels.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopularTvShowsAdapter.OnTvShowClicked {
    MostPopularTVShowsViewModel mostPopularTVShowsViewModel;
    ActivityMainBinding activityMainBinding;
    PopularTvShowsAdapter popularTvShowsAdapter;
    List<TvShowModel> list = new ArrayList<>();
    int currentPage = 1;
    int totalpages = 1;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mostPopularTVShowsViewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        setRecyclerViews();
        getTvShows();
        initView();
    }

    private void initView() {
        activityMainBinding.mainBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WatchListActivity.class));
            }
        });
        activityMainBinding.mainSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));

            }
        });
    }

    private void setRecyclerViews() {
        popularTvShowsAdapter = new PopularTvShowsAdapter(this, list, this, false);
        activityMainBinding.mainRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        activityMainBinding.mainRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {

                    if (currentPage <= totalpages) {
                        currentPage = currentPage + 1;
                        getTvShows();
                    }
                }
            }
        });

        activityMainBinding.mainRecyclerview.setAdapter(popularTvShowsAdapter);
    }

    private void getTvShows() {

        loadingToggle();
        mostPopularTVShowsViewModel.getPopularTvshows(currentPage).observe(this, new Observer<TvShowResponse>() {
            @Override
            public void onChanged(TvShowResponse tvShowResponse) {
                loadingToggle();

                if (tvShowResponse != null) {

                    if (tvShowResponse.getTv_shows() != null) {
                        totalpages = tvShowResponse.getPages();
                        int oldCount = list.size();
                        list.addAll(tvShowResponse.getTv_shows());
//                        popularTvShowsAdapter.setData(list);
                        popularTvShowsAdapter.notifyItemRangeInserted(oldCount, list.size());
                    }
                }
            }
        });
    }

    private void loadingToggle() {
        if (currentPage == 1) {

            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()) {
                activityMainBinding.setIsLoading(false);
            } else {
                activityMainBinding.setIsLoading(true);
            }

        } else {
            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()) {
                activityMainBinding.setIsLoadingMore(false);
            } else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTvShowClickedListener(TvShowModel tvShowModel) {

        String data = gson.toJson(tvShowModel);
        Intent intent = new Intent(this, ShowDetailActivity.class);
        intent.putExtra(Constants.TV_SHOW_MODEL, data);
        startActivity(intent);
    }

    @Override
    public void onTvShowDelClickedListener(int position, TvShowModel tvShowModel) {
        //no need to use this
    }

}