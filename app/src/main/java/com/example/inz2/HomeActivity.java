package com.example.inz2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {
    private TextView textViewHome;
    private Button button, buttonHistory;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textViewHome = findViewById(R.id.textViewHome);
        button = findViewById(R.id.material_button);
        buttonHistory = findViewById(R.id.history_button);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getTruckInfoLiveData().observe(this, truckInfo -> {
            if (truckInfo != null && !truckInfo.isEmpty()) {
                textViewHome.setText(truckInfo);
            } else {
                textViewHome.setText("nie masz obecnie przypisanej ciężarówki");
                Log.e("TruckInfo", "Brak informacji");
            }
        });

        button.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddressesActivity.class);
            startActivity(intent);
        });

        buttonHistory.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        String token = readTokenFromSharedPreferences();
        homeViewModel.fetchDataForTruck(token);
    }

    private String readTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("mySharedPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("token_key", "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        String token = readTokenFromSharedPreferences();
        homeViewModel.refreshData(token);
    }
}

