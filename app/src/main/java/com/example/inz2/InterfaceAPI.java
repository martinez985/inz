package com.example.inz2;

import com.example.inz2.Model.Route;
import com.example.inz2.Model.RouteHistory;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InterfaceAPI {
    @POST("/authenticate")
    Call<LoginResponse> checkLogin(@Body LoginBody loginBody);

    @GET("/getRouteData")
    Call<Route> getRouteData(@Header("Authorization") String authToken);

    @GET("/routeHistory")
    Call<List<RouteHistory>> getRouteHistory(@Header("Authorization") String authToken);

    @POST("/commissionLoaded")
    Call<Void> commissionLoaded(@Header("Authorization") String authToken, @Query("id") int id);

    @POST("/commissionUnloaded")
    Call<Void> commissionUnloaded(@Header("Authorization") String authToken, @Query("id") int id);

    @POST("/commissionLoaded")
    Call<Void> commissionUndo(@Header("Authorization") String authToken, @Query("id") int id);

    @GET("/endRoute")
    Call<Void> endRoute(@Header("Authorization") String authToken, @Query("id") int id);
}
