package com.example.samoney;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    Context context;

    RecyclerView billsRecyclerView, operationsRecyclerView;
    DatabaseHelper db;
    ArrayList<Bill> bills;
    ArrayList<Operation> operations;
    BillsAdapter billsAdapter;
    OperationsAdapter operationsAdapter;

    Integer commonBalance = 0, commonIncome = 0, commonOutcome = 0;
    TextView commonBalanceThousandsView, commonBalanceRestView, commonIncomeView, commonOutcomeView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        billsRecyclerView = view.findViewById(R.id.dashboardBillsRecycler);
        operationsRecyclerView = view.findViewById(R.id.dashboardOperationsRecycler);

        db = new DatabaseHelper(context);

        bills = new ArrayList<>();
        operations = new ArrayList<>();

        storeDataInArrays();

        billsAdapter = new BillsAdapter(context, bills);
        billsRecyclerView.setAdapter(billsAdapter);
        billsRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        operationsAdapter = new OperationsAdapter(context, operations);
        operationsRecyclerView.setAdapter(operationsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        operationsRecyclerView.setLayoutManager(layoutManager);

        commonBalanceThousandsView = view.findViewById(R.id.dashboardCommonBalanceThousands);
        commonBalanceRestView = view.findViewById(R.id.dashboardCommonBalanceRest);
        commonIncomeView = view.findViewById(R.id.dashboardCommonIncome);
        commonOutcomeView = view.findViewById(R.id.dashboardCommonOutcome);

        String thousands = String.valueOf(commonBalance);
//        String rest = String.valueOf(commonBalance % 1000);

        commonBalanceThousandsView.setText(thousands);
//        commonBalanceRestView.setText(commonBalance > 999 ? rest : "");
        commonIncomeView.setText(String.valueOf(commonIncome));
        commonOutcomeView.setText(String.valueOf(commonOutcome));
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
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    void storeDataInArrays() {
        Cursor billsCursor = db.getAllBills();
        Cursor operationsCursor = db.getAllOperations();

        if (billsCursor.getCount() == 0) {
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (billsCursor.moveToNext()) {
                String _id = billsCursor.getString(0);
                String name = billsCursor.getString(1);
                String category = billsCursor.getString(2);
                Integer balance = billsCursor.getInt(3);
                Integer from = billsCursor.getInt(4);

                commonBalance += balance;
                bills.add(new Bill(_id, name, category, balance, from));
            }
        }

        if (operationsCursor.getCount() == 0) {
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (operationsCursor.moveToNext()) {
                String _id = operationsCursor.getString(0);
                String name = operationsCursor.getString(1);
                Integer sum = operationsCursor.getInt(2);
                String comments = operationsCursor.getString(3);
                String object = operationsCursor.getString(4);

                if (sum > 0) {
                    commonIncome += sum;
                } else if (sum < 0) {
                    commonOutcome += sum * -1;
                }

                operations.add(new Operation(_id, name, sum, comments, object));
            }
        }
    }
}