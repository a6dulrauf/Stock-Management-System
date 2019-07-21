package com.generics.infinite.stockmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.generics.infinite.stockmanagementsystem.adapter.PurchaseOrderAdapter;
import com.generics.infinite.stockmanagementsystem.model.PurchaseEntry;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Key;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrdersActivity extends AppCompatActivity {

    ListView listView;
    PurchaseOrderAdapter purchaseOrderAdapter;
    List list;
    List<PurchaseEntry> specificList;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_orders);

        value = getIntent().getStringExtra(Key.keyProductListToPurchaseOrder);

        if(value == null){
            dataSource();
        }else{
            specificProductDataSource(value);
        }

        listView = (ListView) findViewById(R.id.purchase_order_listview);
        if (this.list !=null && !this.list.isEmpty()) {
            purchaseOrderAdapter = new PurchaseOrderAdapter(this, this, list);

            listView.setAdapter(purchaseOrderAdapter);
        }else{
            Helper.showMsg(this,Message.msgNoRecordFound,Message.infoType);
        }
    }

    private void dataSource(){
        this.list = PurchaseEntry.listAll(PurchaseEntry.class);
    }

    private void specificProductDataSource(String value){
        this.specificList = PurchaseEntry.listAll(PurchaseEntry.class);
        this.list = new ArrayList();
        for(PurchaseEntry p : this.specificList){
            if(p.getProductName().contentEquals(value)){
                this.list.add(p);
            }
        }
    }
}
