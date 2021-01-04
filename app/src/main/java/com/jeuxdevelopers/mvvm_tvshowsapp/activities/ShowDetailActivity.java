package com.jeuxdevelopers.mvvm_tvshowsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.jeuxdevelopers.mvvm_tvshowsapp.R;
import com.jeuxdevelopers.mvvm_tvshowsapp.adapters.EpisodeAdapter;
import com.jeuxdevelopers.mvvm_tvshowsapp.adapters.ViewpagerAdapter;
import com.jeuxdevelopers.mvvm_tvshowsapp.databinding.ActivityShowDetailBinding;
import com.jeuxdevelopers.mvvm_tvshowsapp.models.EpisodeModel;
import com.jeuxdevelopers.mvvm_tvshowsapp.models.TvShowModel;
import com.jeuxdevelopers.mvvm_tvshowsapp.response.TvShowDetailResponse;
import com.jeuxdevelopers.mvvm_tvshowsapp.utilities.Constants;
import com.jeuxdevelopers.mvvm_tvshowsapp.viewmodels.TvshowDetailViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ShowDetailActivity extends AppCompatActivity {
    TvshowDetailViewModel tvshowDetailViewModel;
    Gson gson = new Gson();
    TvShowModel tvShowModel;
    ViewpagerAdapter adapter;
    EpisodeAdapter episodeAdapter;
    List<EpisodeModel> episodesList = new ArrayList<>();
    ActivityShowDetailBinding activityShowDetailBinding;
    BottomSheetBehavior bottomSheetBehavior;
    View view;
    boolean isShowAddedToWatchList = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityShowDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_detail);
        tvshowDetailViewModel = new ViewModelProvider(this).get(TvshowDetailViewModel.class);
        adapter = new ViewpagerAdapter(this);
        view = findViewById(R.id.bottom_layout);
        bottomSheetBehavior = BottomSheetBehavior.from(view);
        getData();
        checkTvShow();

    }

    private void checkTvShow() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(tvshowDetailViewModel.getTvShowFromWatchList(tvShowModel.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(tvShowModel1 -> {
                    isShowAddedToWatchList = true;
                    activityShowDetailBinding.bookmarkFab.setImageResource(R.drawable.ic_check);
                    compositeDisposable.dispose();

                }));
    }

    private void initView(TvShowDetailResponse tvShowDetailResponse) {
        activityShowDetailBinding.bookmarkFab.setVisibility(View.VISIBLE);
        activityShowDetailBinding.bookmarkFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvShowModel != null) {
                    CompositeDisposable compositeDisposable = new CompositeDisposable();
                    if (isShowAddedToWatchList) {
//                       compositeDisposable.add(tvshowDetailViewModel.removeShow(tvShowModel));

                        tvshowDetailViewModel.removeShow(tvShowModel);
                        Toast.makeText(ShowDetailActivity.this, "Removed from WatchList", Toast.LENGTH_SHORT).show();
                        activityShowDetailBinding.bookmarkFab.setImageResource(R.drawable.ic_bookmark);
                        isShowAddedToWatchList = false;

                    } else {

                        compositeDisposable.add(tvshowDetailViewModel.addToWatchList(tvShowModel)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    activityShowDetailBinding.bookmarkFab.setImageResource(R.drawable.ic_check);
                                    Toast.makeText(ShowDetailActivity.this, "Added to WatchList", Toast.LENGTH_SHORT).show();
                                    isShowAddedToWatchList = true;
                                    compositeDisposable.dispose();
                                }));
                    }


                }

            }
        });
        activityShowDetailBinding.detailBackImagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        activityShowDetailBinding.episodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        activityShowDetailBinding.websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(tvShowDetailResponse.getTvShow().getUrl()));
                startActivity(intent);
            }
        });
        view.findViewById(R.id.bottom_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    private void getData() {
        if (getIntent() != null) {

            String data = getIntent().getStringExtra(Constants.TV_SHOW_MODEL);
            tvShowModel = gson.fromJson(data, TvShowModel.class);
            if (tvShowModel != null) {

                activityShowDetailBinding.setIsLoading(true);

                tvshowDetailViewModel.getShowDetail(tvShowModel.getId()).observe(this, new Observer<TvShowDetailResponse>() {
                    @Override
                    public void onChanged(TvShowDetailResponse tvShowDetailResponse) {
                        activityShowDetailBinding.setIsLoading(false);
                        setUpSlider(tvShowDetailResponse);
                        setData(tvShowDetailResponse);
                        setUpEpisodes(tvShowDetailResponse);
                        initView(tvShowDetailResponse);

                    }
                });
            }
        }
    }

    private void setUpEpisodes(TvShowDetailResponse tvShowDetailResponse) {
        episodeAdapter = new EpisodeAdapter(this, tvShowDetailResponse.getTvShow().getEpisodes());
        activityShowDetailBinding.episodeRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        activityShowDetailBinding.episodeRecyclerview.setAdapter(episodeAdapter);
    }


    private void setData(TvShowDetailResponse tvShowDetailResponse) {
        activityShowDetailBinding.setShowCountry(tvShowDetailResponse.getTvShow().getCountry());
        activityShowDetailBinding.setShowImageUrl(tvShowDetailResponse.getTvShow().getImage_path());
        activityShowDetailBinding.setShowName(tvShowDetailResponse.getTvShow().getName());
        activityShowDetailBinding.setShowNetwork(tvShowDetailResponse.getTvShow().getNetwork());
        activityShowDetailBinding.setShowstartedDate(tvShowDetailResponse.getTvShow().getStart_date());
        activityShowDetailBinding.setShowstatus(tvShowDetailResponse.getTvShow().getStatus());
        activityShowDetailBinding.itemsLayout.setVisibility(View.VISIBLE);
        activityShowDetailBinding.setShowDescription(tvShowDetailResponse.getTvShow().getDescription());
        activityShowDetailBinding.readMoreTextview.setVisibility(View.VISIBLE);
        activityShowDetailBinding.readMoreTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityShowDetailBinding.readMoreTextview.getText().toString().equals("Read More")) {
                    activityShowDetailBinding.readMoreTextview.setText("Read Less");
                    activityShowDetailBinding.descriptionTextView.setMaxLines(Integer.MAX_VALUE);
                    activityShowDetailBinding.descriptionTextView.setEllipsize(null);


                } else if (activityShowDetailBinding.readMoreTextview.getText().toString().equals("Read Less")) {
                    activityShowDetailBinding.readMoreTextview.setText("Read More");
                    activityShowDetailBinding.descriptionTextView.setMaxLines(4);
                    activityShowDetailBinding.descriptionTextView.setEllipsize(TextUtils.TruncateAt.END);

                }
            }
        });

        if (tvShowDetailResponse.getTvShow().getGenres() != null) {
            activityShowDetailBinding.setShowGenre(tvShowDetailResponse.getTvShow().getGenres().get(0));
        } else {
            activityShowDetailBinding.setShowGenre("N/A");

        }

        activityShowDetailBinding.setShowRating(String.format(Locale.getDefault(), "%.2f", Double.parseDouble(tvShowDetailResponse.getTvShow().getRating())));
        activityShowDetailBinding.setShowRuntime(tvShowDetailResponse.getTvShow().getRuntime() + " Min");
        activityShowDetailBinding.genreLayout.setVisibility(View.VISIBLE);

    }

    private void setUpSlider(TvShowDetailResponse tvShowDetailResponse) {


        activityShowDetailBinding.horizontalViewpager.setAdapter(adapter);
        activityShowDetailBinding.fadingEdge.setVisibility(View.VISIBLE);
        activityShowDetailBinding.horizontalViewpager.setOffscreenPageLimit(1);
        adapter.setData(tvShowDetailResponse.getTvShow().getPictures());


        new TabLayoutMediator(activityShowDetailBinding.indicatorTablayout, activityShowDetailBinding.horizontalViewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            }
        }).attach();


    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}