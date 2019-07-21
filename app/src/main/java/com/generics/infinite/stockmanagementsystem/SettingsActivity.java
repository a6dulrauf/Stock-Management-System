package com.generics.infinite.stockmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.generics.infinite.stockmanagementsystem.adapter.SettingsAdapter;
import com.generics.infinite.stockmanagementsystem.utilities.DataBase;
import com.generics.infinite.stockmanagementsystem.utilities.Helper;
import com.generics.infinite.stockmanagementsystem.utilities.Message;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    ListView listView;
    List<String> dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        dataSource();
        listView = (ListView) findViewById(R.id.settings_listview);
        if(this.dataSource != null && !this.dataSource.isEmpty()) {
            SettingsAdapter settingsAdapter = new SettingsAdapter(this, this, dataSource);
            listView.setAdapter(settingsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    switch (i){
                        case 0:
                            DataBase.backUp(SettingsActivity.this);
                            break;
                        case 1:
                            DataBase.restore(SettingsActivity.this);
                            break;
                    }
                }
            });
        }else{
            Helper.showMsg(this, Message.msgNoRecordFound,Message.infoType);
        }

    }

    private void dataSource(){
        dataSource = new ArrayList<>();
        dataSource.add("Data Backup,Path,"+R.drawable.database_backup);
        dataSource.add("Data Restore,Path,"+R.drawable.database_restore);
    }
}
