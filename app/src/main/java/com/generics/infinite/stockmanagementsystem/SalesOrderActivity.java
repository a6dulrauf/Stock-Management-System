package com.generics.infinite.stockmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.generics.infinite.stockmanagementsystem.adapter.SalesOrdersAdapter;
import com.generics.infinite.stockmanagementsystem.model.SalesEntry;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Key;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

import java.util.ArrayList;
import java.util.List;

public class SalesOrderActivity extends AppCompatActivity {

    ListView listView;
    SalesOrdersAdapter salesOrdersAdapter;
    List<SalesEntry>list;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order);

        value = getIntent().getStringExtra(Key.keyCustomerToSalesOrder);
        if(value == null){
            dataSource();
        }else{
            specificProductDataSource(value);
        }

        listView = (ListView) findViewById(R.id.sales_order_listview);
        if (this.list != null && !this.list.isEmpty()) {
            salesOrdersAdapter = new SalesOrdersAdapter(this, this, list);
            listView.setAdapter(salesOrdersAdapter);
        }else{
            Helper.showMsg(this,Message.msgNoRecordFound, Message.infoType);
        }
    }

    private void dataSource(){
        this.list = SalesEntry.listAll(SalesEntry.class);
    }

    private void specificProductDataSource(String value){
        List<SalesEntry>specificList = SalesEntry.listAll(SalesEntry.class);
        this.list = new ArrayList();
        for(SalesEntry p : specificList){
            if(p.getCustomerName().contentEquals(value)){
                this.list.add(p);
            }
        }
    }
}
