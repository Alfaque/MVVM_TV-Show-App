package com.jeuxdevelopers.mvvm_tvshowsapp.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jeuxdevelopers.mvvm_tvshowsapp.tvShowDao.TvShowDao;
import com.jeuxdevelopers.mvvm_tvshowsapp.models.TvShowModel;

@Database(entities = TvShowModel.class, version = 1, exportSchema = true)
public abstract class TvShowDataBase extends RoomDatabase {

    public abstract TvShowDao tvShowDao();

    private static TvShowDataBase tvShowDataBase;

    public static synchronized TvShowDataBase getInstance(Context context) {
        if (tvShowDataBase == null) {

            tvShowDataBase = Room
                    .databaseBuilder(context, TvShowDataBase.class, "TvShowDataBase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return tvShowDataBase;
    }


}
