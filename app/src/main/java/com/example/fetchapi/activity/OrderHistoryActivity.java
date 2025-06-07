package com.example.fetchapi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchapi.MainActivity;
import com.example.fetchapi.R;
import com.example.fetchapi.adapter.HistoryAdapter;

import com.example.fetchapi.database.DatabaseHelperHistory;
import com.example.fetchapi.model.History;

import java.util.List;
import java.util.Map;

public class OrderHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.historyRecyclerView);
        backBtn = findViewById(R.id.mainOrderHistory);

        DatabaseHelperHistory dbHelper = new DatabaseHelperHistory(this);
        Map<Integer, List<History>> grouped = dbHelper.getHistory();

        HistoryAdapter adapter = new HistoryAdapter(grouped);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(OrderHistoryActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
