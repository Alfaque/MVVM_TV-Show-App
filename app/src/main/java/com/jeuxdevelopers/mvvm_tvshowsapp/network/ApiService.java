package com.jeuxdevelopers.mvvm_tvshowsapp.network;

import com.jeuxdevelopers.mvvm_tvshowsapp.response.TvShowDetailResponse;
import com.jeuxdevelopers.mvvm_tvshowsapp.response.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TvShowResponse> getPopularTvShows(@Query("page") int page);

    @GET("show-details")
    Call<TvShowDetailResponse> getTvShowDetail(@Query("q") int showId);

    @GET("search")
    Call<TvShowResponse> searchShow(@Query("q") String query, @Query("page") int page);
}
