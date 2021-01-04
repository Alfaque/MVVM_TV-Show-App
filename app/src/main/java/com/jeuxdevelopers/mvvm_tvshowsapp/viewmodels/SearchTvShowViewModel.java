package com.jeuxdevelopers.mvvm_tvshowsapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jeuxdevelopers.mvvm_tvshowsapp.repositories.TvShowSearchRepository;
import com.jeuxdevelopers.mvvm_tvshowsapp.response.TvShowResponse;

public class SearchTvShowViewModel extends AndroidViewModel {
    TvShowSearchRepository tvShowSearchRepository;

    public SearchTvShowViewModel(@NonNull Application application) {
        super(application);
        tvShowSearchRepository = new TvShowSearchRepository();
    }

    public LiveData<TvShowResponse> searchTvShow(String query, int page) {
        return tvShowSearchRepository.searchTvShow(query, page);
    }
}
