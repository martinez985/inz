package com.example.inz2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inz2.Model.DataPackage;

import java.util.ArrayList;
import java.util.List;

public class HistoryViewModel extends ViewModel {
    private final PackageRepository repository;
    private LiveData<List<DataPackage>> packagesLiveData;

    public HistoryViewModel() {
        this.repository = new PackageRepository();
    }

    public LiveData<List<DataPackage>> getPackages(String token) {
        packagesLiveData = repository.getPackages(token);
        return packagesLiveData;
    }

    public LiveData<List<DataPackage>> getFilteredPackages() {
        MutableLiveData<List<DataPackage>> filteredPackagesLiveData = new MutableLiveData<>();
        List<DataPackage> originalPackages = packagesLiveData.getValue();

        if (originalPackages != null) {
            List<DataPackage> filteredPackages = new ArrayList<>();
            for (DataPackage dataPackage : originalPackages) {
                if (dataPackage.getIs_loaded() && dataPackage.getIs_unloaded()) {
                    filteredPackages.add(dataPackage);
                }
            }
            filteredPackagesLiveData.setValue(filteredPackages);
        }

        return filteredPackagesLiveData;
    }
}
