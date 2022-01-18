package com.myacumen.whatsappdirectmsgr.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myacumen.whatsappdirectmsgr.databinding.ActivityViewImagesBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ViewImagesActivity extends AppCompatActivity {
    ActivityViewImagesBinding imagesBinding;
    Bundle bundle;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagesBinding = ActivityViewImagesBinding.inflate(getLayoutInflater());
        setContentView(imagesBinding.getRoot());

        bundle = getIntent().getExtras();
        String path = bundle.getString("path");

        if (Uri.fromFile(new File(path)).toString().endsWith(".png") || Uri.fromFile(new File(path)).toString().endsWith(".jpg")) {
            imagesBinding.imageView.setVisibility(View.VISIBLE);

            Glide.with(ViewImagesActivity.this)
                    .load(path)
                    .into(imagesBinding.imageView);
        } else {
            imagesBinding.videoView.setVisibility(View.VISIBLE);
            mediaController = new MediaController(this);
            mediaController.setAnchorView(imagesBinding.videoView);
            imagesBinding.videoView.setMediaController(mediaController);
            imagesBinding.videoView.setVideoURI(Uri.parse(path));

            imagesBinding.videoView.requestFocus();
            imagesBinding.videoView.start();
        }

        imagesBinding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse(path);
                sharingIntent.setType("image/jpeg");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share image using"));

            }
        });

        imagesBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}