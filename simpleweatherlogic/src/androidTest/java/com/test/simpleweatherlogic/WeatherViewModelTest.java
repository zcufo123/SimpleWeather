package com.test.simpleweatherlogic;

import android.support.test.runner.AndroidJUnit4;

import com.test.simpleweatherapi.SimpleWeatherAPI;
import com.test.simpleweatherapi.model.City;
import com.test.simpleweatherapi.model.WeatherInfo;
import com.test.simpleweatherlogic.impl.WeatherViewModelImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;


import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class WeatherViewModelTest {

    private static final int TIMEOUT = 10000;

    private SimpleWeatherAPI mockSimpleWeatherAPI;
    private WeatherViewModel weatherViewModel;

    @Before
    public void init() {
        mockSimpleWeatherAPI = Mockito.mock(SimpleWeatherAPI.class);
        weatherViewModel = new WeatherViewModelImpl(mockSimpleWeatherAPI);
    }

    @After
    public void clear() {
        weatherViewModel = null;
        mockSimpleWeatherAPI = null;
    }

    @Test
    public void testCurrentWeather() throws Exception {
        ResultObserver<WeatherInfo> mockResultObserver = Mockito.mock(ResultObserver.class);
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setMinTemp(292.04);
        weatherInfo.setMaxTemp(293.15);
        weatherInfo.setTemp(292.42);
        weatherInfo.setPressure(1023);
        weatherInfo.setHumidity(100);
        weatherInfo.setWeather("Rain");
        weatherInfo.setWeatherDescription("proximity shower rain");
        weatherInfo.setIcon("09n");
        Mockito.when(mockSimpleWeatherAPI.fetchCurrentWeather(Mockito.any(City.class))).thenReturn(weatherInfo);

        City city = new City(2193733, "Auckland", -36.8535, 174.7656, "NZ");
        ResultObservable<WeatherInfo> resultObservable = weatherViewModel.getCurrentWeather(city);
        resultObservable.setResultObserver(mockResultObserver);

        ArgumentCaptor<WeatherInfo> argumentCaptorCaptor = ArgumentCaptor.forClass(WeatherInfo.class);
        Mockito.verify(mockResultObserver, Mockito.timeout(TIMEOUT)).notifyUpdate(argumentCaptorCaptor.capture());
        WeatherInfo returnWeatherInfo = argumentCaptorCaptor.getValue();
        assertEquals(292.04, returnWeatherInfo.getMinTemp(), 0.0);
        assertEquals(293.15, returnWeatherInfo.getMaxTemp(), 0.0);
        assertEquals(292.42, returnWeatherInfo.getTemp(), 0.0);
        assertEquals(1023, returnWeatherInfo.getPressure(), 0.0);
        assertEquals(100, returnWeatherInfo.getHumidity(), 0.0);
        assertEquals("Rain", returnWeatherInfo.getWeather());
        assertEquals("proximity shower rain", returnWeatherInfo.getWeatherDescription());
        assertEquals("09n", returnWeatherInfo.getIcon());
    }

}
