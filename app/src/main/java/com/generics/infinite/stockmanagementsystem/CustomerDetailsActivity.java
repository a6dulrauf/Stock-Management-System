package com.generics.infinite.stockmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.generics.infinite.stockmanagementsystem.adapter.CustomerDetailsAdapter;
import com.generics.infinite.stockmanagementsystem.model.Customer;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

import java.util.List;

public class CustomerDetailsActivity extends AppCompatActivity {

    ListView listView;
    CustomerDetailsAdapter customerDetailsAdapter;
    List <Customer>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);


        listView = (ListView) findViewById(R.id.customer_details_listview);

        try{
            dataSource();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
        if(list!=null && !list.isEmpty()){
            try{
                customerDetailsAdapter = new CustomerDetailsAdapter(this,this,list);

                listView.setAdapter(customerDetailsAdapter);

            }catch (Exception e){
                Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_LONG).show();
            }

        }else{
            Helper.showMsg(this,Message.msgNoRecordFound,Message.infoType);
        }
    }

    private void dataSource(){
        this.list=Customer.listAll(Customer.class);
    }
}
