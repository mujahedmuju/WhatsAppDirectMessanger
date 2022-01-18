package com.myacumen.whatsappdirectmsgr.Fragments;


import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.myacumen.whatsappdirectmsgr.Adapters.WhatsAppStatusAdapter;
import com.myacumen.whatsappdirectmsgr.WhatsappUtils.StoreFilePath;
import com.myacumen.whatsappdirectmsgr.WhatsappUtils.WhatsAppStatusModel;
import com.myacumen.whatsappdirectmsgr.databinding.FragmentViewedStatusBinding;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PhotosStatusFragment extends Fragment {

    FragmentViewedStatusBinding binding;
    WhatsAppStatusAdapter statusAdapter;
    List<WhatsAppStatusModel> statusModelList;
    String saveFilePath = StoreFilePath.RootDirectoryWhatsapp + "/";
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewedStatusBinding.inflate(inflater, container, false);
        dialog = ProgressDialog.show(getContext(), "Loading", "Please wait...", true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                dialog.dismiss();
            }
        },2500);

        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                binding.refreshLayout.setRefreshing(false);
            }
        });
        return binding.getRoot();
    }

    private void getData() {
        WhatsAppStatusModel model;
        statusModelList = new ArrayList<>();

        String targetPath,targetPathBusiness;
        File targetDirectory,targetDirectoryBusiness;
        File[] allFiles,allFilesBusiness;

        if (android.os.Build.VERSION.SDK_INT < 30) {
            // Less then 11
            targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses";
        } else {
            // Android 11
            targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses";

        }
        targetDirectory = new File(targetPath);
        allFiles = targetDirectory.listFiles();


        //If using Whats App Business account...
        if (android.os.Build.VERSION.SDK_INT < 30) {
            // Less then 11
            targetPathBusiness = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.Statuses";
        } else {
            // Android 11
            targetPathBusiness = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/media/com.whatsapp/WhatsApp Business/Media/.Statuses";

        }
        targetDirectoryBusiness = new File(targetPathBusiness);
        allFilesBusiness = targetDirectory.listFiles();


        //Target path for getting data
//        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.statuses";
//        File targetDirectory = new File(targetPath);
//        File[] allFiles = targetDirectory.listFiles();

//        String targetPathBusiness = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.statuses";
//        File targetDirectoryBusiness = new File(targetPathBusiness);
//        File[] allFilesBusiness = targetDirectoryBusiness.listFiles();

        //Sorting new ones first
        if (targetDirectory.exists()) {
            Arrays.sort(allFiles, new Comparator() {
                public int compare(Object o1, Object o2) {

                    if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                        return -1;
                    } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                        return +1;
                    } else {
                        return 0;
                    }
                }
            });


            for (int i = 0; i < allFiles.length; i++) {
                File file = allFiles[i];
                if (Uri.fromFile(file).toString().endsWith(".png") ||
                        Uri.fromFile(file).toString().endsWith(".jpg")) {
                    model = new WhatsAppStatusModel("whats" + i,
                            Uri.fromFile(file),
                            allFiles[i].getAbsolutePath(),
                            file.getName());
                    statusModelList.add(model);
                }
            }
        }

        if (targetDirectoryBusiness.exists()) {
            Arrays.sort(allFilesBusiness, new Comparator() {
                public int compare(Object o1, Object o2) {

                    if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                        return -1;
                    } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                        return +1;
                    } else {
                        return 0;
                    }
                }
            });

            for (int i = 0; i < allFilesBusiness.length; i++) {
                File file = allFilesBusiness[i];
                if (Uri.fromFile(file).toString().endsWith(".png") ||
                        Uri.fromFile(file).toString().endsWith(".jpg")) {
                    model = new WhatsAppStatusModel("whatsBusiness" + i,
                            Uri.fromFile(file),
                            allFilesBusiness[i].getAbsolutePath(),
                            file.getName());
                    statusModelList.add(model);
                }
            }
        }

        statusAdapter = new WhatsAppStatusAdapter(getContext(), true, false, new WhatsAppStatusAdapter.StatusItemEvent() {
            @Override
            public void onDelete(WhatsAppStatusModel statusModel) {

            }

            @Override
            public void onDownload(WhatsAppStatusModel statusModel) {
                StoreFilePath.createFileFolder();
                final String path = statusModel.getPath();
                final File file = new File(path);
                File destinationFile = new File(saveFilePath);

                try {
                    FileUtils.copyFileToDirectory(file, destinationFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Status Downloaded Successfully", Toast.LENGTH_LONG).show();
            }
        });
        binding.statusRecycler.setAdapter(statusAdapter);
        if (statusModelList.size() == 0) {
            binding.emptyBoxLayout.setVisibility(View.VISIBLE);
        } else binding.emptyBoxLayout.setVisibility(View.GONE);
        statusAdapter.updateStatusList(statusModelList);
    }
}