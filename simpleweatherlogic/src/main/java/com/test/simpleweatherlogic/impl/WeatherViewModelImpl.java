package com.test.simpleweatherlogic.impl;

import com.test.simpleweatherapi.SimpleWeatherAPI;
import com.test.simpleweatherapi.model.City;
import com.test.simpleweatherapi.model.WeatherInfo;
import com.test.simpleweatherlogic.Reactive.RxRunner;
import com.test.simpleweatherlogic.ResultObservable;
import com.test.simpleweatherlogic.WeatherViewModel;

import java.util.List;

public class WeatherViewModelImpl implements WeatherViewModel {

    private SimpleWeatherAPI simpleWeatherAPI;

    public WeatherViewModelImpl(SimpleWeatherAPI simpleWeatherAPI) {
        this.simpleWeatherAPI = simpleWeatherAPI;
    }

    @Override
    public ResultObservable<WeatherInfo> getCurrentWeather(final City city) {
        final ResultObservable<WeatherInfo> resultObservable = new ResultObservable<>();
        new RxRunner<WeatherInfo>().run(new RxRunner.RxRunnerExecutor<WeatherInfo>() {
            @Override
            public WeatherInfo execute() {
                return simpleWeatherAPI.fetchCurrentWeather(city);
            }
        }, resultObservable);
        return resultObservable;
    }

    @Override
    public ResultObservable<List<WeatherInfo>> getForecastWeather(final City city) {
        final ResultObservable<List<WeatherInfo>> resultObservable = new ResultObservable<>();
        new RxRunner<List<WeatherInfo>>().run(new RxRunner.RxRunnerExecutor<List<WeatherInfo>>() {
            @Override
            public List<WeatherInfo> execute() {
                return simpleWeatherAPI.fetchForecastWeather(city);
            }
        }, resultObservable);
        return resultObservable;
    }
}
