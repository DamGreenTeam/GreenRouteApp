package com.sanvalero.greenrouteapp.service;

import com.sanvalero.greenrouteapp.domain.Location;
import com.sanvalero.greenrouteapp.domain.Road;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import java.util.List;

import static com.sanvalero.greenrouteapp.util.Constants.URL;

/**
 * Creado por @ author: Pedro Orós
 * el 08/04/2021
 */

public class RoadService {

    private RoadApiService api;

    public RoadService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        api = retrofit.create(RoadApiService.class);
    }

    public Observable<List<Road>> getAllRoads() {
        return api.getAllRoads();
    }

    public Observable<List<Location>> getAllLocations() {
        return api.getAllLocations();
    }
}
