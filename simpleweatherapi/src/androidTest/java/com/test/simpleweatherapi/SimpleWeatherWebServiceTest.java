package com.test.simpleweatherapi;

import com.test.simpleweatherapi.model.City;
import com.test.simpleweatherapi.webservice.OpenWeatherMapService;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleWeatherWebServiceTest {
    private OpenWeatherMapService openWeatherMapService;

    @Before
    public void init() {
        openWeatherMapService = new OpenWeatherMapService();
    }

    @Test
    public void simpleTest() throws Exception {
        List<City> cityList = openWeatherMapService.fetchCity("auckland");
        assertTrue(cityList.size() == 2);
        City city1 = cityList.get(0);
        assertEquals(2193733, city1.getId());
        assertEquals("Auckland", city1.getName());
        assertEquals(-36.8535, city1.getLat(), 0.0);
        assertEquals(174.7656, city1.getLon(), 0.0);
        assertEquals("NZ", city1.getCounty());
        City city2 = cityList.get(1);
        assertEquals(5379533, city2.getId());
        assertEquals("Auckland", city2.getName());
        assertEquals(36.588, city2.getLat(), 0.0);
        assertEquals(-119.1068, city2.getLon(), 0.0);
        assertEquals("US", city2.getCounty());
    }

}
