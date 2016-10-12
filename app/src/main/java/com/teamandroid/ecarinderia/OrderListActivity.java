package com.teamandroid.ecarinderia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OrderListActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
    }

    public void onBackFromOrder(View view) {
        Intent i = new Intent(OrderListActivity.this, NavigationActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        finish();
    }
}
