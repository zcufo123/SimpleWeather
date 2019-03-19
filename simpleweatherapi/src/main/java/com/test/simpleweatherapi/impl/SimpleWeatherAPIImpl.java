package com.test.simpleweatherapi.impl;

import android.content.Context;

import com.test.simpleweatherapi.SimpleWeatherAPI;
import com.test.simpleweatherapi.db.CityDAO;
import com.test.simpleweatherapi.db.SimpleWeatherDatabase;
import com.test.simpleweatherapi.model.City;
import com.test.simpleweatherapi.model.WeatherInfo;
import com.test.simpleweatherapi.webservice.OpenWeatherMapService;

import java.io.IOException;
import java.util.List;

public class SimpleWeatherAPIImpl implements SimpleWeatherAPI {

    private SimpleWeatherDatabase simpleWeatherDatabase;
    private OpenWeatherMapService openWeatherMapService;

    public SimpleWeatherAPIImpl(Context context) {
        simpleWeatherDatabase = SimpleWeatherDatabase.getInstance(context);
        openWeatherMapService = new OpenWeatherMapService();
    }

    @Override
    public void addCity(City city) {
        CityDAO cityDao = simpleWeatherDatabase.getCityDao();
        cityDao.insert(city);
        simpleWeatherDatabase.close();
    }

    @Override
    public void removeCity(City city) {
        CityDAO cityDao = simpleWeatherDatabase.getCityDao();
        cityDao.delete(city);
        simpleWeatherDatabase.close();
    }

    @Override
    public List<City> fetchAllCities() {
        CityDAO cityDao = simpleWeatherDatabase.getCityDao();
        List<City> cities = cityDao.getAll();
        simpleWeatherDatabase.close();
        return cities;
    }

    @Override
    public List<City> fetchCity(String name) {
        try {
            return openWeatherMapService.fetchCity(name);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<City> fetchCity(double lat, double lon) {
        try {
            return openWeatherMapService.fetchCity(lat, lon);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public WeatherInfo fetchCurrentWeather(City city) {
        try {
            return openWeatherMapService.fetchCurrentWeather(city.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<WeatherInfo> fetchForecastWeather(City city) {
        try {
            return openWeatherMapService.fetchForecastWeather(city.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
