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

import com.generics.infinite.stockmanagementsystem.AddCustomerActivity;
import com.generics.infinite.stockmanagementsystem.AddProductActivity;
import com.generics.infinite.stockmanagementsystem.CustomerDetailsActivity;
import com.generics.infinite.stockmanagementsystem.R;
import com.generics.infinite.stockmanagementsystem.SalesOrderActivity;
import com.generics.infinite.stockmanagementsystem.model.Customer;
import com.generics.infinite.stockmanagementsystem.model.SalesEntry;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Key;
import com.generics.infinite.stockmanagementsystem.utilities.Message;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CustomerDetailsAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    List <Customer> customerDetailList;
    Activity activity;
    Intent intent;

    public CustomerDetailsAdapter(Activity activity, Context context, List customerDetailList) {
        this.customerDetailList = customerDetailList;
        this.activity = activity;
        this.context = context;
        this.layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return customerDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return customerDetailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.item_rep_customer_details,null);

        TextView name = (TextView) view.findViewById(R.id.customer_details_name);
        TextView contact = (TextView) view.findViewById(R.id.customer_details_contact);
        TextView company = (TextView) view.findViewById(R.id.customer_details_company);
        TextView address = (TextView) view.findViewById(R.id.customer_details_address);
        TextView email = (TextView) view.findViewById(R.id.customer_details_email);

        ImageView edit = (ImageView) view.findViewById(R.id.customer_details_edit);
        ImageView delete = (ImageView) view.findViewById(R.id.customer_details_delete);
        ImageView salesOrders = (ImageView)view.findViewById(R.id.customer_details_salesOrders);

        final Customer customer = customerDetailList.get(position);

        name.setText(customer.getName());
        contact.setText(customer.getContact());
        company.setText(customer.getCompanyName());
        address.setText(customer.getAddress());
        email.setText(customer.getEmail());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<SalesEntry> salesEntryCusomers = SalesEntry.find(SalesEntry.class,"customer_Name=?",customer.getName());
                if(salesEntryCusomers.size()<=0){
                    Helper.showConfirmationDialog(context,Message.dialogMsgContentCustomerDelete, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.setTitleText(Message.dialogMsgTitleDelete)
                                    .setContentText(Message.dialogMsgCustomerDeleted)
                                    .setConfirmText(Message.dialogMsgOK)
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            customer.delete();
                            Helper.navigateActivity(context,CustomerDetailsActivity.class);
                            activity.finish();
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

                   // Helper.showMsg(context,"Null",Message.infoType);
                }else{
                    Helper.showAlertDialog(context, Message.dialogMsgFoundRecordsInSales, Message.dialogMsgContentFoundRecordsInSales, Message.errorType,
                            new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            });
                   // Helper.showMsg(context,salesEntryCusomers.get(0).getCustomerName(),Message.infoType);
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Helper.editableCustomer = customer;
               Helper.navigateActivity(context,AddCustomerActivity.class,Key.KEY_CUSTOMER_EDIT,Key.VALUE_CUSTOMER_EDIT);
               activity.finish();
            }
        });

        salesOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Helper.navigateActivity(context,SalesOrderActivity.class,Key.keyCustomerToSalesOrder,customer.getName());
                activity.finish();
            }
        });



        return view;
    }
}
