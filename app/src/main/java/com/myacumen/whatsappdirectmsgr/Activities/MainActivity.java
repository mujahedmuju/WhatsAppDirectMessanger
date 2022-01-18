package com.myacumen.whatsappdirectmsgr.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import com.myacumen.whatsappdirectmsgr.Adapters.VpAdapter;
import com.myacumen.whatsappdirectmsgr.Fragments.DirectMessageFragment;
import com.myacumen.whatsappdirectmsgr.Fragments.MoreFragment;
import com.myacumen.whatsappdirectmsgr.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    VpAdapter vpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        if (!checkPermission()) {
            showPermissionDialog();
        }

        mainBinding.tabLayout.setupWithViewPager(mainBinding.viewPager);
        vpAdapter = new VpAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        vpAdapter.addFragment(new DirectMessageFragment(), "Direct Message");
        vpAdapter.addFragment(new MoreFragment(), "More");

        mainBinding.viewPager.setAdapter(vpAdapter);

//        mainBinding.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                if (position == 1) {
//                    checkPermission();
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    private void showPermissionDialog() {
        if (SDK_INT >= Build.VERSION_CODES.R) {

            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", new Object[]{getApplicationContext().getPackageName()})));
                startActivityForResult(intent, 2000);
            } catch (Exception e) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2000);

            }

        } else
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 333);
    }

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int write = ContextCompat.checkSelfPermission(getApplicationContext(),
                    WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(getApplicationContext(),
                    READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED &&
                    read == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 333) {
            if (grantResults.length > 0) {
                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (read && write) {

                } else {
                       showPermissionDialog();
                 }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {

                } else {
                    showPermissionDialog();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mainBinding.viewPager.getCurrentItem() > 0) {
            mainBinding.viewPager.setCurrentItem(0);
        } else super.onBackPressed();
    }

}


//        mainBinding.tabLayout.setupWithViewPager(mainBinding.viewPager);
//        vpAdapter = new VpAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//
//        vpAdapter.addFragment(new DirectMessageFragment(), "Direct Message");
//        vpAdapter.addFragment(new DownloadStatusFragment(), "Save Status");
//
//        mainBinding.viewPager.setAdapter(vpAdapter);
//
//        mainBinding.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                if(position ==1)
//                {
//                    checkPermission();
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }
//    public void checkPermission() {
//        Dexter.withContext(getApplicationContext())
//                .withPermissions(
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if (!report.areAllPermissionsGranted()) {
//                            mainBinding.viewPager.setCurrentItem(0);
//                            Toast.makeText(getApplicationContext(), "The App will not work,if the permissions are not granted", Toast.LENGTH_LONG).show();
//                            return;
//                        }
//
//                    }
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();
//    }
