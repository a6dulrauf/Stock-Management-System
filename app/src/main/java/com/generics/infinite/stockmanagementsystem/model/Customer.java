package com.generics.infinite.stockmanagementsystem.model;

import com.orm.SugarRecord;

public class Customer extends SugarRecord<Customer> {

    private String name;
    private String companyName;
    private String address;
    private String contact;
    private String email;

    public Customer(){


    }

    public Customer(String name, String companyName, String address, String contact, String email){
        this.name = name;
        this.companyName = companyName;
        this.address = address;
        this.contact = contact;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
