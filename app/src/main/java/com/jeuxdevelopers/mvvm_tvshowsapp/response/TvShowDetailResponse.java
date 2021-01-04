package com.jeuxdevelopers.mvvm_tvshowsapp.response;

import com.jeuxdevelopers.mvvm_tvshowsapp.models.TvShowDetailModel;

public class TvShowDetailResponse {
    TvShowDetailModel tvShow;

    public TvShowDetailResponse(TvShowDetailModel tvShow) {
        this.tvShow = tvShow;
    }

    public TvShowDetailModel getTvShow() {
        return tvShow;
    }

    public void setTvShow(TvShowDetailModel tvShow) {
        this.tvShow = tvShow;
    }
}
