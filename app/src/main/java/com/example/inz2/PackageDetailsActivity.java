package com.example.inz2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.inz2.Model.DataPackage;

public class PackageDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            cityStartTextView.setText(selectedPackage.getDelivery_pickup().getCity());

            TextView addressStartTextView = findViewById(R.id.addressStartTextView);

            String adressPickup = selectedPackage.getDelivery_pickup().getAddress() +" "+ selectedPackage.getDelivery_pickup().getHouse_number();
            addressStartTextView.setText(adressPickup);

            TextView cityEndTextView = findViewById(R.id.cityEndTextView);
            cityEndTextView.setText(selectedPackage.getDelivery_endpoint().getCity());

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

            TextView descriptionTextView = findViewById(R.id.descriptionTextView);
            String description = selectedPackage.getDescription();

            if (description != null && !description.isEmpty()) {
                descriptionTextView.setText(description);
            } else {
                descriptionTextView.setText("Brak opisu");
            }
        }
    }
}
