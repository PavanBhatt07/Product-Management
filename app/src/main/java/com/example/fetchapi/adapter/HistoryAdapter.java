package com.example.fetchapi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchapi.R;
import com.example.fetchapi.model.History;

import java.util.List;
import java.util.Map;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.OrderViewHolder> {
    private List<Map.Entry<Integer, List<History>>> orderList;


    public HistoryAdapter(Map<Integer, List<History>> grouped) {
        this.orderList = new java.util.ArrayList<>(grouped.entrySet());
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_view, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Map.Entry<Integer, List<History>> entry = orderList.get(position);
        int orderId = entry.getKey();
        List<History> items = entry.getValue();

        holder.orderTitle.setText("Order #" + orderId);
        HistoryListAdapter listAdapter = new HistoryListAdapter(items);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerView.setAdapter(listAdapter);
        holder.recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderTitle;
        RecyclerView recyclerView;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderTitle = itemView.findViewById(R.id.historyOrderCount);
            recyclerView = itemView.findViewById(R.id.historyListView);
        }
    }
}

