package com.myacumen.whatsappdirectmsgr.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myacumen.whatsappdirectmsgr.Activities.ViewImagesActivity;
import com.myacumen.whatsappdirectmsgr.R;
import com.myacumen.whatsappdirectmsgr.WhatsappUtils.StoreFilePath;
import com.myacumen.whatsappdirectmsgr.WhatsappUtils.WhatsAppStatusModel;
import com.myacumen.whatsappdirectmsgr.databinding.ItemWhatsappStatusBinding;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WhatsAppStatusAdapter extends RecyclerView.Adapter<WhatsAppStatusAdapter.viewHolder> {

    //Saving image file path
    LayoutInflater inflater;

    StatusItemEvent statusItemEvent;
    boolean isDownloadOptionVisible, deleteOptionVisible;
    Context context;
    List<WhatsAppStatusModel> statusModelList = new ArrayList<>();

    public WhatsAppStatusAdapter(Context context, boolean isDownloadOptionVisible, boolean deleteOptionVisible,StatusItemEvent statusItemEvent) {
        this.context = context;
        this.isDownloadOptionVisible = isDownloadOptionVisible;
        this.deleteOptionVisible = deleteOptionVisible;
        this.statusItemEvent = statusItemEvent;
    }

    public void updateStatusList(List<WhatsAppStatusModel> statusModelList) {
        this.statusModelList = statusModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return new viewHolder(ItemWhatsappStatusBinding.inflate(inflater));

    }

    public interface StatusItemEvent{
       void onDelete(WhatsAppStatusModel statusModel);
       void onDownload(WhatsAppStatusModel statusModel);
    }
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        WhatsAppStatusModel item = statusModelList.get(position);
        if (item.getUri().toString().endsWith(".mp4"))
            holder.statusBinding.playBtn.setVisibility(View.VISIBLE);
        else holder.statusBinding.playBtn.setVisibility(View.GONE);

        Glide.with(context).load(item.getPath()).into(holder.statusBinding.statusImage);

        if (isDownloadOptionVisible) {
            holder.statusBinding.downloadStatusBtn.setVisibility(View.VISIBLE);
            holder.statusBinding.downloadStatusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    statusItemEvent.onDownload(item);
                }
            });
        } else holder.statusBinding.downloadStatusBtn.setVisibility(View.GONE);

        if (deleteOptionVisible) {
            holder.statusBinding.deleteBtn.setVisibility(View.VISIBLE);
            holder.statusBinding.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    statusItemEvent.onDelete(item);
                }
            });
        }else holder.statusBinding.deleteBtn.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewImagesActivity.class);
                intent.putExtra("path", item.getPath());
                intent.putExtra("videoPath", holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return statusModelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ItemWhatsappStatusBinding statusBinding;

        public viewHolder(ItemWhatsappStatusBinding statusBinding) {
            super(statusBinding.getRoot());
            this.statusBinding = statusBinding;
        }
    }
}
