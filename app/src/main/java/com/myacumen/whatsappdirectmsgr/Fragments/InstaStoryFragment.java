package com.myacumen.whatsappdirectmsgr.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.myacumen.whatsappdirectmsgr.Adapters.WhatsAppStatusAdapter;
import com.myacumen.whatsappdirectmsgr.R;
import com.myacumen.whatsappdirectmsgr.WhatsappUtils.WhatsAppStatusModel;
import com.myacumen.whatsappdirectmsgr.databinding.FragmentDownloadedStatusBinding;
import com.myacumen.whatsappdirectmsgr.databinding.FragmentInstaStoryBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class InstaStoryFragment extends Fragment {
    FragmentInstaStoryBinding binding;
    List<WhatsAppStatusModel> list;
    WhatsAppStatusAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInstaStoryBinding.inflate(inflater, container, false);

        getData();
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list = new ArrayList<>();
                getData();
                binding.refreshLayout.setRefreshing(false);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        WhatsAppStatusModel model;
        list = new ArrayList<>();

        //Target path for getting data
        String targetPath = Environment.getExternalStorageDirectory() + "/Download/Insta Video Downloader/instagram videos/";
        File targetDirectory = new File(targetPath);
        File[] allFiles = targetDirectory.listFiles();

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
                        Uri.fromFile(file).toString().endsWith(".jpg") ||
                        Uri.fromFile(file).toString().endsWith(".mp4")) {
                    model = new WhatsAppStatusModel("whats " + i,
                            Uri.fromFile(file),
                            allFiles[i].getAbsolutePath(),
                            file.getName());
                    list.add(model);
                }
            }
        }

        adapter = new WhatsAppStatusAdapter(getContext(), false, true, new WhatsAppStatusAdapter.StatusItemEvent() {
            @Override
            public void onDelete(WhatsAppStatusModel statusModel) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.create();
                builder.setTitle("Delete");
                builder.setMessage("Are you sure wants to Delete ?");
                builder.setPositiveButton(Html.fromHtml("<font color='#FF0000'>Delete</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        File deleteFile = new File(statusModel.getPath());
                        if (deleteFile.exists()) {
                            if (deleteFile.delete()) {
                                list = new ArrayList<>();
                                getData();
                                if (Uri.fromFile(deleteFile).toString().endsWith(".png") || Uri.fromFile(deleteFile).toString().endsWith(".jpg")) {
                                    Toast.makeText(getContext(), "Photo Deleted Successfully", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(getContext(), "Video Deleted Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Not Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.setNegativeButton(Html.fromHtml("<font color='#000000'>CANCEL</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                Dialog dialog = new Dialog(getContext());
                dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.color.white);
            }

            @Override
            public void onDownload(WhatsAppStatusModel statusModel) {

            }
        });
        binding.savedStoryRecycler.setAdapter(adapter);

        if (list.size() == 0) {
            binding.emptyBoxLayout.setVisibility(View.VISIBLE);
        } else binding.emptyBoxLayout.setVisibility(View.GONE);

        adapter.updateStatusList(list);
    }

}