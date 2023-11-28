package com.example.inz2;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.inz2.Model.DataPackage;
import com.example.inz2.Model.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddressesViewModel extends ViewModel {

    private final PackageRepository repository;
    private MutableLiveData<Map<String, List<DataPackage>>> unloadedPackagesByCityLiveData = new MutableLiveData<>();
    private MutableLiveData<Map<String, List<DataPackage>>> loadedPackagesByCityLiveData = new MutableLiveData<>();
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
                    Map<String, List<DataPackage>> unloadedPackagesByCity = groupUnloadedPackagesByCity(packages);
                    unloadedPackagesByCityLiveData.setValue(unloadedPackagesByCity);

                    Map<String, List<DataPackage>> loadedPackagesByCity = groupLoadedPackagesByCity(packages);
                    loadedPackagesByCityLiveData.setValue(loadedPackagesByCity);

                    boolean allPackagesCompleted = checkAllPackagesCompleted(packages);
                    allPackagesCompletedLiveData.setValue(allPackagesCompleted);
                }
            }
        };
    }

    public LiveData<Map<String, List<DataPackage>>> getUnloadedPackagesByCity(String token) {
        LiveData<List<DataPackage>> packagesLiveData = repository.getPackages(token);
        packagesLiveData.observeForever(packagesObserver);
        return unloadedPackagesByCityLiveData;
    }

    public LiveData<Map<String, List<DataPackage>>> getLoadedPackagesByCity(String token) {
        LiveData<List<DataPackage>> packagesLiveData = repository.getPackages(token);
        packagesLiveData.observeForever(packagesObserver);
        return loadedPackagesByCityLiveData;
    }

    public void setRouteClosedListener(RouteClosedListener listener) {
        this.routeClosedListener = listener;
    }

    private Map<String, List<DataPackage>> groupUnloadedPackagesByCity(List<DataPackage> dataPackages) {
        Map<String, List<DataPackage>> unloadedPackagesByCity = new HashMap<>();

        for (DataPackage dataPackageItem : dataPackages) {
            Boolean isLoaded = dataPackageItem.getIs_loaded();
            if (isLoaded != null && !isLoaded) {
                String city = dataPackageItem.getDelivery_pickup().getCity();
                List<DataPackage> cityDataPackages = unloadedPackagesByCity.get(city);
                if (cityDataPackages == null) {
                    cityDataPackages = new ArrayList<>();
                    unloadedPackagesByCity.put(city, cityDataPackages);
                }
                cityDataPackages.add(dataPackageItem);
            }
        }
        return unloadedPackagesByCity;
    }

    private Map<String, List<DataPackage>> groupLoadedPackagesByCity(List<DataPackage> dataPackages) {
        Map<String, List<DataPackage>> loadedPackagesByCity = new HashMap<>();

        for (DataPackage dataPackageItem : dataPackages) {
            Boolean isLoaded = dataPackageItem.getIs_loaded();
            Boolean isUnloaded = dataPackageItem.getIs_unloaded();
            if (isLoaded != null && isUnloaded != null && isLoaded && !isUnloaded) {
                String city = dataPackageItem.getDelivery_endpoint().getCity();
                List<DataPackage> cityDataPackages = loadedPackagesByCity.get(city);
                if (cityDataPackages == null) {
                    cityDataPackages = new ArrayList<>();
                    loadedPackagesByCity.put(city, cityDataPackages);
                }
                cityDataPackages.add(dataPackageItem);
            }
        }
        return loadedPackagesByCity;
    }

    private boolean checkAllPackagesCompleted(List<DataPackage> packages) {
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
                // Handle failure
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