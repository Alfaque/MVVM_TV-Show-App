package com.jeuxdevelopers.mvvm_tvshowsapp.viewmodels;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jeuxdevelopers.mvvm_tvshowsapp.DataBase.TvShowDataBase;
import com.jeuxdevelopers.mvvm_tvshowsapp.models.TvShowModel;
import com.jeuxdevelopers.mvvm_tvshowsapp.repositories.TvShowDetailRepository;
import com.jeuxdevelopers.mvvm_tvshowsapp.response.TvShowDetailResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TvshowDetailViewModel extends AndroidViewModel {

    TvShowDetailRepository tvShowDetailRepository;
    TvShowDataBase tvShowDataBase;

    public TvshowDetailViewModel(@NonNull Application application) {
        super(application);
        tvShowDetailRepository = new TvShowDetailRepository();
        tvShowDataBase = TvShowDataBase.getInstance(application);
    }

    public LiveData<TvShowDetailResponse> getShowDetail(int showId) {
        return tvShowDetailRepository.getTvShowDetail(showId);
    }

    public Completable addToWatchList(TvShowModel tvShowModel) {
        return tvShowDataBase.tvShowDao().addToWatchList(tvShowModel);
    }

    public Flowable<TvShowModel> getTvShowFromWatchList(int tvShowId) {
        return tvShowDataBase.tvShowDao().getShowFromWatchList(tvShowId);
    }


    public void removeShow(TvShowModel tvShowModel) {
        new RemoveShowTask(tvShowDataBase).execute(tvShowModel);
    }


    public class RemoveShowTask extends AsyncTask<TvShowModel, Void, Void> {
        TvShowDataBase tvShowDataBase;

        public RemoveShowTask(TvShowDataBase tvShowDataBase) {
            this.tvShowDataBase = tvShowDataBase;
        }

        @Override
        protected Void doInBackground(TvShowModel... tvShowModels) {

            tvShowDataBase.tvShowDao().deleteFromWatchList(tvShowModels[0]);
            return null;
        }
    }
}
