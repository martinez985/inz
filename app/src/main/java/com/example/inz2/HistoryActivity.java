package com.example.inz2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.inz2.Model.DataPackage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private HistoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        viewModel.getFilteredPackages().observe(this, packages -> {
            if (packages != null) {
                adapter.updateData(packages);
                TextView noHistoryTextView = findViewById(R.id.noHistoryTextView);
                noHistoryTextView.setVisibility(packages.isEmpty() ? View.VISIBLE : View.GONE);
            }
        });
    }

    private String readTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("mySharedPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("token_key", "");
    }
}