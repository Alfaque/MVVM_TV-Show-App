package com.jeuxdevelopers.mvvm_tvshowsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.google.gson.Gson;
import com.jeuxdevelopers.mvvm_tvshowsapp.R;
import com.jeuxdevelopers.mvvm_tvshowsapp.adapters.PopularTvShowsAdapter;
import com.jeuxdevelopers.mvvm_tvshowsapp.databinding.ActivitySearchBinding;
import com.jeuxdevelopers.mvvm_tvshowsapp.models.TvShowModel;
import com.jeuxdevelopers.mvvm_tvshowsapp.repositories.TvShowSearchRepository;
import com.jeuxdevelopers.mvvm_tvshowsapp.response.TvShowResponse;
import com.jeuxdevelopers.mvvm_tvshowsapp.utilities.Constants;
import com.jeuxdevelopers.mvvm_tvshowsapp.viewmodels.SearchTvShowViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements PopularTvShowsAdapter.OnTvShowClicked {
    ActivitySearchBinding activitySearchBinding;
    SearchTvShowViewModel searchTvShowViewModel;
    int currentPage = 1;
    int totalPages = 1;
    PopularTvShowsAdapter popularTvShowsAdapter;
    Gson gson = new Gson();
    List<TvShowModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        searchTvShowViewModel = new ViewModelProvider(this).get(SearchTvShowViewModel.class);

        setRecyclerView();
        initView();
    }

    private void initView() {
        activitySearchBinding.searchEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(s.toString())) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentPage = 1;
                            totalPages = 1;

                            searchShow(s.toString());

                        }
                    }, 800);

                } else {
                    list.clear();
                    popularTvShowsAdapter.setData(list);
                }

            }
        });

    }

    private void searchShow(String toString) {

        searchTvShowViewModel.searchTvShow(toString, currentPage).observe(this, new Observer<TvShowResponse>() {
            @Override
            public void onChanged(TvShowResponse tvShowResponse) {

                totalPages = tvShowResponse.getPages();
                int oldCount = list.size();
                list.addAll(tvShowResponse.getTv_shows());
//                popularTvShowsAdapter.setData(list);

                popularTvShowsAdapter.notifyItemRangeChanged(oldCount, list.size());

            }
        });
    }

    private void setRecyclerView() {
        popularTvShowsAdapter = new PopularTvShowsAdapter(this, list, this, false);
        activitySearchBinding.searchRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        activitySearchBinding.searchRecyclerview.setAdapter(popularTvShowsAdapter);
        activitySearchBinding.searchRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activitySearchBinding.searchRecyclerview.canScrollVertically(1)) {
                    if (!TextUtils.isEmpty(activitySearchBinding.searchEdittext.getText().toString())) {
                        currentPage = currentPage + 1;
                        searchShow(activitySearchBinding.searchEdittext.getText().toString());
                    }
                }
            }
        });
    }

    private void getData() {

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

    }
}