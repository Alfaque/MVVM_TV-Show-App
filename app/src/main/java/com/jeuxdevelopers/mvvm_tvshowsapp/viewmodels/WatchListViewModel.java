package com.jeuxdevelopers.mvvm_tvshowsapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.jeuxdevelopers.mvvm_tvshowsapp.DataBase.TvShowDataBase;
import com.jeuxdevelopers.mvvm_tvshowsapp.models.TvShowModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchListViewModel extends AndroidViewModel {
    TvShowDataBase tvShowDataBase;

    public WatchListViewModel(@NonNull Application application) {
        super(application);
        tvShowDataBase = TvShowDataBase.getInstance(application);
    }

    public Flowable<List<TvShowModel>> getWatchList() {
        return tvShowDataBase.tvShowDao().getWatchList();

    }

    public Completable removeShow(TvShowModel tvShowModel) {
        return  tvShowDataBase.tvShowDao().deleteFromWatchList(tvShowModel);
    }

}
