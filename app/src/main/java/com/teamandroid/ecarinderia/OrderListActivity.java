package com.teamandroid.ecarinderia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;

    ArrayList<OrderItem> orders;
    OrderItemArrayAdapter itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        lvItems = (ListView) findViewById(R.id.lvItems);
        orders = new ArrayList<OrderItem>();
        readItems();
        itemsAdapter = new OrderItemArrayAdapter(this,R.layout.activity_order_list_item, orders);
        lvItems.setAdapter(itemsAdapter);

    }

    public void onBackFromOrder(View view) {
        Intent i = new Intent(OrderListActivity.this, NavigationActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        finish();
    }
    private void readItems() {
        File filesDir = getFilesDir();
        File orderfile = new File(filesDir, "all_orders.txt");
        try{
            ArrayList<String> stringItems = new ArrayList<String>(FileUtils.readLines(orderfile));
            orders = new ArrayList<OrderItem>();
            for (String stringItem: stringItems) {
                OrderItem item = new OrderItem();
                item.deserialize(stringItem);
                orders.add(item);
            }

        }catch (IOException e){
            orders = new ArrayList<OrderItem>();
        }

    }
}
