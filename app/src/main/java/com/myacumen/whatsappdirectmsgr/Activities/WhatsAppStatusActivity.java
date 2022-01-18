package com.myacumen.whatsappdirectmsgr.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.myacumen.whatsappdirectmsgr.Adapters.VpAdapter;
import com.myacumen.whatsappdirectmsgr.Fragments.DownloadedStatusFragment;
import com.myacumen.whatsappdirectmsgr.Fragments.VideosStatusFragment;
import com.myacumen.whatsappdirectmsgr.Fragments.PhotosStatusFragment;
import com.myacumen.whatsappdirectmsgr.databinding.ActivityWhatsAppStatusBinding;

public class WhatsAppStatusActivity extends AppCompatActivity {
    ActivityWhatsAppStatusBinding whatsAppStatusBinding;
    VpAdapter vpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        whatsAppStatusBinding = ActivityWhatsAppStatusBinding.inflate(getLayoutInflater());
        setContentView(whatsAppStatusBinding.getRoot());

        whatsAppStatusBinding.tabLayout.setupWithViewPager(whatsAppStatusBinding.viewPager);
        vpAdapter = new VpAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new PhotosStatusFragment(),"Photos");
        vpAdapter.addFragment(new VideosStatusFragment(),"Videos");
        vpAdapter.addFragment(new DownloadedStatusFragment(),"Downloads");

        whatsAppStatusBinding.viewPager.setAdapter(vpAdapter);
        whatsAppStatusBinding.viewPager.setOffscreenPageLimit(3);

    }

    @Override
    public void onBackPressed() {
        if (whatsAppStatusBinding.viewPager.getCurrentItem() > 0){
            whatsAppStatusBinding.viewPager.setCurrentItem(0);
        }else super.onBackPressed();
    }
}