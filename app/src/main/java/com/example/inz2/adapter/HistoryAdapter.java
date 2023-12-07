package com.example.inz2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inz2.Model.RouteHistory;
import com.example.inz2.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<RouteHistory> routeHistoryList;
    private Context context;

    public HistoryAdapter(Context context, List<RouteHistory> routeHistoryList) {
        this.context = context;
        this.routeHistoryList = routeHistoryList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        RouteHistory currentRouteHistory = routeHistoryList.get(position);
       // holder.idTextView.setText("ID przejazdu: " + currentRouteHistory.getId());
        holder.truckTextView.setText(currentRouteHistory.getTruck());
        holder.startDateTextView.setText( currentRouteHistory.getDataStart());
        holder.endDateTextView.setText( currentRouteHistory.getDataEnd());

    }

    @Override
    public int getItemCount() {
        return routeHistoryList.size();
    }

    public void updateData(List<RouteHistory> newData) {
        routeHistoryList.clear();
        routeHistoryList.addAll(newData);
        notifyDataSetChanged();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView idTextView;
        private TextView truckTextView;
        private TextView startDateTextView;
        private TextView endDateTextView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            //idTextView = itemView.findViewById(R.id.idTextView);
            truckTextView = itemView.findViewById(R.id.truckTextView);
            startDateTextView = itemView.findViewById(R.id.startDateTextView);
            endDateTextView = itemView.findViewById(R.id.endDateTextView);
        }
    }
}

