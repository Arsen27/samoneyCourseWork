package com.example.samoney;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.ViewHolder> {

    Context context;
    ArrayList<Bill> bills;

    int position;

    BillsAdapter(Context context, ArrayList<Bill> bills) {
        this.context = context;
        this.bills = bills;
    }

    @NonNull
    @Override
    public BillsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bill_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillsAdapter.ViewHolder holder, int position) {
        this.position = position;

        Bill bill = bills.get(position);

        holder.billRowName.setText(String.valueOf(bill.name));
        Integer balance = bill.balance;
//        Integer thousands = balance / 1000;
//        Integer rest = balance % 1000;
        String character = balance > 0 ? "+" : balance == 0 ? "" : "-";
        holder.billRowBalanceCharacter.setText(character);
        holder.billRowBalanceCharacter.setTextColor(balance > 0
                ? context.getResources().getColor(R.color.teal_200)
                : context.getResources().getColor(R.color.orange_200)
        );
//        holder.billRowBalanceThousands.setText(balance > 1000 ? String.valueOf(thousands) : "");
        holder.billRowBalanceRest.setText(String.valueOf(balance > 0 ? balance : balance * -1));

        String type = bill.category;

        switch (type) {
            case "Simple":
                holder.billRowIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_wallet_icon));
                break;
            case "Credit":
                holder.billRowIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_discount_symbol));
                break;
            case "Goal":
                holder.billRowIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_goal_icon));
        }

        holder.billRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BillDetails.class);
                Bill current = bills.get(position);
                intent.putExtra("bill", current);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView billRowIcon;
        TextView billRowName, billRowBalanceCharacter, billRowBalanceThousands, billRowBalanceRest;
        ConstraintLayout billRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            billRowIcon = itemView.findViewById(R.id.billRowIcon);
            billRowName = itemView.findViewById(R.id.billRowName);
            billRowBalanceCharacter = itemView.findViewById(R.id.billRowBalanceCharacter);
            billRowBalanceThousands = itemView.findViewById(R.id.billRowBalanceThousands);
            billRowBalanceRest = itemView.findViewById(R.id.billRowBalanceRest);
            billRow = itemView.findViewById(R.id.billRow);
        }
    }
}
