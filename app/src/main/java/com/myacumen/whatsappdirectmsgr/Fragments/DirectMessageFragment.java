package com.myacumen.whatsappdirectmsgr.Fragments;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberUtils;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.hbb20.CountryCodePicker;
import com.myacumen.whatsappdirectmsgr.databinding.FragmentDirectMessageBinding;

import java.net.URLEncoder;

public class DirectMessageFragment extends Fragment {
    FragmentDirectMessageBinding messageBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        messageBinding = FragmentDirectMessageBinding.inflate(inflater, container, false);
        View view = messageBinding.getRoot();
        bannerAds();
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Toast.makeText(getContext(), "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();
            }
        });

        AudienceNetworkAds.initialize(getContext());

        PackageManager packageManager = getContext().getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);

        messageBinding.sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (messageBinding.enterNo.getText().toString().trim().isEmpty()) {
                    messageBinding.enterNo.setError("Empty field");
                    return;
                }
//                if (messageBinding.enterNo.getText().toString().length() < 10){
//                    messageBinding.enterNo.setError("Enter Correct Number");
//                    return;
//                }
                try {
                    String url = " https://api.whatsapp.com/send?phone=" + messageBinding.spinner.getSelectedCountryCode() +
                            messageBinding.enterNo.getText().toString() + "&text=" + URLEncoder.encode("Hello", "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        interstitialAds();
                        startActivity(i);
                    } else {
                        //Samsung,Oppo for intent
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + messageBinding.spinner.getSelectedCountryCode() +
                                messageBinding.enterNo.getText().toString() + "&text=" + "Hello"));
                        interstitialAds();
                        startActivity(intent);
                    }
                    messageBinding.enterNo.setText("");
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private void bannerAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        messageBinding.adView.loadAd(adRequest);
        messageBinding.adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }
        });
    }

    private void interstitialAds() {
        com.facebook.ads.InterstitialAd interstitialAdFb = new com.facebook.ads.InterstitialAd(getContext(),"1508280382690917_1508292322689723");

        AdRequest adRequest = new AdRequest.Builder().build();
        Toast.makeText(getContext(), "Interstitial Ad is loading ", Toast.LENGTH_LONG).show();
        InterstitialAd.load(getContext(), "ca-app-pub-2008006514657655/3487590388", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                Toast.makeText(getContext(), "Interstitial Ad is Loaded", Toast.LENGTH_LONG).show();
                interstitialAd.show((Activity) getContext());

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Toast.makeText(getContext(), "Failed to Load ads???", Toast.LENGTH_LONG).show();
                InterstitialAdListener listener = new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {

                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {

                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {

                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        interstitialAdFb.show();
                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                };
                interstitialAdFb.loadAd(interstitialAdFb.buildLoadAdConfig().withAdListener(listener).build());
            }
        });


    }
}