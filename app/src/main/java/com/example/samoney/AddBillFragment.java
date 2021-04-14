package com.example.samoney;

import android.content.Context;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddBillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddBillFragment extends Fragment {

    Button submitButton;
    EditText nameInput, typeInput, balanceInput, fromInput;

    DatabaseHelper db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddBillFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddBillFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddBillFragment newInstance(String param1, String param2) {
        AddBillFragment fragment = new AddBillFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameInput = view.findViewById(R.id.addNewBillNameInput);
        balanceInput = view.findViewById(R.id.addNewBillBalanceInput);
        fromInput = view.findViewById(R.id.addNewBillFromInput);
        fromInput.setVisibility(View.INVISIBLE);

        Spinner spinner = (Spinner) view.findViewById(R.id.addNewBillTypeSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 2) {
                    fromInput.setVisibility(View.VISIBLE);
                } else {
                    fromInput.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.bill_type_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        submitButton = view.findViewById(R.id.addNewBillButton);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString().trim();
                String type = spinner.getSelectedItem().toString();
                String balanceInitial = balanceInput.getText().toString().trim();
                Integer balance = Integer.parseInt(balanceInitial);
                String fromInitial = fromInput.getText().toString().trim();
                Integer from = Integer.parseInt(fromInitial);
                
                if (type.trim().equals("Credit")) {
                    balance = balance * -1;
                }

                db.addBill(new Bill("", name, type, balance, from));
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        db = new DatabaseHelper(context);
    }
}