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
import android.widget.TextView;

import com.generics.infinite.stockmanagementsystem.model.Customer;
import com.generics.infinite.stockmanagementsystem.model.Product;
import com.generics.infinite.stockmanagementsystem.model.SalesEntry;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SalesEntryActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    Spinner salesType;
    TextView cash,debit;
    AutoCompleteTextView productName;
    AutoCompleteTextView customerName;
    EditText salesQuantity;
    EditText salesDate;
    EditText salesPrice;
    ImageView addCustomer;
    ImageView addProduct;
    Button btnAdd;

    Calendar calendar;

    ArrayAdapter<String> arrayAdapterProduct;
    ArrayAdapter<String> arrayAdapterCustomer;
    ArrayAdapter<String> arrayAdapterSalesType;

    List<String> productSugesstions;
    List<String> customerSugesstions;
    List<String> salesTypeSuggestions;

    List<Product> productList;
    List<Customer> customerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_entry);
        initComponents();
        updateDateNow();
    }

    private void initComponents(){

        calendar = Calendar.getInstance();

        productName = (AutoCompleteTextView)findViewById(R.id.sales_entry_productName);
        customerName = (AutoCompleteTextView)findViewById(R.id.sales_entry_customerName);

        salesQuantity = (EditText) findViewById(R.id.sales_entry_salesQuantity);
        salesDate = (EditText) findViewById(R.id.sales_entry_salesDate);
        salesPrice = (EditText) findViewById(R.id.sales_entry_salesPrice);

        addCustomer = (ImageView) findViewById(R.id.sales_entry_addCustomer);
        addProduct = (ImageView) findViewById(R.id.sales_entry_addProduct);

        btnAdd = (Button) findViewById(R.id.sales_entry_btnAdd);

        salesType = (Spinner) findViewById(R.id.sales_entry_salesType);

        arrayAdapterSalesType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getSalesTypeSuggestions());
        salesType.setAdapter(arrayAdapterSalesType);

        btnAdd.setOnClickListener(this);
        addCustomer.setOnClickListener(this);
        addProduct.setOnClickListener(this);
        salesDate.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayAdapterCustomer = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getCustomerName());
        customerName.setAdapter(arrayAdapterCustomer);

        arrayAdapterProduct = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getProductNames());
        productName.setAdapter(arrayAdapterProduct);
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
    private boolean isCustomerNameValid(){
        List<Customer> customerNames = Customer.listAll(Customer.class);
        if(customerNames != null){
            for(Customer vendorName : customerNames){
                if(this.customerName.getText().toString().contentEquals(vendorName.getName())){
                    return true;
                }
            }
        }
        return false;
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
    private List<String> getCustomerName(){
        this.customerList = Customer.listAll(Customer.class);

        this.customerSugesstions=new ArrayList<>();
        if(this.customerList!=null && !this.customerList.isEmpty()){
            for(int i=0;i<this.customerList.size();i++){
                this.customerSugesstions.add(this.customerList.get(i).getName());
            }
        }
        return this.customerSugesstions;
    }
    private  List<String> getSalesTypeSuggestions(){

        this.salesTypeSuggestions = new ArrayList<>();
        salesTypeSuggestions.add("Cash");
        salesTypeSuggestions.add("Debit");

        return this.salesTypeSuggestions;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.sales_entry_btnAdd:
                addSalesEntry();
                break;
            case R.id.sales_entry_addCustomer:
                Helper.navigateActivity(this,AddCustomerActivity.class);
                break;
            case R.id.sales_entry_addProduct:
                Helper.navigateActivity(this,AddProductActivity.class);
                break;
            case R.id.sales_entry_salesDate:
                DatePickerDialog dialog = new DatePickerDialog(this, this,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
        }
    }

    private void addSalesEntry(){

        boolean checkToastMsg = false;
        boolean checkAllEmptyMsg = false;
        String productName,customerName,Qty,date,price,type;
        productName = this.productName.getText().toString();
        customerName = this.customerName.getText().toString();
        Qty = this.salesQuantity.getText().toString();
        date = this.salesDate.getText().toString();
        price = this.salesPrice.getText().toString();
        type = this.salesType.getSelectedItem().toString();

        if(Helper.validateFields(productName,customerName,Qty,date,price,type)&&isProductNameValid()&&isCustomerNameValid()) {
            SalesEntry salesEntry = new SalesEntry();
            salesEntry.setProductName(productName);
            salesEntry.setCustomerName(customerName);
            salesEntry.setSalesQuantity(Qty);
            salesEntry.setSalesDate(date);
            salesEntry.setSalesPrice(price);
            salesEntry.setSalesType(type);
            salesEntry.save();
            Helper.showMsg(this, Message.msgAddedSuccessFully, Message.successType);
            finish();
        }else{
            checkAllEmptyMsg = Helper.validateFields(productName,customerName,Qty,date,price,type);
            String msg="";
            if(!isProductNameValid()&&!isCustomerNameValid()){
                msg = "Customer and Product doesnot exist";
                checkToastMsg = true;
            }else{
                checkToastMsg = true;
                if(!isProductNameValid()){
                    msg = "Product doesnot exist ";
                }
                if(!isCustomerNameValid()){
                    msg = "Customer doesnot exist ";
                }
            }
            if(checkToastMsg && checkAllEmptyMsg){
                Helper.showMsg(this,msg,Message.errorType);
            }else{
                Helper.showMsg(this,Message.msgEnterRequiredFields,Message.warnningType);
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

        salesDate.setText(sdf.format(new Date()));
    }
    private void updateLabel() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        salesDate.setText(sdf.format(calendar.getTime()));
    }
}
