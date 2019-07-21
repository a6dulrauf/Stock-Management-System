package com.generics.infinite.stockmanagementsystem.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.generics.infinite.stockmanagementsystem.R;

import java.util.List;

public class SettingsAdapter extends BaseAdapter {

    List<String> listItems;
    LayoutInflater layoutInflater;
    Activity activity;
    Context context;

    public SettingsAdapter(Activity activity, Context context,List<String> listItems) {
        this.listItems = listItems;
        this.activity = activity;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = layoutInflater.inflate(R.layout.item_rep_settings,null);

        TextView mainText = v.findViewById(R.id.settings_main_text);
        TextView detailText = v.findViewById(R.id.settings_details_text);

        ImageView image = v.findViewById(R.id.settings_image_view);
        String text[]=listItems.get(i).split(",");
        mainText.setText(text[0]);
        detailText.setText(text[1]);
        image.setImageResource(Integer.parseInt(text[2]));

        return v;
    }
}
