package com.test.simpleweatherapi;

import android.content.Context;

import com.test.simpleweatherapi.impl.SimpleWeatherAPIImpl;
import com.test.simpleweatherapi.model.City;
import com.test.simpleweatherapi.model.WeatherInfo;

import java.util.List;

/**
 * Public interface to access all the functions that provided by the SimpleWeather.
 * <p>
 * This API is providing the functions including City Search and Weather Fetch.
 */
public interface SimpleWeatherAPI {

    class Builder {
        public static SimpleWeatherAPI build(Context context) {
            return new SimpleWeatherAPIImpl(context);
        }
    }

    /**
     * Add new City to favour.
     *
     * @param city New City need to be added.
     */
    void addCity(City city);

    /**
     * Remove new City from favour.
     *
     * @param city New city need to be removed.
     */
    void removeCity(City city);

    /**
     * Get all favourite cities.
     *
     * @return city list.
     */
    List<City> fetchAllCities();

    /**
     * Get city list from the key word.
     *
     * @param name the name of the city searched for.
     * @return city list.
     */
    List<City> fetchCity(String name);

    /**
     * Get city list from lat/lon
     *
     * @param lat the latitude of the city searched for.
     * @param lon the longitude of the city searched for.
     * @return city list.
     */
    List<City> fetchCity(double lat, double lon);

    /**
     * Fetch current weather information for a city.
     *
     * @param city City to be searched for.
     * @return WeatherInfo current weather information.
     */
    WeatherInfo fetchCurrentWeather(City city);

    /**
     * Fetch forecast weather information for a city.
     *
     * @param city City to be searched for.
     * @return List<WeatherInfo> forecast weather information.
     */
    List<WeatherInfo> fetchForecastWeather(City city);
}
