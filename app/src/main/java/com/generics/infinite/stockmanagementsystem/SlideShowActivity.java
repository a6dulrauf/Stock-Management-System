package com.generics.infinite.stockmanagementsystem;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.generics.infinite.stockmanagementsystem.adapter.ViewPagerAdapter;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

import java.util.ArrayList;
import java.util.List;

public class SlideShowActivity extends Activity {

    ViewPager viewPager;
    List<Integer> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        initComponents();
        dataSource();
        if(list.isEmpty() && list == null){
            Helper.showMsg(this,Message.msgNoImageFound,Message.errorType);
        }else {
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, list);
            viewPager.setAdapter(viewPagerAdapter);
        }
    }

    private void initComponents(){
        viewPager = (ViewPager) findViewById(R.id.slide_show_ViewPager);
    }

    private void dataSource(){
        this.list = new ArrayList<>();
        list.add(R.drawable.app_guide_lines_1);
        list.add(R.drawable.app_guide_lines_2);
        list.add(R.drawable.app_guide_lines_3);
        list.add(R.drawable.app_guide_lines_4);
    }
}
