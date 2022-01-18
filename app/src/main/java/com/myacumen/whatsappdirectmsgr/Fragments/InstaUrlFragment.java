package com.myacumen.whatsappdirectmsgr.Fragments;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.hcr2bot.instagramvideosdownloader.InstaVideo;
import com.hcr2bot.instaimagesdownloader.InstagramImages;
import com.myacumen.whatsappdirectmsgr.databinding.FragmentInstaUrlBinding;

public class InstaUrlFragment extends Fragment {
    FragmentInstaUrlBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInstaUrlBinding.inflate(inflater,container,false);
        binding.pasteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                binding.urlLink.setText(clipboard.getText().toString());
            }
        });
        binding.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.urlLink.getText().toString().trim().isEmpty()){
                    binding.urlLink.setError("Empty Field");
                    return;
                }
                if (binding.urlLink.getText().toString().contains("?utm_medium=share_sheet")){
                    Toast.makeText(getContext(),"Stories, Can't be download.",Toast.LENGTH_SHORT).show();
                    return;
                }
//                InstagramImages.downloadImage(getContext(),binding.urlLink.getText().toString());
                InstaVideo.downloadVideo(getContext(),binding.urlLink.getText().toString());
                ProgressDialog dialog = ProgressDialog.show(getContext(), "Downloading", "Please wait...", true);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                },2000);
                binding.urlLink.getText().clear();
            }
        });
        return binding.getRoot();
    }
}
//Download any thing from url...
//String getUrl = binding.urlLink.getText().toString();
//    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(getUrl));
//    String title = URLUtil.guessFileName(getUrl,null,null);
//                request.setTitle(title);
//                        request.setDescription("Downloading files please wait...");
//                        String cookie = CookieManager.getInstance().getCookie(getUrl);
//                        request.addRequestHeader("cookie",cookie);
//                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
//
//                        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
//                        downloadManager.enqueue(request);
//                        Toast.makeText(getContext(), "Downloading Started...", Toast.LENGTH_SHORT).show();