package com.jeuxdevelopers.mvvm_tvshowsapp.models;

import java.util.List;

public class TvShowDetailModel {
    public int id;
    public String name;
    public String permalink;
    public String url;
    public String description;
    public String description_source;
    public String start_date;
    public Object end_date;
    public String country;
    public String status;
    public int runtime;
    public String network;
    public Object youtube_link;
    public String image_path;
    public String image_thumbnail_path;
    public String rating;
    public String rating_count;
    public Object countdown;
    public List<String> genres;
    public List<String> pictures;
    public List<EpisodeModel> episodes;

    public TvShowDetailModel(int id, String name, String permalink, String url, String description, String description_source, String start_date, Object end_date, String country, String status, int runtime, String network, Object youtube_link, String image_path, String image_thumbnail_path, String rating, String rating_count, Object countdown, List<String> genres, List<String> pictures, List<EpisodeModel> episodes) {
        this.id = id;
        this.name = name;
        this.permalink = permalink;
        this.url = url;
        this.description = description;
        this.description_source = description_source;
        this.start_date = start_date;
        this.end_date = end_date;
        this.country = country;
        this.status = status;
        this.runtime = runtime;
        this.network = network;
        this.youtube_link = youtube_link;
        this.image_path = image_path;
        this.image_thumbnail_path = image_thumbnail_path;
        this.rating = rating;
        this.rating_count = rating_count;
        this.countdown = countdown;
        this.genres = genres;
        this.pictures = pictures;
        this.episodes = episodes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_source() {
        return description_source;
    }

    public void setDescription_source(String description_source) {
        this.description_source = description_source;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public Object getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Object end_date) {
        this.end_date = end_date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public Object getYoutube_link() {
        return youtube_link;
    }

    public void setYoutube_link(Object youtube_link) {
        this.youtube_link = youtube_link;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getImage_thumbnail_path() {
        return image_thumbnail_path;
    }

    public void setImage_thumbnail_path(String image_thumbnail_path) {
        this.image_thumbnail_path = image_thumbnail_path;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating_count() {
        return rating_count;
    }

    public void setRating_count(String rating_count) {
        this.rating_count = rating_count;
    }

    public Object getCountdown() {
        return countdown;
    }

    public void setCountdown(Object countdown) {
        this.countdown = countdown;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public List<EpisodeModel> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EpisodeModel> episodes) {
        this.episodes = episodes;
    }
}
