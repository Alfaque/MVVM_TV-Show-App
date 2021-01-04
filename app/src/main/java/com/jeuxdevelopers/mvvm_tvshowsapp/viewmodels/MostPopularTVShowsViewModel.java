package com.jeuxdevelopers.mvvm_tvshowsapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jeuxdevelopers.mvvm_tvshowsapp.repositories.MostPopularTvShowRepository;
import com.jeuxdevelopers.mvvm_tvshowsapp.response.TvShowResponse;


public class MostPopularTVShowsViewModel extends AndroidViewModel {

    MostPopularTvShowRepository mostPopularTvShowRepository;

    public MostPopularTVShowsViewModel(@NonNull Application application) {
        super(application);
        mostPopularTvShowRepository = new MostPopularTvShowRepository();
    }

    public LiveData<TvShowResponse> getPopularTvshows(int page) {
        return mostPopularTvShowRepository.getMostPopularTvShows(page);
    }
}
