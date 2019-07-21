package com.generics.infinite.stockmanagementsystem.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import com.generics.infinite.stockmanagementsystem.AddProductActivity;
import com.generics.infinite.stockmanagementsystem.model.Customer;
import com.generics.infinite.stockmanagementsystem.model.Product;
import com.generics.infinite.stockmanagementsystem.model.PurchaseEntry;
import com.generics.infinite.stockmanagementsystem.model.SalesEntry;
import com.generics.infinite.stockmanagementsystem.model.Vendor;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class Helper {

    public static final String[] email = {"generics.infinite@gmail.com"};
    public static final String emailSubject = "STOCK MANAGEMENT SYSTEM FEEDBACK";
    public static final String emailType = "message/rfc822";
    public static final String shareType = "text/plain";
    public static final String shareSubject = "Stock Management System\n\n";
    public static final String shareMessage = "Try this amazing app to keep record and manage your stocks";
    public static final String appLink = "LINK COMING SOON !";
    public static final String demoAppLink = "https://play.google.com/store/apps/details?id=\" + BuildConfig.APPLICATION_ID +\"\\n\\n";

    // STATIC OBJECTS FOR EDIT

    public static Vendor editableVendor = null;
    public static Customer editableCustomer = null;
    public static SalesEntry editableSalesEntry = null;
    public static PurchaseEntry editablePurchaseEntry = null;
    public static Product editableProduct = null;

    // VALIDATE FEILDS METHOD

    public static boolean validateFields(String ...data ){

        for(String d : data){
            if(d.isEmpty()){
                return false;
            }
        }
        return true;
    }

    // SHOW TOAST METHOD

    public static void showMsg(Context context, String msg, String type){
        if(type.contentEquals(Message.successType)){
            Toasty.success(context,msg,Toasty.LENGTH_SHORT).show();
        }else if(type.contentEquals(Message.errorType)){
            Toasty.error(context,msg,Toasty.LENGTH_SHORT).show();
        }else if(type.contentEquals(Message.warnningType)){
            Toasty.warning(context,msg,Toasty.LENGTH_SHORT).show();
        }else if(type.contentEquals(Message.infoType)){
            Toasty.info(context,msg,Toasty.LENGTH_SHORT).show();
        }
    }

    // NAVIGATION METHODS

    public static void navigateActivity(Context context, Class destination){
        Intent intent = new Intent(context,destination);
        context.startActivity(intent);
    }
    public static void navigateActivity(Context context, Class destination, String key, String value){
        Intent intent = new Intent(context,destination);
        intent.putExtra(key,value);
        context.startActivity(intent);
    }

    // ALERT DIALOG METHODS

    public static void showConfirmationDialog(Context context, String contentMessage, SweetAlertDialog.OnSweetClickListener confirmListener,
                                     SweetAlertDialog.OnSweetClickListener cancelListener){
        new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE)
                .setTitleText(Message.dialogMsgTitleYouSure)
                .setContentText(contentMessage)
                .setCancelText(Message.dialogMsgCancel)
                .setConfirmText(Message.dialogMsgDelete)
                .showCancelButton(true)
                .setCancelClickListener(cancelListener)
                .setConfirmClickListener(confirmListener)
                .show();
    }

    public static void alertDialog(Context context, final Activity activity){

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(context);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to exit ?");

        // Set Alert Title
        builder.setTitle("Alert !");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                activity.finish();
                            }
                        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

    public static void showAlertDialog(Context context, String Title, String contentMessage, String messageType,
                                            SweetAlertDialog.OnSweetClickListener listener){

        if(messageType.contentEquals(Message.errorType)) {
            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(Title)
                    .setContentText(contentMessage)
                    .setConfirmText(Message.dialogMsgOK)
                    .showCancelButton(false)
                    .setConfirmClickListener(listener)
                    .show();
        }else if(messageType.contentEquals(Message.warnningType)){
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(Title)
                    .setContentText(contentMessage)
                    .setConfirmText(Message.dialogMsgOK)
                    .showCancelButton(false)
                    .setConfirmClickListener(listener)
                    .show();
        }
        else{
            new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(Title)
                    .setContentText(contentMessage)
                    .setConfirmText(Message.dialogMsgOK)
                    .showCancelButton(false)
                    .setConfirmClickListener(listener)
                    .show();
        }
    }


    // BITMAP COMPRESS IMAGE METHOD

    public static Bitmap getCompressedBitmapImage(InputStream inputStream) throws IOException {

        Bitmap image  = BitmapFactory.decodeStream(inputStream);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytes = stream.toByteArray();

        stream.close();
        stream = null;

        return image;
    }

    private static int calcImageSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        int sampleSize = 1;

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        if(height>reqHeight || width>reqWidth){
            //final int halfHeight = height/2;
            //final int halfWidth = width/2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.

            if (width > height) {
                sampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                sampleSize = Math.round((float)width / (float)reqWidth);
            }
        }

           /* while ((halfHeight / sampleSize) >= reqHeight
                    && (halfWidth / sampleSize) >= reqWidth) {
                sampleSize *= 2;
            }*/
        return sampleSize;
    }

    public static Bitmap getSampleBitmap(InputStream inputStream, int reqWidth, int reqHeight) throws IOException {

        Bitmap bitmap = null;

        //InputStream stream  = new BufferedInputStream(inputStream);

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream,null,options);

        // Calculate inSampleSize
        options.inSampleSize = calcImageSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        //stream.reset();
        bitmap =  BitmapFactory.decodeStream(inputStream, null, options);
        return bitmap;
    }

    // REQUEST PERMISSIONS

    public static boolean hasPermissions(Context context,String ...permissions){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null){
            for(String permission : permissions){
                if(ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    // IMAGE PATH METHODS

    public static Uri getUriPath(String folderName, String fileName, String fileExtension){
        Uri Path = null;
        File fileDirectory = Environment.getExternalStoragePublicDirectory(folderName);
        String name = getFileName(fileName, fileExtension);
        if(!fileDirectory.exists()){
            fileDirectory.mkdirs();
        }
        File imageFile = new File(fileDirectory,name);
        Path = Uri.fromFile(imageFile);
        return Path;
    }

    public static File getDirectoryForDatabase(String folderName, String fileName){
        File imageDirectory = Environment.getExternalStoragePublicDirectory(folderName);
        if(!imageDirectory.exists()){
            imageDirectory.mkdirs();
        }
        File dbBackup = new File(imageDirectory,fileName);

        return dbBackup;
    }

    public static File getDirectoryForImage(String folderName, String fileName, String fileExtension){
        File imageDirectory = Environment.getExternalStoragePublicDirectory(folderName);
        String imageName = getFileName(fileName, fileExtension);
        if(!imageDirectory.exists()){
            imageDirectory.mkdirs();
        }
        File imageFile = new File(imageDirectory,imageName);

        return imageFile;
    }

    public static void deleteFileFromStorage(Uri path){

       // if(isFileExist(path)){
            File file = new File(path.getPath());
            file.delete();
     //   }
    }

    public static boolean isFileExist(String path){

        File file = new File(path);
        if(file.exists()){
            return true;
        }
        return false;
    }

    private static String getFileName(String name, String fileExtension){
        String fileName = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = sdf.format(new Date());
        fileName = name+timeStamp+fileExtension;
        return fileName;
    }

}
