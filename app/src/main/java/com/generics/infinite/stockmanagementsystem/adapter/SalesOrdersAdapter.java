package com.generics.infinite.stockmanagementsystem.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.generics.infinite.stockmanagementsystem.PurchaseOrdersActivity;
import com.generics.infinite.stockmanagementsystem.R;
import com.generics.infinite.stockmanagementsystem.SalesOrderActivity;
import com.generics.infinite.stockmanagementsystem.model.SalesEntry;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SalesOrdersAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    List<SalesEntry> salesOrdersList;
    Activity activity;
    Intent intent;
    private int count;

    public SalesOrdersAdapter(Context context, Activity activity, List salesOrdersList){
        this.salesOrdersList = salesOrdersList;
        this.activity = activity;
        this.context = context;
        this.count=0;
        this.layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return salesOrdersList.size();
    }

    @Override
    public Object getItem(int position) {
        return salesOrdersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=layoutInflater.inflate(R.layout.item_rep_sales_orders,null);
        TextView srNo=(TextView)view.findViewById(R.id.sales_orders_datatxtSrNo);
        TextView date=(TextView)view.findViewById(R.id.sales_orders_datatxtDate);
        TextView productName=(TextView)view.findViewById(R.id.sales_orders_dataproductName);
        TextView productQty=(TextView)view.findViewById(R.id.sales_orders_dataqty);
        TextView productPrice=(TextView)view.findViewById(R.id.sales_orders_datapriceunit);
        TextView totalAmount=(TextView)view.findViewById(R.id.sales_orders_datatotalAmount);
        TextView salesType=(TextView)view.findViewById(R.id.sales_orders_datasalesType);
        TextView customerName=(TextView)view.findViewById(R.id.sales_orders_dataCustomerName);

        ImageView edit=(ImageView)view.findViewById(R.id.sales_orders_dataedit);
        ImageView delete=(ImageView)view.findViewById(R.id.sales_orders_datadelete);

        final SalesEntry salesEntry = salesOrdersList.get(position);
        this.count++;

        srNo.setText(String.valueOf(this.count));
        date.setText(salesEntry.getSalesDate());
        productName.setText(salesEntry.getProductName());
        productQty.setText(salesEntry.getSalesQuantity());
        productPrice.setText(salesEntry.getSalesPrice());
        double amount = Double.parseDouble(salesEntry.getSalesQuantity())*Double.parseDouble(salesEntry.getSalesPrice());
        totalAmount.setText(String.valueOf(amount));
        salesType.setText(salesEntry.getSalesType());
        customerName.setText(salesEntry.getCustomerName());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Helper.showConfirmationDialog(context,Message.dialogMsgContentSalesDelete, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.setTitleText(Message.dialogMsgTitleDelete)
                                .setContentText(Message.dialogMsgSalesDeleted)
                                .setConfirmText(Message.dialogMsgOK)
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        salesEntry.delete();
                        Helper.navigateActivity(context,SalesOrderActivity.class);
                        Helper.showMsg(context,Message.msgDeletedSuccessFully,Message.successType);
                        activity.finish();
                        count--;
                    }
                }, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.setTitleText(Message.dialogMsgTitleCancel)
                                .setContentText(Message.dialogMsgDeletionCancelled)
                                .setConfirmText(Message.dialogMsgOK)
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                });
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.showMsg(context,"EDIT",Message.infoType);
            }
        });

        return view;
    }
}
