package com.generics.infinite.stockmanagementsystem;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.generics.infinite.stockmanagementsystem.model.Product;
import com.generics.infinite.stockmanagementsystem.model.SalesEntry;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Message;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class ProfitLossActivity extends AppCompatActivity {

    BarChart barChart;
    LineChart lineChart;
    Spinner spinner;
    BarData barData;
    final float barSpace = 0.02f;
    final float barWidth = 0.40f;
    final float barTextSize = 12.0f;
    List<String> months;
    List<String> monthsLabels;
    List<Product> spinnerList;
    int noOfBars;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_loss);
        initComponents();

        Helper.showMsg(this,curentYear()+"",Message.infoType);
    }

    private void initComponents(){
        barChart = (BarChart) findViewById(R.id.profit_loss_barChart);
        lineChart = (LineChart) findViewById(R.id.profit_loss_lineChart);
        spinner = (Spinner) findViewById(R.id.profit_loss_spinner);

        //this.months = SalesEntry.findWithQuery(SalesEntry.class,"SELECT strftime('%m',sales_Date) FROM Sales_Entry",null);

        //Helper.showMsg(this,this.sales.get(0).getCustomerName(), Message.successType);
        //this.months = SalesEntry.listAll(SalesEntry.class);

        setSpinner();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                months = getSalesMonths(spinner.getSelectedItem().toString());
                groupBarChart(spinner.getSelectedItem().toString());
               //simpleBarChart();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Helper.showMsg(ProfitLossActivity.this,"Nothing Selected",Message.warnningType);
            }
        });
    }

//KAAM KA METHOD
    private void setSpinner(){

        spinnerList = new ArrayList<>();
        spinnerList=Product.findWithQuery(Product.class,"SELECT name FROM Product",null);

        List <String>dropDownList=new ArrayList<>();
        if(spinnerList != null && !spinnerList.isEmpty()) {
            for (Product sl : spinnerList) {
                dropDownList.add(sl.getName());
            }
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,dropDownList);
         spinner.setAdapter(arrayAdapter);
    }

    private List<String> getSalesMonths(String productName){

        List<SalesEntry> salesMonths = SalesEntry.findWithQuery(SalesEntry.class,"SELECT sales_Date FROM Sales_Entry WHERE product_Name = ?",productName);
        List<String> monthNames=new ArrayList<>();
        for(SalesEntry se:salesMonths){
           monthNames.add(se.getSalesDate().split(" ")[1]);
        }
        return monthNames;
    }
    private List<Integer> getSalesYear(String productName){

        List<SalesEntry> salesMonths = SalesEntry.findWithQuery(SalesEntry.class,"SELECT sales_Date FROM Sales_Entry WHERE product_Name = ?",productName);
        List<Integer> yearNames=new ArrayList<>();
        for(SalesEntry se:salesMonths){
            yearNames.add(Integer.parseInt(se.getSalesDate().split(" ")[2]));
        }
        return yearNames;
    }

    private Hashtable<String,Integer> calcProductFrequency(){
        Hashtable<String , Integer> hashtable = new Hashtable<>();
        if(this.months != null && !this.months.isEmpty()) {
            for (String monthName : this.months) {
                if(hashtable.containsKey(monthName)){
                   int val = hashtable.get(monthName);
                   val++;
                   hashtable.put(monthName,val);
                }else{
                   hashtable.put(monthName,1);
                }
            }
        }
        return hashtable;
    }
    private BarDataSet getDataSetForYaxis(String productName){

        List<BarEntry> barEntries = new ArrayList<>();
        monthsLabels = new ArrayList<>();
        List <Integer> yearList=getSalesYear(productName);

        for (String m : getSalesMonths(productName)){
            if(!monthsLabels.contains(m)){
                monthsLabels.add(m);
            }
        }

        Hashtable<String,Integer> hashtable = calcProductFrequency();
        for(String month : this.monthsLabels){
            int x = 1;
            if(yearList.contains((curentYear())))
             barEntries.add(new BarEntry(x,hashtable.get(month)));

        }
        BarDataSet dataSet = new BarDataSet(barEntries,curentYear()+"");
        dataSet.setColor(Color.RED);
        noOfBars = barEntries.size();
        return dataSet;
    }

    private String getMonthName(String m){
        int monthNumber = Integer.parseInt(m);
        String month = "";
        /*DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (monthNumber >= 0 && monthNumber <= 11 ) {
            month = months[monthNumber];
        }*/
        if(monthNumber>=1 && monthNumber<=12 && m != null) {
            return new DateFormatSymbols().getMonths()[monthNumber - 1];
        }


       // return month;
        return "";
    }

    private void simpleBarChart(){

        BarDataSet YaxisData_1 = getDataSetForYaxis(spinner.getSelectedItem().toString());

        if(YaxisData_1 != null){
            barData = new BarData(YaxisData_1);
            setSimpleBarChart();
        }
    }

    private void setSimpleBarChart() {

        if(barData != null) {
            barChart.setData(barData);

            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(monthsLabels));
            //  xAxis.setValueFormatter(new IndexAxisValueFormatter(monthsLabels));
            xAxis.setCenterAxisLabels(true);
            xAxis.setPosition(XAxis.XAxisPosition.TOP);
          //  xAxis.setGranularity(1);
           // xAxis.setGranularityEnabled(true);

            barData.setValueTextSize(barTextSize);
            barData.setBarWidth(barWidth);
            barChart.setFitBars(true);
            barChart.getXAxis().setAxisMinimum(0);
            barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(getGroupSpace(barWidth,barSpace,1),barSpace)*noOfBars);

            barChart.setDragEnabled(true);
            barChart.invalidate();
            barChart.setVisibleXRangeMaximum(3);
        }
    }

    private void groupBarChart(String productName){
        //BarDataSet YaxisData_1 = getDataSetForYaxis_1();
        BarDataSet YaxisData_1 = getDataSetForYaxis(productName);
        BarDataSet YaxisData_2 = getDataSetForYaxis_2(productName);

        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(YaxisData_2);
       dataSets.add(YaxisData_1);

        if(dataSets != null && !dataSets.isEmpty()) {
            barData = new BarData(dataSets);
            setGroupBarChart(productName);
        }
    }

    private void setGroupBarChart(String productName){
        if(barChart != null) {
            barChart.setData(barData);

            setXaxis();

            barData.setValueTextSize(barTextSize);
            barData.setBarWidth(barWidth);
            barChart.setDragEnabled(true);
            barChart.setFitBars(true);

            barChart.getXAxis().setAxisMinimum(0);
            barChart.getXAxis().setAxisMaximum(0+barChart.getBarData().getGroupWidth(getGroupSpace(barWidth,barSpace,2),barSpace)*noOfBars);
            barChart.groupBars(0,getGroupSpace(barWidth,barSpace,2),barSpace);
            barChart.animateY(3000);
            barChart.invalidate();
            barChart.setVisibleXRangeMaximum(3);
        }
    }

    private float getGroupSpace(float barWidth, float barSpace, int noOfDataSet){
        float groupSpace = (float) (1-((barWidth+barSpace)*noOfDataSet));
        return groupSpace;
    }

    private List<String> getDataForXaxis(){

        List<String> monthLabels = new ArrayList<>();

        monthLabels.add("January");
        monthLabels.add("February");
        monthLabels.add("March");
        monthLabels.add("April");
        monthLabels.add("May");
        monthLabels.add("June");
        monthLabels.add("July");
        monthLabels.add("August");
        monthLabels.add("September");
        monthLabels.add("October");
        monthLabels.add("November");
        monthLabels.add("December");


        List<String> year = new ArrayList<>();
        year.add("2019");

        return monthLabels;
    }

    private void legend(){
        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        List<LegendEntry> legendEntries = new ArrayList<>();
        legendEntries.add(new LegendEntry("2019",Legend.LegendForm.CIRCLE,8f,8f,null, Color.RED));
        legendEntries.add(new LegendEntry("2018",Legend.LegendForm.CIRCLE,8f,8f,null, Color.YELLOW));

        legend.setCustom(legendEntries);

        legend.setYOffset(2f);
        legend.setXOffset(2f);
        legend.setYEntrySpace(0f);
        legend.setTextSize(barTextSize);

    }

    private void setXaxis(){

        if(getDataForXaxis()!= null && !getDataForXaxis().isEmpty()) {
            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(getDataForXaxis()));
          //  xAxis.setValueFormatter(new IndexAxisValueFormatter(monthsLabels));
            xAxis.setCenterAxisLabels(true);
            xAxis.setPosition(XAxis.XAxisPosition.TOP);
            xAxis.setGranularity(1);
            xAxis.setGranularityEnabled(true);
        }
    }

    private BarDataSet getDataSetForYaxis_1(){

        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(100,0));
        barEntries.add(new BarEntry(200,1));
        barEntries.add(new BarEntry(300,2));
        barEntries.add(new BarEntry(400,3));
        barEntries.add(new BarEntry(500,4));
        barEntries.add(new BarEntry(600,5));
        barEntries.add(new BarEntry(600,5));
        barEntries.add(new BarEntry(600,5));

        BarDataSet dataSet = new BarDataSet(barEntries,spinner.getSelectedItem().toString());
        dataSet.setColor(Color.RED);
        noOfBars = barEntries.size();
        return dataSet;
    }
    private BarDataSet getDataSetForYaxis_2(String productName){

        List<BarEntry> barEntries = new ArrayList<>();
       // barEntries.add(new BarEntry(0,0));


        Hashtable <Integer,Integer>sales=new Hashtable();
        for(Integer year:this.getSalesYear(productName)){
            if(year==curentYear()-1){
                if(sales.containsKey(year)){
                    sales.put(year,sales.get(year)+1);
                }else{
                    sales.put(year,1);
                }
            }
        }


        for(Integer y:sales.keySet()) {
            barEntries.add(new BarEntry(1,sales.get(y)));
        }

        BarDataSet dataSet = new BarDataSet(barEntries,(curentYear()-1)+"");
        return dataSet;
    }

    private int curentYear(){
        Date date = new Date();
       // return "MODE";
        return Integer.parseInt(date.toString().split(" ")[5]);
    }
}
