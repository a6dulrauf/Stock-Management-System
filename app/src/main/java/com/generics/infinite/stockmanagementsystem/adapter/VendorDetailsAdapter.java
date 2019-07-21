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

import com.generics.infinite.stockmanagementsystem.AddVendorActivity;
import com.generics.infinite.stockmanagementsystem.CustomerDetailsActivity;
import com.generics.infinite.stockmanagementsystem.PurchaseOrdersActivity;
import com.generics.infinite.stockmanagementsystem.R;
import com.generics.infinite.stockmanagementsystem.VendorDetailsActivity;
import com.generics.infinite.stockmanagementsystem.model.PurchaseEntry;
import com.generics.infinite.stockmanagementsystem.model.Vendor;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Key;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class VendorDetailsAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    List <Vendor> vendorDetailList;
    Activity activity;
    Intent intent;

    public VendorDetailsAdapter(Activity activity, Context context, List vendorDetailList) {
        this.vendorDetailList = vendorDetailList;
        this.activity = activity;
        this.context = context;
        this.layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return vendorDetailList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return vendorDetailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.item_rep_vendor_details,null);

        TextView name = (TextView) view.findViewById(R.id.vendor_details_name);
        TextView contact = (TextView) view.findViewById(R.id.vendor_details_contact);
        TextView company = (TextView) view.findViewById(R.id.vendor_details_company);
        TextView address = (TextView) view.findViewById(R.id.vendor_details_address);
        TextView email = (TextView) view.findViewById(R.id.vendor_details_email);

        ImageView edit = (ImageView) view.findViewById(R.id.vendor_details_edit);
        ImageView delete = (ImageView) view.findViewById(R.id.vendor_details_delete);
       // ImageView purchaseOrders = (ImageView) view.findViewById(R.id.vendor_details_purchaseOrders);

        final Vendor vendor = vendorDetailList.get(position);

        name.setText(vendor.getName());
        contact.setText(vendor.getContact());
        company.setText(vendor.getCompanyName());
        address.setText(vendor.getAddress());
        email.setText(vendor.getEmail());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<PurchaseEntry> purchaseEntryVendors = PurchaseEntry.find(PurchaseEntry.class, "vendor_Name=?", vendor.getName());

                if (purchaseEntryVendors.size() <= 0) {
                    Helper.showConfirmationDialog(context, Message.dialogMsgContentVendorDelete, new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.setTitleText(Message.dialogMsgTitleDelete)
                                    .setContentText(Message.dialogMsgVendorDeleted)
                                    .setConfirmText(Message.dialogMsgOK)
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                            vendor.delete();
                            Helper.navigateActivity(context, VendorDetailsActivity.class);
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

                  //  Helper.showMsg(context,"Null",Message.infoType);
                }else{
                    Helper.showAlertDialog(context, Message.dialogMsgFoundRecordsInPurchases, Message.dialogMsgContentFoundRecordsInPurchases, Message.errorType,
                            new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            });
                  //  Helper.showMsg(context,purchaseEntryVendors.get(0).getVendorName(),Message.infoType);
                }
            }

        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.editableVendor = vendor;
                Helper.navigateActivity(context,AddVendorActivity.class, Key.KEY_VENDOR_EDIT,Key.VALUE_VENDOR_EDIT);
                activity.finish();

            }
        });
        /*
        purchaseOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Helper.navigateActivity(context, PurchaseOrdersActivity.class,Key.keyCustomerToSalesOrder,vendor.getName());
                activity.finish();
            }
        });
        */

        return view;
    }
}
