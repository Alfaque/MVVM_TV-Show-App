package com.jeuxdevelopers.mvvm_tvshowsapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jeuxdevelopers.mvvm_tvshowsapp.network.ApiClient;
import com.jeuxdevelopers.mvvm_tvshowsapp.network.ApiService;
import com.jeuxdevelopers.mvvm_tvshowsapp.response.TvShowDetailResponse;
import com.jeuxdevelopers.mvvm_tvshowsapp.response.TvShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailRepository {

    ApiService apiService;

    public TvShowDetailRepository() {
        apiService = ApiClient.getInstanse().create(ApiService.class);
    }

    public LiveData<TvShowDetailResponse> getTvShowDetail(int showId) {
        MutableLiveData<TvShowDetailResponse> data = new MutableLiveData<>();
        apiService.getTvShowDetail(showId).enqueue(new Callback<TvShowDetailResponse>() {
            @Override
            public void onResponse(Call<TvShowDetailResponse> call, Response<TvShowDetailResponse> response) {
                if (response.body() != null && response.isSuccessful() && response.code() == 200) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TvShowDetailResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

}
