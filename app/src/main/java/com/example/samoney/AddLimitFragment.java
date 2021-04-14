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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddLimitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddLimitFragment extends Fragment {


    Context context;

    DatabaseHelper db;
    ArrayList<Bill> bills = new ArrayList<>();
    ArrayList<Operation> operations = new ArrayList<>();

    EditText nameInput, sumInput;
    Spinner typeSpinner, objectSpinner;
    Button submitButton;
    Bill bill;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddLimitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddLimitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddLimitFragment newInstance(String param1, String param2) {
        AddLimitFragment fragment = new AddLimitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_add_limit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DatabaseHelper(getContext());

        loadBills();

        nameInput = view.findViewById(R.id.addNewLimitName);
        sumInput = view.findViewById(R.id.addNewLimitSum);

        Spinner spinner = (Spinner) view.findViewById(R.id.addNewLimitObject);
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

        submitButton = view.findViewById(R.id.addNewLimitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString().trim();
                String object = bill._id;
                String sumInitial = sumInput.getText().toString().trim();
                Integer sum = Integer.parseInt(sumInitial);

                db.addLimit(new Limit("", name, sum, object));
            }
        });
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