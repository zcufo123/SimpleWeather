package com.test.simpleweatherlogic;

import android.support.test.runner.AndroidJUnit4;

import com.test.simpleweatherapi.SimpleWeatherAPI;
import com.test.simpleweatherapi.model.City;
import com.test.simpleweatherlogic.impl.CityViewModelImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CityViewModelTest {

    private static final int TIMEOUT = 10000;

    private SimpleWeatherAPI mockSimpleWeatherAPI;
    private CityViewModel cityViewModel;

    @Before
    public void init() {
        mockSimpleWeatherAPI = Mockito.mock(SimpleWeatherAPI.class);
        cityViewModel = new CityViewModelImpl(mockSimpleWeatherAPI);
    }

    @After
    public void clear() {
        cityViewModel = null;
        mockSimpleWeatherAPI = null;
    }

    @Test
    public void testGetAllCities() throws Exception {
        ResultObserver<List<City>> mockResultObserver = Mockito.mock(ResultObserver.class);
        List<City> list = new ArrayList<>();
        City city1 = new City(2193733, "Auckland", -36.8535, 174.7656, "NZ");
        City city2 = new City(5379533, "Auckland", 36.588, -119.1068, "US");
        list.add(city1);
        list.add(city2);
        Mockito.when(mockSimpleWeatherAPI.fetchAllCities()).thenReturn(list);

        ResultObservable<List<City>> resultObservable = cityViewModel.getAllCities();
        resultObservable.setResultObserver(mockResultObserver);

        ArgumentCaptor<List> listArgumentCaptorCaptor = ArgumentCaptor.forClass(List.class);
        Mockito.verify(mockResultObserver, Mockito.timeout(TIMEOUT)).notifyUpdate(listArgumentCaptorCaptor.capture());
        List<City> returnList = listArgumentCaptorCaptor.getValue();
        assertTrue(returnList.size() == 2);
        City cityFromResult1 = returnList.get(0);
        assertEquals(2193733, cityFromResult1.getId());
        assertEquals("Auckland", cityFromResult1.getName());
        assertEquals(-36.8535, cityFromResult1.getLat(), 0.0);
        assertEquals(174.7656, cityFromResult1.getLon(), 0.0);
        assertEquals("NZ", cityFromResult1.getCounty());
        City cityFromResult2 = returnList.get(1);
        assertEquals(5379533, cityFromResult2.getId());
        assertEquals("Auckland", cityFromResult2.getName());
        assertEquals(36.588, cityFromResult2.getLat(), 0.0);
        assertEquals(-119.1068, cityFromResult2.getLon(), 0.0);
        assertEquals("US", cityFromResult2.getCounty());
    }

}
