package com.test.simpleweatherapi.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.test.simpleweatherapi.model.City;

@Database(entities = {City.class}, version = 1)
public abstract class SimpleWeatherDatabase  extends RoomDatabase {

    private static final String DATABASE_NAME = "SimpleWeatherDatabase";
    private static SimpleWeatherDatabase instance = null;

    public abstract CityDAO getCityDao();

    public static SimpleWeatherDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SimpleWeatherDatabase.class, DATABASE_NAME).
                    allowMainThreadQueries().build();
        }
        return instance;
    }

    public static void destroyInstance() {
        if (instance.isOpen()) {
            instance.close();
        }
        instance = null;
    }
}
