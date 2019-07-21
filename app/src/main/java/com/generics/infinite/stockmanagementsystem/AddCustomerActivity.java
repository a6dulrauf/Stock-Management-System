package com.generics.infinite.stockmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.generics.infinite.stockmanagementsystem.model.Customer;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Key;
import com.generics.infinite.stockmanagementsystem.utilities.Message;


public class AddCustomerActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name;
    EditText companyName;
    EditText address;
    EditText contact;
    EditText email;
    Button btnAddCustomer;

    private Customer editableCustomer;
    private boolean isCustomerEditable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        initComponents();

        btnAddCustomer.setText("ADD CUSTOMER");
        String value = getIntent().getStringExtra(Key.KEY_CUSTOMER_EDIT);
        if(value != null && value.contentEquals(Key.VALUE_CUSTOMER_EDIT)){
            setFields();
            isCustomerEditable = true;
        }
    }

    private void setFields() {
        editableCustomer = Helper.editableCustomer;

        if(editableCustomer!= null){
            name.setText(editableCustomer.getName());
            address.setText(editableCustomer.getAddress());
            companyName.setText(editableCustomer.getCompanyName());
            contact.setText(editableCustomer.getContact());
            email.setText(editableCustomer.getEmail());

            btnAddCustomer.setText("UPDATE CUSTOMER");
        }
    }

    private void initComponents(){

        isCustomerEditable = false;

        name = (EditText) findViewById(R.id.add_customer_customerName);
        address = (EditText) findViewById(R.id.add_customer_Address);
        companyName = (EditText) findViewById(R.id.add_customer_companyName);
        contact = (EditText) findViewById(R.id.add_customer_mobileNo);
        email = (EditText) findViewById(R.id.add_customer_emailID);

        btnAddCustomer= (Button) findViewById(R.id.add_customer_btnAdd);

        btnAddCustomer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.add_customer_btnAdd:
                addCustomer();
        }
    }

    private void addCustomer() {

        String name = this.name.getText().toString();
        String companyName = this.companyName.getText().toString();
        String email = this.email.getText().toString();
        String address = this.address.getText().toString();
        String contact = this.contact.getText().toString();

        if(Helper.validateFields(name,companyName,email,address,contact)) {

            if(isCustomerEditable){
                editableCustomer = Customer.findById(Customer.class,Helper.editableCustomer.getId());
                editableCustomer.setName(name);
                editableCustomer.setAddress(address);
                editableCustomer.setContact(contact);
                editableCustomer.setCompanyName(companyName);
                editableCustomer.setEmail(email);

                editableCustomer.save();
                Helper.showMsg(this,Message.msgUpdatedSuccessFully,Message.successType);
                finish();

            }else {
                Customer customer = new Customer(name, companyName, address, contact, email);
                customer.save();
                Helper.showMsg(this, Message.msgAddedSuccessFully, Message.successType);
                finish();
            }
        }else{

            Helper.showMsg(this,Message.msgEnterRequiredFields,Message.warnningType);
        }
    }
}
