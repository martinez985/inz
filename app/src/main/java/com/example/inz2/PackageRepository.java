package com.example.inz2;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inz2.Model.DataPackage;
import com.example.inz2.Model.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackageRepository {
    private final InterfaceAPI apiService;

    public PackageRepository() {
        this.apiService = RetrofitClientInstance.getRetrofitInstance().create(InterfaceAPI.class);
    }

    public LiveData<List<DataPackage>> getPackages(String token) {
        MutableLiveData<List<DataPackage>> packagesLiveData = new MutableLiveData<>();

        Call<Route> call = apiService.getRouteData("Bearer " + token);
        call.enqueue(new Callback<Route>() {
            @Override
            public void onResponse(Call<Route> call, Response<Route> response) {
                if (response.isSuccessful()) {
                    Route route = response.body();
                    if (route != null) {
                        List<DataPackage> dataPackages = route.getPackages();
                        packagesLiveData.setValue(dataPackages);
                        Log.e("PackageRepository", "Udało sie pobrać");
                    } else {
                        Log.e("PackageRepository", "getPackages, Brak danych trasy");
                    }
                } else {
                    Log.e("PackageRepository", "getPackages, Błąd odpowiedzi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Route> call, Throwable t) {
                Log.e("PackageRepository", "getPackages, Błąd zapytania", t);
            }
        });

        return packagesLiveData;
    }


}
