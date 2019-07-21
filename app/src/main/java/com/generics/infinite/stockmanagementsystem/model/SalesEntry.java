package com.generics.infinite.stockmanagementsystem.model;

import com.orm.SugarRecord;

public class SalesEntry extends SugarRecord<SalesEntry> {

    private String productName;
    private String customerName;
    private String salesQuantity;
    private String salesDate;
    private String salesPrice;
    private String salesType;

    public SalesEntry() {

    }

    public SalesEntry(String productName, String customerName, String salesQuantity, String salesDate, String salesPrice, String salesType) {
        this.productName = productName;
        this.customerName = customerName;
        this.salesQuantity = salesQuantity;
        this.salesDate = salesDate;
        this.salesPrice = salesPrice;
        this.salesType = salesType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(String salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public String getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
    }

    public String getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }
}
