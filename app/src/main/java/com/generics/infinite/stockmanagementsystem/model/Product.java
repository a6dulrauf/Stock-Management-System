package com.generics.infinite.stockmanagementsystem.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import com.orm.SugarRecord;

import java.io.File;

public class Product extends SugarRecord<Product> {

    private String name;
    private String color;
    private int barCode;
    private String size;
    private String image;

    public Product(){

    }

    public Product(String name, String color, int barCode,String size,String image) {
        this.name = name;
        this.color = color;
        this.barCode = barCode;
        this.size = size;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }
}
