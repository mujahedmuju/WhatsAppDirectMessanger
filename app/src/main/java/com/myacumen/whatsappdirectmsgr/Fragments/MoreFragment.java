package com.myacumen.whatsappdirectmsgr.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myacumen.whatsappdirectmsgr.Activities.AboutUsActivity;
import com.myacumen.whatsappdirectmsgr.Activities.InstaStoryActivity;
import com.myacumen.whatsappdirectmsgr.Activities.WhatsAppStatusActivity;
import com.myacumen.whatsappdirectmsgr.databinding.FragmentMoreBinding;

public class MoreFragment extends Fragment {
    FragmentMoreBinding statusBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        statusBinding = FragmentMoreBinding.inflate(inflater, container, false);
        View view = statusBinding.getRoot();

        statusBinding.whatsAppLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WhatsAppStatusActivity.class);
                startActivity(intent);
            }
        });

//        statusBinding.instagramLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), InstaStoryActivity.class);
//                startActivity(intent);
//            }
//        });
        statusBinding.shareAppLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApp(getContext());
            }
        });
        statusBinding.aboutUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AboutUsActivity.class));
            }
        });
        statusBinding.rateUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=")));
            }
        });
        return view;
    }
    public static void shareApp(Context context)
    {
        final String appPackageName = context.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out the App at: https://play.google.com/store/apps/details?id=" + appPackageName);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }
}