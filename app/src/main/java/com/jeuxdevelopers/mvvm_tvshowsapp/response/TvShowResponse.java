package com.jeuxdevelopers.mvvm_tvshowsapp.response;

import com.jeuxdevelopers.mvvm_tvshowsapp.models.TvShowModel;

import java.util.List;

public class TvShowResponse {
    String total;
    int page;
    int pages;
    List<TvShowModel> tv_shows;

    public TvShowResponse() {
    }

    public TvShowResponse(String total, int page, int pages, List<TvShowModel> tv_shows) {
        this.total = total;
        this.page = page;
        this.pages = pages;
        this.tv_shows = tv_shows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<TvShowModel> getTv_shows() {
        return tv_shows;
    }

    public void setTv_shows(List<TvShowModel> tv_shows) {
        this.tv_shows = tv_shows;
    }
}
