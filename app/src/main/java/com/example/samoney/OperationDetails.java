package com.example.samoney;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OperationDetails extends AppCompatActivity {

    TextView nameView, sumView, commentsView, billView, dateView;

    Operation operation;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_details);

        ImageView contextMenuTrigger = findViewById(R.id.detailsContextMenuTrigger);
        registerForContextMenu(contextMenuTrigger);

        nameView = findViewById(R.id.operationDetailsName);
        sumView = findViewById(R.id.operationDetailsSum);
        commentsView = findViewById(R.id.operationDetailsComments);
        billView = findViewById(R.id.operationDetailsBill);

        db = new DatabaseHelper(OperationDetails.this);
        operation = (Operation) getIntent().getSerializableExtra("operation");

        nameView.setText(operation.name);
        sumView.setText(String.valueOf(operation.sum));
        commentsView.setText(operation.comments);

        Bill bill = new Bill("", "Undefined", "", 0, 0);
        Cursor cursor = db.getBillById(operation.object);
        while (cursor.moveToNext()) {
            String _id = cursor.getString(0);
            String name = cursor.getString(1);
            String category = cursor.getString(2);
            Integer balance = cursor.getInt(3);
            Integer from = cursor.getInt(4);

            bill = new Bill(_id, name, category, balance, from);
        }

        billView.setText(bill.name);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        //find out which menu item was pressed
        switch (item.getItemId()) {
            case R.id.detailsDelete:
                deleteItem();
                return true;
            default:
                return false;
        }
    }


    private void deleteItem() {
        db.deleteOneOperation(operation._id);

//        Toast.makeText(this, "Option Two Chosen...", Toast.LENGTH_LONG).show();
    }
}