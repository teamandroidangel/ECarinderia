package com.teamandroid.ecarinderia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CreateOrderActivity extends AppCompatActivity {

    ArrayList<MenuItem> items;
    ArrayList<OrderItem> orders;
    MenuItemArrayAdapter itemsAdapter;
    ListView orderItems;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        orderItems = (ListView) findViewById(R.id.orderItems);
        items = new ArrayList<MenuItem>();
        readItems();
        itemsAdapter = new MenuItemArrayAdapter(this,R.layout.activity_menu_list_item, items);
        orderItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {

        final Context currentContext = this;

        orderItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MenuItem selectedItemFromList = (MenuItem) parent.getItemAtPosition(position);
                        String itemName = selectedItemFromList.getName();
                        writeItem(selectedItemFromList);
                        Toast.makeText(currentContext, "ORDERED: " + itemName, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


    private void updateOrders(){

        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "all_orders.txt");
        try{
            ArrayList<String> stringItems = new ArrayList<String>();

            for (OrderItem item: orders) {
                stringItems.add(item.serialize());
            }
            FileUtils.writeLines(todoFile, stringItems);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void writeItem(MenuItem item) {

        if(item.getQuantity()>0){

            String stringMenuItem = item.serialize();

            stringMenuItem+="|PROCESSING";

            OrderItem newOrder = new OrderItem();
            newOrder.deserialize(stringMenuItem);
            newOrder.setQuantity(1);
            orders.add(newOrder);
            updateOrders();

            item.setQuantity(item.getQuantity()-1);
            updateMenuWithQuantity();

        }else{

            Toast.makeText(this, item.getName() +" IS OUT OF STOCK", Toast.LENGTH_SHORT).show();

        }


    }


    private void updateMenuWithQuantity() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "all_menus.txt");
        try{
            ArrayList<String> stringItems = new ArrayList<String>();

            for (MenuItem item: items) {
                stringItems.add(item.serialize());
            }
            FileUtils.writeLines(todoFile, stringItems);


            orderItems.invalidateViews();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "all_menus.txt");
        try{
            ArrayList<String> stringItems = new ArrayList<String>(FileUtils.readLines(todoFile));
            items = new ArrayList<MenuItem>();
            for (String stringItem: stringItems) {
                MenuItem item = new MenuItem();
                item.deserialize(stringItem);
                items.add(item);
            }

        }catch (IOException e){
            items = new ArrayList<MenuItem>();
        }


        File orderFile = new File(filesDir, "all_orders.txt");
        try{
            ArrayList<String> stringOrderItems = new ArrayList<String>(FileUtils.readLines(orderFile));
            orders = new ArrayList<OrderItem>();
            for (String orderStringItem: stringOrderItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.deserialize(orderStringItem);
                orders.add(orderItem);
            }

        }catch (IOException e){
            orders = new ArrayList<OrderItem>();
        }

    }


    public void onBackFromCreateOrder(View view) {
        Intent i = new Intent(CreateOrderActivity.this, NavigationActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        finish();
    }
}
