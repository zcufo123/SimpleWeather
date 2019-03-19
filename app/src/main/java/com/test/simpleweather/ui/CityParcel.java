package com.test.simpleweather.ui;

import android.os.Parcel;
import android.os.Parcelable;

import com.test.simpleweatherapi.model.City;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CityParcel implements Parcelable {

    private long id;
    private String name;
    private double lat;
    private double lon;
    private String county;

    protected CityParcel(Parcel in) {
        id = in.readLong();
        name = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
        county = in.readString();
    }

    public static final Creator<CityParcel> CREATOR = new Creator<CityParcel>() {
        @Override
        public CityParcel createFromParcel(Parcel in) {
            return new CityParcel(in);
        }

        @Override
        public CityParcel[] newArray(int size) {
            return new CityParcel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(county);
    }

    public City generateCity() {
        City city = new City(id, name, lat, lon, county);
        return city;
    }
}
