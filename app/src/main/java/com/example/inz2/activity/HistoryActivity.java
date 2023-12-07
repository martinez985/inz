package com.example.inz2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.inz2.adapter.HistoryAdapter;
import com.example.inz2.ViewModel.HistoryViewModel;
import com.example.inz2.R;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private HistoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_history);

        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);

        recyclerView = findViewById(R.id.historyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HistoryAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());

        observeViewModel();
    }

    private void observeViewModel() {
        String JWTToken = readTokenFromSharedPreferences();
        viewModel.getRouteHistory(JWTToken).observe(this, routeHistoryList -> {
            if (routeHistoryList != null) {
                adapter.updateData(routeHistoryList);
                TextView noHistoryTextView = findViewById(R.id.noHistoryTextView);
                noHistoryTextView.setVisibility(routeHistoryList.isEmpty() ? View.VISIBLE : View.GONE);
            }
        });
    }

    private String readTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("mySharedPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("token_key", "");
    }
}