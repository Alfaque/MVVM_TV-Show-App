package com.jeuxdevelopers.mvvm_tvshowsapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jeuxdevelopers.mvvm_tvshowsapp.R;
import com.jeuxdevelopers.mvvm_tvshowsapp.adapters.PopularTvShowsAdapter;
import com.jeuxdevelopers.mvvm_tvshowsapp.databinding.ActivityWatchListBinding;
import com.jeuxdevelopers.mvvm_tvshowsapp.models.TvShowModel;
import com.jeuxdevelopers.mvvm_tvshowsapp.utilities.Constants;
import com.jeuxdevelopers.mvvm_tvshowsapp.viewmodels.WatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements PopularTvShowsAdapter.OnTvShowClicked {
    ActivityWatchListBinding activityWatchListBinding;
    List<TvShowModel> list = new ArrayList<>();
    PopularTvShowsAdapter popularTvShowsAdapter;
    WatchListViewModel watchListViewModel;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        watchListViewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        setUprecyclerView();
        getData();
    }

    private void setUprecyclerView() {
        popularTvShowsAdapter = new PopularTvShowsAdapter(this, list, this, true);
        activityWatchListBinding.watchListRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        activityWatchListBinding.watchListRecyclerview.setAdapter(popularTvShowsAdapter);
    }

    private void getData() {
//        activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchListViewModel.getWatchList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShowModels -> {

                    if (list.size() > 0) {
                        list.clear();
                    }
                    list.addAll(tvShowModels);

                    popularTvShowsAdapter.setData(list);
                    compositeDisposable.dispose();
//                    activityWatchListBinding.;

                }));
    }


    @Override
    public void onTvShowClickedListener(TvShowModel tvShowModel) {
        String data = gson.toJson(tvShowModel);
        Intent intent = new Intent(this, ShowDetailActivity.class);
        intent.putExtra(Constants.TV_SHOW_MODEL, data);
        startActivity(intent);
    }

    @Override
    public void onTvShowDelClickedListener(int position, TvShowModel tvShowModel) {
        Toast.makeText(this, "del" + tvShowModel.getName(), Toast.LENGTH_SHORT).show();
        watchListViewModel.removeShow(tvShowModel);
        list.remove(tvShowModel);
        popularTvShowsAdapter.notifyItemRemoved(position);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchListViewModel
                .removeShow(tvShowModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(() -> {

                            list.remove(tvShowModel);
                            popularTvShowsAdapter.notifyItemRemoved(position);
                            popularTvShowsAdapter.notifyItemRangeRemoved(position, list.size());
                            compositeDisposable.dispose();
                        }
                ));
    }
}