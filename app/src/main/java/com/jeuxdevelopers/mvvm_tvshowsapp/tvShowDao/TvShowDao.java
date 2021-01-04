package com.jeuxdevelopers.mvvm_tvshowsapp.tvShowDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jeuxdevelopers.mvvm_tvshowsapp.models.TvShowModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface TvShowDao {

    @Query("SELECT * FROM tvShows")
    Flowable<List<TvShowModel>> getWatchList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchList(TvShowModel tvShowModel);

    @Delete
    Completable deleteFromWatchList(TvShowModel tvShowModel);

    @Query("SELECT * FROM tvshows WHERE id=:tvShowId")
    Flowable<TvShowModel> getShowFromWatchList(int tvShowId);


}
