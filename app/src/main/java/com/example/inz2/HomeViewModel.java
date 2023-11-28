package com.example.inz2;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inz2.Model.Route;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<String> truckInfoLiveData = new MutableLiveData<>();
    private final MutableLiveData<Route> routeInfoLiveData = new MutableLiveData<>();
    private final Repository repository;

    public HomeViewModel() {
        this.repository = new Repository();
    }

    public void fetchDataForTruck(String token) {
        repository.getRouteData(token, new Repository.IRouteDataResponse() {
            @Override
            public void onResponse(Route route) {
                String displayText = "Model: " + route.getTruckModel() +
                        "\nNumer rejestracyjny: " + route.getTruckReg();
                truckInfoLiveData.setValue(displayText);
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public LiveData<String> getTruckInfoLiveData() {
        return truckInfoLiveData;
    }

    public void refreshData(String token) {
        truckInfoLiveData.setValue(null);
        fetchDataForTruck(token);
    }
    public void clearData() {
        truckInfoLiveData.setValue(null);
    }
}
