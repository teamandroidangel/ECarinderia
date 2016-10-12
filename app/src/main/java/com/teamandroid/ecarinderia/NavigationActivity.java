package com.teamandroid.ecarinderia;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NavigationActivity extends Activity {

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }

    public void onViewOrders(View view) {
        Intent i = new Intent(NavigationActivity.this, OrderListActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        finish();
    }

    public void onListMenu(View view) {
        Intent i = new Intent(NavigationActivity.this, MenuListActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        finish();
    }

    public void onCreateOrder(View view) {
        Intent i = new Intent(NavigationActivity.this, CreateOrderActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        finish();
    }




}
