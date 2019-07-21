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

import com.generics.infinite.stockmanagementsystem.ProductListActivity;
import com.generics.infinite.stockmanagementsystem.PurchaseEntryActivity;
import com.generics.infinite.stockmanagementsystem.PurchaseOrdersActivity;
import com.generics.infinite.stockmanagementsystem.R;
import com.generics.infinite.stockmanagementsystem.model.PurchaseEntry;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Key;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PurchaseOrderAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    List<PurchaseEntry> purchaseList;
    Activity activity;
    Intent intent;
    private int count;

    public PurchaseOrderAdapter(Context context, Activity activity, List purchaseList){
        this.purchaseList = purchaseList;
        this.activity = activity;
        this.context = context;
        this.count=0;
        this.layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return purchaseList.size();
    }

    @Override
    public Object getItem(int position) {
        return purchaseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=layoutInflater.inflate(R.layout.item_rep_purchase_orders,null);
        TextView srNo=(TextView)view.findViewById(R.id.purchase_orders_datatxtSrNo);
        TextView date=(TextView)view.findViewById(R.id.purchase_orders_datatxtDate);
        TextView productName=(TextView)view.findViewById(R.id.purchase_orders_dataproductName);
        TextView productQty=(TextView)view.findViewById(R.id.purchase_orders_dataqty);
        TextView productPrice=(TextView)view.findViewById(R.id.purchase_orders_datapriceunit);
        TextView totalAmount=(TextView)view.findViewById(R.id.purchase_orders_datatotalAmount);
        TextView purchaseType=(TextView)view.findViewById(R.id.purchase_orders_datapurchaseType);
        TextView vendorName=(TextView)view.findViewById(R.id.purchase_orders_datavendorName);

        ImageView edit=(ImageView)view.findViewById(R.id.purchase_orders_dataedit);
        ImageView delete=(ImageView)view.findViewById(R.id.purchase_orders_datadelete);

        final PurchaseEntry purchaseEntry = purchaseList.get(position);
        this.count++;

        srNo.setText(String.valueOf(this.count));
        date.setText(purchaseEntry.getPurchaseDate());
        productName.setText(purchaseEntry.getProductName());
        productQty.setText(purchaseEntry.getPurchaseQuantity());
        productPrice.setText(purchaseEntry.getPurchasePrice());
        double amount=Double.parseDouble(purchaseEntry.getPurchaseQuantity())*Double.parseDouble(purchaseEntry.getPurchasePrice());
        totalAmount.setText(String.valueOf(amount));
        purchaseType.setText(purchaseEntry.getPurchaseType());
        vendorName.setText(purchaseEntry.getVendorName());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Helper.showConfirmationDialog(context,Message.dialogMsgContentPurchaseDelete, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.setTitleText(Message.dialogMsgTitleDelete)
                                .setContentText(Message.dialogMsgPurchaseDeleted)
                                .setConfirmText(Message.dialogMsgOK)
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        purchaseEntry.delete();
                        Helper.navigateActivity(context,PurchaseOrdersActivity.class);
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
                Helper.editablePurchaseEntry = purchaseEntry;
                Helper.navigateActivity(context,PurchaseEntryActivity.class, Key.KEY_PURCHASE_ORDERS_EDIT,Key.VALUE_PURCHASE_ORDERS_EDIT);
                activity.finish();
            }
        });

        return view;
    }
}
