package com.example.inz2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inz2.Model.DataPackage;
import com.example.inz2.Model.RouteHistory;

import java.util.ArrayList;
import java.util.List;

public class HistoryViewModel extends ViewModel {
    private final PackageRepository repository;
    private LiveData<List<RouteHistory>> routeHistoryLiveData;

    public HistoryViewModel() {
        this.repository = new PackageRepository();
    }

    public LiveData<List<RouteHistory>> getRouteHistory(String token) {
        routeHistoryLiveData = repository.getRouteHistory(token);
        return routeHistoryLiveData;
    }

}
