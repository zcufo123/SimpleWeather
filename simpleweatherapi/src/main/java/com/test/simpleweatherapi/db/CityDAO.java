package com.test.simpleweatherapi.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.test.simpleweatherapi.model.City;

import java.util.List;

@Dao
public interface CityDAO {
    @Insert
    void insert(City... city);

    @Delete
    void delete(City... cities);

    @Update
    void update(City... cities);

    @Query("SELECT * FROM Cities")
    List<City> getAll();
}
