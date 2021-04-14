package com.example.samoney;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddOperationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddOperationFragment extends Fragment {

    Context context;

    Button submitButton;
    EditText nameInput, sumInput, commentsInput, objectInput;

    List<Bill> bills;
    Bill bill;

    ArrayList<Limit> limits = new ArrayList<>();

    String name, comments, object;
    Integer sum = 0;
    Boolean isSumPositive = true;

    RadioGroup rg;

    DatabaseHelper db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddOperationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddOperationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddOperationFragment newInstance(String param1, String param2) {
        AddOperationFragment fragment = new AddOperationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bills = new ArrayList<Bill>();


        db = new DatabaseHelper(getContext());

        nameInput = view.findViewById(R.id.addNewOperationNameInput);
        sumInput = view.findViewById(R.id.addNewOperationSumInput);
        commentsInput = view.findViewById(R.id.addNewOperationCommentsInput);
        rg = view.findViewById(R.id.addNewOperationRadioGroup);
        rg.check(R.id.addNewOperationIncomeRadio);
//        objectInput = view.findViewById(R.id.addNewOperationObjectInput);

        loadBills();

        Spinner spinner = (Spinner) view.findViewById(R.id.addNewOperationBillSpinner);
        ArrayAdapter<Bill> adapter = new ArrayAdapter<Bill>(getContext(), android.R.layout.simple_spinner_item, bills);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the value selected by the user
                // e.g. to store it as a field or immediately call a method
                bill = (Bill) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        submitButton = view.findViewById(R.id.addNewOperationButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameInput.getText().toString().trim();
                object = bill._id;
                comments = commentsInput.getText().toString().trim();
                String sumInitial = sumInput.getText().toString().trim();
                sum = Integer.parseInt(sumInitial);

                switch (rg.getCheckedRadioButtonId()) {
                    case R.id.addNewOperationIncomeRadio:
                        break;
                    case R.id.addNewOperationOutcomeRadio:
                        sum = sum * -1;
                }

                loadLimits();

                boolean allow = true;
                if (sum < 0) {
                    for (int i = 0; i < limits.size(); i++) {
                        if (limits.get(i).sum < (sum * -1)) {
                            allow = false;
                        }
                    }
                }

                Bill newBill = bill;
                newBill.balance += sum;

                if (allow) {
                    db.addOperation(new Operation("", name, sum, comments, object));
                    db.updateBill(newBill);
                } else {
                    Toast.makeText(context, "Limit exceeded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    public void onRadioClicked(View view) {
//        boolean checked = ((RadioButton) view).isChecked();
//
//        switch (view.getId()) {
//            case R.id.addOperationIncomeRadio:
//                if (checked) isSumPositive = true;
//                break;
//            case R.id.addOperationOutcomeRadio:
//                if (checked) isSumPositive = false;
//                break;
//        }
//    }

    private void loadLimits() {
        Cursor billsCursor = db.getLimitsByBill(bill._id);

        if (billsCursor.getCount() == 0) {
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (billsCursor.moveToNext()) {
                String _id = billsCursor.getString(0);
                String name = billsCursor.getString(1);
                Integer sum = billsCursor.getInt(2);
                String object = billsCursor.getString(3);

                limits.add(new Limit(_id, name, sum, object));
            }
        }
    }

    public void onRadioIncomeClicked(View view) {
        Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
//        isSumPositive = true;
    }

    public void onRadioOutcomeClicked(View view) {
//        isSumPositive = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_operation, container, false);
    }

    private void loadBills() {
        Cursor cursor = db.getAllBills();

        if (cursor.getCount() == 0) {
//            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String _id = cursor.getString(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                Integer balance = cursor.getInt(3);
                Integer from = cursor.getInt(4);

                bills.add(new Bill(_id, name, category, balance, from));
            }
        }
    }
}