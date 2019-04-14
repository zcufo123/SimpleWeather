package com.test.simpleweatherlogic;

import com.test.simpleweatherapi.model.City;

import java.util.List;

public interface CityViewModel {
    ResultObservable<List<City>> fetchCity(String keyWord);

    ResultObservable<List<City>> fetchCity(List<String> keyWord);

    ResultObservable<List<City>> fetchCity(double lat, double lon);

    void addCity(City city);

    void deleteCity(City city);

    ResultObservable<List<City>> getAllCities();
}
