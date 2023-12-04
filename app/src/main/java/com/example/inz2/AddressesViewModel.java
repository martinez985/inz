package com.example.inz2;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.inz2.Model.DataPackage;
import com.example.inz2.Model.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddressesViewModel extends ViewModel {

    private final PackageRepository repository;
    private MutableLiveData<List<DataPackage>> unloadedPackagesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<DataPackage>> loadedPackagesLiveData = new MutableLiveData<>();
    private Observer<List<DataPackage>> packagesObserver;
    private MutableLiveData<Boolean> allPackagesCompletedLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> routeIdLiveData = new MutableLiveData<>();
    private RouteClosedListener routeClosedListener;

    public interface RouteClosedListener {
        void onRouteClosed();
    }

    public AddressesViewModel() {
        this.repository = new PackageRepository();
        this.packagesObserver = new Observer<List<DataPackage>>() {
            @Override
            public void onChanged(List<DataPackage> packages) {
                if (packages != null) {
                    List<DataPackage> unloadedPackages = filterUnloadedPackages(packages);
                    unloadedPackagesLiveData.setValue(unloadedPackages);

                    List<DataPackage> loadedPackages = filterLoadedPackages(packages);
                    loadedPackagesLiveData.setValue(loadedPackages);

                    boolean allPackagesCompleted = checkAllPackagesCompleted(packages);
                    allPackagesCompletedLiveData.setValue(allPackagesCompleted);
                }
            }
        };
    }

    public LiveData<List<DataPackage>> getUnloadedPackagesByCity(String token) {
        LiveData<List<DataPackage>> packagesLiveData = repository.getPackages(token);
        packagesLiveData.observeForever(packagesObserver);
        return unloadedPackagesLiveData;
    }

    public LiveData<List<DataPackage>> getLoadedPackagesByCity(String token) {
        LiveData<List<DataPackage>> packagesLiveData = repository.getPackages(token);
        packagesLiveData.observeForever(packagesObserver);
        return loadedPackagesLiveData;
    }

    private List<DataPackage> filterUnloadedPackages(List<DataPackage> dataPackages) {
        List<DataPackage> unloadedPackages = new ArrayList<>();

        for (DataPackage dataPackageItem : dataPackages) {
            Boolean isLoaded = dataPackageItem.getIs_loaded();
            if (isLoaded != null && !isLoaded) {
                unloadedPackages.add(dataPackageItem);
            }
        }
        Collections.sort(unloadedPackages, new Comparator<DataPackage>() {
            @Override
            public int compare(DataPackage package1, DataPackage package2) {
                return package1.getPoint_number_start().compareTo(package2.getPoint_number_start());
            }
        });

        return unloadedPackages;
    }

    private List<DataPackage> filterLoadedPackages(List<DataPackage> dataPackages) {
        List<DataPackage> loadedPackages = new ArrayList<>();

        for (DataPackage dataPackageItem : dataPackages) {
            Boolean isLoaded = dataPackageItem.getIs_loaded();
            Boolean isUnloaded = dataPackageItem.getIs_unloaded();
            if (isLoaded != null && isUnloaded != null && isLoaded && !isUnloaded) {
                loadedPackages.add(dataPackageItem);
            }
        }

        Collections.sort(loadedPackages, new Comparator<DataPackage>() {
            @Override
            public int compare(DataPackage package1, DataPackage package2) {
                return package1.getPoint_number_end().compareTo(package2.getPoint_number_end());
            }
        });

        return loadedPackages;
    }

    private boolean checkAllPackagesCompleted(List<DataPackage> packages) {
       // if(packages.isEmpty()){
       //     return false;
      //  }
        for (DataPackage dataPackage : packages) {
            Boolean isLoaded = dataPackage.getIs_loaded();
            Boolean isUnloaded = dataPackage.getIs_unloaded();
            if (isLoaded == null || isUnloaded == null || !isLoaded || !isUnloaded) {
                return false;
            }
        }
        return true;
    }

    public LiveData<Boolean> getAllPackagesCompletedLiveData() {
        return allPackagesCompletedLiveData;
    }

    public void endRoute(String token, Integer id) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        InterfaceAPI interfaceAPI = retrofit.create(InterfaceAPI.class);
        Log.d("EndRoute", "Sending endRoute request with ID: " + id);
        Call<Void> call = interfaceAPI.endRoute("Bearer " + token, id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("EndRoute", "Trasa została zakończona pomyślnie");
                    if (routeClosedListener != null) {
                        routeClosedListener.onRouteClosed();
                    }
                } else {
                    Log.e("EndRoute", "Błąd w odpowiedzi z serwera");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AddressesActivity", "Błąd wysyłania danych na serwer", t);
            }
        });
    }

    public LiveData<Integer> getRouteIdLiveData() {
        return routeIdLiveData;
    }

    public void fetchRouteData(String token) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        InterfaceAPI interfaceAPI = retrofit.create(InterfaceAPI.class);

        Call<Route> call = interfaceAPI.getRouteData("Bearer " + token);

        call.enqueue(new Callback<Route>() {
            @Override
            public void onResponse(Call<Route> call, Response<Route> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Integer routeId = response.body().getRoute_id();
                    routeIdLiveData.setValue(routeId);
                } else {
                    Log.e("FetchRouteData", "Błąd w odpowiedzi z serwera");
                }
            }

            @Override
            public void onFailure(Call<Route> call, Throwable t) {
                Log.e("FetchRouteData", "Błąd pobierania danych na serwer", t);
            }
        });
    }
}
