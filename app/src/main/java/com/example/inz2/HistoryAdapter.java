package com.example.inz2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inz2.Model.DataPackage;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<DataPackage> packageList;
    private Context context;

    public HistoryAdapter(Context context, List<DataPackage> packageList) {
        this.context = context;
        this.packageList = packageList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        DataPackage currentPackage = packageList.get(position);
        holder.idTextView.setText("ID: " + currentPackage.getCommission_id());
        holder.dateOfReceiptTextView.setText("Data odbioru: " + currentPackage.getDate_of_receipt());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, PackageDetailsActivity.class);
            intent.putExtra("SelectedPackage", currentPackage);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    public void updateData(List<DataPackage> newData) {
        packageList.clear();
        packageList.addAll(newData);
        notifyDataSetChanged();
    }
    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView idTextView;
        private TextView dateOfReceiptTextView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            dateOfReceiptTextView = itemView.findViewById(R.id.dateOfReceiptTextView);
        }
    }
}

