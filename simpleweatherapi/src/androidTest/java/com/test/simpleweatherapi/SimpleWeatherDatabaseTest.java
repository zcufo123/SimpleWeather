package com.test.simpleweatherapi;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.test.simpleweatherapi.db.CityDAO;
import com.test.simpleweatherapi.db.SimpleWeatherDatabase;
import com.test.simpleweatherapi.model.City;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleWeatherDatabaseTest {

    private CityDAO mCityDao;
    private SimpleWeatherDatabase mDataBase;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDataBase = Room.inMemoryDatabaseBuilder(context, SimpleWeatherDatabase.class).build();
        mCityDao = mDataBase.getCityDao();
    }

    @After
    public void closeDb() throws IOException {
        mDataBase.close();
    }

    @Test
    public void simpleTest() throws Exception {
        City city1 = new City(2193733, "Auckland", -36.8535, 174.7656, "NZ");
        City city2 = new City(5379533, "Auckland", 36.588, -119.1068, "US");
        mCityDao.insert(city1, city2);
        List<City> cities = mCityDao.getAll();
        assertTrue(cities.size() == 2);
        City cityFromDatabase1 = cities.get(0);
        assertEquals(2193733, cityFromDatabase1.getId());
        assertEquals("Auckland", cityFromDatabase1.getName());
        assertEquals(-36.8535, cityFromDatabase1.getLat(), 0.0);
        assertEquals(174.7656, cityFromDatabase1.getLon(), 0.0);
        assertEquals("NZ", cityFromDatabase1.getCounty());
        City cityFromDatabase2 = cities.get(1);
        assertEquals(5379533, cityFromDatabase2.getId());
        assertEquals("Auckland", cityFromDatabase2.getName());
        assertEquals(36.588, cityFromDatabase2.getLat(), 0.0);
        assertEquals(-119.1068, cityFromDatabase2.getLon(), 0.0);
        assertEquals("US", cityFromDatabase2.getCounty());
    }

}
