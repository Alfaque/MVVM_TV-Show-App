package com.jeuxdevelopers.mvvm_tvshowsapp.repositories;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jeuxdevelopers.mvvm_tvshowsapp.network.ApiClient;
import com.jeuxdevelopers.mvvm_tvshowsapp.network.ApiService;
import com.jeuxdevelopers.mvvm_tvshowsapp.response.TvShowResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTvShowRepository {

    ApiService apiService;
    public final String TAG = "PopularTvShowRepository";

    public MostPopularTvShowRepository() {

        apiService = ApiClient.getInstanse().create(ApiService.class);


    }

    public LiveData<TvShowResponse> getMostPopularTvShows(int page) {
        MutableLiveData<TvShowResponse> data = new MutableLiveData<>();
        apiService.getPopularTvShows(page).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.code() == 200 && response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: getMostPopularTvShows -> error:" + t.getMessage());
            }
        });

        return data;

    }
}
