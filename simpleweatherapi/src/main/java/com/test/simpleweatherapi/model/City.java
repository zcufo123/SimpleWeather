package com.test.simpleweatherapi.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity(tableName = "Cities")
public class City {

    @PrimaryKey
    @Getter @Setter
    private long id;

    @ColumnInfo(name = "name")
    @Getter @Setter
    private String name;

    @ColumnInfo(name = "lat")
    @Getter @Setter
    private double lat;

    @ColumnInfo(name = "lon")
    @Getter @Setter
    private double lon;

    @ColumnInfo(name = "county")
    @Getter @Setter
    private String county;

    public City(long id, String name, double lat, double lon, String county) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.county = county;
    }
}
