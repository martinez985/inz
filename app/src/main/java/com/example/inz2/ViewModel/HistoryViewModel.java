package com.example.inz2.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.inz2.Model.RouteHistory;
import com.example.inz2.repository.PackageRepository;

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
