package com.generics.infinite.stockmanagementsystem;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.generics.infinite.stockmanagementsystem.model.PurchaseEntry;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Message;
import com.generics.infinite.stockmanagementsystem.utilities.Permission;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    ImageView purchase_entry;
    ImageView product_list;
    ImageView purchase_orders;
    ImageView vendor_details;
    ImageView sales_orders;
    ImageView add_customer;
    ImageView add_vendor;
    ImageView customer_details;
    ImageView add_Product;
    ImageView sales_entry;
    ImageView profit_loss;
    ImageView stock_on_hand;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initComponents();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initComponents(){
        purchase_entry = (ImageView) findViewById(R.id.menu_img_purchase_entry);
        product_list = (ImageView) findViewById(R.id.menu_img_product_list);
        purchase_orders = (ImageView) findViewById(R.id.menu_img_purchase_orders);
        vendor_details = (ImageView) findViewById(R.id.menu_img_vendor_details);
        sales_orders = (ImageView) findViewById(R.id.menu_img_sales_order);
        add_customer = (ImageView) findViewById(R.id.menu_img_add_customer);
        add_vendor = (ImageView) findViewById(R.id.menu_img_add_vendor);
        customer_details = (ImageView) findViewById(R.id.menu_img_customer_details);
        add_Product = (ImageView) findViewById(R.id.menu_img_add_product);
        sales_entry = (ImageView) findViewById(R.id.menu_img_sales_entry);
        profit_loss = (ImageView) findViewById(R.id.menu_img_profitloss);
        stock_on_hand = (ImageView) findViewById(R.id.menu_img_stockonhand);


        setClickListeners();
    }

    private void setClickListeners(){
        purchase_entry.setOnClickListener(this);
        purchase_orders.setOnClickListener(this);
        product_list.setOnClickListener(this);
        vendor_details.setOnClickListener(this);
        customer_details.setOnClickListener(this);
        sales_orders.setOnClickListener(this);
        add_customer.setOnClickListener(this);
        add_vendor.setOnClickListener(this);
        add_Product.setOnClickListener(this);
        sales_entry.setOnClickListener(this);
        profit_loss.setOnClickListener(this);
        stock_on_hand.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id. nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_settings) {
            Helper.navigateActivity(this,SettingsActivity.class);

        } else if (id == R.id.nav_slideshow) {
            Helper.navigateActivity(this,SlideShowActivity.class);

        } else if (id == R.id.nav_aboutApp) {
            newFeatureCommingSoon();

        } else if (id == R.id.nav_getPremium) {
            newFeatureCommingSoon();

        } else if (id == R.id.nav_share) {
            shareApp();

        } else if (id == R.id.nav_feedback) {
            sendFeedBack();

        } else if (id == R.id.nav_rateApp) {
            rateApp();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendFeedBack(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));//only for email apps
        intent.putExtra(Intent.EXTRA_EMAIL,Helper.email) ;
        intent.putExtra(Intent.EXTRA_SUBJECT,Helper.emailSubject);

        //intent.setType(Helper.emailType);
        try {
            startActivity(Intent.createChooser(intent, "EMAIL"));
        }catch (Exception e){
            Helper.showMsg(this,e.getMessage(),Message.errorType);
        }
    }

    private void shareApp(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(Helper.shareType);
        intent.putExtra(Intent.EXTRA_SUBJECT,Helper.shareSubject);
        intent.putExtra(Intent.EXTRA_TEXT,Helper.shareSubject+Helper.appLink);
        startActivity(Intent.createChooser(intent,"SHARE"));
    }

    private void rateApp(){
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }

    private void newFeatureCommingSoon(){
        Helper.showAlertDialog(this,Message.dialogMsgNewFeatureComing, Message.dialogMsgContentNewFeatureComing,Message.successType,
                new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.menu_img_purchase_entry:
                Helper.navigateActivity(this,PurchaseEntryActivity.class);
                break;
            case R.id.menu_img_product_list:
                Helper.navigateActivity(this,ProductListActivity.class);
                break;
            case R.id.menu_img_purchase_orders:
                Helper.navigateActivity(this,PurchaseOrdersActivity.class);
                break;
            case R.id.menu_img_vendor_details:
                Helper.navigateActivity(this,VendorDetailsActivity.class);
                break;
            case R.id.menu_img_sales_order:
                Helper.navigateActivity(this,SalesOrderActivity.class);
                break;
            case R.id.menu_img_add_vendor:
                Helper.navigateActivity(this,AddVendorActivity.class);
                break;
            case R.id.menu_img_customer_details:
                Helper.navigateActivity(this,CustomerDetailsActivity.class);
                break;
            case R.id.menu_img_add_customer:
                Helper.navigateActivity(this,AddCustomerActivity.class);
                break;
            case R.id.menu_img_add_product:
                Helper.navigateActivity(this,AddProductActivity.class);
                break;
            case R.id.menu_img_sales_entry:
                Helper.navigateActivity(this,SalesEntryActivity.class);
                break;
            case R.id.menu_img_profitloss:
                Helper.navigateActivity(this,ProfitLossActivity.class);
                break;
            case R.id.menu_img_stockonhand:
                newFeatureCommingSoon();
                break;
        }
    }
}
