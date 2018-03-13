package com.russellgutierrez.demo.zuhlke.dog.data.local;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.russellgutierrez.demo.zuhlke.dog.injection.ActivityContext;

import javax.inject.Inject;

public class ImageHelper {
    //TODO create test for ImageHelper. Do we need to test Glide?
    private final Context context;

    @Inject
    public ImageHelper(@ActivityContext Context context) {
        this.context = context;
    }

    public void loadImage(ImageView imageView, String url) {
        Glide.with(context).load(url).into(imageView);
    }
}
