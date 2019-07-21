package com.generics.infinite.stockmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.generics.infinite.stockmanagementsystem.adapter.ProductListAdapter;
import com.generics.infinite.stockmanagementsystem.model.Product;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    ListView listView;
    ProductListAdapter productListAdapter;
    List <Product>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        listView = (ListView) findViewById(R.id.product_list_listview);
        dataSource();
        if (this.list==null || this.list.isEmpty()){
            Helper.showMsg(this,Message.msgNoRecordFound, Message.infoType);
        }else{

            productListAdapter = new ProductListAdapter(this,this,list);

            listView.setAdapter(productListAdapter);

        }
    }

    private void dataSource(){
        this.list=Product.listAll(Product.class);
    }
}
