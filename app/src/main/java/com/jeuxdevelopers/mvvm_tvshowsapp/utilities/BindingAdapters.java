package com.jeuxdevelopers.mvvm_tvshowsapp.utilities;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BindingAdapters {


    @androidx.databinding.BindingAdapter("imageURL")
    public static void setImageURL(ImageView image, String URL) {
        image.setAlpha(0f);
        Picasso.get().load(URL).noFade().into(image, new Callback() {
            @Override
            public void onSuccess() {
                image.animate().setDuration(300).alpha(1f).start();

            }

            @Override
            public void onError(Exception e) {

            }
        });

    }
}
