package com.example.inz2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inz2.Model.DataPackage;
import com.example.inz2.Model.Route;

import java.util.List;
import java.util.Map;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {
    private List<String> citiesList;
    private Map<String, List<DataPackage>> packagesByCity;

    public CityAdapter(List<String> citiesList, Map<String, List<DataPackage>> packagesByCity) {
        this.citiesList = citiesList;
        this.packagesByCity = packagesByCity;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_address, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        String city = citiesList.get(position);
        List<DataPackage> packages = packagesByCity.get(city);
        holder.bind(city, packages);
    }

    public void updateData(List<String> citiesList, Map<String, List<DataPackage>> packagesByCity) {
        this.citiesList = citiesList;
        this.packagesByCity = packagesByCity;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {
        private TextView cityTextView;
        private RecyclerView addressRecyclerView;
        private AddressAdapter addressAdapter;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTextView = itemView.findViewById(R.id.cityTextView);
            addressRecyclerView = itemView.findViewById(R.id.addressRecyclerView);
        }

        public void bind(String city, List<DataPackage> packages) {
            cityTextView.setText(city);
            addressAdapter = new AddressAdapter(packages);
            addressRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            addressRecyclerView.setAdapter(addressAdapter);
        }
    }
}