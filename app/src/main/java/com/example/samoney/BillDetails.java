package com.example.samoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BillDetails extends AppCompatActivity {

    Bill bill;
    DatabaseHelper db;
    ArrayList<Operation> opers = new ArrayList<>();
    OperationsAdapter adapter;
    RecyclerView recyclerView;

    TextView nameView, balanceView, fromView;
    TextView incomeView, outcomeView;
    Integer income = 0, outcome = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);

        ImageView contextMenuTrigger = findViewById(R.id.detailsContextMenuTrigger);
        registerForContextMenu(contextMenuTrigger);

        db = new DatabaseHelper(BillDetails.this);
        bill = (Bill) getIntent().getSerializableExtra("bill");

        recyclerView = findViewById(R.id.billDetailsOperationsRecycler);

        loadOpers();

        nameView = findViewById(R.id.billDetailsName);
        balanceView = findViewById(R.id.billDetailsBalance);

        nameView.setText(bill.name);
        balanceView.setText(String.valueOf(bill.balance));

        adapter = new OperationsAdapter(BillDetails.this, opers);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BillDetails.this));

        incomeView = findViewById(R.id.billDetailsIncome);
        outcomeView = findViewById(R.id.billDetailsOutcome);
        fromView = findViewById(R.id.billDetailsFrom);

        incomeView.setText(String.valueOf(income));
        outcomeView.setText(String.valueOf(outcome));



        if (bill.category.trim().equals("Goal")) {
            String from = "from " + String.valueOf(bill.from);
            fromView.setText(from);
        } else {
            fromView.setText("");
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detailsDelete:
                deleteItem();
                return true;
            default:
                return false;
        }
    }

    private void deleteItem() {
        db.deleteOneBill(bill._id);

//        Toast.makeText(this, "Option Two Chosen...", Toast.LENGTH_LONG).show();
    }

    private void loadOpers() {
        Cursor operationsCursor = db.getOperationsByBill(bill._id);
        if (operationsCursor.getCount() == 0) {
//            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (operationsCursor.moveToNext()) {
                String _id = operationsCursor.getString(0);
                String name = operationsCursor.getString(1);
                Integer sum = operationsCursor.getInt(2);
                String comments = operationsCursor.getString(3);
                String object = operationsCursor.getString(4);

                if (sum > 0) {
                    income += sum;
                } else if (sum < 0) {
                    outcome += sum * -1;
                }

                opers.add(new Operation(_id, name, sum, comments, object));
            }
        }
    }
}