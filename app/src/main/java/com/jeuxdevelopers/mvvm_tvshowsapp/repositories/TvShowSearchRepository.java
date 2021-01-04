package com.jeuxdevelopers.mvvm_tvshowsapp.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jeuxdevelopers.mvvm_tvshowsapp.network.ApiClient;
import com.jeuxdevelopers.mvvm_tvshowsapp.network.ApiService;
import com.jeuxdevelopers.mvvm_tvshowsapp.response.TvShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowSearchRepository {
    ApiService apiService;

    public TvShowSearchRepository() {
        apiService = ApiClient.getInstanse().create(ApiService.class);
    }

    public LiveData<TvShowResponse> searchTvShow(String name, int page) {
        MutableLiveData<TvShowResponse> liveData = new MutableLiveData<>();
        apiService.searchShow(name, page).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.code() == 200 && response.isSuccessful() && response.body() != null) {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }

}
