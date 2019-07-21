package com.generics.infinite.stockmanagementsystem.model;

import com.orm.SugarRecord;

import java.util.Date;

public class PurchaseEntry extends SugarRecord<PurchaseEntry> {

    private String productName;
    private String vendorName;
    private String purchaseQuantity;
    private String purchaseDate;
    private String purchasePrice;
    private String purchaseType;

    public PurchaseEntry() {

    }

    public PurchaseEntry(String productName, String vendorName, String purchaseQuantity, String purchaseDate, String purchasePrice, String purchaseType) {
        this.productName = productName;
        this.vendorName = vendorName;
        this.purchaseQuantity = purchaseQuantity;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.purchaseType = purchaseType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(String purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }
}
