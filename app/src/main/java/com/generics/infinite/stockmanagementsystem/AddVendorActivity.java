package com.generics.infinite.stockmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.generics.infinite.stockmanagementsystem.model.Vendor;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Key;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

public class AddVendorActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name;
    EditText companyName;
    EditText address;
    EditText contact;
    EditText email;
    Button btnAddVendor;

    private Vendor editableVendor;
    private boolean isVendorEditable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vendor);
        initComponents();

        btnAddVendor.setText("ADD VENDOR");
        String value = getIntent().getStringExtra(Key.KEY_VENDOR_EDIT);
        if(value != null && value.contentEquals(Key.VALUE_VENDOR_EDIT)){
            setFields();
            isVendorEditable = true;
        }
    }

    private void setFields() {
        editableVendor = Helper.editableVendor;

        name.setText(editableVendor.getName());
        contact.setText(editableVendor.getContact());
        companyName.setText(editableVendor.getCompanyName());
        address.setText(editableVendor.getAddress());
        email.setText(editableVendor.getEmail());

        btnAddVendor.setText("UPDATE VENDOR");
    }

    private void initComponents(){

        isVendorEditable = false;

        name = (EditText) findViewById(R.id.add_vendor_vendorName);
        address = (EditText) findViewById(R.id.add_vendor_Address);
        companyName = (EditText) findViewById(R.id.add_vendor_companyName);
        contact = (EditText) findViewById(R.id.add_vendor_mobileNo);
        email = (EditText) findViewById(R.id.add_vendor_emailID);

        btnAddVendor = (Button) findViewById(R.id.add_vendor_btnAdd);

        btnAddVendor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.add_vendor_btnAdd:
                addVendor();
        }
    }

    private void addVendor(){

        String name = this.name.getText().toString();
        String companyName = this.companyName.getText().toString();
        String email = this.email.getText().toString();
        String address = this.address.getText().toString();
        String contact = this.contact.getText().toString();

        if(Helper.validateFields(name,companyName,email,address,contact)) {

            if(isVendorEditable){
                editableVendor = Vendor.findById(Vendor.class,Helper.editableVendor.getId());
                editableVendor.setName(name);
                editableVendor.setCompanyName(companyName);
                editableVendor.setAddress(address);
                editableVendor.setContact(contact);
                editableVendor.setEmail(email);

                editableVendor.save();
                Helper.showMsg(this,Message.msgUpdatedSuccessFully,Message.successType);
                finish();

            }else {
                Vendor vendor = new Vendor(name, companyName, address, contact, email);
                vendor.save();
                Helper.showMsg(this, Message.msgAddedSuccessFully, Message.successType);
                finish();
            }
        }else{

            Helper.showMsg(this,Message.msgEnterRequiredFields, Message.warnningType);
        }
    }
}
