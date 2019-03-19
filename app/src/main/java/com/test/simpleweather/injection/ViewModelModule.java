package com.test.simpleweather.injection;

import android.content.Context;

import com.test.simpleweatherapi.impl.SimpleWeatherAPIImpl;
import com.test.simpleweatherlogic.CityViewModel;
import com.test.simpleweatherlogic.WeatherViewModel;
import com.test.simpleweatherlogic.impl.CityViewModelImpl;
import com.test.simpleweatherlogic.impl.WeatherViewModelImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    Context context;

    public ViewModelModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public CityViewModel providesCityViewModel() {
        SimpleWeatherAPIImpl simpleWeatherAPI = new SimpleWeatherAPIImpl(context);
        CityViewModelImpl cityViewModel = new CityViewModelImpl(simpleWeatherAPI);
        return cityViewModel;
    }

    @Singleton
    @Provides
    public WeatherViewModel providesWeatherViewModel() {
        SimpleWeatherAPIImpl simpleWeatherAPI = new SimpleWeatherAPIImpl(context);
        WeatherViewModelImpl weatherViewModel = new WeatherViewModelImpl(simpleWeatherAPI);
        return weatherViewModel;
    }
}
