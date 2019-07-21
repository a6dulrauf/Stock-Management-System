package com.generics.infinite.stockmanagementsystem;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.generics.infinite.stockmanagementsystem.model.Product;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Key;
import com.generics.infinite.stockmanagementsystem.utilities.Message;
import com.generics.infinite.stockmanagementsystem.utilities.Permission;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Permissions;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView productImage;
    Intent intent;

    Bitmap captureImage;
    Uri imagePath;
    EditText name;
    EditText color;
    EditText barcode;
    EditText size;
    Button btnAdd;
    boolean isImageExist;

    Product editableProduct;
    boolean isProductEditable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initComponents();

        btnAdd.setText("ADD PRODUCT");
        String value = getIntent().getStringExtra(Key.KEY_PRODUCT_LIST_EDIT);
        if(value != null && value.contentEquals(Key.VALUE_PRODUCT_LIST_EDIT)){
            setFields();
            isProductEditable = true;
        }
    }

    private void setFields() {
        editableProduct = Helper.editableProduct;
        if(editableProduct != null) {
            if(editableProduct.getImage() != null){
                Glide.with(this).load(editableProduct.getImage())
                        .fitCenter().into(productImage);
            }
            this.name.setText(editableProduct.getName());
            this.color.setText(editableProduct.getColor());
            this.barcode.setText(String.valueOf(editableProduct.getBarCode()));
            this.size.setText(editableProduct.getSize());

            this.btnAdd.setText("UPDATE PRODUCT");
        }
    }

    private void initComponents(){

        isImageExist = false;
        isProductEditable = false;

        productImage = (ImageView) findViewById(R.id.add_product_productImage);
        name = (EditText)findViewById(R.id.add_product_productName);
        color = (EditText)findViewById(R.id.add_product_productColor);
        barcode = (EditText)findViewById(R.id.add_product_productBarCode);
        size = (EditText)findViewById(R.id.add_product_productSize);
        btnAdd = (Button)findViewById(R.id.add_product_btnAdd);

        btnAdd.setOnClickListener(this);
        productImage.setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.add_product_productImage:

                String[] permissions = {Permission.READ_PERMISSION,Permission.WRITE_PERMISSION,Permission.CAMERA_PERMISSION};
                if(Helper.hasPermissions(this,permissions)){
                    imagePath = null;
                    startCropImageActivity();
                }else {
                    ActivityCompat.requestPermissions(this,permissions,Permission.ALL_PERMISSION_KEY);
                }
                break;
            case R.id.add_product_btnAdd:
                addProduct();
                break;
        }
    }

    private void oldMethodToStoreImage(){
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            imagePath = Helper.getUriPath("Product_Images", "Product_Image",".jpg");
            if (imagePath != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath);
                startActivityForResult(intent, Permission.ALL_PERMISSION_KEY);
            }
        }
    }
    private void oldActivityResult(){

        int resultCode = 1;
        int requestCode = 2;
        if(resultCode == RESULT_OK && requestCode == Permission.ALL_PERMISSION_KEY) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(imagePath);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                //  Bitmap image = Helper.getCompressedBitmapImage(inputStream);
                productImage.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                Helper.showMsg(this,e.getMessage(), Message.errorType);
            } catch (IOException e) {
                Helper.showMsg(this,e.getMessage(), Message.errorType);
            }
        }
    }

    private void startCropImageActivity(){
        CropImage.activity()
                .setAspectRatio(1,1)
              //  .setFixAspectRatio(true)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private void addProduct() {
        String pName,barCode,size,color;
        pName = this.name.getText().toString();
        barCode = this.barcode.getText().toString();
        size = this.size.getText().toString();
        color = this.color.getText().toString();
        if(Helper.validateFields(pName,barCode,size,color)){

            if(isProductEditable){

                editableProduct = Product.findById(Product.class,Helper.editableProduct.getId());

                editableProduct.setName(pName);
                editableProduct.setColor(color);
                editableProduct.setSize(size);
                editableProduct.setBarCode(Integer.parseInt(barCode));

                if(isImageExist){
                    if(editableProduct.getImage() != null) {
                        Helper.deleteFileFromStorage(Uri.parse(editableProduct.getImage()));
                    }
                     imagePath = Uri.fromFile(saveImageOnMobileStorage());
                     editableProduct.setImage(imagePath.toString());
                }
                editableProduct.save();
                Helper.showMsg(this, Message.msgUpdatedSuccessFully, Message.successType);
                finish();
            }
            else {
                Product product = new Product();
                product.setBarCode(Integer.parseInt(barCode));
                product.setColor(color);
                product.setSize(size);
                product.setName(pName);
                if (isImageExist) {
                    imagePath = Uri.fromFile(saveImageOnMobileStorage());
                    product.setImage(imagePath.toString());
                }
                product.save();
                Helper.showMsg(this, Message.msgAddedSuccessFully, Message.successType);
                finish();
            }
        }
        else{
            Helper.showMsg(this, Message.msgEnterRequiredFields, Message.warnningType);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
/*
        if(requestCode == Permission.ALL_PERMISSION_KEY && resultCode == Activity.RESULT_OK){
           // Uri imageUri = CropImage.getPickImageResultUri(this, data);
            CropImage.activity(imagePath).start(this);
            //productImage.setImageURI(imageUri);
        }

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            productImage.setImageURI(imageUri);
            //startCropImageActivity(imageUri);
        }*/
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri uri = result.getUri();
                productImage.setImageURI(uri);
                isImageExist = true;
            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception exception = result.getError();
                Helper.showMsg(this,exception.getMessage(),Message.errorType);
            }
        }
    }

    private File saveImageOnMobileStorage(){
        FileOutputStream  outputStream = null;
        File outFile = null;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) productImage.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        try {
            outFile = Helper.getDirectoryForImage("Product_Images", "Product_Image",".jpg");
            outputStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            Helper.showMsg(this, e.getMessage(), Message.errorType);
        }
        return outFile;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){

            case CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Helper.showMsg(this,"PERMISSION CAMERA GRANTED",Message.infoType);
                    CropImage.startPickImageActivity(this);
                }else{
                    Helper.showMsg(this,"PERMISSIONS NOT GIVEN",Message.infoType);
                }
            case CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE:
                if(imagePath != null && grantResults.length >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    startCropImageActivity(imagePath);
                }else{
                    Helper.showMsg(this,"PERMISSIONS NOT GIVEN",Message.infoType);
                }
        }
    }
}
