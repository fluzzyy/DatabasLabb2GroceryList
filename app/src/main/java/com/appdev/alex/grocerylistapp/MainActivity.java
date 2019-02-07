package com.appdev.alex.grocerylistapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

        private GroceryAdapter mAdapter;

        private EditText mEditTextName;
        private TextView mTextViewAmount;
        private int mAmount = 0;
        private SQLiteDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       GroceryDBHelper dbHelper = new GroceryDBHelper(this);
       mDatabase = dbHelper.getWritableDatabase();

       RecyclerView recyclerView = findViewById(R.id.recyclerview);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       mAdapter = new GroceryAdapter(this,getAllItems());
       recyclerView.setAdapter(mAdapter);

       new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
           @Override
           public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
               return false;
           }

           @Override
           public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            removeItem((long)viewHolder.itemView.getTag());
           }
       }).attachToRecyclerView(recyclerView);


        mEditTextName = findViewById(R.id.editText_name);
        mTextViewAmount = findViewById(R.id.textview_amount);

        Button buttonIncrease = findViewById(R.id.button_increase);
        Button buttonDecrease = findViewById(R.id.button_decrease);
        Button buttonAdd = findViewById(R.id.button_add);
        Button buttonSort = findViewById(R.id.button_sort);

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();

            }
        });
        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease();
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
        buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortItems();

            }
        });

    }

    private void increase(){
        mAmount++;
        mTextViewAmount.setText(String.valueOf(mAmount));


    }
    private void decrease(){

        if(mAmount > 0 ){
            mAmount--;
            mTextViewAmount.setText(String.valueOf(mAmount));
        }

    }
    private void addItem (){

        if(mEditTextName.getText().toString().trim().length() == 0 || mAmount == 0){
            return;
        }
        String name = mEditTextName.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(GroceryContract.GroceryEntry.COLUMN_NAME,name);
        cv.put(GroceryContract.GroceryEntry.COLUM_AMOUNT,mAmount);
        mDatabase.insert(GroceryContract.GroceryEntry.TABLE_NAME,null,cv);
        mAdapter.swapCursor(getAllItems());
        mEditTextName.getText().clear();
    }
    private Cursor getAllItems(){
        return mDatabase.query(GroceryContract.GroceryEntry.TABLE_NAME,null,null,null,null,null,
                GroceryContract.GroceryEntry.COLUMN_TIMESTAMP + " DESC");
    }
    private void removeItem(long id){
        mDatabase.delete(GroceryContract.GroceryEntry.TABLE_NAME, GroceryContract.GroceryEntry._ID + "=" + id,null);
        mAdapter.swapCursor(getAllItems());

    }
    public void sortItems(){

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + GroceryContract.GroceryEntry.TABLE_NAME + " ORDER BY " + GroceryContract.GroceryEntry.COLUMN_NAME , null);

        mAdapter.swapCursor(cursor);

        mAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Sorted alphabetically", Toast.LENGTH_SHORT).show();
    }

}
