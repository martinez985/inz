package com.example.inz2.repository;

import com.example.inz2.dto.LoginBody;
import com.example.inz2.dto.LoginResponse;
import com.example.inz2.Model.Route;
import com.example.inz2.api.InterfaceAPI;
import com.example.inz2.api.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private final InterfaceAPI ApiService;

    public Repository() {
        this.ApiService = RetrofitClientInstance.getRetrofitInstance().create(InterfaceAPI.class);
    }

    public void loginRemote(LoginBody loginBody, ILoginResponse loginResponse) {
        Call<LoginResponse> initiateLogin = ApiService.checkLogin(loginBody);

        initiateLogin.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    loginResponse.onResponse(response.body());
                } else {
                    loginResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponse.onFailure(t);
            }
        });
    }

    public interface ILoginResponse {
        void onResponse(LoginResponse loginResponse);

        void onFailure(Throwable t);
    }

    public void getRouteData(String token, IRouteDataResponse routeDataResponse) {
        Call<Route> routeCall = ApiService.getRouteData("Bearer " + token);

        routeCall.enqueue(new Callback<Route>() {
            @Override
            public void onResponse( Call<Route> call, Response<Route> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Route route = response.body();
                        routeDataResponse.onResponse(route);
                    }
                }
            }
            @Override
            public void onFailure(Call<Route> call, Throwable t) {
                routeDataResponse.onResponse(null);
            }
        });
    }

    public interface IRouteDataResponse {
        void onResponse(Route route);

        void onFailure(Throwable t);
    }
}


