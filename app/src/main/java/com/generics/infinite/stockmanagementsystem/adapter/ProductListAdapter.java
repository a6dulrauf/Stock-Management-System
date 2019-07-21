package com.generics.infinite.stockmanagementsystem.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.generics.infinite.stockmanagementsystem.AddProductActivity;
import com.generics.infinite.stockmanagementsystem.CustomerDetailsActivity;
import com.generics.infinite.stockmanagementsystem.ProductListActivity;
import com.generics.infinite.stockmanagementsystem.PurchaseOrdersActivity;
import com.generics.infinite.stockmanagementsystem.R;
import com.generics.infinite.stockmanagementsystem.model.Product;
import com.generics.infinite.stockmanagementsystem.utilities.GlideApp;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Key;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductListAdapter extends BaseAdapter{

    LayoutInflater layoutInflater;
    Context context;
    List<Product> productList;
    Activity activity;
    Intent intent;

    public ProductListAdapter(Activity activity, Context context, List productList) {
        this.productList = productList;
        this.activity = activity;
        this.context = context;
        this.layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.item_rep_product_list,null);

        TextView name  = (TextView) view.findViewById(R.id.product_list_name);
        TextView price  = (TextView) view.findViewById(R.id.product_list_price);
        TextView qty  = (TextView) view.findViewById(R.id.product_list_qty);
        ImageView image = (ImageView) view.findViewById(R.id.product_list_imgview);

        ImageView edit = (ImageView) view.findViewById(R.id.product_list_edit);
        ImageView delete = (ImageView) view.findViewById(R.id.product_list_delete);
        ImageView purchase = (ImageView) view.findViewById(R.id.product_list_purchase);

        final Product product = productList.get(position);

        name.setText(product.getName());
        price.setText(product.getColor());
        qty.setText(product.getSize());
        if(product.getImage() != null) {
                GlideApp.with(context).load(product.getImage()).fitCenter().into(image);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Helper.showConfirmationDialog(context,Message.dialogMsgContentProductDelete, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.setTitleText(Message.dialogMsgTitleDelete)
                                .setContentText(Message.dialogMsgProductDeleted)
                                .setConfirmText(Message.dialogMsgOK)
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        if(product.getImage() != null) {
                            Helper.deleteFileFromStorage(Uri.parse(product.getImage()));
                        }
                        product.delete();
                        Helper.navigateActivity(context,ProductListActivity.class);
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
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.editableProduct = product;
                Helper.navigateActivity(context,AddProductActivity.class,Key.KEY_PRODUCT_LIST_EDIT, Key.VALUE_PRODUCT_LIST_EDIT);
                activity.finish();
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Only Show Clicked Product Purchases
               Helper.navigateActivity(context,PurchaseOrdersActivity.class,Key.keyProductListToPurchaseOrder,product.getName());
               activity.finish();
            }
        });

        return view;
    }
}
