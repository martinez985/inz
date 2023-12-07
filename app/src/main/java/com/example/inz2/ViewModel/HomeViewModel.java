package com.example.inz2.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inz2.Model.Route;
import com.example.inz2.repository.Repository;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<String> truckInfoLiveData = new MutableLiveData<>();
    private final MutableLiveData<Route> routeInfoLiveData = new MutableLiveData<>();
    private final Repository repository;

    public HomeViewModel() {
        this.repository = new Repository();
    }

    public void fetchDataForRoute(String token) {
        repository.getRouteData(token, new Repository.IRouteDataResponse() {
            @Override
            public void onResponse(Route route) {
                if (route != null) {
                    routeInfoLiveData.setValue(route);
                } else {
                    routeInfoLiveData.setValue(null);
                }
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public LiveData<Route> getRouteInfoLiveData() {
        return routeInfoLiveData;
    }

    public void refreshData(String token) {
        truckInfoLiveData.setValue(null);
        fetchDataForRoute(token);
    }
    public void clearData() {
        truckInfoLiveData.setValue(null);
    }


}
