package com.test.simpleweatherlogic.impl;

import android.os.Parcelable;

import com.test.simpleweatherapi.SimpleWeatherAPI;
import com.test.simpleweatherapi.model.City;
import com.test.simpleweatherlogic.CityViewModel;
import com.test.simpleweatherlogic.Reactive.RxRunner;
import com.test.simpleweatherlogic.ResultObservable;

import java.util.ArrayList;
import java.util.List;

public class CityViewModelImpl implements CityViewModel {

    private SimpleWeatherAPI simpleWeatherAPI;

    public CityViewModelImpl(SimpleWeatherAPI simpleWeatherAPI) {
        this.simpleWeatherAPI = simpleWeatherAPI;
    }

    @Override
    public ResultObservable<List<City>> fetchCity(final String keyWord) {
        final ResultObservable<List<City>> resultObservable = new ResultObservable<>();
        new RxRunner<List<City>>().run(new RxRunner.RxRunnerExecutor<List<City>>() {
            @Override
            public List<City> execute() {
                return simpleWeatherAPI.fetchCity(keyWord);
            }
        }, resultObservable);
        return resultObservable;
    }

    @Override
    public ResultObservable<List<City>> fetchCity(final List<String> keyWords) {
        final ResultObservable<List<City>> resultObservable = new ResultObservable<>();
        new RxRunner<List<City>>().run(new RxRunner.RxRunnerExecutor<List<City>>() {
            @Override
            public List<City> execute() {
                List<City> cityList = new ArrayList<>();
                for(String keyWord : keyWords) {
                    List<City> cities = simpleWeatherAPI.fetchCity(keyWord);
                    cityList.addAll(cities);
                }
                return cityList;
            }
        }, resultObservable);
        return resultObservable;
    }

    @Override
    public ResultObservable<List<City>> fetchCity(final double lat, final double lon) {
        final ResultObservable<List<City>> resultObservable = new ResultObservable<>();
        new RxRunner<List<City>>().run(new RxRunner.RxRunnerExecutor<List<City>>() {
            @Override
            public List<City> execute() {
                return simpleWeatherAPI.fetchCity(lat, lon);
            }
        }, resultObservable);
        return resultObservable;
    }

    @Override
    public void addCity(final City city) {
        new RxRunner<City>().run(new RxRunner.RxRunnerExecutor<City>() {
            @Override
            public City execute() {
                simpleWeatherAPI.addCity(city);
                return null;
            }
        }, null);
    }

    @Override
    public void deleteCity(final City city) {
        new RxRunner<City>().run(new RxRunner.RxRunnerExecutor<City>() {
            @Override
            public City execute() {
                simpleWeatherAPI.removeCity(city);
                return null;
            }
        }, null);
    }

    @Override
    public ResultObservable<List<City>> getAllCities() {
        final ResultObservable<List<City>> resultObservable = new ResultObservable<>();
        new RxRunner<List<City>>().run(new RxRunner.RxRunnerExecutor<List<City>>() {
            @Override
            public List<City> execute() {
                return simpleWeatherAPI.fetchAllCities();
            }
        }, resultObservable);
        return resultObservable;
    }
}
