package com.techno_twit.harshal.pharmahelp;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class History extends ListActivity {

    String occ[] = {"Empty", "Empty", "Empty", "Empty"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        System.out.println("2");

        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, occ);
        System.out.println("3");

        setListAdapter(ad);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {


    }

}