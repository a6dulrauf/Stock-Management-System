package com.generics.infinite.stockmanagementsystem;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.generics.infinite.stockmanagementsystem.model.Product;
import com.generics.infinite.stockmanagementsystem.model.PurchaseEntry;
import com.generics.infinite.stockmanagementsystem.model.Vendor;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Key;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PurchaseEntryActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    AutoCompleteTextView productName;
    AutoCompleteTextView vendorName;
    EditText quantity;
    EditText price;
    EditText date;

    Calendar calendar;

    ImageView addProduct;
    ImageView addVendor;

    Spinner purchaseType;

    Button btnAdd;

    ArrayAdapter<String> arrayAdapterPurchaseType;
    ArrayAdapter<String> arrayAdapterProduct;
    ArrayAdapter<String> arrayAdapterVendor;

    List<String> purchaseTypeSuggestions;
    List<String> productSugesstions;
    List<String> vendorSugesstions;

    List<Product> productList;
    List<Vendor> vendorList;

    String productID="-1";
    String purchaseEntryID="-1";

    private PurchaseEntry editablePurchaseEntry;
    private boolean isPurchaseEditable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_purchase_entry);
        initComponents();
        isPurchaseEditable=false;
        btnAdd.setText("ADD");
        String value = getIntent().getStringExtra(Key.KEY_PURCHASE_ORDERS_EDIT);
        if(value !=null && value.contentEquals(Key.VALUE_PURCHASE_ORDERS_EDIT)){
            setFields();
            isPurchaseEditable = true;
            btnAdd.setText("Update");
        }
        updateDateNow();
    }

    private void initComponents(){
        calendar = Calendar.getInstance();

        productName=(AutoCompleteTextView)findViewById(R.id.purchase_entry_productName);
        vendorName=(AutoCompleteTextView)findViewById(R.id.purchase_entry_VendorName);

        quantity=(EditText)findViewById(R.id.purchase_entry_purchaseQty);
        date=(EditText)findViewById(R.id.purchase_entry_purchaseDate);
        price=(EditText)findViewById(R.id.purchase_entry_purchasePrice);

        purchaseType=(Spinner) findViewById(R.id.purchase_entry_purchaseType);

        addProduct = (ImageView) findViewById(R.id.purchase_entry_addProduct);
        addVendor= (ImageView) findViewById(R.id.purchase_entry_addVendor);

        btnAdd= (Button) findViewById(R.id.purchase_entry_btnAdd);

        arrayAdapterPurchaseType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getPurchaseTypeSuggestions());
        purchaseType.setAdapter(arrayAdapterPurchaseType);

        btnAdd.setOnClickListener(this);
        addProduct.setOnClickListener(this);
        addVendor.setOnClickListener(this);
        date.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        arrayAdapterVendor = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getVendorName());
        vendorName.setAdapter(arrayAdapterVendor);

        arrayAdapterProduct = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getProductNames());
        productName.setAdapter(arrayAdapterProduct);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.purchase_entry_btnAdd:
                if(isPurchaseEditable)
                    addPurchaseEntry();
                else
                    addPurchaseEntryWithoutUpdate();
                break;
            case R.id.purchase_entry_addProduct:
                Helper.navigateActivity(this,AddProductActivity.class);
                break;
            case R.id.purchase_entry_addVendor:
                Helper.navigateActivity(this,AddVendorActivity.class);
                break;
            case R.id.purchase_entry_purchaseDate:
                DatePickerDialog dialog = new DatePickerDialog(this, this,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
        }
    }

    private List<String> getPurchaseTypeSuggestions(){

        this.purchaseTypeSuggestions = new ArrayList<>();
        purchaseTypeSuggestions.add("Cash");
        purchaseTypeSuggestions.add("Debit");

        return this.purchaseTypeSuggestions;
    }
    private List<String> getProductNames(){
        this.productList=Product.listAll(Product.class);

        this.productSugesstions=new ArrayList<>();
        if(this.productList!=null && !this.productList.isEmpty()){
            for(int i=0;i<this.productList.size();i++){
                this.productSugesstions.add(this.productList.get(i).getName());
            }
        }
        return this.productSugesstions;
    }
    private List<String> getVendorName(){
        this.vendorList = Vendor.listAll(Vendor.class);

        this.vendorSugesstions=new ArrayList<>();
        if(this.vendorList!=null && !this.vendorList.isEmpty()){
            for(int i=0;i<this.vendorList.size();i++){
                this.vendorSugesstions.add(this.vendorList.get(i).getName());
            }
        }
        return this.vendorSugesstions;
    }

    private void addPurchaseEntry(){
        String productName,qty,price,date,purchaseType,vendorName;
        boolean checkToastMsg = false;
        boolean checkAllEmptyMsg = false;
        vendorName = this.vendorName.getText().toString();
        productName = this.productName.getText().toString();
        qty = this.quantity.getText().toString();
        price = this.price.getText().toString();
        date = this.date.getText().toString();
        purchaseType = this.purchaseType.getSelectedItem().toString();

        if(Helper.validateFields(vendorName,productName,qty,price,date,purchaseType)&&isProductNameValid()&&isVendorNameValid()){

            if(isPurchaseEditable){

                editablePurchaseEntry = PurchaseEntry.findById(PurchaseEntry.class,Helper.editablePurchaseEntry.getId());

                editablePurchaseEntry.setPurchasePrice(price);
                editablePurchaseEntry.setPurchaseType(purchaseType);
                editablePurchaseEntry.setPurchaseQuantity(qty);
                editablePurchaseEntry.setVendorName(vendorName);
                editablePurchaseEntry.setProductName(productName);
                editablePurchaseEntry.setPurchaseDate(date);

                editablePurchaseEntry.save();
                Helper.showMsg(this,Message.msgUpdatedSuccessFully,Message.successType);
                finish();

            } else{

                if(!isPurchaseEditable){

                    if(isProductExist()){
                        Product product = Product.findById(Product.class,Long.parseLong(this.productID));
                        PurchaseEntry purchaseEntry = PurchaseEntry.findById(PurchaseEntry.class,Long.parseLong(this.purchaseEntryID));
                        if(purchaseEntry != null){
                            purchaseEntry.setPurchaseQuantity(String.valueOf(Integer.parseInt(purchaseEntry.getPurchaseQuantity())+Integer.parseInt(this.quantity.getText().toString())));
                            purchaseEntry.save();
                            Helper.showMsg(this,Message.msgQuantityIncrease,Message.successType);
                            finish();
                        }
                    }else{
                        PurchaseEntry purchaseEntry = new PurchaseEntry();
                        purchaseEntry.setProductName(productName);
                        purchaseEntry.setVendorName(vendorName);
                        purchaseEntry.setPurchaseDate(date);
                        purchaseEntry.setPurchaseQuantity(qty);
                        purchaseEntry.setPurchaseType(purchaseType);
                        purchaseEntry.setPurchasePrice(price);
                        purchaseEntry.save();
                        Helper.showMsg(this, Message.msgAddedSuccessFully, Message.successType);
                        finish();
                    }
                }else{
                    checkAllEmptyMsg = Helper.validateFields(vendorName,productName,qty,price,date,purchaseType);
                    String msg="";
                    if(!isProductNameValid()&&!isVendorNameValid()){
                        msg = "Vendor and Product doesnot exist";
                        checkToastMsg = true;
                    }else{
                        checkToastMsg = true;
                        if(!isProductNameValid()){
                            msg = "Product doesnot exist ";
                        }
                        if(!isVendorNameValid()){
                            msg = "Vendor doesnot exist ";
                        }
                    }
                    if(checkToastMsg && checkAllEmptyMsg){
                        Helper.showMsg(this,msg,Message.errorType);
                    }else{
                        Helper.showMsg(this,Message.msgEnterRequiredFields,Message.warnningType);
                    }

                }
            }

        }
        isPurchaseEditable = false;
    }

    private void addPurchaseEntryWithoutUpdate(){
        
        String productName,qty,price,date,purchaseType,vendorName;
        boolean checkToastMsg = false;
        boolean checkAllEmptyMsg = false;
        vendorName = this.vendorName.getText().toString();
        productName = this.productName.getText().toString();
        qty = this.quantity.getText().toString();
        price = this.price.getText().toString();
        date = this.date.getText().toString();
        purchaseType = this.purchaseType.getSelectedItem().toString();

        if(Helper.validateFields(vendorName,productName,qty,price,date,purchaseType)&&isProductNameValid()&&isVendorNameValid()){
            if(isProductExist()){
                Product product = Product.findById(Product.class,Long.parseLong(this.productID));
                PurchaseEntry purchaseEntry = PurchaseEntry.findById(PurchaseEntry.class,Long.parseLong(this.purchaseEntryID));
                if(purchaseEntry != null){
                    purchaseEntry.setPurchaseQuantity(String.valueOf(Integer.parseInt(purchaseEntry.getPurchaseQuantity())+Integer.parseInt(this.quantity.getText().toString())));
                    purchaseEntry.save();
                    Helper.showMsg(this,Message.msgQuantityIncrease,Message.successType);
                    finish();
                }
            }else{
                PurchaseEntry purchaseEntry = new PurchaseEntry();
                purchaseEntry.setProductName(productName);
                purchaseEntry.setVendorName(vendorName);
                purchaseEntry.setPurchaseDate(date);
                purchaseEntry.setPurchaseQuantity(qty);
                purchaseEntry.setPurchaseType(purchaseType);
                purchaseEntry.setPurchasePrice(price);
                purchaseEntry.save();
                Helper.showMsg(this, Message.msgAddedSuccessFully, Message.successType);
                finish();
            }
        }
        else{
            checkAllEmptyMsg = Helper.validateFields(vendorName,productName,qty,price,date,purchaseType);
            String msg="";
            if(!isProductNameValid()&&!isVendorNameValid()){
                msg = "Vendor and Product doesnot exist";
                checkToastMsg = true;
            }else{
                checkToastMsg = true;
                if(!isProductNameValid()){
                    msg = "Product doesnot exist ";
                }
                if(!isVendorNameValid()){
                    msg = "Vendor doesnot exist ";
                }
            }
            if(checkToastMsg && checkAllEmptyMsg){
                Helper.showMsg(this,msg,Message.errorType);
            }else{
                Helper.showMsg(this,Message.msgEnterRequiredFields,Message.warnningType);
            }
        }
    }

    private boolean isProductNameValid(){
        List<Product> productNames = Product.listAll(Product.class);
        if(productNames != null){
            for(Product productName : productNames){
                if(this.productName.getText().toString().contentEquals(productName.getName())){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isVendorNameValid(){
        List<Vendor> vendorNames = Vendor.listAll(Vendor.class);
        if(vendorNames != null){
            for(Vendor vendorName : vendorNames){
                if(this.vendorName.getText().toString().contentEquals(vendorName.getName())){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isProductExist(){
        List<PurchaseEntry> purchaseEntries = PurchaseEntry.listAll(PurchaseEntry.class);
        List<Product> products = Product.listAll(Product.class);
        if(purchaseEntries  != null && products != null) {
            for (int i =0;i<purchaseEntries.size()&& i< products.size(); i++ ) {

                if(productName.getText().toString().contentEquals(purchaseEntries.get(i).getProductName())&&
                        vendorName.getText().toString().contentEquals(purchaseEntries.get(i).getVendorName())&&
                        price.getText().toString().contentEquals(purchaseEntries.get(i).getPurchasePrice())&&
                        date.getText().toString().contentEquals(purchaseEntries.get(i).getPurchaseDate())&&
                        purchaseType.getSelectedItem().toString().contentEquals(purchaseEntries.get(i).getPurchaseType())){

                    this.purchaseEntryID = String.valueOf(purchaseEntries.get(i).getId());
                    return true;
                }
            }
        }
        return false;
    }

    private void setFields(){
        editablePurchaseEntry = Helper.editablePurchaseEntry;

        if(editablePurchaseEntry != null){
            productName.setText(editablePurchaseEntry.getProductName());
            vendorName.setText(editablePurchaseEntry.getVendorName());
            quantity.setText(editablePurchaseEntry.getPurchaseQuantity());
            date.setText(editablePurchaseEntry.getPurchaseDate());
            price.setText(editablePurchaseEntry.getPurchasePrice());
            if(editablePurchaseEntry.getPurchaseType().contentEquals("Cash")){
                purchaseType.setSelection(0);
            }else{
                purchaseType.setSelection(1);
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        updateLabel();
    }
    private void updateDateNow() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        date.setText(sdf.format(new Date()));
    }
    private void updateLabel() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(calendar.getTime()));
    }
}
