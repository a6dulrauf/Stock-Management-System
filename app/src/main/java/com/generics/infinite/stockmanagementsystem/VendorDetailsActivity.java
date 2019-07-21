package com.generics.infinite.stockmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.generics.infinite.stockmanagementsystem.adapter.VendorDetailsAdapter;
import com.generics.infinite.stockmanagementsystem.model.Vendor;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

import java.util.ArrayList;
import java.util.List;

public class VendorDetailsActivity extends AppCompatActivity {

    ListView listView;
    VendorDetailsAdapter vendorDetailsAdapter;
    List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_details);

        listView = (ListView) findViewById(R.id.vendor_details_listView);

        dataSource();
        if(list!=null && !list.isEmpty()){
            vendorDetailsAdapter = new VendorDetailsAdapter(this,this,list);

            listView.setAdapter(vendorDetailsAdapter);

        }else{
            Helper.showMsg(this,Message.msgNoRecordFound, Message.infoType);
        }
    }

    private void dataSource(){
        this.list=Vendor.listAll(Vendor.class);
    }

}
