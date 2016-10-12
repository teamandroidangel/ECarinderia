package com.teamandroid.ecarinderia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MenuListActivity extends AppCompatActivity {

    ArrayList<MenuItem> items;
    MenuItemArrayAdapter itemsAdapter;
    ListView lvItems;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<MenuItem>();
        readItems();
        itemsAdapter = new MenuItemArrayAdapter(this,R.layout.activity_menu_list_item, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(MenuListActivity.this, EditMenuActivity.class);
                        MenuItem selectedItemFromList = (MenuItem) parent.getItemAtPosition(position);
                        i.putExtra("item", selectedItemFromList);
                        i.putExtra("itemIndex", position);
                        i.putExtra("itemId", id);
                        startActivityForResult(i, REQUEST_CODE);
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            MenuItem item = (MenuItem)  data.getSerializableExtra("item");
            int itemIndex = data.getExtras().getInt("itemIndex");

            items.set(itemIndex, item);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
            Toast.makeText(this, item.getName(), Toast.LENGTH_SHORT).show();
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

    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "all_menus.txt");
        try{
            ArrayList<String> stringItems = new ArrayList<String>();

            for (MenuItem item: items) {
                stringItems.add(item.serialize());
            }
            FileUtils.writeLines(todoFile, stringItems);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        MenuItem item = new MenuItem();
        item.setName(etNewItem.getText().toString());
        itemsAdapter.add(item);
        etNewItem.setText("");
        writeItems();
    }

    public void onBack(View view) {
        Intent i = new Intent(MenuListActivity.this, NavigationActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        finish();
    }
}
