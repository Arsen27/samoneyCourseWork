package com.example.samoney;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LimitsAdapter extends RecyclerView.Adapter<LimitsAdapter.ViewHolder> {

    Context context;
//    Activity activity;
    ArrayList<Limit> limits;

    int position;

    LimitsAdapter(Context context, ArrayList<Limit> limits) {
//        this.activity = activity;
        this.context = context;
        this.limits = limits;
    }

    @NonNull
    @Override
    public LimitsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.limit_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LimitsAdapter.ViewHolder holder, int position) {
        this.position = position;

        Limit limit = limits.get(position);

        holder.limitRowName.setText(String.valueOf(limit.name));
        holder.limitRowSum.setText(String.valueOf(limit.sum));
        holder.limitRowChar.setText("<");

        holder.limitRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LimitDetails.class);
                Limit current = limits.get(position);
                intent.putExtra("limit", current);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return limits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView limitRowName, limitRowSum, limitRowChar;
        ConstraintLayout limitRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            limitRowName = itemView.findViewById(R.id.limitRowName);
            limitRowSum = itemView.findViewById(R.id.limitRowSum);
            limitRowChar = itemView.findViewById(R.id.limitRowCharacter);
            limitRow = itemView.findViewById(R.id.limitRow);
        }
    }
}
