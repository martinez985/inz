package com.example.inz2;

import com.example.inz2.Model.Route;

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
            public void onResponse(Call<Route> call, Response<Route> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Route route = response.body();
                    routeDataResponse.onResponse(route);
                } else {
                    routeDataResponse.onFailure(new Exception("Błąd podczas pobierania danych trasy"));
                }
            }

            @Override
            public void onFailure(Call<Route> call, Throwable t) {
                routeDataResponse.onFailure(t);
            }
        });
    }

    public interface IRouteDataResponse {
        void onResponse(Route route);

        void onFailure(Throwable t);
    }
}


