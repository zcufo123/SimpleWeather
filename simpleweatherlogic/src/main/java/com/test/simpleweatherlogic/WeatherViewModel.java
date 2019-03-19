package com.test.simpleweatherlogic;

import com.test.simpleweatherapi.model.City;
import com.test.simpleweatherapi.model.WeatherInfo;

import java.util.List;

public interface WeatherViewModel {
    ResultObservable<WeatherInfo> getCurrentWeather(City city);

    ResultObservable<List<WeatherInfo>> getForecastWeather(City city);
}
