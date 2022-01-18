package com.myacumen.whatsappdirectmsgr.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;

import com.myacumen.whatsappdirectmsgr.Adapters.VpAdapter;
import com.myacumen.whatsappdirectmsgr.Fragments.InstaStoryFragment;
import com.myacumen.whatsappdirectmsgr.Fragments.InstaUrlFragment;
import com.myacumen.whatsappdirectmsgr.R;
import com.myacumen.whatsappdirectmsgr.databinding.ActivityInstaStoryBinding;

public class InstaStoryActivity extends AppCompatActivity {
    ActivityInstaStoryBinding binding;
    VpAdapter vpAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstaStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(Color.parseColor("#c43639"));

        binding.instaTabLayout.setupWithViewPager(binding.instaViewPager);
        vpAdapter = new VpAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        vpAdapter.addFragment(new InstaUrlFragment(), "URL");
        vpAdapter.addFragment(new InstaStoryFragment(),"Downloaded Reels");
        binding.instaViewPager.setAdapter(vpAdapter);
    }
}