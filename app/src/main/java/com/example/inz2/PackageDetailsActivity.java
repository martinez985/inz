package com.example.inz2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.inz2.Model.DataPackage;

public class PackageDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_package_details);

        showPackageDetails();
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Powrót do poprzedniej aktywności
            }
        });
    }

    private void showPackageDetails() {
        Intent intent = getIntent();
        DataPackage selectedPackage = (DataPackage) intent.getSerializableExtra("SelectedPackage");

        if (selectedPackage != null) {
            TextView cityStartTextView = findViewById(R.id.cityStartTextView);
            String cityStart =selectedPackage.getDelivery_pickup().getZip_code() + " " + selectedPackage.getDelivery_pickup().getCity();
            cityStartTextView.setText(cityStart);

            TextView addressStartTextView = findViewById(R.id.addressStartTextView);

            String adressPickup = selectedPackage.getDelivery_pickup().getAddress() +" "+ selectedPackage.getDelivery_pickup().getHouse_number();
            addressStartTextView.setText(adressPickup);

            TextView cityEndTextView = findViewById(R.id.cityEndTextView);
            String cityEnd = selectedPackage.getDelivery_endpoint().getZip_code() +" " + selectedPackage.getDelivery_endpoint().getCity();
            cityEndTextView.setText(cityEnd);

            TextView addressEndTextView = findViewById(R.id.addressEndTextView);
            String adressEndpoint = selectedPackage.getDelivery_endpoint().getAddress() + " " + selectedPackage.getDelivery_endpoint().getHouse_number();
            addressEndTextView.setText(adressEndpoint);

            TextView IDtextView = findViewById(R.id.IDtextView);
            IDtextView.setText(String.valueOf(selectedPackage.getCommission_id()));

            TextView CounttextView = findViewById(R.id.CounttextView);
            CounttextView.setText(String.valueOf(selectedPackage.getCount()));

            TextView dimensionsTextView = findViewById(R.id.DimensionsTextView);

            double x = selectedPackage.getX();
            double y = selectedPackage.getY();
            double z = selectedPackage.getZ();

            String xFormatted = x % 1 == 0 ? String.valueOf((int) x) : String.format("%.2f", x);
            String yFormatted = y % 1 == 0 ? String.valueOf((int) y) : String.format("%.2f", y);
            String zFormatted = z % 1 == 0 ? String.valueOf((int) z) : String.format("%.2f", z);

            String dimensions = xFormatted + "/" + yFormatted + "/" + zFormatted + " cm";
            dimensionsTextView.setText(dimensions);

            TextView MassTextView = findViewById(R.id.MassTextView);
            MassTextView.setText(String.valueOf(selectedPackage.getMass()));

            TextView StackableTextView = findViewById(R.id.StackableTextView);
            boolean isStackable = selectedPackage.getStackable();
            StackableTextView.setText(isStackable ? "Tak" : "Nie");

            TextView PointNumbers = findViewById(R.id.PointsTextView);
            String points = "Załadowanie: " + selectedPackage.getPoint_number_start() + " Wyładowanie: " + selectedPackage.getPoint_number_end();
            PointNumbers.setText(points);
            TextView descriptionTextView = findViewById(R.id.descriptionTextView);
            String description = selectedPackage.getDescription();

            TextView GPSText = findViewById(R.id.textGPS);
            TextView GPSTextView = findViewById(R.id.GPSTextView);
            String gps = "";
            if(selectedPackage.getDelivery_pickup().getGps_X() == null
                    && selectedPackage.getDelivery_pickup().getGps_Y() == null
                    && selectedPackage.getDelivery_endpoint().getGps_X() == null
                    && selectedPackage.getDelivery_endpoint().getGps_Y() == null){
                GPSTextView.setVisibility(View.GONE);
                GPSText.setVisibility(View.GONE);
            }
            if(selectedPackage.getDelivery_pickup().getGps_X() != null
                    && selectedPackage.getDelivery_pickup().getGps_Y() != null){
                GPSTextView.setVisibility(View.VISIBLE);
                GPSText.setVisibility(View.VISIBLE);
                gps = "Punkt początkowy φ/λ: " + selectedPackage.getDelivery_pickup().getGps_X() + "/" +selectedPackage.getDelivery_pickup().getGps_Y();
            }
            if(selectedPackage.getDelivery_pickup().getGps_X() != null
                    && selectedPackage.getDelivery_pickup().getGps_Y() != null){
                GPSTextView.setVisibility(View.VISIBLE);
                GPSText.setVisibility(View.VISIBLE);
                gps += "\nPunkt końcowy φ/λ: " + selectedPackage.getDelivery_endpoint().getGps_X() + "/" +selectedPackage.getDelivery_endpoint().getGps_Y();
            }
            GPSTextView.setText(gps);
            if (description != null && !description.isEmpty()) {
                descriptionTextView.setText(description);
            } else {
                descriptionTextView.setText("Brak opisu");
            }
        }
    }
}
