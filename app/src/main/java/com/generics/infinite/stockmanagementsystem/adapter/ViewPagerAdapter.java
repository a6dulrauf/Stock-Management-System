package com.generics.infinite.stockmanagementsystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.generics.infinite.stockmanagementsystem.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<Integer> imagesList;

    public ViewPagerAdapter(Context context, List<Integer>imagesList){
        this.context = context;
        this.imagesList = imagesList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imagesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = layoutInflater.inflate(R.layout.item_rep_slide_show,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.slide_show_imageView);
        Glide.with(context).load(imagesList.get(position)).into(imageView);

        ViewPager pager = (ViewPager) container;
        pager.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager pager = (ViewPager) container;
        View view = (View) object;
        pager.removeView(view);
    }
}
