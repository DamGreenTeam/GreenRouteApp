package com.sanvalero.greenrouteapp.service;

import com.sanvalero.greenrouteapp.domain.Location;
import com.sanvalero.greenrouteapp.domain.Road;
import retrofit2.http.GET;
import rx.Observable;

import java.util.List;

/**
 * Creado por @ author: Pedro Orós
 * el 08/04/2021
 */

public interface RoadApiService {

    @GET("/GreenRoute/roads")
    Observable<List<Road>> getAllRoads();

    @GET("/GreenRoute/locations")
    Observable<List<Location>> getAllLocations();
}
