package com.example.samoney;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.ViewHolder> {

    Context context;
//    Activity activity;
    ArrayList<Operation> operations;

    int position;

    OperationsAdapter(Context context, ArrayList<Operation> operations) {
//        this.activity = activity;
        this.context = context;
        this.operations = operations;
    }

    @NonNull
    @Override
    public OperationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.operation_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OperationsAdapter.ViewHolder holder, int position) {
        this.position = position;

        Operation operation = operations.get(position);

        holder.operationRowName.setText(String.valueOf(operation.name));

        Integer balance = operation.sum;
//        Integer thousands = balance / 1000;
//        Integer rest = balance % 1000;
        String character = balance > 0 ? "+" : balance == 0 ? "" : "-";
        holder.operationRowCharacter.setText(character);
        holder.operationRowCharacter.setTextColor(balance > 0
                ? context.getResources().getColor(R.color.teal_200)
                : context.getResources().getColor(R.color.orange_200)
        );
//        holder.operationRowThousands.setText(balance > 1000 ? String.valueOf(thousands) : "");
        holder.operationRowRest.setText(String.valueOf(balance > 0 ? balance : balance * -1));

//        String type = bill.category;
//
//        switch (type) {
//            case "Simple":
//                holder.billRowIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_wallet_icon));
//                break;
//            case "Credit":
//                holder.billRowIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_discount_symbol));
//                break;
//            case "Goal":
//                holder.billRowIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_goal_icon));
//        }

        holder.billRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OperationDetails.class);
                Operation current = operations.get(position);
                intent.putExtra("operation", current);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView billRowIcon;
        TextView operationRowName, operationRowThousands, operationRowRest, operationRowCharacter, billRowBalanceCharacter, billRowBalanceThousands, billRowBalanceRest;
        ConstraintLayout billRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            billRowIcon = itemView.findViewById(R.id.operationRowIcon);
            operationRowName = itemView.findViewById(R.id.operationRowName);
            operationRowThousands = itemView.findViewById(R.id.operationRowBalanceThousands);
            operationRowRest = itemView.findViewById(R.id.operationRowBalanceRest);
            operationRowCharacter = itemView.findViewById(R.id.operationRowBalanceCharacter);
            //            billRowBalanceCharacter = itemView.findViewById(R.id.operationRowBalanceCharacter);
//            billRowBalanceThousands = itemView.findViewById(R.id.operationRowBalanceThousands);
//            billRowBalanceRest = itemView.findViewById(R.id.operationRowBalanceRest);
            billRow = itemView.findViewById(R.id.operationRow);
        }
    }
}
