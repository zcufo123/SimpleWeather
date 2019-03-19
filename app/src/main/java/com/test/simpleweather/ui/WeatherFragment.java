package com.test.simpleweather.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.simpleweather.R;
import com.test.simpleweather.injection.DaggerViewModelComponent;
import com.test.simpleweather.injection.ViewModelComponent;
import com.test.simpleweather.injection.ViewModelModule;
import com.test.simpleweatherapi.model.City;
import com.test.simpleweatherapi.model.WeatherInfo;
import com.test.simpleweatherlogic.ResultObservable;
import com.test.simpleweatherlogic.ResultObserver;
import com.test.simpleweatherlogic.WeatherViewModel;

import java.util.List;

import javax.inject.Inject;

public class WeatherFragment extends Fragment {

    public static final String CITY = "city";

    @Inject
    WeatherViewModel weatherViewModel;

    private City city;

    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CityParcel cityParcel = getArguments().getParcelable(CITY);
        city = cityParcel.generateCity();

        ViewModelComponent viewModelComponent = DaggerViewModelComponent.builder()
                .viewModelModule(new ViewModelModule(getContext())).build();
        viewModelComponent.inject(this);

        final View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        TextView textView = rootView.findViewById(R.id.textView_city);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(generateCityString(city));

        ResultObservable<WeatherInfo> weatherInfoResultObservable = weatherViewModel.getCurrentWeather(city);
        weatherInfoResultObservable.setResultObserver(new ResultObserver<WeatherInfo>() {
            @Override
            public void notifyUpdate(WeatherInfo weatherInfo) {
                TextView textView = (TextView) rootView.findViewById(R.id.textView_currentWeather);
                textView.setMovementMethod(new ScrollingMovementMethod());
                textView.setText(generateWeatherInfo(weatherInfo));
            }
        });

        ResultObservable<List<WeatherInfo>> weatherInforListResutObservable = weatherViewModel.getForecastWeather(city);
        weatherInforListResutObservable.setResultObserver(new ResultObserver<List<WeatherInfo>>() {
            @Override
            public void notifyUpdate(List<WeatherInfo> weatherInfos) {
                TextView textView = (TextView) rootView.findViewById(R.id.textView_forecastWeather);
                textView.setMovementMethod(new ScrollingMovementMethod());
                StringBuilder stringBuilder = new StringBuilder();
                for(WeatherInfo weatherInfo : weatherInfos) {
                    stringBuilder.append(generateWeatherInfo(weatherInfo));
                    stringBuilder.append(System.getProperty("line.separator"));
                    stringBuilder.append(System.getProperty("line.separator"));
                }
                textView.setText(stringBuilder.toString());
            }
        });


        return rootView;
    }

    private String generateCityString(City city) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id: " + city.getId());
        stringBuilder.append("\nname: " + city.getName());
        stringBuilder.append("\nlat: " + city.getLat());
        stringBuilder.append("\nlon: " + city.getLon());
        stringBuilder.append("\ncountry: " + city.getCounty());
        return stringBuilder.toString();
    }

    private String generateWeatherInfo(WeatherInfo weatherInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date: " + weatherInfo.getDate());
        stringBuilder.append("  temp: " + weatherInfo.getTemp());
        stringBuilder.append("  maxTemp: " + weatherInfo.getMaxTemp());
        stringBuilder.append("  minTemp: " + weatherInfo.getMinTemp());
        stringBuilder.append("  pressure: " + weatherInfo.getPressure());
        stringBuilder.append("  humidity: " + weatherInfo.getHumidity());
        stringBuilder.append("  weather: " + weatherInfo.getWeather());
        stringBuilder.append("  weatherDescription: " + weatherInfo.getWeatherDescription());
        stringBuilder.append("  icon: " + weatherInfo.getIcon());
        return stringBuilder.toString();
    }
}
