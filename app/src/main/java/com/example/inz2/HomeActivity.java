package com.example.inz2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {
    private TextView textViewHome,Start_tv,End_tv,AdresStartText_tv,DateStartText_tv,AdresEndText_tv,TruckText_tv,AdresStart_tv,DateStart_tv,AdresEnd_tv,Truck_tv;
    private Button button, buttonHistory;
    private HomeViewModel homeViewModel;
    private ConstraintLayout backgroundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_home);

        textViewHome = findViewById(R.id.textViewHome);
        Start_tv = findViewById(R.id.textViewStartTitle);
        End_tv = findViewById(R.id.textViewEndTitle);
        AdresStart_tv = findViewById(R.id.textViewAdressStartDisplay);
        DateStart_tv = findViewById(R.id.textViewDateStartDisplay);
        AdresEnd_tv = findViewById(R.id.textViewAdressEndDisplay);
        Truck_tv = findViewById(R.id.textViewTruckDisplay);
        AdresStartText_tv = findViewById(R.id.textViewAdressStart);
        DateStartText_tv = findViewById(R.id.textViewDateStart);
        AdresEndText_tv = findViewById(R.id.textViewAdressEnd);
        TruckText_tv = findViewById(R.id.textViewTruck);

        button = findViewById(R.id.material_button);
        buttonHistory = findViewById(R.id.history_button);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel.getRouteInfoLiveData().observe(this, route -> {
            if (route != null) {

                String startAddressText = route.getZipCode() + ", " + route.getCity() + " " + route.getAddress()+ ", "+ route.getHouseNumber();
                String startDateText =  route.getDate();
                String endAddressText = route.getZipCodeEnd() + ", "+ route.getCityEnd() + " " + route.getAddressEnd()+ ", "+ route.getHouseNumberEnd();

                String truckData = route.getTruckModel()+"\n" + route.getTruckReg();

                textViewHome.setVisibility(View.GONE);
                Start_tv.setVisibility(View.VISIBLE);
                End_tv.setVisibility(View.VISIBLE);
                AdresStart_tv.setVisibility(View.VISIBLE);
                DateStart_tv.setVisibility(View.VISIBLE);
                AdresEnd_tv.setVisibility(View.VISIBLE);
                Truck_tv.setVisibility(View.VISIBLE);
                AdresStartText_tv.setVisibility(View.VISIBLE);
                DateStartText_tv.setVisibility(View.VISIBLE);
                AdresEndText_tv.setVisibility(View.VISIBLE);
                TruckText_tv.setVisibility(View.VISIBLE);

                AdresStart_tv.setText(startAddressText);
                DateStart_tv.setText(startDateText);
                AdresEnd_tv.setText(endAddressText);
                Truck_tv.setText(truckData);

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

