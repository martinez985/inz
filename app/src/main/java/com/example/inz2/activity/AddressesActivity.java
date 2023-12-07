package com.example.inz2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.inz2.R;
import com.example.inz2.adapter.AddressAdapter;
import com.example.inz2.ViewModel.AddressesViewModel;

import java.util.ArrayList;

public class AddressesActivity extends AppCompatActivity {

    private AddressAdapter addressAdapter;
    private AddressAdapter loadedAddressAdapter;
    private AddressesViewModel viewModel;
    private Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_addresses);

        viewModel = new ViewModelProvider(this).get(AddressesViewModel.class);

        loadedAddressAdapter = new AddressAdapter(new ArrayList<>());

        addressAdapter = new AddressAdapter(new ArrayList<>());
        RecyclerView recyclerView = findViewById(R.id.addressRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addressAdapter);

        RecyclerView loadedRecyclerView = findViewById(R.id.addressRecyclerView2);
        loadedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadedRecyclerView.setAdapter(loadedAddressAdapter);

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

        viewModel.getUnloadedPackagesByCity(token).observe(this, unloadedPackages -> {
            if (unloadedPackages != null && !unloadedPackages.isEmpty()) {
                addressAdapter.updateData(unloadedPackages);
                findViewById(R.id.noPackagesUnloadedTextView).setVisibility(View.GONE);
            } else {
                addressAdapter.updateData(new ArrayList<>());
                findViewById(R.id.noPackagesUnloadedTextView).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.noPackagesUnloadedTextView)).setText("Brak paczek do załadowania");
            }
        });

        viewModel.getLoadedPackagesByCity(token).observe(this, loadedPackages -> {
            if (loadedPackages != null && !loadedPackages.isEmpty()) {
                loadedAddressAdapter.updateData(loadedPackages);
                findViewById(R.id.noPackagesLoadedTextView).setVisibility(View.GONE);
            } else {
                loadedAddressAdapter.updateData(new ArrayList<>());
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
