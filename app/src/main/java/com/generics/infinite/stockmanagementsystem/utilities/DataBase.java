package com.generics.infinite.stockmanagementsystem.utilities;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class DataBase {

    public static void backUp(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.generics.infinite.stockmanagementsystem//databases//sms_db.db";
             //  String backupDBPath = "sms_db.db";
                File currentDB = new File(data, currentDBPath);
               // File backupDB = new File(sd, backupDBPath);
                File backupDB = Helper.getDirectoryForDatabase("Db_Backup","sms_db.db");

                Log.d("backupDB path", "" + backupDB.getAbsolutePath());

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Helper.showMsg(context,"Data Backup Successful",Message.successType);
                }else{
                    Helper.showMsg(context,"Some Issue Occurred Please Try Again Later",Message.errorType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
           // Helper.showMsg(context,e.getMessage(),Message.errorType);
        }
    }

    public static void restore(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.generics.infinite.stockmanagementsystem//databases//sms_db.db";
                //String backupDBPath = "sms_db.db";
                File currentDB = new File(data, currentDBPath);
                //File backupDB = new File(sd, backupDBPath);
                File backupDB = Helper.getDirectoryForDatabase("Db_Backup","sms_db.db");

                if (currentDB.exists()&&backupDB.exists()) {
                    FileChannel src = new FileInputStream(backupDB).getChannel();
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Helper.showMsg(context,"Data Restored Successful",Message.successType);
                }else{
                    Helper.showMsg(context,"No Data Backup To Restore",Message.errorType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
           // Helper.showMsg(context,e.getMessage(),Message.errorType);
        }
    }
}
