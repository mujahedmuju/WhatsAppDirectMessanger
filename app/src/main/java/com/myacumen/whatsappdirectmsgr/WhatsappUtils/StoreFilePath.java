package com.myacumen.whatsappdirectmsgr.WhatsappUtils;

import android.os.Environment;

import java.io.File;

public class StoreFilePath {
    //Storing the Directory
    public static File RootDirectoryWhatsapp = new File(Environment.getExternalStorageDirectory()+"/Download/DirectMSGR_Saver/Whatsapp");
    //Creating our folder like my story saver

    //For creating myStorySaver folder
    public static void createFileFolder(){
        //Checking if already there is an existing folder or not...
        if (!RootDirectoryWhatsapp.exists()){
            RootDirectoryWhatsapp.mkdirs();
        }
    }
}
