package com.example.inz2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.inz2.Model.DataPackage;
import com.example.inz2.Model.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddressesActivity extends AppCompatActivity {

    private CityAdapter cityAdapter;
    private CityAdapter loadedCityAdapter;
    private AddressesViewModel viewModel;
    private Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_addresses);

        viewModel = new ViewModelProvider(this).get(AddressesViewModel.class);


        loadedCityAdapter = new CityAdapter(new ArrayList<>(), new HashMap<>());

        cityAdapter = new CityAdapter(new ArrayList<>(), new HashMap<>());
        RecyclerView recyclerView = findViewById(R.id.addressRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cityAdapter);

        RecyclerView loadedRecyclerView = findViewById(R.id.addressRecyclerView2);
        loadedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadedRecyclerView.setAdapter(loadedCityAdapter);

        observeViewModel();

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        this.finish = findViewById(R.id.finishTripButton);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = readTokenFromSharedPreferences();

                viewModel.fetchRouteData(token);

                viewModel.getRouteIdLiveData().observe(AddressesActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer routeId) {
                        if (routeId != null) {
                            String token = readTokenFromSharedPreferences();
                            viewModel.endRoute(token, routeId);
                            finish();
                        } else {
                            Log.e("AddressesActivity", "Błąd: IDroute jest null");
                        }
                    }
                });

            }
        });
    }

    private void observeViewModel() {
        String token = readTokenFromSharedPreferences();

        viewModel.getUnloadedPackagesByCity(token).observe(this, unloadedPackagesByCity -> {
            if (unloadedPackagesByCity != null && !unloadedPackagesByCity.isEmpty()) {
                cityAdapter.updateData(new ArrayList<>(unloadedPackagesByCity.keySet()), unloadedPackagesByCity);
                findViewById(R.id.noPackagesUnloadedTextView).setVisibility(View.GONE);
            } else {
                cityAdapter.updateData(new ArrayList<>(unloadedPackagesByCity.keySet()), unloadedPackagesByCity);
                findViewById(R.id.noPackagesUnloadedTextView).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.noPackagesUnloadedTextView)).setText("Brak paczek do załadowania");
            }
        });

        viewModel.getLoadedPackagesByCity(token).observe(this, loadedPackagesByCity -> {
            if (loadedPackagesByCity != null && !loadedPackagesByCity.isEmpty()) {
                loadedCityAdapter.updateData(new ArrayList<>(loadedPackagesByCity.keySet()), loadedPackagesByCity);
                findViewById(R.id.noPackagesLoadedTextView).setVisibility(View.GONE);
            } else {
                loadedCityAdapter.updateData(new ArrayList<>(loadedPackagesByCity.keySet()), loadedPackagesByCity);
                findViewById(R.id.noPackagesLoadedTextView).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.noPackagesLoadedTextView)).setText("Brak paczek do wyładowania");
            }
        });

        viewModel.getAllPackagesCompletedLiveData().observe(this, allPackagesCompleted -> {
            if (allPackagesCompleted != null && allPackagesCompleted) {
                finish.setVisibility(View.VISIBLE);
            } else {
                finish.setVisibility(View.GONE);
            }
        });
    }

    private String readTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("mySharedPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("token_key", "");
    }

    public void refreshData() {
        observeViewModel();
    }
}